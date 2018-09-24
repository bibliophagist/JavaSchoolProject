package server.connection.service.request.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import server.connection.service.Request;
import server.connection.service.Response;
import server.data.base.controller.jpadb.AppCore;

import java.util.HashMap;

public class ForeignBank {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForeignBank.class);
    private final ClientHttpRequestFactory requestFactory = new
            HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
    private final RestTemplate restTemplate = new RestTemplate(requestFactory);
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final HashMap<String, String> listOfBanks = new HashMap<>();
    private final Gson gson = new GsonBuilder().create();

    public ForeignBank() {
        //TODO is it necessary or mistake?
        listOfBanks.put("10", "https://bankonline.azurewebsites.net/api/transfer/");
        listOfBanks.put("20", "https://onlinebank-group2.tk/api/transfer");
        listOfBanks.put("70", "http://localhost:8080/70");
        /*try (JsonReader reader = new JsonReader(new FileReader("src/main/resources/listOfBanks.json"))) {
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            listOfBanks = gson.fromJson(reader, type);
        } catch (IOException ex) {
            LOGGER.error("File src/main/resources/listOfBanks.json was not found", ex);
        }*/
    }

    public ResponseEntity<String> beforeSendingRequest(String bank, String account, Request request) {
        if (listOfBanks.containsKey(bank)) {
            Response response = new Response();
            LOGGER.debug("Sending request with id {} to DB controller", request.getRequestId());
            return AppCore.handleRequestFromAnotherBank(response, request, this, account, bank);
        } else {
            LOGGER.debug("Incorrect Bank to send money in Request with id {}", request.getRequestId());
            return new ResponseEntity<>("Incorrect Bank Id", httpHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> sendRequest(Request request, Response response, String account, String bank) {
        if (response.isRequestSuccessful()) {
            BodyForRequest bodyForRequest = new BodyForRequest(Long.toString(request.getMoneyToMove()),
                    request.getAccTitle(), account, "RUB", "From 70 with love.");
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(listOfBanks.get(bank))
                    .queryParam("body", gson.toJson(bodyForRequest));
            LOGGER.debug("Request with id {} sending to foreign bank with body {}", request.getRequestId(),
                    builder.build().encode().toUri());

            httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<?> entity = new HttpEntity<>(gson.toJson(bodyForRequest), httpHeaders);
            ResponseEntity<String> responseEntity = restTemplate.exchange(listOfBanks.get(bank),
                    HttpMethod.POST, entity, String.class);

            ForeignBankResponse foreignBankResponse = new ForeignBankResponse();
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                foreignBankResponse.setRequestSuccessful(true);
                foreignBankResponse.setResponseMessage("Money was transferred successfully");
            }
            LOGGER.info("Request with id {} was sent to another bank, response - {}",
                    request.getRequestId(), foreignBankResponse);
            return new ResponseEntity<>(gson.toJson(foreignBankResponse), httpHeaders, HttpStatus.OK);
        } else {
            LOGGER.info("Request with id {} was handled successfully: {}", request.getRequestId(), response.isRequestSuccessful());
            return new ResponseEntity<>(gson.toJson(response), httpHeaders, HttpStatus.OK);
        }
    }

    private ResponseEntity<String> responseEntityFromOurBank(UriComponentsBuilder builder) {
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        return responseEntity;
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
