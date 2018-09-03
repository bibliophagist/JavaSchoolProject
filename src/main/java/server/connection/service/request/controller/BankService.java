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
public class BankService {

    @RequestMapping(
            path = "moneyTransfer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> moneyTransfer(@RequestParam("bank") String bank,
                                                @RequestParam("user") String user,
                                                @RequestParam("account") String account,
                                                @RequestParam("moneyAmount") String moneyAmount,
                                                @RequestParam("login") String login,
                                                @RequestParam("password") String password) {
        //TODO talk and make moneyTransfer
        String uri = "";
        long account_id = Long.getLong(account);

        return new RequestHandler(new Request(RequestType.DECREASE_BALANCE, login, password)).getResponseEntity();
    }

    @RequestMapping(
            path = "moneyTransfer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> moneyTransfer(@RequestParam("user") String user,
                                                @RequestParam("account") String account,
                                                @RequestParam("moneyAmount") String moneyAmount,
                                                @RequestParam("login") String login,
                                                @RequestParam("password") String password) {
        //TODO talk and make moneyTransfer and remove null
        Request requestForDecrease = new Request(RequestType.DECREASE_BALANCE, login, password);
        Request requestForIncrease = new Request(RequestType.INCREASE_BALANCE, null, null);
        return null;
    }

    @RequestMapping(
            path = "checkBalance",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> checkBalance(@RequestParam("login") String login) {
        return new RequestHandler(new Request(RequestType.CHECK_BALANCE, login)).getResponseEntity();
    }
}
