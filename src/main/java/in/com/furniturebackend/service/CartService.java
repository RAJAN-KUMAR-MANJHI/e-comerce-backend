package in.com.furniturebackend.service;

import in.com.furniturebackend.model.Cart;

public interface CartService {
	 Cart getCartByUserId(Long userId);

	    Cart addToCart(Long userId, Long productId, int quantity);

	    Cart removeItem(Long userId, Long productId);

	    Cart clearCart(Long userId);
	

}
