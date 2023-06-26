package org.example.completablefuture.future.repository;

import org.example.completablefuture.common.ImageEntity;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ImageFutureRepository {
    private final Map<String, ImageEntity> imageMap;

    public ImageFutureRepository() {
        this.imageMap = Map.of(
                "image#1000", new ImageEntity("image#1000", "profileImage", "/images/profileImage.jpg")
        );
    }

    public CompletableFuture<Optional<ImageEntity>> findById(String id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("ImageRepository.findById" + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Optional.ofNullable(imageMap.get(id));
        });
    }
}
