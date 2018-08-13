package Server.RequestHandler;

import Server.DataBaseController.CoreDB;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
public class ConnectionService {

    private static final HttpHeaders headers = new HttpHeaders();
    private final CoreDB coreDB = new CoreDB();

    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestParam("login") String login,
                                        @RequestParam("password") String password) {
        Request request = new Request(RequestType.login, login, password);
        return new ResponseEntity<>(request.getReqMessage(), headers, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(
            path = "register",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> register(@RequestParam("login") String login,
                                           @RequestParam("password") String password) {
        Request request = new Request(RequestType.login, login, password);

        return new ResponseEntity<>(request.getReqMessage(), headers, HttpStatus.CREATED);
    }

}
