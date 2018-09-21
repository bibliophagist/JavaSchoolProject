package server.connection.service.request.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import server.connection.service.Request;
import server.connection.service.Response;
import server.data.base.controller.jpadb.AppCore;

import java.util.HashMap;

public class ForeignBankRequest {
    public static final Logger LOGGER = LoggerFactory.getLogger(ForeignBankRequest.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final HashMap<String, String> listOfBanks = new HashMap<>();
    private final Gson gson = new GsonBuilder().create();

    public ForeignBankRequest() {
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("x-api-key", "hello");
        httpHeaders.add("content-type", "application/json;charset=UTF-8");
    }

    public ResponseEntity<String> sendRequest(String bank, String userNameToTransfer, Request request) {
        if (listOfBanks.containsKey(bank)) {
            Response response = AppCore.handleRequest(request);
            RequestBody requestBody = new RequestBody(Long.toString(request.getRequestId()),
                    Long.toString(request.getMoneyToMove()), request.getUsername(), userNameToTransfer,
                    "RUB", response.getResponseMessage());
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(listOfBanks.get(bank))
                    .queryParam("body", gson.toJson(requestBody));
            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST,
                    entity,
                    String.class);
            //TODO rework
            return responseEntity;
        } else {
            LOGGER.info("Incorrect Bank to send money in Request with id {}", request.getRequestId());
            return new ResponseEntity<>("Incorrect Bank Id", httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    public void sendResponse(String bank, ResponseEntity<String> responseEntity) {

    }
}
