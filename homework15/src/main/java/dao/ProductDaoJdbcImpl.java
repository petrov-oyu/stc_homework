package dao;

import connection.ConnectionManager;
import connection.ConnectionManagerJdbcImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Product;
import utils.DBUtil;

import java.sql.*;
import java.util.Optional;

/**
 * Task 2.1
 */
public class ProductDaoJdbcImpl implements ProductDao {
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbcImpl.class);
    private static final ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    public ProductDaoJdbcImpl() throws SQLException {
        DBUtil.renewDatabase();
    }

    @Override
    public Long addProduct(Product product) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO product values (DEFAULT, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }

        } catch (SQLException e) {
            logger.error("Cannot add product {} : {}", product, e.getMessage());
        }
        return 0L;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM product WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new Product(id, resultSet.getString(2)));
                }
            }
        } catch (SQLException e) {
	        logger.error("Cannot get product with id {} : {}", id, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean updateProductById(Product product) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE product SET name=? WHERE id=?")) {
            preparedStatement.setString(1, product.getName());
	        preparedStatement.setLong(1, product.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
	        logger.error("Cannot update product {} : {}", product, e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteProductById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM product WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
	        return true;
        } catch (SQLException e) {
	        logger.error("Cannot delete product with id {} : {}", id, e.getMessage());
        }
	    return false;
    }
}
