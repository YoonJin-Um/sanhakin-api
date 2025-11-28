
package sanhakin.api.util;

import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class ApiUtil {

    private static final RestTemplate restTemplate = new RestTemplate();

    public static <T> T postJson(String url, Object requestObj, Class<T> responseType){
        return postJson(url,requestObj,responseType,null);
    }

    public static <T> T postJson(String url,Object req,Class<T>resType,Map<String,String>headersMap){

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if(headersMap!=null){
            headersMap.forEach(headers::add);
        }

        HttpEntity<Object> entity=new HttpEntity<>(req,headers);

        try{
            ResponseEntity<T> response=restTemplate.exchange(url,HttpMethod.POST,entity,resType);
            return response.getBody();
        }catch(HttpStatusCodeException e){
            System.err.println("[API ERROR] STATUS: "+e.getStatusCode());
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }
}
