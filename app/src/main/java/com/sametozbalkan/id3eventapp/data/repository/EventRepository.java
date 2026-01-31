package com.sametozbalkan.id3eventapp.data.repository;

import com.sametozbalkan.id3eventapp.data.model.Event;
import com.sametozbalkan.id3eventapp.data.model.EventType;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private final List<Event> events = new ArrayList<>();

    public EventRepository() {
        events.add(new Event(
                1,
                "Global Tech Summit 2024",
                "Annual gathering of tech leaders discussing AI.",
                "https://picsum.photos/800/400?1",
                "2h ago",
                "OCT 24",
                EventType.TODAY,
                false
        ));

        events.add(new Event(
                2,
                "Local Charity Marathon",
                "Join the community for a 5k run.",
                "https://picsum.photos/800/400?2",
                "5h ago",
                "OCT 25",
                EventType.UPCOMING,
                false
        ));

        events.add(new Event(
                3,
                "Modern Art Exhibition",
                "Contemporary digital art showcase.",
                "https://picsum.photos/800/400?3",
                "1d ago",
                "OCT 20",
                EventType.PAST,
                true
        ));
    }

    public List<Event> getAll() {
        return new ArrayList<>(events);
    }

    public List<Event> getByType(EventType type) {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getType() == type) {
                result.add(e);
            }
        }
        return result;
    }
}

