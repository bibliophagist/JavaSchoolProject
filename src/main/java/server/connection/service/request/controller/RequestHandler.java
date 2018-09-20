package server.connection.service.request.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.connection.service.Request;
import server.connection.service.Response;
import server.data.base.controller.AppCore;
import server.data.base.controller.NoSuchRequestException;

import java.util.List;

public class RequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private final HttpHeaders headers = new HttpHeaders();
    private final AppCore appCore = new AppCore();
    private final Gson gson = new Gson();
    private ResponseEntity<String> responseEntity = new ResponseEntity<>("Why can u see this?",
            headers, HttpStatus.BAD_REQUEST);

    public RequestHandler() {
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Credentials", "true");
    }

    public RequestHandler(Request request) {
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Credentials", "true");
        try {
            LOGGER.debug("Sending request with id {} to DB controller", request.getRequestId());
            Response response = appCore.handleRequest(request);
            this.responseEntity = new ResponseEntity<>(gson.toJson(response), headers, HttpStatus.OK);
            LOGGER.info("Request with id {} was handled successfully: {}", request.getRequestId(), response.isRequestSuccessful());
        } catch (NoSuchRequestException e) {
            LOGGER.error("Error while signing in", e);
            this.responseEntity = new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }

    public ResponseEntity<String> multipleRequestHandler(String jsonStringOfRequests) {
        LOGGER.debug("Making List of Requests from Json {}", jsonStringOfRequests);
        List<Request> requests = gson.fromJson(jsonStringOfRequests, new TypeToken<List<Request>>() {
        }.getType());
        //TODO remake responseEntity
        return responseEntity;
    }
}
