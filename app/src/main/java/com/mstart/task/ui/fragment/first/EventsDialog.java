package com.mstart.task.ui.fragment.first;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mstart.task.Room.Events;
import com.mstart.task.Room.EventsDatabase;
import com.mstart.task.databinding.ContentForDialogBinding;
import com.mstart.task.model.Data;
import com.mstart.task.ui.fragment.second.SecondScreenViewModel;

import java.util.Calendar;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class EventsDialog extends AppCompatDialogFragment {

    private final String TAG = "EventsDialog";
    private Data eventData;

    private ContentForDialogBinding binding;
    private EventsDatabase eventsDatabase;
    private Events mEvents;

    private SecondScreenViewModel screenViewModel;
    private RecyclerView recyclerView;
    private AlertDialog.Builder builder;

    //When submitting event data, an event is added
    public EventsDialog(Data data) {
        eventData = data;
    }

    //update event
    public EventsDialog(Events events, SecondScreenViewModel screenViewModel, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.screenViewModel = screenViewModel;
        mEvents = events;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        init();
        //When submitting event data, an event is added
        if (eventData != null) {

            builder.setTitle("Add event");

            binding.setModel(eventData);

            binding.btnSaveDialog.setOnClickListener(v -> {
                saveDataInRoom();
                dismiss();

            });
        }
        //update event
        else {
            builder.setTitle("update event");

            binding.tvGregorian.setText(mEvents.getGregorianDate());
            binding.tvHijri.setText(mEvents.getHijriDate());
            binding.btnSaveDialog.setOnClickListener(v -> {

                updateDataInRoom();

                recyclerView.getAdapter().notifyDataSetChanged();
                dismiss();
            });
        }

        return builder.create();
    }

    private void init() {
        eventsDatabase = EventsDatabase.getInstance(getContext());
        binding = ContentForDialogBinding.inflate(getLayoutInflater());
        builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());
    }

    private void updateDataInRoom() {
        mEvents.setDescription(binding.etDescriptionDialog.getText().toString());
        mEvents.setName(binding.etNameEventDialog.getText().toString());
        screenViewModel.update(mEvents);
    }

    private void saveDataInRoom() {
        eventsDatabase.eventsDao().insertEvents(
                new Events( binding.etNameEventDialog.getText().toString(),
                        binding.etDescriptionDialog.getText().toString(),
                        binding.tvGregorian.getText().toString(),
                        binding.tvHijri.getText().toString(),
                        Calendar.getInstance().getTime().toString()))
                //work in background u should work in anther thread
                .subscribeOn(Schedulers.computation())//s1
                .subscribe(new CompletableObserver() {//s2
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    public void setEventData(Data eventData) {
        this.eventData = eventData;
    }
}
