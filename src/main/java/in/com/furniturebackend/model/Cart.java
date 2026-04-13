package in.com.furniturebackend.model;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//---one cart belong to one user--//
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	
	//---one cart can have multiple cart item--//
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    @JsonManagedReference
	private List<CartItem>items;

//------getter & setter------//
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public List<CartItem> getItems() {
		return items;
	}


	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	
	

}
