import connection.ConnectionManager;
import connection.ConnectionManagerJdbcImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

public class Manual {
	private static final Logger logger = LoggerFactory.getLogger(Manual.class);
	private static final ConnectionManager connectionManager =
			ConnectionManagerJdbcImpl.getInstance();

	public static void main(String[] args) {
		logger.debug("2.3 manual transaction");
		//2.3 manual transaction

		try (Connection connection = connectionManager.getConnection()) {
			DBUtil.renewDatabase();

			try (Statement statement = connection.createStatement()) {

				connection.setAutoCommit(false);

				statement.executeUpdate(
						"INSERT INTO product (name)\n"
								+ "VALUES\n"
								+ "   ('noname1'),\n"
								+ "   ('noname2'),\n"
								+ "   ('noname3'),\n"
								+ "   ('noname4');"
				);
				Savepoint savepoint = connection.setSavepoint();

				statement.executeUpdate(
						"INSERT INTO product (name)\n"
								+ "VALUES\n"
								+ "   ('noname5'),\n"
								+ "   ('noname6'),\n"
								+ "   ('noname7'),\n"
								+ "   ('noname8');"
				);

				connection.rollback(savepoint);
				connection.commit();

			} catch (SQLException e) {
				logger.error("rollback  transaction : {}", e.getMessage());
				connection.rollback();
			}

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
