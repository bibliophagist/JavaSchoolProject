package server.connection.service.request.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.connection.service.Request;
import server.connection.service.RequestType;

import java.util.Objects;

@Controller
@CrossOrigin
public class BankService {
    private final String bankName = "";
    private final Gson gson = new Gson();
    private ForeignBankRequest foreignBankRequest = new ForeignBankRequest();

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
        if (Objects.equals(bank, bankName)) {
            RequestHandler requestHandler = new RequestHandler();
            String stringBuilder = gson.toJson(new Request(RequestType.INCREASE_BALANCE, user, account,
                    moneyAmount, password)) +
                    gson.toJson(new Request(RequestType.DECREASE_BALANCE, login,
                            account, moneyAmount, password));
            return requestHandler.multipleRequestHandler(stringBuilder);
        } else {
            return foreignBankRequest.sendRequest(bank, user, moneyAmount);
        }
    }

    @RequestMapping(
            path = bankName,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> moneyTransfer(@RequestParam("user") String user,
                                                @RequestParam("account") String account,
                                                @RequestParam("moneyAmount") String moneyAmount,
                                                @RequestParam("login") String login,
                                                @RequestParam("password") String password) {
        //TODO talk with other teams
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
