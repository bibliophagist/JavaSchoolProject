package Server.DataBaseController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServiceDB {
	
	public static boolean checkUser(Connection conn, String username) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "select * from users a where a.user_name=" + "'" + username +"'";
		ResultSet rs = stmt.executeQuery(query);
		return rs.next();
	}
	
	public static boolean checkPass(Connection conn, String username, String password) throws SQLException {
		Statement stmt = conn.createStatement();
		String query = "select * from users a where a.user_name=" + "'" + username + "' user_pass=" + "'" + password + "'";
		ResultSet rs = stmt.executeQuery(query);
		return rs.next();
	}
	
	public void listUserAccounts() {
		
	}
	
	public void addUser() {
		
	}
	
	public void removeUser() {
		
	}
	
	public void addAccount() {
		
	}
	
	public void removeAccount() {
		
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
	    
	    System.out.println(checkUser(conn, null));
		conn.close();
	}
}
