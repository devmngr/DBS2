package applicationDesign;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionSeven extends QuestionRunner {

	public QuestionSeven(Connection iconn) {

		this.conn = iconn;
	}

	public void runCase() throws SQLException {

		Statement stmtMother = conn.createStatement();
		Statement stmtChild = conn.createStatement();
		int count = 0;
		
		stmtMother.executeQuery("Alter system flush shared_pool");
		stmtChild.executeQuery("Alter system flush shared_pool");
		
		ResultSet rs = stmtMother.executeQuery(
				"select id from" + " mother" + " where haircolor = 'blond'" + " join" + " (select motherId from child where yearBorn = 2012)" + " on id = motherId");

		while (rs.next()) {
				count++;
		}

		rs.close();
		rs = null;
		stmtMother.close();
		stmtChild.close();

		System.out.println("number of children born in 2012 by blond mothers join yearBorn: " + count);

	}
}

