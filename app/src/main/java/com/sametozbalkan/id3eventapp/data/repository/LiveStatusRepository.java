package com.sametozbalkan.id3eventapp.data.repository;

import java.util.Arrays;
import java.util.List;

public class LiveStatusRepository {

    public List<String> getDefaultStatuses() {
        return Arrays.asList(
                "Event is about to start",
                "Keynote is in progress",
                "Coffee break",
                "Q&A session started",
                "Event finished"
        );
    }
}
