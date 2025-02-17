package com.tienda.perfumeria.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tienda.perfumeria.exceptions.CartException;
import com.tienda.perfumeria.exceptions.InvalidProductException;

@RestController
@RequestMapping("/api/images")
public class ImageUploadController {

    @Value("${file.upload-dir}") // Inject the upload directory from properties
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidProductException("El archivo está vacío");
        }

        try {
            // Ensure the upload directory exists
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate a unique filename
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // Save the file to the local storage
            Files.write(filePath, file.getBytes());

            return ResponseEntity.ok(fileName);
        } catch (IOException e) {
            throw new CartException("Error al subir el archivo");
        }
    }
}
