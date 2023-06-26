package org.example.completablefuture.future.repository;

import lombok.SneakyThrows;
import org.example.completablefuture.common.ArticleEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ArticleFutureRepository {
    private static List<ArticleEntity> articleEntities;

    public ArticleFutureRepository() {
        articleEntities = List.of(
                new ArticleEntity("1","소식1","내용1","1234"),
                new ArticleEntity("2","소식2","내용2","1234"),
                new ArticleEntity("3","소식3","내용3","10000")
        );
    }

    public CompletableFuture<List<ArticleEntity>> findAllByUserId(String userId) {

            return CompletableFuture.supplyAsync(() -> {
                System.out.println("ArticleRepository findAllByUserId : " + Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return articleEntities.stream()
                        .filter(articleEntity -> articleEntity.getUserId().equals(userId))
                        .collect(Collectors.toList());
            });


    }
}
