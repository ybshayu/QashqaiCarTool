package com.qashqai.cartool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends BaseActivity {

    private TextView tvClock;
    private final Handler clockHandler = new Handler();
    private final Runnable clockTick = new Runnable() {
        @Override
        public void run() {
            if (tvClock != null) {
                tvClock.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            }
            clockHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cardLogs = findViewById(R.id.card_logs);
        CardView cardKey = findViewById(R.id.card_key);
        CardView cardTools = findViewById(R.id.card_tools);
        CardView cardSettings = findViewById(R.id.card_settings);
        CardView cardVehicle = findViewById(R.id.card_vehicle);

        cardLogs.setOnClickListener(v ->
                startActivity(new Intent(this, LogReaderActivity.class)));
        cardKey.setOnClickListener(v ->
                startActivity(new Intent(this, KeyMonitorActivity.class)));
        cardTools.setOnClickListener(v ->
                startActivity(new Intent(this, NissanToolsActivity.class)));
        cardSettings.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));
        cardVehicle.setOnClickListener(v ->
                startActivity(new Intent(this, VehicleInfoActivity.class)));

        tvClock = findViewById(R.id.tv_clock);
        setupThemeToggle(R.id.btn_theme_toggle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        clockTick.run();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clockHandler.removeCallbacks(clockTick);
    }
}
