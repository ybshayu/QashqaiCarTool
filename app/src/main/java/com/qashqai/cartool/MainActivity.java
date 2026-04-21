package com.qashqai.cartool;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardView cardLogs = findViewById(R.id.card_logs);
        CardView cardKey = findViewById(R.id.card_key);
        CardView cardTools = findViewById(R.id.card_tools);
        CardView cardSettings = findViewById(R.id.card_settings);

        cardLogs.setOnClickListener(v ->
                startActivity(new Intent(this, LogReaderActivity.class)));

        cardKey.setOnClickListener(v ->
                startActivity(new Intent(this, KeyMonitorActivity.class)));

        cardTools.setOnClickListener(v ->
                startActivity(new Intent(this, NissanToolsActivity.class)));

        cardSettings.setOnClickListener(v ->
                startActivity(new Intent(this, SettingsActivity.class)));
    }
}
