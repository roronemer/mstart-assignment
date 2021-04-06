package com.mstart.task.ui.fragment.second;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.mstart.task.Room.Events;
import com.mstart.task.repository.EventsRepository;

import java.util.List;

public class SecondScreenViewModel extends AndroidViewModel {
    private final String TAG = "ExperienceViewModel";

    private EventsRepository eventsRepository;

    public LiveData<List<Events>> eventsMutableLiveData = new MediatorLiveData<>();

    public SecondScreenViewModel(@NonNull Application application) {
        super(application);

        eventsRepository = new EventsRepository(application);
        eventsMutableLiveData = eventsRepository.getAllEvents();
    }

    public void insert(Events events) {
        eventsRepository.insert(events);
    }

    public void update(Events events) {
        eventsRepository.update(events);
    }

    public void delete(Events events) {
        eventsRepository.delete(events);
    }

    public LiveData<List<Events>> getAllEvents() {
        return eventsMutableLiveData;
    }
}
