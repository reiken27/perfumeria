package com.tienda.perfumeria.controllers;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tienda.perfumeria.exceptions.CartException;
import com.tienda.perfumeria.exceptions.InvalidProductException;
import com.tienda.perfumeria.exceptions.ProductNotFoundException;

@RestController
@RequestMapping("/api/images")
public class ImageServeController {

    private final String uploadDir = "uploads/"; // Ensure this matches your storage location

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Path imagePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // You can make this dynamic
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new ProductNotFoundException("Archivo no encontrado: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new InvalidProductException("URL del archivo no válida");
        } catch (Exception e) {
            throw new CartException("Error al servir la imagen");
        }
    }
}

