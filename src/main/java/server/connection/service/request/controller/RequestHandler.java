package server.connection.service.request.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.connection.service.Request;
import server.connection.service.Response;
import server.data.base.controller.AppCore;
import server.data.base.controller.NoSuchRequestException;

public class RequestHandler {

    private final HttpHeaders headers = new HttpHeaders();
    private final AppCore appCore = new AppCore();
    private final Gson gson = new Gson();
    private ResponseEntity<String> responseEntity;

    public RequestHandler(Request request) {
        try {
            Response response = appCore.handleRequest(request);
            this.responseEntity = new ResponseEntity<>(gson.toJson(response), headers, HttpStatus.OK);
        } catch (NoSuchRequestException e) {
            e.printStackTrace();
            this.responseEntity = new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }
}
