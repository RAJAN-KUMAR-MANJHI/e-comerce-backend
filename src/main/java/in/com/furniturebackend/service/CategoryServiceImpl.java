package in.com.furniturebackend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.com.furniturebackend.model.Category;
import in.com.furniturebackend.repository.CategoryRepository;
@Service
public class CategoryServiceImpl implements CategoryService {
   
	
	  private final CategoryRepository categoryRepository;

	    public CategoryServiceImpl(CategoryRepository categoryRepository) {
	        this.categoryRepository = categoryRepository;
	    }

	    @Override
	    public Category addCategory(Category category) {

	        if (categoryRepository.existsByName(category.getName())) {
	            throw new RuntimeException("Category already exists");
	        }

	        return categoryRepository.save(category);
	    }

	    @Override
	    public List<Category> getAllCategories() {
	        return categoryRepository.findAll();
	    }

	    @Override
	    public Category getCategoryById(Long id) {
	        return categoryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Category not found"));
	    }

	    @Override
	    public void deleteCategory(Long id) {
	        categoryRepository.deleteById(id);
	    }
}
