package in.com.furniturebackend.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.com.furniturebackend.dto.CartRequest;
import in.com.furniturebackend.model.Cart;
import in.com.furniturebackend.model.Product;
import in.com.furniturebackend.service.CartService;
import in.com.furniturebackend.service.UserService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	private final CartService cartService;
	private final UserService userService;
	
	public CartController(CartService cartService, UserService userService) {
		this.cartService = cartService;
		this.userService = userService;
	}
	
	
	//--Get cart--//
	
	@GetMapping("/{userId}")
	public Cart getCart(@PathVariable Long userId) {
		return cartService.getCartByUserId(userId);
	}

	//---add to cart---//
	
//	@PostMapping("/add")
//	public Cart addToCart(
//			@RequestParam Long userId,
//			@RequestParam Long productId,
//			@RequestParam int quantity) {
//		return cartService.addToCart(userId, productId, quantity);
//	}
	
	@PostMapping("/add")
	public Cart addToCart(
	        Authentication authentication,
	        @RequestBody CartRequest request) {

	    String username = authentication.getName();

	    Long userId = userService.findByMobileNumber(username).getId();

	    return cartService.addToCart(
	            userId,
	            request.getProductId(),
	            request.getQuantity());
	}
	
	//---Remove Item--//
	
	@DeleteMapping("/remove")
	public Cart removeItem(@RequestParam Long userId, @RequestParam Long productId) {
		return cartService.removeItem(userId, productId);
	}
	
	//--cler cart---//
	
	@DeleteMapping("/clear/{userId}")
		public Cart clearCart(@PathVariable Long userId) {
			return cartService.clearCart(userId);
		
	}
	
	
}
