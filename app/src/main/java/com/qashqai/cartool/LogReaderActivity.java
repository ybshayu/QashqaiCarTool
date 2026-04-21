package com.qashqai.cartool;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.qashqai.cartool.adapters.LogAdapter;
import com.qashqai.cartool.utils.LogcatReader;
import java.util.ArrayList;
import java.util.List;

public class LogReaderActivity extends AppCompatActivity implements LogcatReader.LogListener {

    private static final int PERMISSION_REQUEST_CODE = 100;

    private RecyclerView recyclerView;
    private LogAdapter adapter;
    private EditText etSearch;
    private TextView tvStatus;
    private Button btnStart, btnStop, btnClear, btnAutoScroll;

    private LogcatReader logcatReader;
    private final List<String> allLogs = new ArrayList<>();
    private final List<String> filteredLogs = new ArrayList<>();
    private String currentFilter = "";
    private boolean autoScroll = true;
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_reader);

        initViews();
        checkPermissions();

        logcatReader = new LogcatReader();
        logcatReader.setLogListener(this);
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_logs);
        etSearch = findViewById(R.id.et_search);
        tvStatus = findViewById(R.id.tv_status);
        btnStart = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        btnClear = findViewById(R.id.btn_clear);
        btnAutoScroll = findViewById(R.id.btn_auto_scroll);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LogAdapter(filteredLogs);
        recyclerView.setAdapter(adapter);

        btnStart.setOnClickListener(v -> startReading());
        btnStop.setOnClickListener(v -> stopReading());
        btnClear.setOnClickListener(v -> clearLogs());

        btnAutoScroll.setOnClickListener(v -> {
            autoScroll = !autoScroll;
            btnAutoScroll.setText(autoScroll ? "自动滚动:开" : "自动滚动:关");
        });

        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) {
                currentFilter = s.toString();
                applyFilter();
            }
        });
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void startReading() {
        if (logcatReader.isReading()) {
            Toast.makeText(this, "日志已在读取中", Toast.LENGTH_SHORT).show();
            return;
        }
        logcatReader.startReading();
        updateStatus("读取中...");
    }

    private void stopReading() {
        logcatReader.stopReading();
        updateStatus("已停止");
    }

    private void clearLogs() {
        allLogs.clear();
        applyFilter();
        updateCount();
        updateStatus("已清空");
    }

    @Override
    public void onNewLog(String logLine) {
        allLogs.add(logLine);
        if (TextUtils.isEmpty(currentFilter) || logLine.toLowerCase().contains(currentFilter.toLowerCase())) {
            filteredLogs.add(logLine);
        }
        mainHandler.post(() -> {
            adapter.notifyItemInserted(filteredLogs.size() - 1);
            if (autoScroll) {
                recyclerView.scrollToPosition(filteredLogs.size() - 1);
            }
            updateCount();
        });
    }

    private void applyFilter() {
        filteredLogs.clear();
        if (TextUtils.isEmpty(currentFilter)) {
            filteredLogs.addAll(allLogs);
        } else {
            String lowerFilter = currentFilter.toLowerCase();
            for (String log : allLogs) {
                if (log.toLowerCase().contains(lowerFilter)) {
                    filteredLogs.add(log);
                }
            }
        }
        adapter.notifyDataSetChanged();
        if (autoScroll && !filteredLogs.isEmpty()) {
            recyclerView.scrollToPosition(filteredLogs.size() - 1);
        }
        updateCount();
    }

    private void updateCount() {
        tvStatus.setText(getString(R.string.log_count, filteredLogs.size()));
    }

    private void updateStatus(String status) {
        tvStatus.setText(status);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logcatReader.stopReading();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "存储权限已授予", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
