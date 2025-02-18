package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tienda.perfumeria.PerfumeriaApplication;
import com.tienda.perfumeria.controllers.ShoppingCartController;
import com.tienda.perfumeria.dtos.CartItemDto;
import com.tienda.perfumeria.entities.CartProduct;
import com.tienda.perfumeria.entities.Product;
import com.tienda.perfumeria.exceptions.GlobalExceptionHandler;
import com.tienda.perfumeria.repositories.ProductRepository;

@SpringBootTest(classes = PerfumeriaApplication.class)
@AutoConfigureMockMvc
public class ShoppingCartControllerTest {

    @InjectMocks
    private ShoppingCartController shoppingCartController;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MockHttpSession session;

    private List<CartProduct> cart;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  
        when(session.getAttribute("cart")).thenReturn(cart);  
        shoppingCartController = new ShoppingCartController(productRepository);
    }

    @Test
    public void testGetCart() throws Exception {
        when(session.getAttribute("cart")).thenReturn(cart);
        
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();

        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testAddToCart() {

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(1);
        cartItemDto.setQuantity(2);

        Product product = new Product();
        product.setId(1);
        product.setName("Perfume");
        product.setPrice(100.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        ResponseEntity<?> response = shoppingCartController.addToCart(session, cartItemDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<CartProduct> cart = (List<CartProduct>) response.getBody();
        assertNotNull(cart);
        assertEquals(1, cart.size());  
        assertEquals(1, cart.get(0).getProductId());
        assertEquals(2, cart.get(0).getQuantity());
    }


    @Test
    public void testAddToCart_ProductNotFound() throws Exception {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(99);
        cartItemDto.setQuantity(2);
        
        when(session.getAttribute("cart")).thenReturn(cart);
        when(productRepository.findById(99)).thenReturn(Optional.empty());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController)
                                        .setControllerAdvice(new GlobalExceptionHandler()) // Asegúrate de tener tu handler global para excepciones
                                        .build();

        mockMvc.perform(post("/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\": 99, \"quantity\": 2}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Producto no encontrado con ID: 99"));
    }



    @Test
    public void testClearCart() throws Exception {

        List<CartProduct> cart = new ArrayList<>();
        cart.add(new CartProduct(1, "Perfume", 1, 50.0));

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("cart", cart);  

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();

        mockMvc.perform(delete("/cart/clear")
                .session(mockSession))  
                .andExpect(status().isOk())  
                .andExpect(content().string("Carrito vacío"));  
    }



    @Test
    public void testCheckout() throws Exception {

        List<CartProduct> cart = new ArrayList<>();
        cart.add(new CartProduct(1, "Perfume", 2, 100.0));

        MockHttpSession mockSession = new MockHttpSession();
        mockSession.setAttribute("cart", cart);  

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();

    
        mockMvc.perform(post("/cart/checkout")
                .session(mockSession))  
                .andExpect(status().isOk())  
                .andExpect(content().string("Compra realizada con éxito."));  
    }




    @Test
    public void testCheckout_EmptyCart() throws Exception {

        when(session.getAttribute("cart")).thenReturn(new ArrayList<>());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController)
                .setControllerAdvice(new GlobalExceptionHandler()) 
                .build();

        mockMvc.perform(post("/cart/checkout"))
                .andExpect(status().isBadRequest()) 
                .andExpect(jsonPath("$.message").value("El carrito está vacío. No se puede procesar la compra."));  
    }



    @Test
    public void testCheckoutPage_EmptyCart() throws Exception {
        when(session.getAttribute("cart")).thenReturn(new ArrayList<>());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();

        mockMvc.perform(get("/cart/checkout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }
}
