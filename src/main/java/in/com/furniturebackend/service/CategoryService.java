package in.com.furniturebackend.service;

import java.util.List;

import in.com.furniturebackend.model.Category;

public interface CategoryService {
  
	 Category addCategory(Category category);

	    List<Category> getAllCategories();

	    Category getCategoryById(Long id);

	    void deleteCategory(Long id);
}
