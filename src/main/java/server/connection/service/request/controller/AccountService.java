package server.connection.service.request.controller;

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


    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("login") String login,
                                        @RequestParam("password") String password) {
        return new RequestHandler(new Request(RequestType.LOGIN, login, password)).getResponseEntity();
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
        return new RequestHandler(new Request(RequestType.REMOVE_ACC, login, account, password)).getResponseEntity();
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
        return new RequestHandler(new Request(RequestType.CREATE_ACC, login, account)).getResponseEntity();
    }

    @RequestMapping(
            path = "deleteUser",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeUSer(@RequestParam("login") String login,
                                             @RequestParam("password") String password) {
        return new RequestHandler(new Request(RequestType.REMOVE_USER, login, password)).getResponseEntity();
    }

    @RequestMapping(
            path = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(@RequestParam("login") String login,
                                           @RequestParam("password") String password) {
        return new RequestHandler(new Request(RequestType.REGISTER, login, password)).getResponseEntity();
    }
}
