package sber.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@Column(name="user_id")
	private String id;

	@Column(name="user_name")
	private String username;
	
	@Column(name="user_pass")
	private String password;
	
	@OneToMany(mappedBy="user")
	private List<Account> accounts;

	public User() {}
	
	public User(String id, String username, String password) {
		setId(id);
		setUserName(username);
		setPassword(password);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if(id.length() == 10) {
			this.id = id;
		} else {
			throw new IllegalArgumentException("UserID length must be 10 characters");
		}
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}
	
	public List<Account> getAccountList() {
		return accounts;		
	}
	
}
