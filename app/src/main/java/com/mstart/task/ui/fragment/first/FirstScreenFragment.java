package com.mstart.task.ui.fragment.first;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.mstart.task.dao.HijriRemoteDao;
import com.mstart.task.databinding.ContentForDialogBinding;
import com.mstart.task.databinding.FragmentFirstScreenBinding;
import com.mstart.task.model.RemoteConvert;
import com.mstart.task.network.HttpStatus;

import java.util.Calendar;

public class FirstScreenFragment extends Fragment {


    private static final String TAG = "FirstScreenFragment";
    private FragmentFirstScreenBinding binding;
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private RemoteConvert remoteConvert;

    private String date;

    private ContentForDialogBinding dialogBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFirstScreenBinding.inflate(getLayoutInflater());

        dialogBinding = ContentForDialogBinding.inflate(LayoutInflater.from(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatePickerDialog();
        initButtonConvert();
        initSaveDate();
        initButtonGoToEvents();
    }

    private void initButtonGoToEvents() {
        binding.btnGoToEvents.setOnClickListener(v -> {
            NavDirections action = FirstScreenFragmentDirections.actionFirstScreenFragmentToSecondFragment();
            Navigation.findNavController(getView()).navigate(action);
        });
    }

    private void initButtonConvert() {

        binding.btnConvert.setOnClickListener(v -> {
            if (date == null) {
                Toast.makeText(getContext(), "Choose a date", Toast.LENGTH_SHORT).show();
                return;
            }
            getHijeryDateRemote(date);
        });
    }

    private void initSaveDate() {
        binding.btnSave.setOnClickListener(v -> {
            if (remoteConvert == null || date == null) {
                Toast.makeText(getContext(), "Choose a date", Toast.LENGTH_SHORT).show();
                return;
            }
            if (remoteConvert.getData() == null) {
                Toast.makeText(getContext(), "Choose a date", Toast.LENGTH_SHORT).show();
            } else {
                EventsDialog exampleDialog = new EventsDialog(remoteConvert.getData());
                exampleDialog.show(getActivity().getSupportFragmentManager(), "exampleDialog");
            }
        });

    }

    private void initDatePickerDialog() {
        Log.d(TAG, "onClick: " + " button dialog2");

        binding.btnGregorian.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getContext(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth, mOnDateSetListener
                    , year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });
        mOnDateSetListener = (view, year, month, dayOfMonth) -> {
            date = dayOfMonth + "-" + month + "-" + year;
            binding.btnGregorian.setText(date);
            getHijeryDateRemote(date);
        };
    }


    public void getHijeryDateRemote(String date) {

        HijriRemoteDao.getInstance().getData(date).enqueue(result -> {
            switch (result.getStatus()) {
                case HttpStatus.SUCCESS:
                    Log.d(TAG, "getRemoteHome: Success");
                    this.remoteConvert = result.getResult();
                    binding.setModel(this.remoteConvert);
                    break;
                default:
                    Log.d(TAG, " not  Success  " + result.getCode() + " " + result.getThrowable());
            }
        });
    }

}