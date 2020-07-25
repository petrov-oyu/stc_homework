import dao.ProductDao;
import dao.ProductDaoJdbcImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.Product;

import java.sql.SQLException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        logger.debug("2.1 CRUD parametrized query");
        //2.1 CRUD parametrized query
        ProductDao productDao = new ProductDaoJdbcImpl();
        // Create
        Product product = new Product(1L, "IceCream");
        Long id = productDao.addProduct(product);
        product.setId(id);
        logger.info(product.toString());
        // Read
        logger.info(productDao.getProductById(2L).toString());
        // Update
        product = new Product(1L, "Hot coffee");
        productDao.updateProductById(product);
        logger.info(productDao.getProductById(1L).toString());
        // Delete
        productDao.deleteProductById(1L);
        assert productDao.getProductById(1L).isPresent();
    }
}
