package in.com.furniturebackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import in.com.furniturebackend.dto.OrderRequest;
import in.com.furniturebackend.model.Order;
import in.com.furniturebackend.model.OrderStatus;
import in.com.furniturebackend.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	//place order
	
	@PostMapping("/place")
	public ResponseEntity<Order>placeOrder(@RequestBody OrderRequest orderRequest) {
		Order order = orderService.placeOrder(orderRequest);
		return ResponseEntity.ok(order);
	}
	
	//get userOrder
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Order>>getOrder(@PathVariable Long userId){
		return ResponseEntity.ok(orderService.getOrderByUser(userId));
	}
	
	//---new add---//
	
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
	    return ResponseEntity.ok(orderService.getOrderById(orderId));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Order>> getAllOrders() {
	    return ResponseEntity.ok(orderService.getAllOrders());
	}
	
	@PutMapping("/status/{orderId}/{status}")
	public Order updateStatus(@PathVariable Long orderId,
	                          @PathVariable OrderStatus status) {
	    return orderService.updateStatus(orderId, status);
	}
	
	
	@DeleteMapping("/cancel/{orderId}")
	public ResponseEntity<String> cancelOrder(
	        @PathVariable Long orderId) {
	    orderService.cancelOrder(orderId);
	    return ResponseEntity.ok("Order cancelled successfully");
	}
	
	

}
