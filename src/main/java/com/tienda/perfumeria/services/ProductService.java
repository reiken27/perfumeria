package com.tienda.perfumeria.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tienda.perfumeria.entities.Category;
import com.tienda.perfumeria.entities.Product;
import com.tienda.perfumeria.repositories.ProductRepository;
import com.tienda.perfumeria.utils.ProductSpecification;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        Float discount = product.getDiscount(); 
    
        if (discount < 0.0f){
            throw new IllegalArgumentException("El descuento no puede ser negativo.");
        }
        else {
            discount = discount * 0.01f;
            product.setDiscount(discount); 
        }
        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Producto con ID " + id + " no encontrado.");
        }
    }

    public List<Product> findByFilters(String brand, String name, String category, Double minPrice, Double maxPrice) {
        Category categoryEnum = (category != null) ? Category.valueOf(category.toUpperCase()) : null;

        Specification<Product> spec = Specification.where(ProductSpecification.hasBrand(brand))
                .and(ProductSpecification.hasName(name))
                .and(ProductSpecification.hasCategory(categoryEnum))
                .and(ProductSpecification.hasPriceBetween(minPrice, maxPrice));

        return productRepository.findAll(spec);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }

    public List<Product> findRandomProducts() {
        long count = productRepository.count();

        if (count <= 4) {
            return productRepository.findAll();
        }

        List<Product> allProducts = productRepository.findAll();

        Collections.shuffle(allProducts);

        return allProducts.subList(0, 4);
    }

    public List<Product> findDiscountProducts() {
        return productRepository.findByDiscountGreaterThan(0.0f);
    }

}
