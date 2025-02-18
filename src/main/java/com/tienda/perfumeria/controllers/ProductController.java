package com.tienda.perfumeria.controllers;

import java.util.List;

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
import com.tienda.perfumeria.exceptions.InvalidProductException;
import com.tienda.perfumeria.exceptions.ProductNotFoundException;
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
        Product product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (product.getName() == null || product.getPrice() <= 0) {
            throw new InvalidProductException("Datos del producto no válidos");
        }

        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        if (productService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        product.setId(id); 
        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));

        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/productos")
    public String getAllProducts(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "./fragments/product-list";
    }

    @GetMapping("/recomendados")
    public String findRandomProducts(Model model) {
        List<Product> products = productService.findRandomProducts();
        model.addAttribute("products", products);
        return "./fragments/recomendados-list";
    }

    @GetMapping("/fragment/{id}")
    public String getProductFragment(@PathVariable int id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));

        model.addAttribute("product", product);
        return "fragments/product-item :: productItem";
    }

    @GetMapping("/woman")
    public String getWomenPerfumes(Model model) {
        List<Product> products = productService.findByFilters(null, null, "WOMEN", null, null); 
        model.addAttribute("products", products);
        return "perfumesMujer";
    }

    @GetMapping("/man")
    public String getMenPerfumes(Model model) {
        List<Product> products = productService.findByFilters(null, null, "MEN", null, null); 
        model.addAttribute("products", products);
        return "perfumesHombre";
    }

    @GetMapping("/all")
    public String getAllPerfumes(Model model) {
        List<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "productos";
    }

    @GetMapping("/promociones")
    public String getPromocionesPerfumes(Model model) {
        List<Product> products = productService.findTenProducts();
        model.addAttribute("products", products);
        return "promociones";
    }
}

