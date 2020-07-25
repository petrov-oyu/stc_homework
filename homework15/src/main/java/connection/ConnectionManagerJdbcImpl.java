package connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manage connection to postgresSql
 *
 * @author Petrov_OlegYu
 */
public class ConnectionManagerJdbcImpl implements ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManagerJdbcImpl.class);
    private static final ConnectionManager INSTANCE = new ConnectionManagerJdbcImpl();

    private ConnectionManagerJdbcImpl() {
    }

    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    @Override
    public Connection getConnection() {
        logger.debug("getting new connection");

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "12345");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return connection;
    }
}
