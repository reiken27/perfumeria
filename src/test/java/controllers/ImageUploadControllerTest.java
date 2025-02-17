package controllers;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tienda.perfumeria.PerfumeriaApplication;
import com.tienda.perfumeria.controllers.ImageUploadController;
import com.tienda.perfumeria.exceptions.CartException;
import com.tienda.perfumeria.exceptions.InvalidProductException;

@SpringBootTest(classes = PerfumeriaApplication.class)
@AutoConfigureMockMvc
class ImageUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageUploadController imageUploadController;

    @Mock
    private Path mockPath;

    private static final String UPLOAD_URL = "/api/images/upload";

    @Test
    void shouldUploadImageSuccessfully() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test-image.png", MediaType.IMAGE_PNG_VALUE, "test data".getBytes());

        when(imageUploadController.uploadImage(file)).thenReturn(
                ResponseEntity.ok("test-image.png"));

        mockMvc.perform(multipart(UPLOAD_URL)
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("test-image.png"));
    }

    @Test
    void shouldReturnBadRequestWhenFileIsEmpty() throws Exception {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "", MediaType.IMAGE_PNG_VALUE, new byte[0]);

        doThrow(new InvalidProductException("El archivo está vacío"))
                .when(imageUploadController).uploadImage(emptyFile);

        mockMvc.perform(multipart(UPLOAD_URL)
                        .file(emptyFile))
                .andExpect(status().isBadRequest());
    }

    @Test
        void shouldReturnInternalServerErrorWhenUploadFails() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "error-image.png", MediaType.IMAGE_PNG_VALUE, "test data".getBytes());

        doThrow(new CartException("Error al subir el archivo"))
                .when(imageUploadController).uploadImage(file);

        mockMvc.perform(multipart(UPLOAD_URL)
                        .file(file))
                .andExpect(status().isInternalServerError());
}

}
