package org.example.completablefuture.blocking.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.completablefuture.blocking.common.UserEntity;

import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private final Map<String, UserEntity> userMap;

    public UserRepository() {
        var user =  new UserEntity("1234","junseong",30,"image#1000");
        userMap = Map.of("1234",user);
    }

    public Optional<UserEntity> findById(String id) throws InterruptedException {
        System.out.println("UserRepository findById : "+ Thread.currentThread().getName());
        Thread.sleep(1000);
        return Optional.ofNullable(userMap.get(id));
    }
}
