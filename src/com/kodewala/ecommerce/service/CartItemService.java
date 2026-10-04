package com.kodewala.ecommerce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kodewala.ecommerce.exception.InsufficientStockException;
import com.kodewala.ecommerce.exception.InvalidQuantityException;
import com.kodewala.ecommerce.exception.ProductNotFoundException;
import com.kodewala.ecommerce.model.CartItem;
import com.kodewala.ecommerce.model.Product;

public class CartItemService {

	private  final Map<Integer, List<CartItem>> carts = new HashMap<Integer, List<CartItem>>();
	private final ProductService productService;
	
	public CartItemService(ProductService productService) {
		this.productService = productService;
	}
	
	private List<CartItem> getOrCreateCart(int customerId) {
		carts.putIfAbsent(customerId, new ArrayList<>());
		return carts.get(customerId);
	}
	
	public void addToCart(int customerId, int productId, int quantity) {
		if(quantity <= 0) throw new InvalidQuantityException("Quantity Should be greater then 0");
		Product product = productService.getProduct(productId);
		if(product.getQuantity() < quantity) throw new InsufficientStockException("Stock is less then for " + productId + "\n "
				+ " Total Available stock is " + product.getQuantity());
		List<CartItem> cart = getOrCreateCart(customerId);
		
		for(CartItem item : cart) {
			if(item.getProductId() == productId) {
				int newQty = item.getQuantity() + quantity;
				if(product.getQuantity() < newQty) throw new InsufficientStockException("Stock is less then for " + productId + "\n "
						+ " Total Available stock is " + product.getQuantity());
				item.setQuantity(newQty);
				System.out.println("Qunatity is for " + product.getProductName()+ " is now " + newQty);
				return;
			}
		}
		
		cart.add(new CartItem(productId, product.getProductName(), product.getPrice(), quantity));
		System.out.println(" Added to cart "+ product.getProductName() + " "  + quantity);
	}
	
	// Remove product from cart
		public void removeFromCart(int customerId, int productId) {
			List<CartItem> cart = getOrCreateCart(customerId);
			/* if(cart.isEmpty()) return; */
			boolean removed = cart.removeIf(item -> item.getProductId() == productId);
			if (!removed) {
				throw new ProductNotFoundException("Product [ID: " + productId + "] not found in your cart.");
			}
			System.out.println("  Product [ID: " + productId + "] removed from cart.");
		}

		// Increase quantity
		public void increaseQuantity(int customerId, int productId, int qty) {
			if (qty <= 0)
				throw new InvalidQuantityException("InvalidQuantityException: Qty must be positive.");
			List<CartItem> cart = getOrCreateCart(customerId);
			CartItem item = findCartItem(cart, productId);
			Product product = productService.getProduct(productId);
			int newQty = item.getQuantity() + qty;
			if (product.getQuantity() < newQty) {
				throw new InsufficientStockException(
						"InsufficientStockException: Only " + product.getQuantity() + " unit(s) available.");
			}
			item.setQuantity(newQty);
			System.out.println("  Quantity increased to " + newQty + " for '" + item.getProductName() + "'");
		}
}
