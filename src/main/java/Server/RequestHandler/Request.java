package Server.RequestHandler;

public class Request {

    private final RequestType reqType;
    private RequestError reqError = RequestError.noError;
    private final String username;
    private final String password;
    private String reqMessage = "testMessage";

    public Request(RequestType reqType, String username, String password) {
        this.reqType= reqType;
        this.username=username;
        this.password=password;
    }

    public void setReqError(RequestError reqError) {
        this.reqError = reqError;
    }

    public void setReqMessage(String reqMessage) {
        this.reqMessage = reqMessage;
    }

    public RequestType getReqType() {
        return reqType;
    }

    public RequestError getReqError() {
        return reqError;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getReqMessage() {
        return reqMessage;
    }

}
