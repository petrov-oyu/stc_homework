package utils;

import connection.ConnectionManagerJdbcImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Util for reset database data and tables
 *
 * @author Petrov_OlegYu
 */
public final class DBUtil {
    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);

    private DBUtil() {
    }

    public static void main(String[] args) {
        try {
            renewDatabase();
        } catch (SQLException e) {
            logger.error("Cannot reset Database : {}", e.getMessage());
        }
    }

    public static void renewDatabase() throws SQLException {
        try (Connection connection = ConnectionManagerJdbcImpl.getInstance().getConnection();
             Statement statement = connection.createStatement();
        ) {
            renewSellerTable(statement);
            renewBookingTable(statement);
            renewProductTable(statement);
        }
    }

    private static void renewSellerTable(Statement statement) throws SQLException {
        logger.debug("renew seller table");
        statement.execute("-- Database: postgres\n"
                + "DROP TABLE IF EXISTS seller;"
                + "\n"
                + "CREATE TABLE seller (\n"
                + "    id serial primary key,\n"
                + "    name varchar(100) NOT NULL,\n"
                + "    booking int[]);"
                + "\n"
                + "INSERT INTO seller (name)\n"
                + "VALUES\n"
                + "   ('Magnit'),\n"
                + "   ('SevenEleven'),\n"
                + "   ('Metro'),\n"
                + "   ('Lenta');"
                + "\n");
    }

    private static void renewBookingTable(Statement statement) throws SQLException {
        logger.debug("renew booking table");
        statement.execute("-- Database: postgres\n"
                + "DROP TABLE IF EXISTS booking;"
                + "\n"
                + "CREATE TABLE booking (\n"
                + "    id serial primary key,\n"
                + "    name varchar(100) NOT NULL,\n"
                + "    customer varchar(100) NOT NULL,\n"
                + "    state varchar(30) NOT NULL,\n"
                + "    delivery_date date,\n"
                + "    products int[]);"
                + "\n"
                + "INSERT INTO booking (name, customer, state)\n"
                + "VALUES\n"
                + "   ('booking_1', 'cutomer_1', 'NEW'),\n"
                + "   ('booking_2', 'cutomer_2', 'IN_DELIVERY'),\n"
                + "   ('booking_3', 'cutomer_3', 'COMPLETE'),\n"
                + "   ('booking_4', 'cutomer_4', 'CLOSED');"
                + "\n");
    }

    private static void renewProductTable(Statement statement) throws SQLException {
        logger.debug("renew product table");
        statement.execute("-- Database: postgres\n"
                + "DROP TABLE IF EXISTS product;"
                + "\n"
                + "CREATE TABLE product (\n"
                + "    id serial primary key,\n"
                + "    name varchar(100) NOT NULL);"
                + "\n"
                + "INSERT INTO product (name)\n"
                + "VALUES\n"
                + "   ('Random Fish'),\n"
                + "   ('Random Meat'),\n"
                + "   ('Random Vegetable'),\n"
                + "   ('Random Fruit');"
                + "\n");
    }
}
