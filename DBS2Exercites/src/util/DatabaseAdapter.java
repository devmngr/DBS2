package util;

import java.sql.SQLException;

public class DatabaseAdapter {

	
	private Adaptee adaptee;
	
	public DatabaseAdapter() throws ClassNotFoundException {
		adaptee = new Adaptee();
	}
	
	
	public void createAccount(double amount) throws SQLException {
		
		//String sql = "insert into bankaccount values (sqAcount.,"+amount+")";
		String sql = "insert into bankaccount values (ac_seq.nextval,"+amount+")";
		adaptee.query(sql);

		/*should return the account ID after inserted as a new account*/

	}
	public void insertAmount( int acountID,double amount) {
	
		//String sql = ""
		
	
	}
	public void widrawAmount(double amount) {

		
	}
	
	
	
	
	public double checkBalance(int acountID) {
		String sql = "select amount from bankaccount where id = "+acountID;
		
		return 0;
		/*should return balance on thatacount nr*/
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		DatabaseAdapter ad = new DatabaseAdapter();
		ad.createAccount(256);
	}
	

}
