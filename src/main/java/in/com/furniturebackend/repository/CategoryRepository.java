package in.com.furniturebackend.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import in.com.furniturebackend.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	boolean existsByName(String name);
	Category findByName(String name);

}
