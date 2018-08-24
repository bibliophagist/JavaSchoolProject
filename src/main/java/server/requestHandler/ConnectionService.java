package server.requestHandler;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.dataBaseController.AppCore;
import server.dataBaseController.NoSuchRequestException;

@Controller
@CrossOrigin
public class ConnectionService {

    private static final HttpHeaders headers = new HttpHeaders();
    private final AppCore appCore = new AppCore();
    private final Gson gson=new Gson();

    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("login") String login,
                                        @RequestParam("password") String password) {
        return requestResponse(new Request(RequestType.LOGIN, login, password));
    }

    @RequestMapping(
            path = "moneyTransfer",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> moneyTransfer(@RequestParam("account") String account,
                                                @RequestParam("moneyAmount") String moneyAmount,
                                                @RequestParam("login") String login,
                                                @RequestParam("password") String password) {
        //TODO talk and make moneyTransfer
        return requestResponse(new Request(RequestType.REGISTER, login, password));
    }

    @RequestMapping(
            path = "checkBalance",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> checkBalance(@RequestParam("login") String login) {
        //TODO remove null
        return requestResponse(new Request(RequestType.CHECK_BALANCE, login, null));
    }

    @RequestMapping(
            path = "deleteAccount",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeAcc(@RequestParam("account") String account,
                                            @RequestParam("password") String password) {
        //TODO account is not a login!
        return requestResponse(new Request(RequestType.REMOVE_ACC, account, password));
    }

    @RequestMapping(
            path = "createAccount",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> createAcc(@RequestParam("login") String login) {
        //TODO remove null
        return requestResponse(new Request(RequestType.CREATE_ACC, login, null));
    }

    @RequestMapping(
            path = "deleteUser",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> removeUSer(@RequestParam("login") String login,
                                             @RequestParam("password") String password) {
        //TODO remove null
        return requestResponse(new Request(RequestType.REMOVE_USER, login, password));
    }

    @RequestMapping(
            path = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(@RequestParam("login") String login,
                                           @RequestParam("password") String password) {
        return requestResponse(new Request(RequestType.REGISTER, login, password));
    }

    private ResponseEntity<String> requestResponse(Request request) {
        try {
            appCore.handleRequest(request);
            return new ResponseEntity<>(gson.toJson(request), headers, HttpStatus.OK);
        } catch (NoSuchRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}
