package applicationDesign;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuestionFive extends QuestionRunner {

	public QuestionFive(Connection iconn) {

		this.conn = iconn;
	}

	public void runCase() throws SQLException {

		Statement stmtMother = conn.createStatement();
		Statement stmtChild = conn.createStatement();
		int count = 0;
		
		stmtMother.executeQuery("Alter system flush shared_pool");
		stmtChild.executeQuery("Alter system flush shared_pool");

		ResultSet rs = stmtMother.executeQuery("select mother.id, mother.haircolor,child.yearBorn" + " from mother"
				+ " join child" + " on mother.id = child.motherId");

		while (rs.next()) {
			if (rs.getString("haircolor").equals("blond") && rs.getInt("yearBorn") == 2012)
				count++;
		}

		rs.close();
		rs = null;
		stmtMother.close();
		stmtChild.close();

		System.out.println("number of children born in 2012 by blond mothers with join on PK/FK: " + count);

	}
}
