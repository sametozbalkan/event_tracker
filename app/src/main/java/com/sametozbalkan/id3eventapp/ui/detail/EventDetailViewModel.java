package com.sametozbalkan.id3eventapp.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sametozbalkan.id3eventapp.data.model.Comment;
import com.sametozbalkan.id3eventapp.data.model.Event;
import com.sametozbalkan.id3eventapp.data.model.Participant;
import com.sametozbalkan.id3eventapp.data.repository.CommentRepository;
import com.sametozbalkan.id3eventapp.data.repository.EventRepository;
import com.sametozbalkan.id3eventapp.data.repository.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;

public class EventDetailViewModel extends ViewModel {

    private final EventRepository repository = new EventRepository();
    private final CommentRepository commentRepository = new CommentRepository();
    private final ParticipantRepository participantRepository = new ParticipantRepository();

    private final MutableLiveData<Event> eventLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Comment>> comments = new MutableLiveData<>();
    private final MutableLiveData<List<Participant>> allParticipants = new MutableLiveData<>();
    private final MutableLiveData<List<Participant>> filteredParticipants = new MutableLiveData<>();

    public void setEventId(long id) {
        Event event = repository.getById(id);
        eventLiveData.setValue(event);

        comments.setValue(
                new ArrayList<>(commentRepository.getComments(id))
        );

        List<Participant> list =
                participantRepository.getParticipantsForEvent(id);

        allParticipants.setValue(list);
        filteredParticipants.setValue(list);
    }

    public LiveData<Event> getEvent() {
        return eventLiveData;
    }

    public LiveData<List<Comment>> getComments() {
        return comments;
    }

    public LiveData<List<Participant>> getParticipants() {
        return filteredParticipants;
    }

    public void filterParticipants(String query) {
        List<Participant> source = allParticipants.getValue();
        if (source == null) return;

        if (query == null || query.trim().isEmpty()) {
            filteredParticipants.setValue(source);
            return;
        }

        String q = query.toLowerCase();
        List<Participant> filtered = new ArrayList<>();

        for (Participant p : source) {
            if (p.getName().toLowerCase().contains(q) ||
                    p.getRole().toLowerCase().contains(q)) {
                filtered.add(p);
            }
        }

        filteredParticipants.setValue(filtered);
    }

    public void addComment(String message) {
        if (message == null || message.trim().isEmpty()) return;

        Event event = eventLiveData.getValue();
        if (event == null) return;

        Comment newComment = new Comment(
                "You",
                message,
                "Just now",
                0
        );

        commentRepository.addComment(event.getId(), newComment);

        comments.setValue(
                new ArrayList<>(commentRepository.getComments(event.getId()))
        );
    }
}
