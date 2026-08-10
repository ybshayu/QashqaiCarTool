package com.qashqai.cartool;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(getString(R.string.version_info));

        Switch switchNight = findViewById(R.id.switch_night);
        switchNight.setChecked(SharedPrefsHelper.getNightMode(this));
        switchNight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPrefsHelper.setNightMode(this, isChecked);
            BaseActivity.applyGlobalNightMode(isChecked);
            recreate();
        });

        findViewById(R.id.card_vehicle_info).setOnClickListener(v ->
                startActivity(new Intent(this, VehicleInfoActivity.class)));

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
        setupThemeToggle(R.id.btn_theme_toggle);
    }
}
