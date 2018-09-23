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

public class ForeignBank {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForeignBank.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private HashMap<String, String> listOfBanks;
    private final Gson gson = new GsonBuilder().create();

    public ForeignBank() {
        //TODO is it necessary or mistake?
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("x-api-key", "hello");
        httpHeaders.add("content-type", "application/json;charset=UTF-8");
        /*try (JsonReader reader = new JsonReader(new FileReader("src/main/resources/listOfBanks.json"))) {
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            listOfBanks = gson.fromJson(reader, type);
        } catch (IOException ex) {
            LOGGER.error("File src/main/resources/listOfBanks.json was not found", ex);
        }*/
    }

    public ResponseEntity<String> beforeSendingRequest(String bank, String withdrawAccount, Request request) {
        if (listOfBanks.containsKey(bank)) {
            Response response = new Response();
            LOGGER.debug("Sending request with id {} to DB controller", request.getRequestId());
            return AppCore.handleRequestFromAnotherBank(response, request);
        } else {
            LOGGER.debug("Incorrect Bank to send money in Request with id {}", request.getRequestId());
            return new ResponseEntity<>("Incorrect Bank Id", httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> sendRequest(Request request, Response response, String withdrawAccount, String bank) {
        if (response.isRequestSuccessful()) {
            BodyForRequest bodyForRequest = new BodyForRequest(Long.toString(request.getMoneyToMove()), withdrawAccount,
                    request.getAccTitle(), "RUB", "From 70 with love.");
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(listOfBanks.get(bank))
                    .query(gson.toJson(bodyForRequest));
            HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(builder.build().encode().toUri(),
                    HttpMethod.POST,
                    entity,
                    String.class);
            ForeignBankResponse foreignBankResponse = gson.fromJson(responseEntity.getBody(), ForeignBankResponse.class);
            LOGGER.info("Request with id {} was sent to another bank, response - successful: {}, message {} ",
                    request.getRequestId(), foreignBankResponse.isRequestSuccessful(), response.getResponseMessage());
            AppCore.setWhiteFlag(foreignBankResponse.isRequestSuccessful());
            return new ResponseEntity<>(gson.toJson(foreignBankResponse), httpHeaders, HttpStatus.OK);
        } else {
            LOGGER.info("Request with id {} was handled successfully: {}", request.getRequestId(), response.isRequestSuccessful());
            return new ResponseEntity<>(gson.toJson(response), httpHeaders, HttpStatus.OK);
        }
    }


    public ResponseEntity<String> handleRequest(Request request) {
        LOGGER.debug("Sending request with id {} to DB controller", request.getRequestId());
        Response response = AppCore.handleRequest(request);
        ForeignBankResponse foreignBankResponse = new ForeignBankResponse(response.isRequestSuccessful(), response.getResponseMessage());
        ResponseEntity<String> responseEntity = new ResponseEntity<>(gson.toJson(foreignBankResponse), httpHeaders, HttpStatus.OK);
        LOGGER.info("Request with id {} was handled successfully: {}", request.getRequestId(), response.isRequestSuccessful());
        return responseEntity;
    }
}
