package com.example.lampara;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class temporizador extends Toolbarclass {
    private NumberPicker numberPickerHours, numberPickerMinutes, numberPickerSeconds;
    private TextView selectedTimeTextView;
    private Button startButton, stopButton;
    private CardView notificationCardView;

    private BroadcastReceiver timerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long timeRemaining = intent.getLongExtra(TimerService.EXTRA_TIME_REMAINING, 0);
            updateCountDownText(timeRemaining);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporizador);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setupToolbar(myToolbar);

        numberPickerHours = findViewById(R.id.numberPickerHours);
        numberPickerMinutes = findViewById(R.id.numberPickerMinutes);
        numberPickerSeconds = findViewById(R.id.numberPickerSeconds);
        selectedTimeTextView = findViewById(R.id.selectedTimeTextView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        notificationCardView = findViewById(R.id.notificationCardView);

        numberPickerHours.setMinValue(0);
        numberPickerHours.setMaxValue(23);
        numberPickerMinutes.setMinValue(0);
        numberPickerMinutes.setMaxValue(59);
        numberPickerSeconds.setMinValue(0);
        numberPickerSeconds.setMaxValue(59);

        startButton.setOnClickListener(v -> startTimer());
        stopButton.setOnClickListener(v -> stopTimer());
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(timerReceiver, new IntentFilter(TimerService.ACTION_UPDATE_TIMER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(timerReceiver);
    }

    private void startTimer() {
        int hours = numberPickerHours.getValue();
        int minutes = numberPickerMinutes.getValue();
        int seconds = numberPickerSeconds.getValue();

        Intent intent = new Intent(this, TimerService.class);
        intent.putExtra("hours", hours);
        intent.putExtra("minutes", minutes);
        intent.putExtra("seconds", seconds);
        startService(intent);

    }


    private void stopTimer() {

        numberPickerHours.setValue(0);
        numberPickerMinutes.setValue(0);
        numberPickerSeconds.setValue(0);
        selectedTimeTextView.setText("00:00:00");
        Intent intent = new Intent(this, TimerService.class);
        stopService(intent);


    }
    private void showNotification() {
        notificationCardView.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText(long timeLeftInMillis) {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        selectedTimeTextView.setText(timeLeftFormatted);
    }
}
