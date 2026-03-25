package in.com.furniturebackend.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.com.furniturebackend.exception.ResourceNotFoundException;
import in.com.furniturebackend.model.Category;
import in.com.furniturebackend.model.Product;
import in.com.furniturebackend.repository.CategoryRepository;
import in.com.furniturebackend.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepo;
	//private final String uploadDir = "uploads/";
	 @Autowired
	 private CategoryRepository categoryRepository;
	private final String uploadDir = System.getProperty("user.dir") + "/uploads/";
	
	public ProductServiceImpl(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}
	
	@Override
	public Product createProduct(Product product) {
		return productRepo.save(product);
	}
	
	
	
	@Override
	public Product createProduct(String name,
	                             double price,
	                             int stock,
	                             String description,
	                             Long categoryId,
	                             MultipartFile imageFile) throws Exception {

	    Product product = new Product();
	    product.setName(name);
	    product.setPrice(price);
	    product.setStock(stock);
	    product.setDescription(description);
	    
	 // 🔥 CATEGORY SET
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

//	    if (imageFile != null && !imageFile.isEmpty()) {
//
//	        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
//	        File file = new File("uploads/" + fileName);
//	        imageFile.transferTo(file);
//
//	        product.setImage(fileName);
//	    }
//
//	    return productRepo.save(product);
//	}
	    
	    if (imageFile != null && !imageFile.isEmpty()) {

	        // create uploads folder if not exist
	        File uploadFolder = new File(uploadDir);
	        if (!uploadFolder.exists()) {
	            uploadFolder.mkdirs();
	        }

	        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

	        File file = new File(uploadDir + fileName);
	        imageFile.transferTo(file);

	        product.setImage(fileName);
	    }
	    return productRepo.save(product);
	    }
	
    // ✅ UPDATE PRODUCT WITH NEW IMAGE
    @Override
    public Product updateProductWithImage(Long id,
                                          String name,
                                          double price,
                                           int stock,
                                          String description,
                                          Long categoryId,
                                          MultipartFile imageFile) throws Exception {

        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(name);
        existing.setPrice(price);
        existing.setStock(stock);
        existing.setDescription(description);
        
        // 🔥 ONLY UPDATE IF NOT NULL
//      if (name != null && !name.isEmpty()) {
//          existing.setName(name);
  //    }

//      if (price != null && !price.isEmpty()) {
//          existing.setPrice(Double.parseDouble(price));
//      }
//
//      if (stock != null && !stock.isEmpty()) {
//          existing.setStock(Integer.parseInt(stock));
//      }

//      if (description != null && !description.isEmpty()) {
//          existing.setDescription(description);
//      }
        
     // 🔥 CATEGORY UPDATE
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setCategory(category);

        // 🔥 If new image uploaded
        if (imageFile != null && !imageFile.isEmpty()) {

            // ❌ Delete old image
            if (existing.getImage() != null) {
                File oldFile = new File(uploadDir + existing.getImage());
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            // ✅ Save new image
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            File newFile = new File(uploadDir + fileName);
            imageFile.transferTo(newFile);

            existing.setImage(fileName);
        }

        return productRepo.save(existing);
    }

    // ✅ DELETE PRODUCT + IMAGE
    @Override
    public void deleteProduct(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ❌ Delete image file
        if (product.getImage() != null) {
            File file = new File(uploadDir + product.getImage());
            if (file.exists()) {
                file.delete();
            }
        }

        productRepo.deleteById(id);
    }
	
	
	@Override
	public List<Product>getAllProducts(){
		return productRepo.findAll();
	}
	
	
	@Override
	public Product getProductById(Long productId) {
	    return productRepo.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));
	}

	@Override
	public Product updateProduct(Long id, Product product) {
	    Product existing = productRepo.findById(id)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    existing.setName(product.getName());
	    existing.setPrice(product.getPrice());
	    existing.setDescription(product.getDescription());
	    existing.setCategory(product.getCategory());
	    
	    // 🔥 CATEGORY FIX
        if (product.getCategory() != null && product.getCategory().getId() != null) {

            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            existing.setCategory(category);
        }

	    return productRepo.save(existing);
	}

//	@Override
//	public void deleteProduct(Long id) {
//	    productRepo.deleteById(id);
//	}

	@Override
	public List<Product> searchByName(String name) {
		 System.out.println("Search value = " + name);
	    return productRepo.findByNameContainingIgnoreCase(name.trim());
	}
	
	@Override
	public List<Product> getProductsByCategory(Long categoryId) {
	    return productRepo.findByCategoryId(categoryId);
	}


}
