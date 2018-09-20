package server.connection.service.request.controller;

public class RequestBody {
    private final String id;

    private final String amount;
    private final String fromAccount;
    private final String toAccount;
    private final String currency;
    private final String comment;

    public RequestBody(String id, String amount, String fromAccount, String toAccount, String currency, String comment) {
        this.id = id;
        this.amount = amount;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.currency = currency;
        this.comment = comment;
    }

    public String getAmount() {
        return amount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public String getFromAccount() {
        return fromAccount;
    }
}
