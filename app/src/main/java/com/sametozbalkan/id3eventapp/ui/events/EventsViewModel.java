package com.sametozbalkan.id3eventapp.ui.events;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sametozbalkan.id3eventapp.data.model.Event;
import com.sametozbalkan.id3eventapp.data.model.EventType;
import com.sametozbalkan.id3eventapp.data.repository.EventRepository;

import java.util.List;

public class EventsViewModel extends ViewModel {

    private final EventRepository repository = new EventRepository();
    private final MutableLiveData<List<Event>> eventsLiveData = new MutableLiveData<>();

    public EventsViewModel() {
        showAll();
    }

    public LiveData<List<Event>> getEvents() {
        return eventsLiveData;
    }

    public void showAll() {
        eventsLiveData.setValue(repository.getAll());
    }

    public void filterByType(EventType type) {
        eventsLiveData.setValue(repository.getByType(type));
    }
}