package statementProcessing;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionTwoPrepareStatement extends QuestionRunner{

	public QuestionTwoPrepareStatement(Connection iconn) {

		this.conn = iconn;
	}

	public void runCase() throws SQLException {

		ResultSet rsMother,rsChild;
		PreparedStatement stmtMother = conn.prepareStatement("select id, hairColor from" + " mother where id = ?");
		PreparedStatement stmtChild = conn.prepareStatement("select motherId, yearBorn from child");
		int noOfBlondMothers = 0;

		PreparedStatement sysFlush = conn.prepareStatement("Alter system flush shared_pool");
		sysFlush.executeQuery();
		
		rsChild = stmtChild.executeQuery();
		
		while (rsChild.next()) {
			if (rsChild.getInt("yearBorn")==2012) {
				stmtMother.setLong(1,rsChild.getInt("motherId"));
			rsMother = stmtMother.executeQuery();
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

		System.out.println("PreparedStatement-----number of blond mothers by childeren born in 2012: " + noOfBlondMothers);

	}
}
