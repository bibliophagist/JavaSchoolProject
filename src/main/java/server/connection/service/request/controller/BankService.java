package server.connection.service.request.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.connection.service.Request;
import server.connection.service.RequestType;
import server.connection.service.Requests;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@CrossOrigin
public class BankService {
    //TODO Нужно ли статики?
    private static final Logger LOGGER = LoggerFactory.getLogger(BankService.class);
    private static final Type requestType = new TypeToken<List<Request>>() {
    }.getType();
    private static final RequestHandler requestHandler = new RequestHandler();
    private static final String ourBankName = "70";
    private static final Gson gson = new GsonBuilder().create();
    private static final ForeignBankRequest foreignBankRequest = new ForeignBankRequest();

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
            Request requestInc = new Request(RequestType.ADD_FUNDS, user, account,
                    Long.decode(moneyAmount));
            LOGGER.debug("Request for {} from user {} with id {}", RequestType.ADD_FUNDS, login,
                    requestInc.getRequestId());
            Request requestDec = new Request(RequestType.REMOVE_FUNDS, login, null, password,
                    account, Long.decode(moneyAmount));
            LOGGER.debug("Request for {} from user {} with id {}", RequestType.REMOVE_FUNDS, login,
                    requestDec.getRequestId());
            List<Request> requests = new ArrayList<>();
            requests.add(requestInc);
            requests.add(requestDec);
            return requestHandler.multipleRequestHandler(requests);
        } else {
            Request request = new Request(RequestType.FOREIGN_BANK, login, null, password, account,
                    Long.decode(moneyAmount));
            LOGGER.debug("Request for {} from user {} with id {}", RequestType.FOREIGN_BANK, login,
                    request.getRequestId());
            return foreignBankRequest.sendRequest(bankToSend, user, request);
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
        Request increaseBalanceRequest = new Request(RequestType.ADD_FUNDS, null,
                requestBody.getToAccount(), requestBody.getAmount());
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

    @RequestMapping(
            path = "handleXml",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handleXml(@RequestParam("login") String login, @RequestParam("file") File file) {
        try {
            LOGGER.info("Request with xml file was received from user {}", login);
            JAXBContext context = JAXBContext.newInstance(Request.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Requests requests = (Requests) unmarshaller.unmarshal(file);
            return requestHandler.multipleRequestHandler(requests.getRequests());
        } catch (JAXBException ex) {
            LOGGER.debug("Request with xml file was received, but file wasn't open", ex);
            return requestHandler.errorResponseEntity(ex);
        }
    }

    @RequestMapping(
            path = "handleJson",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handleJson(@RequestParam("login") String login, @RequestParam("file") File file) {
        try {
            LOGGER.info("Request with json file was received from user {}", login);
            JsonReader reader = new JsonReader(new FileReader(file));
            List<Request> requests = gson.fromJson(reader, requestType);
            return requestHandler.multipleRequestHandler(requests);
        } catch (FileNotFoundException ex) {
            LOGGER.debug("Request with json file was received, but file wasn't open", ex);
            return requestHandler.errorResponseEntity(ex);
        }

    }
}
