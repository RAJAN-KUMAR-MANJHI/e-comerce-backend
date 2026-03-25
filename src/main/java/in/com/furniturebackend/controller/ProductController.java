package in.com.furniturebackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.com.furniturebackend.model.Product;
import in.com.furniturebackend.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	


	@Autowired
    private  ProductService productService;

    // ✅ ADD PRODUCT (ADMIN ONLY)

	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<Product> createProduct(
	        @RequestParam String name,
	        @RequestParam double price,
	        @RequestParam int stock,
	        @RequestParam String description,
	        @RequestParam Long categoryId,
	        @RequestParam(required = false) MultipartFile imageFile
	) throws Exception {

	    return ResponseEntity.ok(
	            productService.createProduct(name, price, stock, description, categoryId, imageFile)
	    );
	}

    // ✅ GET ALL PRODUCTS (PUBLIC)
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // ✅ UPDATE PRODUCT (ADMIN ONLY)
   // @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product
    ) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }
      //--image update ---//
//    @PutMapping("/{id}/image")
//    public ResponseEntity<Product> updateProductWithImage(
//            @PathVariable Long id,
//            @RequestParam String name,
//            @RequestParam double price,
//            @RequestParam int stock,
//            @RequestParam String description,
//            @RequestParam Long categoryId,
//            @RequestParam MultipartFile imageFile) throws Exception {
//
//        Product updatedProduct = productService.updateProductWithImage(
//                id, name, price, stock, description, categoryId, imageFile);
//
//        return ResponseEntity.ok(updatedProduct);
//    }
    
    @PutMapping("/{id}/image")
    public ResponseEntity<Product> updateProductWithImage(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) double price,
            @RequestParam(required = false) int stock,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) MultipartFile imageFile) throws Exception {

        Product updatedProduct = productService.updateProductWithImage(
                id, name, price, stock, description, categoryId, imageFile);

        return ResponseEntity.ok(updatedProduct);
    }

    // ✅ GET PRODUCT BY ID (PUBLIC)
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // ✅ DELETE PRODUCT (ADMIN ONLY)
   // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    // ✅ SEARCH PRODUCT (PUBLIC)
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String name) {
    	 System.out.println("Search value = " + name);
        return ResponseEntity.ok(productService.searchByName(name));
    }	
    
    @GetMapping("/category/{id}")
    public List<Product> getProductsByCategory(@PathVariable Long id) {
        return productService.getProductsByCategory(id);
    }
}
