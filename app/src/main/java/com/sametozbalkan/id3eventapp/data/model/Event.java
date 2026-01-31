package com.sametozbalkan.id3eventapp.data.model;

public class Event {

    private final long id;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final String hoursAgo;
    private final String eventDate;
    private final EventType type;
    private boolean saved;

    public Event(
            long id,
            String title,
            String description,
            String imageUrl,
            String hoursAgo,
            String eventDate,
            EventType type,
            boolean saved
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.hoursAgo = hoursAgo;
        this.eventDate = eventDate;
        this.type = type;
        this.saved = saved;
    }

    public long getId() {return id;}
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getHoursAgo() { return hoursAgo; }
    public String getEventDate() { return eventDate; }
    public EventType getType() { return type; }

    public boolean isSaved() { return saved; }
    public void setSaved(boolean saved) { this.saved = saved; }
}
