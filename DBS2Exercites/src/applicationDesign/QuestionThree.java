package applicationDesign;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionThree extends QuestionRunner{

	public QuestionThree(Connection iconn) {

		this.conn = iconn;
	}

	public void runCase() throws SQLException {

		Statement stmtMother = conn.createStatement();
		Statement stmtChild = conn.createStatement();
		int noOfChildrens = 0;
		stmtMother.executeQuery("Alter system flush shared_pool");
		stmtChild.executeQuery("Alter system flush shared_pool");		
		
		ResultSet rsMother = stmtMother.executeQuery("select id from mother where hairColor = 'blond'");

		while (rsMother.next()) {
				ResultSet rsChild = stmtChild
						.executeQuery("select motherId,yearBorn from" + " child where id = " + rsMother.getInt("id"));
				while (rsChild.next()) {
					if (rsChild.getInt("yearBorn") == 2012) {
						noOfChildrens++;
					}
				}
				
		
				rsChild.close();
				rsChild = null;
		}

		rsMother.close();
		rsMother = null;
		stmtMother.close();
		stmtChild.close();

		System.out.println("number of children born in 2012 by blond mothers with were on mother: " + noOfChildrens);

	}
}
