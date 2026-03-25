package in.com.furniturebackend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.com.furniturebackend.model.Product;

public interface ProductService {

	Product createProduct(Product product);
	
	Product createProduct(String name,
            double price,
            int stock,
            String description,
            Long categoryId,
            MultipartFile imageFile) throws Exception;
	
	
	Product updateProductWithImage(Long id,
            String name,
            double price,
            int stock,
            String description,
            Long categoryId,
            MultipartFile imageFile) throws Exception;
	
	
	List<Product>getAllProducts();
	Product getProductById(Long productId);
	
	 Product updateProduct(Long id, Product product);

	    void deleteProduct(Long id);

	    List<Product> searchByName(String name);
	    List<Product> getProductsByCategory(Long categoryId);
	
	
}
