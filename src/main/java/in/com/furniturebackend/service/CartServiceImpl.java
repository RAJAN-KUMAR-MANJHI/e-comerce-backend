package in.com.furniturebackend.service;

import java.util.Optional;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import in.com.furniturebackend.exception.ResourceNotFoundException;
import in.com.furniturebackend.model.Cart;
import in.com.furniturebackend.model.CartItem;
import in.com.furniturebackend.model.Product;
import in.com.furniturebackend.model.User;
import in.com.furniturebackend.repository.CartRepository;
import in.com.furniturebackend.repository.ProductRepository;
import in.com.furniturebackend.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService{
	
	private final CartRepository cartRepo;
	private final ProductRepository productRepo;
	private final UserRepository userRepo;
	
	public CartServiceImpl(CartRepository cartRepo, ProductRepository productRepo, UserRepository userRepo) {
		this.cartRepo = cartRepo;
		this.productRepo = productRepo;
		this.userRepo = userRepo;
	}
    
	
	//---Get or create cart---//
	@Override
	public Cart getCartByUserId(Long userId) {
		return cartRepo.findByUserId(userId).orElseGet(()->{
			User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("Invalid userId: " + userId));
			Cart cart = new Cart();
			cart.setUser(user);
			cart.setItems(new ArrayList<>());
			return cartRepo.save(cart);
			
		});
	}
	
	// ----add product to cart--//
	
	@Override
	public Cart addToCart(Long userId, Long productId, int quantity) {
		Cart cart = getCartByUserId(userId);
		Product product = productRepo.findById(productId).orElseThrow(
				() -> new RuntimeException("Product Not Found"));
				Optional<CartItem> existingItem = cart.getItems()
				.stream()
				.filter(i -> i.getProduct().getId().equals(productId))
				.findFirst();
		if(existingItem.isPresent()) {
			CartItem item = existingItem.get();
			item.setQuantity(item.getQuantity()+quantity);
		}else {
			CartItem item = new CartItem();
			item.setCart(cart);
			item.setProduct(product);
			item.setQuantity(quantity);
			cart.getItems().add(item);
		}
		return cartRepo.save(cart);
	}
	
	
	//----remove Single item ---//
	
	@Override
	public Cart removeItem(Long userId, Long productId) {
		 Cart cart = getCartByUserId(userId);
		 cart.getItems().removeIf(
				 item -> item.getProduct().getId().equals(productId));
		 return cartRepo.save(cart);
	}
	
	//--  clear cart---//
	
	@Override
	public Cart clearCart(Long userId) {
		Cart cart = getCartByUserId(userId);
		cart.getItems().clear();
		return cartRepo.save(cart);
	}
	
	
	
	
}
