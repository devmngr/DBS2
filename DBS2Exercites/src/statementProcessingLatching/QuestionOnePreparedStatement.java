package statementProcessingLatching;

/*
 * Implementation of question number one
 * 
 * Only contains specific code for the question. Handling of statistics gathering
 * is inherited from QuestionRunner
 */

import java.sql.*;

public class QuestionOnePreparedStatement extends QuestionRunner {

	public QuestionOnePreparedStatement(Connection iconn) {

		this.conn = iconn;
	}

	public void runCase() throws SQLException {

		ResultSet rsMother, rsChild;
		PreparedStatement stmtMother = conn.prepareStatement("select id, hairColor from mother");
		PreparedStatement stmtChild = conn
				.prepareStatement("select id, yearBorn from" + " child where motherID = ?");
		int noOfChildren = 0;

		rsMother = stmtMother.executeQuery();
		
		while (rsMother.next()) {
			if (rsMother.getString("hairColor").equals("blond")) {
				stmtChild.setLong(1, rsMother.getInt("id"));
				rsChild = stmtChild.executeQuery();
				while (rsChild.next()) {
					if (rsChild.getInt("yearBorn") == 2012) {
						noOfChildren++;
					}
				}
				rsChild.close();
				rsChild = null;
			}
		}

		rsMother.close();
		rsMother = null;
		stmtMother.close();
		stmtChild.close();

		System.out.println("PreparedStatement-----number of children born in 2012 by blond mothers: " + noOfChildren);

	}
}
