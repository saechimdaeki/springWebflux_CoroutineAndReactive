package org.example.completablefuture.future.repository;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FollowFutureRepository {
    private Map<String, Long> userFollowCountMap;

    public FollowFutureRepository() {
        userFollowCountMap = Map.of("1234", 1000L);
    }

    public CompletableFuture<Long> countByUserId(String userId) {

        return CompletableFuture.supplyAsync(() -> {
            System.out.println("FollowRepository countByUserId : " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return userFollowCountMap.getOrDefault(userId, 0L);
        });
    }
}

