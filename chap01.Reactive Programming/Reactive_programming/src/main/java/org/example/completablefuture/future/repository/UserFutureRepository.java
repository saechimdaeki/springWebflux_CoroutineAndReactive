package org.example.completablefuture.future.repository;

import org.example.completablefuture.common.UserEntity;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class UserFutureRepository {
    private final Map<String, UserEntity> userMap;

    public UserFutureRepository() {
        var user =  new UserEntity("1234","junseong",30,"image#1000");
        userMap = Map.of("1234",user);
    }

    public CompletableFuture<Optional<UserEntity>> findById(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        System.out.println("UserRepository findById : "+ Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return Optional.ofNullable(userMap.get(id));
                }
        );
    }
}
