package dao;

import pojo.Product;

import java.util.Optional;

/**
 * @author Petrov_OlegYu
 */
public interface ProductDao {
    /**
     * @param product which will be added
     * @return added product ID
     */
    Long addProduct(Product product);

    /**
     * @param id of product
     * @return product with given ID
     */
    Optional<Product> getProductById(Long id);

    /**
     * @param product which was updated and will modified table entry
     * @return true if an update was successfully, otherwise returns false
     */
    boolean updateProductById(Product product);

    /**
     * @param id of product which will be deleted
     * @return true if a delete was successfully, otherwise returns false
     */
    boolean deleteProductById(Long id);
}
