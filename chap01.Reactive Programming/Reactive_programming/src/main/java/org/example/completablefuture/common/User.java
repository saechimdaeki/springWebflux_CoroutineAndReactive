package org.example.completablefuture.common;



import java.util.List;
import java.util.Optional;


public class User {
    String id;
    String name;
    int age;
    Optional<ImageEntity> image;
    List<ArticleEntity> articles;

    long followCount;

    public User(String id, String name, int age, Optional<ImageEntity> image, List<ArticleEntity> articles, long followCount) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.image = image;
        this.articles = articles;
        this.followCount = followCount;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Optional<ImageEntity> getImage() {
        return image;
    }

    public List<ArticleEntity> getArticles() {
        return articles;
    }

    public long getFollowCount() {
        return followCount;
    }
}
