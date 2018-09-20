package server.connection.service.request.controller;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);
    private static final String ourBankName = "70";
    private final Gson gson = new Gson();
    private final ForeignBankRequest foreignBankRequest = new ForeignBankRequest();

    @RequestMapping(
            path = "moneyTransfer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> moneyTransfer(@RequestParam("bank") String bankToSend,
                                                @RequestParam("user") String user,
                                                @RequestParam("account") String account,
                                                @RequestParam("moneyAmount") String moneyAmount,
                                                @RequestParam("login") String login,
                                                @RequestParam("password") String password) {
        LOGGER.info("Requests for moneyTransfer from user {}  to {} in bank {}", login, user, bankToSend);
        if (Objects.equals(bankToSend, ourBankName)) {
            Request requestInc = new Request(RequestType.INCREASE_BALANCE, user, account,
                    moneyAmount);
            LOGGER.debug("Request for {} from user {} with id {}", RequestType.INCREASE_BALANCE, login,
                    requestInc.getRequestId());
            Request requestDec = new Request(RequestType.DECREASE_BALANCE, login,
                    account, moneyAmount, password);
            LOGGER.debug("Request for {} from user {} with id {}", RequestType.DECREASE_BALANCE, login,
                    requestDec.getRequestId());
            RequestHandler requestHandler = new RequestHandler();
            String stringOfRequests = gson.toJson(requestInc) +
                    gson.toJson(requestDec);
            return requestHandler.multipleRequestHandler(stringOfRequests);
        } else {
            Request request = new Request(RequestType.FOREIGN_BANK, login, account, moneyAmount, password, user);
            LOGGER.debug("Request for {} from user {} with id {}", RequestType.FOREIGN_BANK, login,
                    request.getRequestId());
            return foreignBankRequest.sendRequest(bankToSend, request);
        }
    }

    @RequestMapping(
            path = ourBankName,
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> moneyTransfer(@RequestParam("body") String body) {
        RequestBody requestBody = gson.fromJson(body, RequestBody.class);
        //TODO rework constructors
        Request getUserIdRequest = new Request(RequestType.GET_USER, null,
                requestBody.getToAccount(), requestBody.getAmount());
        Request increaseBalanceRequest = new Request(RequestType.INCREASE_BALANCE, getUserIdRequest.getUsername(),
                null, Integer.toString(getUserIdRequest.getMoney()));
        ResponseEntity<String> responseEntity = new RequestHandler(increaseBalanceRequest).getResponseEntity();
        //TODO change responseEntity for return to other banks
        return responseEntity;
    }

    @RequestMapping(
            path = "checkBalance",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> checkBalance(@RequestParam("login") String login) {
        Request request = new Request(RequestType.CHECK_BALANCE, login);
        LOGGER.info("Request for {} from user {} with id {}", RequestType.CHECK_BALANCE, login, request.getRequestId());
        return new RequestHandler(request).getResponseEntity();
    }
}
