package sber.jpadb;

public class Response {

	private String responseMessage = "Success";
	private boolean requestSuccessful = true;
	
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
}
