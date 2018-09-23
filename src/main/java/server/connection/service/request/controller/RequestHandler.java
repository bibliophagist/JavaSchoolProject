package server.connection.service.request.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.connection.service.Request;
import server.connection.service.Response;
import server.data.base.controller.jpadb.AppCore;

import java.lang.reflect.Type;
import java.util.List;

public class RequestHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestHandler.class);
    private final HttpHeaders headers = new HttpHeaders();
    private static final Type requestType = new TypeToken<List<Request>>() {
    }.getType();
    private final Gson gson = new GsonBuilder().create();

    public RequestHandler() {
    }

    public ResponseEntity<String> handleRequest(Request request) {
        LOGGER.debug("Sending request with id {} to DB controller", request.getRequestId());
        Response response = AppCore.handleRequest(request);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(gson.toJson(response), headers, HttpStatus.OK);
        LOGGER.info("Request with id {} was handled successfully: {}", request.getRequestId(), response.isRequestSuccessful());
        return responseEntity;
    }

    public ResponseEntity<String> errorResponseEntity(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> multipleRequestHandler(List<Request> listOfRequests) {
        LOGGER.debug("Making List of Requests from {}", gson.toJson(listOfRequests, requestType));
        Response response = AppCore.handleRequestList(listOfRequests);
        return new ResponseEntity<>(gson.toJson(response), headers, HttpStatus.OK);
    }
}
