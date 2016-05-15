package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.product.Product;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.EMFProduct;


/**
 * An endpoint class we are exposing
 */
@Api(
        name = "productApi",
        version = "v1",
        resource = "product",
        namespace = @ApiNamespace(
                ownerDomain = "backend.app.ohanahome.mx",
                ownerName = "backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class ProductEndpoint {

    private static final Logger logger = Logger.getLogger(ProductEndpoint.class.getName());

    /**
     * This method gets the <code>Product</code> object associated with the specified <code>id</code>.
     *
     * @param identify The id of the object to be returned.
     * @return The <code>Product</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "listProducts")
    public CollectionResponse<Product> listProduct(Identify identify) {
        // TODO: Implement this function
        logger.info("Calling getProduct method");



        EntityManager productManager = EMFProduct.get().createEntityManager();

        TypedQuery<Product> query = productManager.createNamedQuery("Product.getProductsMap", Product.class);

        List<Product> productList = query.getResultList();


        return CollectionResponse.<Product>builder().setItems(productList).build();
    }

    /**
     * This inserts a new <code>Product</code> object.
     *
     * @param product The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertProduct")
    public Product insertProduct(Product product) {
        // TODO: Implement this function
        logger.info("Calling insertProduct method");
        return product;
    }

    @ApiMethod(name = "getProduct")
    public Product getProduct(@Named("id")Long id){
        EntityManager productManager = EMFProduct.get().createEntityManager();

        Product product = productManager.find(Product.class,id);

        return product;
    }

}