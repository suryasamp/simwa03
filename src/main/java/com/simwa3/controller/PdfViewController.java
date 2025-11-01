package com.simwa3.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PdfViewController {

    @GetMapping("/view/pdf/kk/{filename:.+}")
    public ResponseEntity<Resource> viewPdf(@PathVariable String filename) throws IOException {
        // Lokasi folder penyimpanan file PDF
        Path filePath = Paths.get("C:/simwa3/uploads/kk/").resolve(filename).normalize();

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());

        // Pastikan content type-nya PDF
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                // "inline" agar tampil di browser, bukan download
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
