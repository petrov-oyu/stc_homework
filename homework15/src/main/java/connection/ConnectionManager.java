package connection;

import java.sql.Connection;

/**
 * Manage connections
 *
 * @author Petrov_OlegYu
 */
public interface ConnectionManager {
    Connection getConnection();
}
