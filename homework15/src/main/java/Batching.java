import connection.ConnectionManager;
import connection.ConnectionManagerJdbcImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Batching {
	private static final Logger logger = LoggerFactory.getLogger(Batching.class);
	private static final ConnectionManager connectionManager =
			ConnectionManagerJdbcImpl.getInstance();

	public static void main(String[] args) {
		logger.debug("2.2 batching");
		//2.2 batching
		Map<Long, String> products = new HashMap<>();
		products.put(1L, "milk");
		products.put(2L, "bread");
		products.put(3L, "eggs");
		products.put(4L, "potato");

		try (Connection connection = connectionManager.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(
				     "update product set name=? where id = ?")) {
			DBUtil.renewDatabase();
			for (Entry<Long, String> prod : products.entrySet()) {
				preparedStatement.setString(1, prod.getValue());
				preparedStatement.setLong(2, prod.getKey());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
