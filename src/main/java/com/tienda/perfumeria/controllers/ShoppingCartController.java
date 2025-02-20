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
import com.tienda.perfumeria.exceptions.CartException;
import com.tienda.perfumeria.exceptions.ProductNotFoundException;
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

        int timeout = session.getMaxInactiveInterval(); // en segundos

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

        if (cartItemDto.getProductId() == null || cartItemDto.getQuantity() == null) {
            throw new CartException("El ID del producto y la cantidad son obligatorios.");
        }

        if (cartItemDto.getQuantity() <= 0) {
            throw new CartException("La cantidad debe ser mayor a cero.");
        }

        CartProduct cartProduct = productRepository.findById(cartItemDto.getProductId())
                .map(product -> new CartProduct(product.getId(), product.getName(), cartItemDto.getQuantity(), (product.getPrice()*(1-product.getDiscount()))))
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + cartItemDto.getProductId()));
            
        cart.add(cartProduct);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(HttpSession session, @PathVariable Integer productId) {
        List<CartProduct> cart = (List<CartProduct>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null || cart.isEmpty()) {
            throw new CartException("No hay productos en el carrito.");
        }

        boolean removed = cart.removeIf(item -> item.getProductId().equals(productId));
        if (!removed) {
            throw new ProductNotFoundException("No se encontró el producto con ID: " + productId + " en el carrito.");
        }

        session.setAttribute(CART_SESSION_KEY, cart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(HttpSession session) {
        if (session.getAttribute(CART_SESSION_KEY) == null) {
            throw new CartException("El carrito ya está vacío.");
        }
        session.removeAttribute(CART_SESSION_KEY);
        return ResponseEntity.ok("Carrito vacío");
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(HttpSession session) {
        List<CartProduct> cart = (List<CartProduct>) session.getAttribute(CART_SESSION_KEY);

        if (cart == null || cart.isEmpty()) {
            throw new CartException("El carrito está vacío. No se puede procesar la compra.");
        }
        session.removeAttribute(CART_SESSION_KEY);

        return ResponseEntity.ok("Compra realizada con éxito.");
    }

    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {
        List<CartProduct> cart = (List<CartProduct>) session.getAttribute(CART_SESSION_KEY);

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("cartItems", cart);
        return "checkout";
    }
}
