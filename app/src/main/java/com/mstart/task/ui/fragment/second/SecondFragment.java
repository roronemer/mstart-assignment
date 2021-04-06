package com.mstart.task.ui.fragment.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.mstart.task.databinding.FragmentSecondBinding;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private EventsAdapter eventsAdapter;


    private SecondScreenViewModel screenViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getRemoteAllEvents();

    }

    private void init() {
        screenViewModel = ViewModelProviders.of(this).get(SecondScreenViewModel.class);

        eventsAdapter = new EventsAdapter(getFragmentManager(), screenViewModel);


    }

    private void getRemoteAllEvents() {
        screenViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
            eventsAdapter.setEventsList(events);
            binding.rvEvents.setAdapter(eventsAdapter);
        });
    }
}