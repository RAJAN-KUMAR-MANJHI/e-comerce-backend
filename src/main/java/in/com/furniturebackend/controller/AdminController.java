package in.com.furniturebackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.com.furniturebackend.model.Order;
import in.com.furniturebackend.model.OrderStatus;
import in.com.furniturebackend.model.Product;
import in.com.furniturebackend.service.OrderService;
import in.com.furniturebackend.service.ProductService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	//add product
	
	@PostMapping("/product")
	public ResponseEntity<Product>addProduct(@RequestBody Product product){
		return ResponseEntity.ok(productService.createProduct(product));
	}
	
	// update product
	@PostMapping("/Product/{id}")
	public ResponseEntity<Product>updateProduct(@PathVariable Long id,@RequestBody Product product){
	
		Product existing = productService.getProductById(id);
		existing.setName(product.getName());
		existing.setPrice(product.getPrice());
		existing.setDescription(product.getDescription());
		existing.setStock(product.getStock());
		existing.setActive(product.isActive());
		return ResponseEntity.ok(productService.createProduct(existing));
	}
	// get all order
	@GetMapping("/orders")
	public ResponseEntity<List<Order>>getAllOrders(){
		return ResponseEntity.ok(orderService.getAllOrders());
	}
	
	// update order status
	@PutMapping("/order/{orderId}/staus")
	public ResponseEntity<Order>updateStatus(@PathVariable Long orderId, @RequestParam OrderStatus status ){
		return ResponseEntity.ok(orderService.updateStatus(orderId, status));
	}
}
