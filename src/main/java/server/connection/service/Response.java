package sber.jpadb;

import java.util.List;

import sber.model.Account;

public class Response {

	private String responseMessage = "Initial message";
	private boolean requestSuccessful = false;
	private String userID;
	private List<Account> accList;
	
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
	
	public List<Account> getAcclist() {
		return accList;
	}
	
	public void setAccList(List<Account> accList) {
		this.accList = accList;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
}
