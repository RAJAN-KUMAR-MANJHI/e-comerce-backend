package in.com.furniturebackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.com.furniturebackend.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
