package server.connection.service;

public class Response {

	private String responseMessage = "Initial message";
	private boolean requestSuccessful = false;
	private String userID;
	//private List<Account> accList;
	private String accListString;
	
	public Response() {
	}
	
	public Response(String msg, boolean successStatus) {
		this.responseMessage = msg;
		this.requestSuccessful = successStatus;
	}

	public String getResponseMessage() {
		return responseMessage;
	}
	
	public void setResponseMessage(String msg) {
		this.responseMessage = msg;
	}

	public boolean isRequestSuccessful() {
		return requestSuccessful;
	}
	
	public void setRequestSuccessful(boolean successStatus) {
		this.requestSuccessful = successStatus;
	}
	
	public String getAccListString() {
		return accListString;
	}
	
	public void setAccListString(String accListString) {
		this.accListString = accListString;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
}
