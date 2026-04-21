package com.qashqai.cartool;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NissanToolsActivity extends AppCompatActivity {

    private Switch switchHotspot, switchBluetooth, switchTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nissan_tools);

        switchHotspot = findViewById(R.id.switch_auto_hotspot);
        switchBluetooth = findViewById(R.id.switch_bluetooth_restart);
        switchTheme = findViewById(R.id.switch_headlight_theme);
        Button btnExportLogs = findViewById(R.id.btn_export_system_logs);
        Button btnRestart = findViewById(R.id.btn_restart_app);

        switchHotspot.setChecked(SharedPrefsHelper.getBoolean(this, SharedPrefsHelper.KEY_AUTO_HOTSPOT, false));
        switchBluetooth.setChecked(SharedPrefsHelper.getBoolean(this, SharedPrefsHelper.KEY_BLUETOOTH_RESTART, false));
        switchTheme.setChecked(SharedPrefsHelper.getBoolean(this, SharedPrefsHelper.KEY_HEADLIGHT_THEME, false));

        switchHotspot.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPrefsHelper.putBoolean(this, SharedPrefsHelper.KEY_AUTO_HOTSPOT, isChecked);
            Toast.makeText(this, isChecked ? "开机自动热点已启用" : "已禁用", Toast.LENGTH_SHORT).show();
        });

        switchBluetooth.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPrefsHelper.putBoolean(this, SharedPrefsHelper.KEY_BLUETOOTH_RESTART, isChecked);
            Toast.makeText(this, isChecked ? "蓝牙自动重启已启用" : "已禁用", Toast.LENGTH_SHORT).show();
        });

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPrefsHelper.putBoolean(this, SharedPrefsHelper.KEY_HEADLIGHT_THEME, isChecked);
            Toast.makeText(this, isChecked ? "大灯联动主题已启用" : "已禁用", Toast.LENGTH_SHORT).show();
        });

        btnExportLogs.setOnClickListener(v ->
                Toast.makeText(this, "导出系统日志（需实现）", Toast.LENGTH_SHORT).show());

        btnRestart.setOnClickListener(v -> {
            android.os.Process.killProcess(android.os.Process.myPid());
        });
    }
}
