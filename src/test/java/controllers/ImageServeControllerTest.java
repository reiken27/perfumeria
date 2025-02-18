package controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.tienda.perfumeria.PerfumeriaApplication;
import com.tienda.perfumeria.controllers.ImageServeController;
import com.tienda.perfumeria.exceptions.CartException;
import com.tienda.perfumeria.exceptions.ProductNotFoundException;

@SpringBootTest(classes = PerfumeriaApplication.class)
@AutoConfigureMockMvc 
@ExtendWith(SpringExtension.class)
class ImageServeControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private ImageServeController imageServeController; 

    @Mock
    private Resource mockResource; 

    @Test
    void shouldReturnImageWhenExists() throws Exception {
        String fileName = "test-image.png";

        when(mockResource.exists()).thenReturn(true);
        when(mockResource.isReadable()).thenReturn(true);
        when(mockResource.getURL()).thenReturn(getClass().getResource("/test-image.png"));

        ResponseEntity<Resource> responseEntity = ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(mockResource);

        when(imageServeController.getImage(fileName)).thenReturn(responseEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/images/" + fileName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.IMAGE_PNG));
    }

    @Test
    void shouldReturnNotFoundWhenImageDoesNotExist() throws Exception {
        String fileName = "non-existent.png";

        when(imageServeController.getImage(fileName)).thenThrow(new ProductNotFoundException("Archivo no encontrado: " + fileName));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/images/" + fileName))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
        void shouldReturnBadRequestForInvalidURL() throws Exception {
        String fileName = "invalid-url.png";

       
        when(imageServeController.getImage(fileName)).thenThrow(new CartException("URL mal formada"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/images/" + fileName))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()) 
                .andExpect(MockMvcResultMatchers.content().json("{\"error\":\"Error en la operación del carrito\",\"message\":\"URL mal formada\"}"));
        }



}
