package services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.tienda.perfumeria.entities.Category;
import com.tienda.perfumeria.entities.Product;
import com.tienda.perfumeria.repositories.ProductRepository;
import com.tienda.perfumeria.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setName("Perfume X");
        product.setBrand("BrandX");
        product.setCategory(Category.MEN);
        product.setPrice(100.0);
    }

    @Test
    void testSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Perfume X", savedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testFindByIdExists() {
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.findById(1);

        assertTrue(foundProduct.isPresent());
        assertEquals(1, foundProduct.get().getId());
    }

    @Test
    void testFindByIdNotExists() {
        when(productRepository.findById(2)).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.findById(2);

        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testFindAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        List<Product> products = productService.findAllProducts();

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void testFindByFilters() {
        when(productRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(product));

        // Usar una categoría válida del enum
        String validCategory = Category.MEN.name(); // "MEN", "WOMEN", "UNISEX" o "KIDS"

        List<Product> filteredProducts = productService.findByFilters("BrandX", "Perfume X", validCategory, 50.0, 200.0);

        assertFalse(filteredProducts.isEmpty());
        assertEquals(1, filteredProducts.size());
    }


    @Test
    void testDeleteProductExists() {
        when(productRepository.existsById(1)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1);

        assertDoesNotThrow(() -> productService.deleteProduct(1));

        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteProductNotExists() {
        when(productRepository.existsById(2)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(2));
        assertEquals("Producto con ID 2 no encontrado.", exception.getMessage());
    }
}
