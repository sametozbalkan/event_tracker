package com.sametozbalkan.id3eventapp.data.repository;

import com.sametozbalkan.id3eventapp.data.model.Comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentRepository {

    private final Map<Long, List<Comment>> commentsByEvent = new HashMap<>();

    public CommentRepository() {

        commentsByEvent.put(1L, new ArrayList<>(List.of(
                new Comment(
                        "Alex Rivera",
                        "Can't wait for this! The lineup looks incredible.",
                        "2m ago",
                        12,
                        new Comment(
                                "Event Team",
                                "Thanks Alex! See you there 🙌",
                                "1m ago",
                                3
                        )
                ),
                new Comment(
                        "Mert",
                        "Will the keynote be streamed online?",
                        "12m ago",
                        5
                )
        )));

        commentsByEvent.put(2L, new ArrayList<>(List.of(
                new Comment(
                        "Selin",
                        "Last year's event was amazing 🔥",
                        "1h ago",
                        21
                )
        )));

        commentsByEvent.put(3L, new ArrayList<>(List.of(
                new Comment(
                        "Salih",
                        "Will the keynote be streamed online?",
                        "15m ago",
                        2
                )
        )));
    }

    public List<Comment> getComments(long eventId) {
        return commentsByEvent.getOrDefault(
                eventId,
                new ArrayList<>()
        );
    }

    public void addComment(long eventId, Comment comment) {
        List<Comment> list = commentsByEvent.computeIfAbsent(eventId, k -> new ArrayList<>());
        list.add(0, comment);
    }
}
