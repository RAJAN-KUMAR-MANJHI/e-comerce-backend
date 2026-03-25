package in.com.furniturebackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.com.furniturebackend.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByUserId(Long userId);

}
