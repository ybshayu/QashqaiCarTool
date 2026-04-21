package com.qashqai.cartool;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(getString(R.string.version_info));
    }
}
