package com.qn_org.backend.controllers.image;

import com.qn_org.backend.models.Image;
import com.qn_org.backend.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;
    @Value("${app.image-path}")
    private String imageDirectory;
    public List<Image> getImage(GetImagesRequest request) {
        return repository.getImageByParentId(request.getParentIds());
    }

    public List<Image> saveImages(SaveImagesRequest request) throws IOException {
        List<Image> images = new ArrayList<>();
        for(MultipartFile file: request.getImages()) {
            String imageId = "IMG_" + (new Date().getTime()) + UUID.randomUUID();
            String imageName = handleSaveImage(file,imageId);
            if(!imageName.isBlank()){
                images.add(Image.builder()
                        .imageId(imageId)
                        .parentId(request.getParentId())
                        .imageUrl("/api/image/" + handleSaveImage(file,imageId))
                        .insDate(new Date())
                        .build());
            }
        }
        repository.saveAll(images);
        return images;
    }

    public String handleSaveImage(MultipartFile image, String imageName) throws IOException {
//        if(!image.isEmpty()) {
//            String originalName = image.getOriginalFilename() == null ? "" : image.getOriginalFilename();
//            String extension = "";
//            if(originalName.contains(".")) {
//                extension = originalName.substring(originalName.lastIndexOf("."));
//            }
//            File directory = new File(imageDirectory);
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//            File destinationFile = new File(directory, imageName + extension);
//            image.transferTo(destinationFile);
//            return imageName + extension;
//        }
//        return "";
        if (!image.isEmpty()) {
            String originalName = image.getOriginalFilename() == null ? "" : image.getOriginalFilename();
            String extension = "";
            if (originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            File directory = new File(imageDirectory);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String fullPath = imageDirectory + File.separator + imageName + extension;

            Files.write(Paths.get(fullPath), image.getBytes());

            return imageName + extension;
        }
        return "";
    }

    public void deleteImage(List<String> delImagesId) {
        repository.deleteAllById(delImagesId);
    }
}
