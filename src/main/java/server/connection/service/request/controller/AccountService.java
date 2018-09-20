package server.connection.service.request.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.connection.service.Request;
import server.connection.service.RequestType;

@Controller
@CrossOrigin
public class AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("login") String login,
                                        @RequestParam("password") String password) {
        Request request = new Request(RequestType.LOGIN, login, password);
        LOGGER.info("Request for {} from user {} with id {}", RequestType.LOGIN, login, request.getRequestId());
        return new RequestHandler(request).getResponseEntity();
    }

    @RequestMapping(
            path = "deleteAccount",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeAcc(@RequestParam("login") String login,
                                            @RequestParam("account") String account,
                                            @RequestParam("password") String password) {
        Request request = new Request(RequestType.REMOVE_ACC, login, account, password);
        LOGGER.info("Request for {} from user {} with id {}", RequestType.REMOVE_ACC, login, request.getRequestId());
        return new RequestHandler(request).getResponseEntity();
    }

    @RequestMapping(
            path = "createAccount",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createAcc(@RequestParam("login") String login,
                                            @RequestParam("account") String account,
                                            @RequestParam("accountType") String accountType) {
        //TODO accountType
        Request request = new Request(RequestType.CREATE_ACC, login, account);
        LOGGER.info("Request for {} from user {} with id {}", RequestType.CREATE_ACC, login, request.getRequestId());
        return new RequestHandler(request).getResponseEntity();
    }

    @RequestMapping(
            path = "deleteUser",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeUSer(@RequestParam("login") String login,
                                             @RequestParam("password") String password) {
        Request request = new Request(RequestType.REMOVE_USER, login, password);
        LOGGER.info("Request for {} from user {} with id {}", RequestType.REMOVE_USER, login, request.getRequestId());
        return new RequestHandler(request).getResponseEntity();
    }

    @RequestMapping(
            path = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(@RequestParam("login") String login,
                                           @RequestParam("password") String password) {
        Request request = new Request(RequestType.REGISTER, login, password);
        LOGGER.info("Request for {} from user {} with id {}", RequestType.REGISTER, login, request.getRequestId());
        return new RequestHandler(request).getResponseEntity();
    }
}
