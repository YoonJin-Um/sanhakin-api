
package sanhakin.api.controller;

import sanhakin.api.dto.Sbc01RequestBody;
import sanhakin.api.dto.Sbc01Response;
import sanhakin.api.service.Sbc01BatchService;
import sanhakin.api.util.ApiUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Sbc01Controller {

    private static final String URL="https://www.smes.go.kr/sanhakin/api/sbc01";

    private final Sbc01BatchService batchService;

    public Sbc01Controller(Sbc01BatchService batchService){
        this.batchService=batchService;
    }

    @PostMapping("/api/sbc01/proxy")
    public Sbc01Response proxyCall(@RequestBody Sbc01RequestBody body){

        Map<String,String> headers=new HashMap<>();
        headers.put("IF_ID","IF-SBC-0001-01");

        return ApiUtil.postJson(URL,body,Sbc01Response.class,headers);
    }

    @GetMapping("/api/sbc01/run-batch")
    public String runBatch(){
        batchService.execute();
        return "Batch executed.";
    }
}
