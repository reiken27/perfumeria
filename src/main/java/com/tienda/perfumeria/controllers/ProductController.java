package com.tienda.perfumeria.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.perfumeria.entities.Product;
import com.tienda.perfumeria.services.ProductService;

@RequestMapping("/products")
@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minprice,
            @RequestParam(required = false) Double maxprice) {

        List<Product> products = productService.findByFilters(brand, name, category, minprice, maxprice);

        return products.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un producto
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    // Actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Optional<Product> existingProduct = productService.findById(id);

        if (existingProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        product.setId(id); // Asegurar que el producto tenga el ID correcto
        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    // Eliminar un producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/productos")
    public String getAllProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        System.out.println("Productos: " + products.size() + "");
        model.addAttribute("products", products);
        return "./fragments/product-list";
    }
}
