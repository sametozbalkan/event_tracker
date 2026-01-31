package com.sametozbalkan.id3eventapp.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sametozbalkan.id3eventapp.data.model.Comment;
import com.sametozbalkan.id3eventapp.data.model.Participant;
import com.sametozbalkan.id3eventapp.data.repository.CommentRepository;
import com.sametozbalkan.id3eventapp.data.repository.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;

public class EventDetailViewModel extends ViewModel {

    private final CommentRepository commentRepository =
            new CommentRepository();

    private final ParticipantRepository participantRepository =
            new ParticipantRepository();

    private final MutableLiveData<Long> eventId = new MutableLiveData<>();

    private final MutableLiveData<List<Comment>> comments =
            new MutableLiveData<>();

    private final MutableLiveData<List<Participant>> allParticipants =
            new MutableLiveData<>();

    private final MutableLiveData<List<Participant>> filteredParticipants =
            new MutableLiveData<>();

    public void setEventId(long id) {
        eventId.setValue(id);
        loadData(id);
    }

    private void loadData(long id) {
        comments.setValue(
                new ArrayList<>(
                        commentRepository.getComments(id)
                )
        );

        List<Participant> list =
                participantRepository.getParticipantsForEvent(id);

        allParticipants.setValue(list);
        filteredParticipants.setValue(list);
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

        Long id = eventId.getValue();
        if (id == null) return;

        Comment newComment = new Comment(
                "You",
                message,
                "Just now",
                0
        );

        commentRepository.addComment(id, newComment);

        comments.setValue(
                new ArrayList<>(
                        commentRepository.getComments(id)
                )
        );
    }
}
