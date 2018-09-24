package server.connection.service.request.controller;

public class ForeignBankResponse {

    private boolean success = false;
    private String responseMessage = "Initial response message";

    public ForeignBankResponse() {

    }

    public ForeignBankResponse(boolean success, String message) {
        this.success = success;
        this.responseMessage = message;
    }


    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String msg) {
        this.responseMessage = msg;
    }

    public boolean isRequestSuccessful() {
        return success;
    }

    public void setRequestSuccessful(boolean successStatus) {
        this.success = successStatus;
    }
}

