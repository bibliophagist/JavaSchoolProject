package server.connection.service.request.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ForeignBankRequest {
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders httpHeaders = new HttpHeaders();

    public ForeignBankRequest() {
        httpHeaders.add("Access-Control-Allow-Origin", "*");
    }

    public void sendRequest(String uri, long account_id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("Account_id",account_id );
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
    }
}
