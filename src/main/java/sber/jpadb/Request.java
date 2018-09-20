package sber.jpadb;

public class Request {
	
	private final String userID;
	private final RequestType reqType;
	private String username;
	private String password;
	private String accTitle;
	private long moneyToMove = 0;
	
	public Request(RequestType reqType, String userID, String username, String password, String accTitle, long money) {
		this.reqType = reqType;
		this.userID = userID;
		this.username = username;
		this.password = password;
		setAccTitle(accTitle);
		setMoneyToMove(money);
	}
	
	public Request(RequestType reqType, String userID, String username, String password, String accTitle) {
		this(reqType, userID, username, password, accTitle, 0);
	}
	
	public Request(RequestType reqType, String userID, String username, String password, long money) {
		this(reqType, userID, username, password, null, money);
	}
	
	public Request(RequestType reqType, String userID, String username, String password) {
		this(reqType, userID, username, password, null, 0);
	}
	
	public Request(RequestType reqType, String userID, String accTitle, long money) {
		this(reqType, userID, null, null, accTitle, money);
	}
	
	public Request(RequestType reqType, String userID, String accTitle) {
		this(reqType, userID, null, null, accTitle, 0);
	}
	
	public Request(RequestType reqType, String userID, long money) {
		this(reqType, userID, null, null, null, money);
	}
	
	public Request(RequestType reqType, String userID) {
		this(reqType, userID, null, null, null, 0);
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
	
	public long getMoneyToMove() {
		return moneyToMove;
	}
	
	public void setMoneyToMove(long money) {
		if(money < 0) {
			throw new IllegalArgumentException("The amount of money can't be a negative number");
		} else {
			this.moneyToMove = money;
		}
	}	
	
	public String getUserID() {
		return userID;
	}
}
