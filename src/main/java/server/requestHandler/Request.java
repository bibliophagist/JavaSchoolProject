package server.requestHandler;

public class Request {
	
	private final RequestType reqType;
	private RequestError reqError = RequestError.NO_ERROR;
	private final String username;
	private final String password;
	private String accTitle;
	private int money = 0;
	private String reqMessage = "Success";
	
	public Request(RequestType reqType, String username, String password) {
		this.reqType = reqType;
		this.username = username;
		this.password = password;
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
