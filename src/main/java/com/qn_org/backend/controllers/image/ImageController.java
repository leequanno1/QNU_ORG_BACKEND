package com.qn_org.backend.controllers.image;

import com.qn_org.backend.models.Image;
import com.qn_org.backend.responses.QnuResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@CrossOrigin
public class ImageController {
    @Value("${app.image-path}")
    private String imagePath;

    private final ImageService service;

    @PostMapping("/get-images")
    public QnuResponseEntity<List<Image>> getImages(@RequestBody GetImagesRequest request) {
        return new QnuResponseEntity<>(service.getImage(request), HttpStatus.OK);
    }

    @PutMapping("/save-images")
    public QnuResponseEntity<List<Image>> saveImages(SaveImagesRequest request) throws IOException {
        return new QnuResponseEntity<>(service.saveImages(request), HttpStatus.OK);
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Path imageFilePath = Paths.get(imagePath).resolve(imageName);
            Resource resource = new UrlResource(imageFilePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(imageFilePath);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : "application/octet-stream")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
