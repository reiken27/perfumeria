package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tienda.perfumeria.controllers.ProductController;
import com.tienda.perfumeria.entities.Category;
import com.tienda.perfumeria.entities.Product;
import com.tienda.perfumeria.exceptions.ProductNotFoundException;
import com.tienda.perfumeria.services.ProductService;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProducts() {
        Product product = new Product();
        product.setId(1);
        product.setName("Perfume A");
        product.setBrand("Marca A");
        product.setCategory(Category.valueOf("MEN"));
        product.setPrice(50.0);

        List<Product> products = Arrays.asList(product);
        when(productService.findByFilters(null, null, null, null, null)).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getProducts(null, null, null, null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }

    @Test
    void testGetProducts_NotFound() {
        when(productService.findByFilters(null, null, null, null, null)).thenReturn(List.of());

        ResponseEntity<List<Product>> response = productController.getProducts(null, null, null, null, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1);
        product.setName("Perfume A");
        product.setBrand("Marca A");
        product.setCategory(Category.valueOf("MEN"));

        product.setPrice(50.0);

        when(productService.findById(1)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testGetProductById_NotFound() {
        when(productService.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productController.getProductById(1));
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Perfume A");
        product.setBrand("Marca A");
        product.setCategory(Category.valueOf("MEN"));
        product.setPrice(50.0);

        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Perfume A");
        product.setBrand("Marca A");
        product.setCategory(Category.valueOf("MEN"));
        product.setPrice(50.0);

        when(productService.findById(1)).thenReturn(Optional.of(product));
        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.updateProduct(1, product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.updateProduct(1, new Product());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setId(1);

        when(productService.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productService).deleteProduct(1);

        ResponseEntity<Void> response = productController.deleteProduct(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productService.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productController.deleteProduct(1));
    }
}
