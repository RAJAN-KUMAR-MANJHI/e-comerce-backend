package in.com.furniturebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.com.furniturebackend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	//--categegory ke basis pr product---//
	List<Product>findByCategoryId(Long categoryId);

	//-- name ke basis pr search ---//
    List<Product> findByNameContainingIgnoreCase(String name);
}
