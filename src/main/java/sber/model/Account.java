package sber.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account {

	//TODO 
	//1) change account structure (in java):
	//-- delete account_id field
	//-- make title a primary key field and unique
	
	private static final String BANK_ID = "70";
	
	@Id
	@Column(name = "title")
	private String title;

	@Column(name = "balance")
	private long balance;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	public Account() {}
	
	public Account(User user) {
		String title = generateUniqueTitle();
		setTitle(title);
		setBalance(0);
		setUser(user);
	}
	
	public Account(long balance, User user) {
		String title = generateUniqueTitle();
		setTitle(title);
		setBalance(balance);
		setUser(user);
	}	
	
	public Account(String title, User user) {
		this(title, 0, user);
	}
	
	public Account(String title, long balance, User user) {
		setTitle(title);
		setBalance(balance);
		setUser(user);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public long getBalance() {
		return balance;
	}

	public void setBalance (long balance) {
		this.balance = balance;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser (User user) {
		this.user = user;
	}
	
	private String generateUniqueTitle() {
		LocalDateTime ldt = LocalDateTime.now();
		String datetime = BANK_ID + Integer.toString(ldt.getYear()) + Integer.toString(ldt.getMonthValue()) 
						+ Integer.toString(ldt.getDayOfMonth()) + Integer.toString(ldt.getHour())
						+ Integer.toString(ldt.getMinute()) + Integer.toString(ldt.getSecond())
						+ Integer.toString(ldt.getNano());
		return datetime;
	}
	
	public static String getBankID() {
		return BANK_ID;
	}
}
