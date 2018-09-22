package server.connection.service.request.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import server.connection.service.Request;
import server.connection.service.Response;
import server.data.base.controller.jpadb.AppCore;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ForeignBankRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForeignBankRequest.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private HashMap<String, String> listOfBanks;
    private final Gson gson = new GsonBuilder().create();

    public ForeignBankRequest() {
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("x-api-key", "hello");
        httpHeaders.add("content-type", "application/json;charset=UTF-8");
        try (JsonReader reader = new JsonReader(new FileReader("src/main/resources/listOfBanks.json"))) {
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            listOfBanks = gson.fromJson(reader, type);
        } catch (IOException ex) {
            LOGGER.error("File src/main/resources/listOfBanks.json was not found", ex);
        }
    }

    public ResponseEntity<String> sendRequest(String bank, String withdrawAccount, Request request) {
        if (listOfBanks.containsKey(bank)) {
            Response response = AppCore.handleRequest(request);
            BodyForRequest bodyForRequest = new BodyForRequest(Long.toString(request.getMoneyToMove()), withdrawAccount,
                    request.getAccTitle(), "RUB", "From 70 with love.");
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(listOfBanks.get(bank))
                    .query(gson.toJson(bodyForRequest));
            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST,
                    entity,
                    String.class);
            //TODO rework
            return responseEntity;
        } else {
            LOGGER.debug("Incorrect Bank to send money in Request with id {}", request.getRequestId());
            return new ResponseEntity<>("Incorrect Bank Id", httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }
}
