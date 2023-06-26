package org.example.completablefuture.blocking.common;


public class ArticleEntity {

    private String id;
    private String title;
    private String content;
    private String userId;


    public ArticleEntity(String id, String title, String content, String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUserId() {
        return userId;
    }
}
