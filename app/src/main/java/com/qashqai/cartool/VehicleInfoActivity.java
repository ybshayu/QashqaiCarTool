package com.qashqai.cartool;

import android.os.Bundle;
import android.widget.ImageButton;

public class VehicleInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        ImageButton btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) btnBack.setOnClickListener(v -> finish());
        setupThemeToggle(R.id.btn_theme_toggle);
    }
}
