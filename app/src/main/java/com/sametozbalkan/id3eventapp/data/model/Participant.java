package com.sametozbalkan.id3eventapp.data.model;

public class Participant {

    private final String name;
    private final String role;
    private final String avatarUrl;

    public Participant(String name, String role, String avatarUrl) {
        this.name = name;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}

