package server.connection.service;

public class Request {

    private final RequestType reqType;
    private RequestError reqError = RequestError.NO_ERROR;
    private final String username;
    private String password;
    private boolean success = true;
    private String accTitle;
    private int money = 0;
    private String reqMessage = "Success";

    public Request(RequestType reqType, String username) {
        this.reqType = reqType;
        this.username = username;
    }

    public Request(RequestType reqType, String username, String password) {
        this.reqType = reqType;
        this.username = username;
        this.password = password;
    }

    public Request(RequestType reqType, String username, String accTitle, String password) {
        this.reqType = reqType;
        this.username = username;
        this.password = password;
        this.accTitle=accTitle;
    }

    public Request(RequestType reqType, String username, String accTitle, String moneyAmount, String password) {
        this.reqType = reqType;
        this.username = username;
        this.password = password;
        this.accTitle=accTitle;
        this.money=Integer.decode(moneyAmount);
    }

    public boolean isSuccess() {
        return success;
    }

    public RequestError getReqError() {
        return reqError;
    }

    public RequestType getReqType() {
        return reqType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccTitle() {
        return accTitle;
    }

    public void setAccTitle(String accTitle) {
        this.accTitle = accTitle;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setReqError(RequestError reqError) {
        this.reqError = reqError;
    }

    public String getReqMessage() {
        return reqMessage;
    }

    public void setReqMessage(String msg) {
        this.reqMessage = msg;
    }

}
