package com.taskmanager.model;

public class User {
    private final String password;
    private final String avatarPath;

    public User(String password, String avatarPath) {
        this.password = password;
        this.avatarPath = avatarPath;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatarPath() {
        return avatarPath;
    }
} 