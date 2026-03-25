package in.com.furniturebackend.dto;

import lombok.Data;

@Data
public class ProductRequest {
	
	private String name;
	private String description;
	private double price;
	private double discount;
	private int stock;
	private Long categoryId;
	private boolean active;

}
