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
    private final MutableLiveData<EventType> selectedFilter = new MutableLiveData<>();

    public EventsViewModel() {
        setFilter(null);
    }

    public LiveData<List<Event>> getEvents() {
        return eventsLiveData;
    }

    public LiveData<EventType> getSelectedFilter() {
        return selectedFilter;
    }

    public void setFilter(EventType type) {
        selectedFilter.setValue(type);

        if (type == null) {
            eventsLiveData.setValue(repository.getAll());
        } else {
            eventsLiveData.setValue(repository.getByType(type));
        }
    }
}
