package statementProcessingLatching;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionTwo extends QuestionRunner{

	public QuestionTwo(Connection iconn) {

		this.conn = iconn;
	}

	public void runCase() throws SQLException {

		Statement stmtMother = conn.createStatement();
		Statement stmtChild = conn.createStatement();
		int noOfBlondMothers = 0;
		
		stmtMother.executeQuery("Alter system flush shared_pool");
		stmtChild.executeQuery("Alter system flush shared_pool");		
		
		ResultSet rsChild = stmtChild.executeQuery("select motherId, yearBorn from child");

		while (rsChild.next()) {
			if (rsChild.getInt("yearBorn")==2012) {
				ResultSet rsMother = stmtMother
						.executeQuery("select id, hairColor from" + " mother where id = " + rsChild.getInt("motherId"));
				while (rsMother.next()) {
					if (rsMother.getString("haircolor").equals("blond")) {
						noOfBlondMothers++;
					}
				}
				
				
				rsMother.close();
				rsMother = null;
			}
		}

		rsChild.close();
		rsChild = null;
		stmtMother.close();
		stmtChild.close();

		System.out.println("number of blond mothers by childeren born in 2012: " + noOfBlondMothers);

	}
}
