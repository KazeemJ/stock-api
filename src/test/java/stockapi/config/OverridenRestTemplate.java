package stockapi.config;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class OverridenRestTemplate extends RestTemplate {

    @Override
    protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException{

        return new MockClientHttpRequest(method, url){

            @Override
            protected ClientHttpResponse executeInternal() throws IOException{
                try{
                    MockClientHttpResponse clientHttpResponse = new MockClientHttpResponse(
                            getClass().getResourceAsStream("alphagoogle.json"), HttpStatus.OK
                    );
                    return clientHttpResponse;
                }catch (Exception e){
                    return new MockClientHttpResponse(e.toString().getBytes(StandardCharsets.UTF_8),HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        };
    }
}
