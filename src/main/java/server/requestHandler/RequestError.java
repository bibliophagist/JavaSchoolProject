package server.requestHandler;

public enum RequestError {
	NO_ERROR, 
	USER_EXISTS, 
	NO_SUCH_NAME, 
	WRONG_PASS,
	LAST_ACCOUNT,
	USER_DOES_NOT_EXIST,
	DIFF_ERROR
}
