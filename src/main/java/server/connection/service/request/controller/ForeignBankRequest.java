package server.connection.service.request.controller;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;

public class ForeignBankRequest {
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final HashMap<String, String> listOfBanks = new HashMap<>();

    public ForeignBankRequest() {
        httpHeaders.add("Access-Control-Allow-Origin", "*");
    }

    public ResponseEntity<String> sendRequest(String bank, String user, String moneyAmount) {
        if (listOfBanks.containsKey(bank)) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(listOfBanks.get(bank)).queryParam("user", user).
                    queryParam("moneyAmount", moneyAmount);
            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST,
                    entity,
                    String.class);
        }
        //TODO remake this
        return  new ResponseEntity<>("This is not working", httpHeaders, HttpStatus.BAD_REQUEST);
    }
}
