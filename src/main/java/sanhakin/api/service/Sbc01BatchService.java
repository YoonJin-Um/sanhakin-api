package sanhakin.api.service;

import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sanhakin.api.dto.Sbc01RecordDto;
import sanhakin.api.entity.Sbc01RecordEntity;
import sanhakin.api.repository.Sbc01RecordRepository;
import sanhakin.api.util.EncDecSupportUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class Sbc01BatchService {
    
    private static final String URL = "https://www.smes.go.kr/sanhakin/api/sbc01";
    
    @Value("${sanhakin.sbc01.encKey}")
    private String encKey;
    
    @Value("${sanhakin.sbc01.ivKey}")
    private String ivKey;
    
    @Value("${sanhakin.sbc01.userKey}")
    private String userKey;
    
    @Autowired
    private Sbc01RecordRepository repository;
    
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(Sbc01BatchService.class);
    
    @Transactional
    public void execute() {
    	
    	EncDecSupportUtil encDecUtil = new EncDecSupportUtil();
        
    	String now = new SimpleDateFormat("yyyyMMddHH").format(new Date());
        String plainToken = userKey + now;

        String encToken = null;
		try {
			encToken = encDecUtil.EncryptAesStringToHex(encKey, ivKey, plainToken);
			
		} catch (InvalidKeyException 
		        | NoSuchAlgorithmException
		        | NoSuchPaddingException
		        | InvalidAlgorithmParameterException
		        | IllegalBlockSizeException
		        | BadPaddingException e) {

		    logger.error("[AES 암호화 오류] token 암호화 실패. 원인: {}", e.getMessage(), e);
		    throw new RuntimeException("AES 암호화 중 오류가 발생했습니다.", e);
		}

        Map<String,Object> reqBody = new HashMap<>();
        Map<String,Object> data = new HashMap<>();
        reqBody.put("DATA", data);
        data.put("token", encToken);

        Map<String,String> headers = new HashMap<>();
        headers.put("IF_ID", "IF-SBC-0001-01");

        RestTemplate rest = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(headers!=null){ headers.forEach(httpHeaders::add); }

        HttpEntity<Object> httpEtt = new HttpEntity<>(reqBody, httpHeaders);
        Map<String, Object> resp;
        
        try{
            ResponseEntity<Map<String, Object>> response = rest.exchange(
                URL,
                HttpMethod.POST,
                httpEtt,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            resp = response.getBody();
            
        }catch(HttpStatusCodeException e){
        	logger.error("[API ERROR] STATUS: {}", e.getStatusCode());
            throw new RuntimeException(e.getResponseBodyAsString());
        }
        
        if(resp == null || resp.get("RECORD") == null) return;
        
        // 1) RECORD 파싱
        Object recordObj = resp.get("RECORD");
        if (!(recordObj instanceof List)) {
            return;
        }
        
        List<?> rawList = (List<?>) recordObj;
        ObjectMapper mapper = new ObjectMapper();
        
        // 2) DTO 변환 + null BIZR_NO 제거
        List<Sbc01RecordDto> records = rawList.stream()
            .map(item -> mapper.convertValue(item, Sbc01RecordDto.class))
            .filter(dto -> dto.BIZR_NO != null)
            .collect(Collectors.toList());

        if (records.isEmpty()) {
            return;
        }

        // 3) BizrNo 목록 distinct
        List<String> bizrNos = records.stream()
                .map(dto -> dto.BIZR_NO)
                .distinct()
                .collect(Collectors.toList());

        // 4) 기존 DB Data 조회 후 Map으로 변환
        List<Sbc01RecordEntity> existList =
                bizrNos.isEmpty() ? Collections.emptyList() : repository.findByBizrNoIn(bizrNos);

        Map<String, Sbc01RecordEntity> existEntityMap = new HashMap<>();
        for (Sbc01RecordEntity entity : existList) {
            existEntityMap.put(entity.getBizrNo(), entity);
        }
        
        // 5) Upsert 대상 구성
        List<Sbc01RecordEntity> toSaveList = new ArrayList<>();
        for (Sbc01RecordDto dto : records) {

            Sbc01RecordEntity entity = existEntityMap.get(dto.BIZR_NO);

            if (entity == null) {
                entity = new Sbc01RecordEntity();
            }

            entity.applyFromDto(dto);
            toSaveList.add(entity);
        }
        
        // ** 임시 테스트
//        List<Sbc01RecordEntity> limitedList = toSaveList.stream()
//                .limit(10)
//                .collect(Collectors.toList());
//
//        if (!limitedList.isEmpty()) {
//            repository.saveAll(limitedList);
//            entityManager.flush();
//            entityManager.clear();
//            logger.debug("============ 일괄 저장 the end =============");
//        }
        
        // 6) 일괄 저장
        if(!toSaveList.isEmpty()){
        	long start = System.currentTimeMillis(); // 시작 시각 기록
            repository.saveAll(toSaveList);
            entityManager.flush();
            entityManager.clear();
            long end = System.currentTimeMillis();   // 종료 시각 기록
            long elapsed = end - start;              // 경과 시간(ms)
            System.out.println("소요 시간: " + elapsed + " ms");
            logger.debug("============ 일괄 저장 the end =============");
        }
        
    }
}
