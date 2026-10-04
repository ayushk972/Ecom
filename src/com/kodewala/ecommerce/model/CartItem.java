package com.kodewala.ecommerce.model;

public class CartItem {
	private int productId;
    private String productName;
    private double price;
    private int quantity;
    private double totalPrice;
    
    public CartItem(int productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = price * quantity;
    }

	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}
    
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    	this.totalPrice = quantity * this.price;
    }
    
    @Override
    public String toString() {
    	return String.format(
    			"[ID: %-4d] %-20s | QTY %-3d | PRICE ₹%-8.2f | TOTAL ₹%-8.2f", 
    			productId, productName, quantity, price, totalPrice);
    }
}
