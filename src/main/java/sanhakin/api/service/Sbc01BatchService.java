
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
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import sanhakin.api.dto.Sbc01RecordDto;
import sanhakin.api.entity.Sbc01RecordEntity;
import sanhakin.api.repository.Sbc01RecordRepository;
import sanhakin.api.util.EncDecSupportUtil;

@Service
public class Sbc01BatchService {
    
    private static final String URL = "https://www.smes.go.kr/sanhakin/api/sbc01";
    
    @Value("${sanhakin.sbc01.key:1234567890123456}")
    private String key;
    
    @Value("${sanhakin.sbc01.iv:1234567890123456}")
    private String iv;
    
    @Value("${sanhakin.sbc01.user-key:sanhakinSBC01}")
    private String userKey;
    
    @Autowired
    private Sbc01RecordRepository repository;
    
    @Autowired
    private EncDecSupportUtil encDecSupportUtil;
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void execute(){

        String now = new SimpleDateFormat("yyyyMMddHH").format(new Date());
        String plainToken = userKey + now;

        String encToken = encDecSupportUtil.encryptAesToHex(key, iv, plainToken);

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

        HttpEntity<Object> entity = new HttpEntity<>(reqBody, httpHeaders);
        Map<String, Object> resp;
        try{
            ResponseEntity<Map<String, Object>> response = rest.exchange(
                URL,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            resp = response.getBody();
        }catch(HttpStatusCodeException e){
            System.err.println("[API ERROR] STATUS: "+e.getStatusCode());
            throw new RuntimeException(e.getResponseBodyAsString());
        }

        if(resp==null || resp.get("RECORD")==null) return;

        Object recordObj = resp.get("RECORD");
        if(!(recordObj instanceof List)) return;

        // Convert raw map list to typed DTOs without Sbc01Response wrapper
        ObjectMapper mapper = new ObjectMapper();
        List<Sbc01RecordDto> records = ((List<?>) recordObj).stream()
            .map(item -> mapper.convertValue(item, Sbc01RecordDto.class))
            .collect(Collectors.toList());

        if(records.isEmpty()) return;

        List<String> bizrNos = records.stream()
            .map(dto -> dto.BIZR_NO)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());

        // 이미 있는 사업자번호 목록조회
        List<Sbc01RecordEntity> existEntities = bizrNos.isEmpty() ? Collections.emptyList() : repository.findByBizrNoIn(bizrNos);
        Map<String, Sbc01RecordEntity> existBizrNo = new HashMap<>();
        
        for(Sbc01RecordEntity e: existEntities){
            existBizrNo.put(e.getBizrNo(), e);
        }
        // 새로운 데이터 저장대상 목록작성
        List<Sbc01RecordEntity> toSaveList = new ArrayList<>();
        for(Sbc01RecordDto dto: records){
            if(dto.BIZR_NO==null) continue;
            Sbc01RecordEntity entity = existBizrNo.getOrDefault(dto.BIZR_NO, new Sbc01RecordEntity());
            entity.applyFromDto(dto);
            toSaveList.add(entity);
        }

        if(!toSaveList.isEmpty()){
            repository.saveAll(toSaveList);
            entityManager.flush();
            entityManager.clear();
        }
    }
}
