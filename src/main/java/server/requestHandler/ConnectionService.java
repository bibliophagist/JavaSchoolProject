package server.requestHandler;

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
            path = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(@RequestParam("login") String login,
                                           @RequestParam("password") String password) {
        return requestResponse(new Request(RequestType.REGISTER, login, password));
    }

    private final ResponseEntity<String> requestResponse (Request request){
        try {
            appCore.handleRequest(request);
            return new ResponseEntity<>(request.getReqMessage(), headers, HttpStatus.OK);
        } catch (NoSuchRequestException e) {
            e.printStackTrace();
            return new ResponseEntity<>(request.getReqMessage(), headers, HttpStatus.BAD_REQUEST);
        }
    }
}
