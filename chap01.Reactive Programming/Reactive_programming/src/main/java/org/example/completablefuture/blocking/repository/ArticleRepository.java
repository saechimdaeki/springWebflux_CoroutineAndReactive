package org.example.completablefuture.blocking.repository;

import org.example.completablefuture.blocking.common.ArticleEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleRepository {
    private static List<ArticleEntity> articleEntities;

    public ArticleRepository() {
        articleEntities = List.of(
                new ArticleEntity("1","소식1","내용1","1234"),
                new ArticleEntity("2","소식2","내용2","1234"),
                new ArticleEntity("3","소식3","내용3","10000")
        );
    }

    public List<ArticleEntity> findAllByUserId(String userId) {
        try {
            System.out.println("ArticleRepository findAllByUserId : " + Thread.currentThread().getName());
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return articleEntities.stream()
                .filter(articleEntity -> articleEntity.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
}
