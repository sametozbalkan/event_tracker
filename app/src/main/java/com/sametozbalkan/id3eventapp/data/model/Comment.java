package com.sametozbalkan.id3eventapp.data.model;

public class Comment {

    private final String username;
    private final String message;
    private final String time;
    private final int likeCount;
    private final Comment reply;

    public Comment(String username, String message, String time, int likeCount, Comment reply) {
        this.username = username;
        this.message = message;
        this.time = time;
        this.likeCount = likeCount;
        this.reply = reply;
    }

    public Comment(String username, String message, String time, int likeCount) {
        this(username, message, time, likeCount, null);
    }

    public String getUsername() { return username; }
    public String getMessage() { return message; }
    public String getTime() { return time; }
    public int getLikeCount() { return likeCount; }
    public Comment getReply() { return reply; }
}

