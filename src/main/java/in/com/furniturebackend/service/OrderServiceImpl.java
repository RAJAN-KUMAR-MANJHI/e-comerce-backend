package in.com.furniturebackend.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import in.com.furniturebackend.dto.OrderRequest;
import in.com.furniturebackend.exception.ResourceNotFoundException;
import in.com.furniturebackend.model.Cart;
import in.com.furniturebackend.model.CartItem;
import in.com.furniturebackend.model.Order;
import in.com.furniturebackend.model.OrderItem;
import in.com.furniturebackend.model.OrderStatus;
import in.com.furniturebackend.repository.CartRepository;
import in.com.furniturebackend.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepo;
	private final CartRepository cartRepo;
	
	
	public OrderServiceImpl(OrderRepository orderRepo, CartRepository cartRepo) {
		this.orderRepo = orderRepo;
		this.cartRepo = cartRepo;
	}
	
	@Override
	public Order placeOrder(OrderRequest orderRequest){
		Cart cart = cartRepo.findByUserId(orderRequest.getUserId()).orElseThrow(()-> new ResourceNotFoundException("cart not found"));
		if(cart.getItems().isEmpty()) {
			throw new RuntimeException("cart is empty");
		}
		
		//create order
		
		Order order = new Order();
		order.setUser(cart.getUser());
		//order.setStatus("pending");
		order.setStatus(OrderStatus.SHIPPED);   
		order.setAddress(orderRequest.getAddress());
		order.setPaymentMethod(orderRequest.getPaymentMethod());
		
		order.setItems(new ArrayList<>());
		double total = 0;
				
		
		for(CartItem cartItem : cart.getItems()) {
			OrderItem orderItem =  new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(orderItem.getProduct().getPrice());
			total+= orderItem.getPrice() * orderItem.getQuantity();
			order.getItems().add(orderItem);
			
		}
		order.setTotalAmount(total);
		
		//--- clear cart --//
		cart.getItems().clear();
		cartRepo.save(cart);
		return orderRepo.save(order);
	}
	
		
	@Override
	public List<Order>getOrderByUser(Long userId){
		return orderRepo.findByUserId(userId);
	}
	
	@Override
	public List<Order>getAllOrders(){
		return orderRepo.findAll();
	}
	
	@Override
	public Order updateStatus(Long orderId, OrderStatus status) {

	    Order order = orderRepo.findById(orderId)
	            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

	    order.setStatus(status);
	    return orderRepo.save(order);
	}  
	    
	
	@Override
	public Order getOrderById(Long orderId) {
	    return orderRepo.findById(orderId)
	            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	}
	
	@Override
	public void cancelOrder(Long orderId) {
	    Order order = orderRepo.findById(orderId)
	            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

	   // order.setStatus("CANCELLED");
	    order.setStatus(OrderStatus.CANCELLED);
	    orderRepo.save(order);
	}
	

}
