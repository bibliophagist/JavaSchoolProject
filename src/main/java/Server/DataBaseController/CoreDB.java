package Server.DataBaseController;

import Server.RequestHandler.Request;
import Server.RequestHandler.RequestError;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CoreDB {
	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/sberdata";
	private static final String DB_USERNAME = "senex";
	private static final String DB_PASSWORD = "sbersql";
	
	class NoSuchRequestException extends Exception {
		private static final long serialVersionUID = 1L;
		public NoSuchRequestException() {}
		public NoSuchRequestException(String message) {super(message);}
	 }
	
	public void handleRequest(Request req) throws NoSuchRequestException, ClassNotFoundException, SQLException {
		if(req == null) {
			throw new NullPointerException("Not a request");
		}
		Class.forName(JDBC_DRIVER);
		Connection conn = null;
		conn = DriverManager.getConnection(DB_URL,DB_USERNAME, DB_PASSWORD);
		switch(req.getReqType()) {
			case register: 
				register(conn, req);
				break;
			case login:
				login(conn, req);
				break;
			case checkBalance:
				checkBalance(conn, req);
				break;
			case createAcc:
				createAcc(conn, req);
				break;
			case transferFunds:
				transferFunds(conn, req);
				break;
			default:
				throw new NoSuchRequestException("No Such Request" + req.getReqType().toString()); 
		}
		conn.close();
	}

	private Request register(Connection conn, Request req) throws SQLException {
		if((req.getUsername().length()<5 && req.getUsername().length()>50) || (req.getPassword().length()<5 && req.getPassword().length()>50)) {
			req.setReqError(RequestError.invLength);
			req.setReqMessage("Username and password should be between 5 and 50 symbols.");
			return req;
		} else if(ServiceDB.checkUser(conn, req.getUsername())) {
			req.setReqError(RequestError.userExists);	
			req.setReqMessage("User with such nickname already exists.");
			return req;
		} else {
			return req;
		}
	}
	
	private Request login(Connection conn, Request req) throws SQLException {
		if((req.getUsername().length()<5 && req.getUsername().length()>50) || (req.getPassword().length()<5 && req.getPassword().length()>50)) {
			req.setReqError(RequestError.invLength);	
			req.setReqMessage("Username and password should be between 5 and 50 symbols.");
			return req;
		} else if(!ServiceDB.checkUser(conn, req.getUsername())) {
			req.setReqError(RequestError.noSuchName);	
			req.setReqMessage("There is no user with such nickname.");
			return req;
		} else if(!ServiceDB.checkPass(conn, req.getUsername(), req.getPassword())) {
			req.setReqError(RequestError.wrongPass);	
			req.setReqMessage("Wrong password.");
			return req;
		} else {
			return req;
		}
	}
		
	private void checkBalance(Connection conn, Request req) {
		// TODO Auto-generated method stub
		
	}
	
	private void createAcc(Connection conn, Request req) {
		// TODO Auto-generated method stub
		
	}

	private void transferFunds(Connection conn, Request req) {
		// TODO Auto-generated method stub
		
	}

	
	public static void main(String[] args) {
		
	}
}
