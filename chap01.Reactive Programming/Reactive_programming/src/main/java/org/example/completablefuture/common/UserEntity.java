package org.example.completablefuture.common;




public class UserEntity {
    private String id;
    private String name;
    private int age;
    private String profileImageId;

    public UserEntity(String id, String name, int age, String profileImageId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.profileImageId = profileImageId;
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

    public String getProfileImageId() {
        return profileImageId;
    }
}
