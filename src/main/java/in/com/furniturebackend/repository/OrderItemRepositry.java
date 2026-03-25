package in.com.furniturebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.com.furniturebackend.model.OrderItem;

public interface OrderItemRepositry extends JpaRepository<OrderItem, Long> {
	

}
