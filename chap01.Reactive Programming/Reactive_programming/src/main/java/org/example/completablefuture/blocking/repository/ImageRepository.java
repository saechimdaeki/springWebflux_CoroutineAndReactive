package org.example.completablefuture.blocking.repository;

import org.example.completablefuture.blocking.common.ImageEntity;

import java.util.Map;
import java.util.Optional;

public class ImageRepository {
    private final Map<String, ImageEntity> imageMap;

    public ImageRepository() {
        this.imageMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "/images/profileImage.jpg")
        );
    }

    public Optional<ImageEntity> findById(String id)  {
        try {
            System.out.println("ImageRepository.findById" + Thread.currentThread().getName());
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return Optional.ofNullable(imageMap.get(id));
    }
}
