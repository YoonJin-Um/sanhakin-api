
package sanhakin.api.service;

import sanhakin.api.dto.*;
import sanhakin.api.entity.Sbc01RecordEntity;
import sanhakin.api.repository.Sbc01RecordRepository;
import sanhakin.api.util.ApiUtil;
import sanhakin.api.util.EncDecSupportUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Sbc01BatchService {

    private static final String URL="https://www.smes.go.kr/sanhakin/api/sbc01";
    private static final String KEY="1234567890123456";
    private static final String IV ="1234567890123456";
    private static final String USER_KEY="sanhakinSBC01";

    private final Sbc01RecordRepository repository;
    private final EncDecSupportUtil encDecSupportUtil=new EncDecSupportUtil();

    public Sbc01BatchService(Sbc01RecordRepository repository){
        this.repository=repository;
    }

    @Transactional
    public void execute(){

        String now=new SimpleDateFormat("yyyyMMddHH").format(new Date());
        String plainToken=USER_KEY+now;

        String encToken=encDecSupportUtil.encryptAesToHex(KEY,IV,plainToken);

        Map<String,Object> reqBody=new HashMap<>();
        Map<String,Object> data=new HashMap<>();
        data.put("token",encToken);
        reqBody.put("DATA",data);

        Map<String,String> headers=new HashMap<>();
        headers.put("IF_ID","IF-SBC-0001-01");

        Sbc01Response resp=ApiUtil.postJson(URL,reqBody,Sbc01Response.class,headers);

        if(resp==null || resp.getRECORD()==null)return;

        for(Sbc01RecordDto dto:resp.getRECORD()){
            if(dto.BIZR_NO==null)continue;
            Optional<Sbc01RecordEntity> existing=repository.findByBizrNo(dto.BIZR_NO);
            Sbc01RecordEntity entity;
            if(existing.isPresent()){
                entity=existing.get();
            }else{
                entity=new Sbc01RecordEntity();
            }
            entity.applyFromDto(dto);
            repository.save(entity);
        }
    }
}
