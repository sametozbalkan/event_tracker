package com.sametozbalkan.id3eventapp.data.repository;

import com.sametozbalkan.id3eventapp.data.model.Participant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticipantRepository {

    private final Map<Long, List<Participant>> participantsByEvent = new HashMap<>();

    public ParticipantRepository() {

        participantsByEvent.put(1L, List.of(
                new Participant(
                        "John Doe",
                        "Speaker",
                        "https://i.pravatar.cc/150?img=1"
                ),
                new Participant(
                        "Alice Smith",
                        "Attendee",
                        "https://i.pravatar.cc/150?img=2"
                )
        ));

        participantsByEvent.put(2L, List.of(
                new Participant(
                        "Mike Runner",
                        "Organizer",
                        "https://i.pravatar.cc/150?img=3"
                ),
                new Participant(
                        "Sarah Miles",
                        "Participant",
                        "https://i.pravatar.cc/150?img=4"
                ),
                new Participant(
                        "Tom Speed",
                        "Participant",
                        "https://i.pravatar.cc/150?img=5"
                )
        ));

        participantsByEvent.put(3L, List.of(
                new Participant(
                        "Gallery Owner",
                        "Host",
                        "https://i.pravatar.cc/150?img=6"
                ),
                new Participant(
                        "Curator Jane",
                        "Curator",
                        "https://i.pravatar.cc/150?img=7"
                )
        ));
    }

    public List<Participant> getParticipantsForEvent(long eventId) {
        return participantsByEvent.getOrDefault(eventId, new ArrayList<>());
    }
}
