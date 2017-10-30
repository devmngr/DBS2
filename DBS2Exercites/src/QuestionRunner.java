
/*
 * Abstract class
 *  
 *  This class handles common functionality for all questions
 *    - getting pre-statistics
 *    - clearing shared pool
 *    - getting post-statistics
 *    - calculating and reporting resource consumption
 *
 * Individual questions inherit from this and
 *  only need to implement the runCase() method, which will 
 *  contain the database code for that particular case
 *
 */

import java.sql.*;

abstract class QuestionRunner {

	Connection conn;

	final int consistentGets = 0;
	final int hardParses = 1;
	final int totalParses = 2;
	final int sessionCPU = 3;
	final int parseTimeCPU = 4;
	final int parseTimeElapsed = 5;
	final int executeCount = 6;
	final int recursiveCalls = 7;

	public void execute() throws SQLException {

		try {
			int[] before;
			int[] after;
			long startTime = 0;
			long endTime = 0;

			clearSharedPool();

			before = getStats();
			startTime = System.currentTimeMillis();

			runCase();

			endTime = System.currentTimeMillis();
			after = getStats();

			report(before, after, startTime, endTime);
		} catch (SQLException e) {
			throw e;
		}
	}

	abstract void runCase() throws SQLException;

	/*
	 * Classes for individual questions must implement this method with the specific
	 * code for the question
	 */

	private void report(int[] before, int[] after, long startTime, long endTime) {

		System.out.println("*************************************************************************");
		System.out.println("*************************************************************************");
		System.out.println("Elapsed time (ms)" + (endTime - startTime));
		System.out.println("Consistent gets " + (after[consistentGets] - before[consistentGets]));
		System.out.println("Total parses " + (after[totalParses] - before[totalParses]));
		System.out.println("Hard parses " + (after[hardParses] - before[hardParses]));
		System.out.println("Session CPU " + (after[sessionCPU] - before[sessionCPU]));
		System.out.println("Elapsed time parsing  " + (after[parseTimeElapsed] - before[parseTimeElapsed]));
		System.out.println("Parse CPU " + (after[parseTimeCPU] - before[parseTimeCPU]));
		System.out.println("Statement Executions " + (after[executeCount] - before[executeCount]));
		System.out.println("Statement Executions (user only)"
				+ ((after[executeCount] - after[recursiveCalls]) - (before[executeCount] - before[recursiveCalls])));
		System.out.println(" ");

	}

	private void clearSharedPool() throws SQLException {

		Statement stmt = conn.createStatement();
		stmt.execute("alter system flush shared_pool");
		stmt.close();
	}

	private int[] getStats() throws SQLException {

		int[] stats = new int[8];

		stats[consistentGets] = getNamedStatistic("consistent gets");
		stats[hardParses] = getNamedStatistic("parse count (hard)");
		stats[totalParses] = getNamedStatistic("parse count (total)");
		stats[sessionCPU] = getNamedStatistic("CPU used by this session");
		stats[parseTimeCPU] = getNamedStatistic("parse time cpu");
		stats[parseTimeElapsed] = getNamedStatistic("parse time elapsed");
		stats[executeCount] = getNamedStatistic("execute count");
		stats[executeCount] = getNamedStatistic("execute count");
		stats[recursiveCalls] = getNamedStatistic("recursive calls");

		return stats;
	}

	private int getNamedStatistic(String statName) throws SQLException {

		int statValue = 0;
		PreparedStatement stmt = conn.prepareStatement("select value " + " from v$session se "
				+ " join v$sesstat st on (st.sid = se.sid) " + " join v$statname nm on (st.statistic# = nm.statistic#)"
				+ " where nm.name = ? " + "  and  se.sid = sys_context('userenv','sid')");
		stmt.setString(1, statName);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		statValue = rs.getInt("value");
		rs.close();
		stmt.close();

		return statValue;

	}

}
