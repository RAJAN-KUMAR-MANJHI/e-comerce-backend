package in.com.furniturebackend.service;

import java.util.List;

import in.com.furniturebackend.dto.OrderRequest;
import in.com.furniturebackend.model.Order;
import in.com.furniturebackend.model.OrderStatus;

public interface OrderService {

	
	Order placeOrder(OrderRequest orderRequest);
	List<Order> getOrderByUser(Long userId);
	
	Order getOrderById(Long orderId); 
	
	
	
	 //admin ke liye
    List<Order> getAllOrders();
    
    Order updateStatus(Long orderId, OrderStatus status);
   // Order updateStatus(Long orderId, String status);
    
    void cancelOrder(Long orderId);

}
