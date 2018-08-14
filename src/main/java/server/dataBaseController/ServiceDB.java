package server.dataBaseController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ServiceDB {
	
	public static boolean checkUser(Connection conn, String username) throws SQLException {
		/*
		try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(String.format("select * from users a where a.user_name='%s'", username));) {
			return rs.next();
		}
		*/
		boolean res = false;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String query = String.format("select * from users a where a.user_name='%s'", username);
			rs = stmt.executeQuery(query);
			res = rs.next();
		} finally {
			if(stmt != null) {stmt.close();}
			if(rs != null) {rs.close();}
		}
		return res;
	}
	
	public static boolean checkPass(Connection conn, String username, String password) throws SQLException  {
		boolean res = false;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String query = String.format("select * from users a where a.user_name='%s' and a.user_pass='%s'", username, password);
			rs = stmt.executeQuery(query);
			res = rs.next();
		} finally {
			if(stmt != null) {stmt.close();}
			if(rs != null) {rs.close();}
		}
		return res;
	}

	public static int getNumOfAccounts(Connection conn, String username) throws SQLException {
		int res = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String query = String.format("select count(account_id) from user_accounts where user_id=(select a.user_id from users a where a.user_name='%s')", username);
			rs = stmt.executeQuery(query);
		    while(rs.next()) {
		        res = rs.getInt(1);
		    }
			rs.close();
		} finally {
			if(stmt != null) {stmt.close();}
			if(rs != null) {rs.close();}
		}
		return res;
	}
	
	public static int getUserID(Connection conn, String username) throws SQLException {
		int res = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String query = String.format("select a.user_id from users a where a.user_name='%s'", username);
			rs = stmt.executeQuery(query);
		    while(rs.next()) {
		        res = rs.getInt(1);
		    }
			rs.close();
		} finally {
			if(stmt != null) {stmt.close();}
			if(rs != null) {rs.close();}
		}
		return res;
	}
	
	public static int addUser(Connection conn, String username, String password) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			//INSERT INTO users (user_name, user_pass) VALUES ( 'Tony' , 'IronCan');
			String query = String.format("insert into users (user_name, user_pass) values ( '%s' , '%s' )", username, password);
			stmt.executeUpdate(query);
		} finally {
			if(stmt != null) {stmt.close();}
		}
		return getUserID(conn, username);
	}
	
	public static int getAccID(Connection conn, String accTitle) throws SQLException {
		int res = 0;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			String query = String.format("select a.account_id from bank_accounts a where a.title='%s'", accTitle);
			rs = stmt.executeQuery(query);
		    while(rs.next()) {
		        res = rs.getInt(1);
		    }
		} finally {
			if(stmt != null) {stmt.close();}
			if(rs != null) {rs.close();}
		}
		return res;
	}
	
	/*
	public static void addAccount(Connection conn, String username) {
		addAccount(conn, username, 0);
	}
	*/
	
	//TODO rework with finally
	public static void addAccount(Connection conn, String username, int amount) throws SQLException {
		int accNumber = getNumOfAccounts(conn, username) + 1;
		String accTitle = String.format("%s_account_%s", username, accNumber);
		addAccount(conn, username, accTitle, amount);
	}
	
	public static void addAccount(Connection conn, String username, String accTitle, int amount) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			//INSERT INTO users (user_name, user_pass) VALUES ( 'Tony' , 'IronCan');
			String query1 = String.format("insert into bank_accounts (title, balance) values ( '%s' , %s)", accTitle, amount);
			stmt.executeUpdate(query1);
			int accID = getAccID(conn, accTitle);
			int userID = getUserID(conn, username);
			String query2 = String.format("insert into user_accounts (account_id, user_id) values ( %s , %s )", accID, userID);
			stmt.executeUpdate(query2);
		} finally {
			if(stmt != null) {stmt.close();}
		}
	}
	
	public static void removeAccount(Connection conn, String accTitle) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			int accID = getAccID(conn, accTitle);
			String query1 = String.format("delete from bank_accounts where title='%s'", accTitle);
			String query2 = String.format("delete from user_accounts where account_id=%s", accID);
			stmt.executeUpdate(query1);
			stmt.executeUpdate(query2);
		} finally {
			if(stmt != null) {stmt.close();}
		}
	}
	
	public static void removeUser(Connection conn, String username) throws SQLException {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			int userID = getUserID(conn, username);
			String query1 = String.format("delete from bank_accounts where account_id=(select b.account_id from users a, user_accounts b where a.user_name='%s' and a.user_id=b.user_id)", username);
		    String query3 = String.format("delete from user_accounts where user_id=%s ", userID);
		    String query4 = String.format("delete from users where user_id=%s ", userID);
		    stmt.executeUpdate(query1);
		    stmt.executeUpdate(query3);
		    stmt.executeUpdate(query4);
		} finally {
			if(stmt != null) {stmt.close();}
		}
	}
	
	public void listUserAccounts() {
		
	}
	
	public void addFunds() {
		
	}
	
	public void removeFunds() {
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		Statement stmt = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sberdata","senex", "sbersql");
		
		stmt = conn.createStatement();
		String sqlQuery = "select * from bank_accounts";
		
	    ResultSet rs = stmt.executeQuery(sqlQuery);
	    //STEP 5: Extract data from result set
	    while(rs.next()){
	       //Retrieve by column name
	       int id  = rs.getInt("account_id");
	       String title = rs.getString("title");
	       double balance = rs.getDouble("balance");
	
	       //Display values
	       System.out.print("Account ID: " + id);
	       System.out.print(", Title: " + title);
	       System.out.println(", Balance: " + balance);
	    }
	    rs.close();
	    
	    //System.out.println(checkUser(conn, "Tony"));
	    //System.out.println(checkUser(conn, "Bony"));
	    //System.out.println(checkPass(conn, "Tony", "IronCan"));
	    //System.out.println(checkPass(conn, "Tony", "IronMan"));
	    //System.out.println(getNumOfAccounts(conn, "BruceB"));
	    //System.out.println(getNumOfAccounts(conn, "BruceW"));
	    //System.out.println(getUserID(conn, "Tony"));
	    //System.out.println(getUserID(conn, "BruceB"));
	    
	    //addUser(conn, "Peter", "SpiderVan");
	    //addAccount(conn, "Peter");
	    removeUser(conn, "Peter");
	    
	    stmt.close();
		conn.close();
	}
}
