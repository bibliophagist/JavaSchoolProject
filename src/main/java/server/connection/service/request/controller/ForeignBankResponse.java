package server.connection.service.request.controller;

public class ForeignBankResponse {

    private boolean error = true;
    private String message = "Success";

    public ForeignBankResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }


    public String getResponseMessage() {
        return message;
    }

    public void setResponseMessage(String msg) {
        this.message = msg;
    }

    public boolean isRequestSuccessful() {
        return error;
    }

    public void setRequestSuccessful(boolean successStatus) {
        this.error = successStatus;
    }
}

