package server.dataBaseController;

public class NoSuchRequestException extends Exception {
    private static final long serialVersionUID = 1L;
    public NoSuchRequestException() {}
    public NoSuchRequestException(String message) {super(message);}
}