package org.example.completablefuture.blocking.repository;

import java.util.Map;

public class FollowRepository {
    private Map<String, Long> userFollowCountMap;

    public FollowRepository() {
        userFollowCountMap = Map.of("1234", 1000L);
    }

    public Long countByUserId(String userId) {
        try {
            System.out.println("FollowRepository countByUserId : " + Thread.currentThread().getName());
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return userFollowCountMap.getOrDefault(userId,0L);
    }
}
