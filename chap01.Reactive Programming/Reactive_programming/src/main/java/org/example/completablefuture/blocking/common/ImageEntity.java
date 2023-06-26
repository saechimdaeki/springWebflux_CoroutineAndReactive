package org.example.completablefuture.blocking.common;




public class ImageEntity {
    private String id;
    private String name;
    private String url;

    public ImageEntity(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
