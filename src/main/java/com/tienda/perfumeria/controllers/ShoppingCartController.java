package com.tienda.perfumeria.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienda.perfumeria.dtos.CartItemDto;
import com.tienda.perfumeria.entities.CartProduct;
import com.tienda.perfumeria.repositories.ProductRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ProductRepository productRepository;
    private static final String CART_SESSION_KEY = "cart";

    public ShoppingCartController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<?> getCart(HttpSession session) {
        List<CartProduct> cart = (List<CartProduct>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(HttpSession session, @RequestBody CartItemDto cartItemDto) {
        List<CartProduct> cart = (List<CartProduct>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        Integer productId = cartItemDto.getProductId();
        Integer quantity = cartItemDto.getQuantity();

        if (productId != null && quantity != null) {
            CartProduct cartProduct = productRepository.findById(productId)
                    .map(product -> new CartProduct(product.getId(), product.getName(), quantity, product.getPrice()))
                    .orElse(null);
            if (cartProduct != null) {
                cart.add(cartProduct);
            }
        }

        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(HttpSession session, @PathVariable Integer productId) {
        List<CartProduct> cart = (List<CartProduct>) session.getAttribute(CART_SESSION_KEY);
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return ResponseEntity.ok(cart);
        
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
        return ResponseEntity.ok("Carrito vacío");
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(HttpSession session) {
    List<CartProduct> cart = (List<CartProduct>) session.getAttribute(CART_SESSION_KEY);

    if (cart == null || cart.isEmpty()) {
        return ResponseEntity.badRequest().body("El carrito está vacío.");
    }

    // Simula el proceso de compra
    session.removeAttribute(CART_SESSION_KEY);  // Vaciar el carrito

    return ResponseEntity.ok("Compra realizada con éxito.");
    }    

    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {
    List<CartProduct> cart = (List<CartProduct>) session.getAttribute("cart");
    
    if (cart == null || cart.isEmpty()) {
        return "redirect:/cart";  // Si el carrito está vacío, redirige a la página del carrito
    }

    model.addAttribute("cartItems", cart);
    return "checkout";  // Muestra la página checkout.html
}



    

}



