package com.qashqai.cartool;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class KeyMonitorActivity extends AppCompatActivity
        implements KeyAccessibilityService.KeyEventListener {

    private RecyclerView recyclerView;
    private KeyHistoryAdapter adapter;
    private TextView tvServiceStatus;
    private Button btnOpenService;
    private final List<KeyHistoryItem> historyItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_monitor);

        tvServiceStatus = findViewById(R.id.tv_service_status);
        btnOpenService = findViewById(R.id.btn_open_accessibility);
        recyclerView = findViewById(R.id.recycler_key_history);
        Button btnClear = findViewById(R.id.btn_clear_history);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new KeyHistoryAdapter(historyItems);
        recyclerView.setAdapter(adapter);

        btnOpenService.setOnClickListener(v -> openAccessibilitySettings());
        btnClear.setOnClickListener(v -> {
            historyItems.clear();
            adapter.notifyDataSetChanged();
        });

        KeyAccessibilityService.setKeyEventListener(this);
    }

    private void openAccessibilitySettings() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
        Toast.makeText(this, "请找到“逍客车机工具”并开启服务", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateServiceStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyAccessibilityService.setKeyEventListener(null);
    }

    private void updateServiceStatus() {
        boolean enabled = Utils.isAccessibilityServiceEnabled(this, KeyAccessibilityService.class);
        tvServiceStatus.setText(enabled ? R.string.service_enabled : R.string.service_disabled);
        btnOpenService.setVisibility(enabled ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onKeyEvent(int keyCode, int action) {
        String actionStr = (action == KeyEvent.ACTION_DOWN) ? "按下" : "释放";
        String keyName = KeyCodeMapper.getKeyName(keyCode);

        final KeyHistoryItem item = new KeyHistoryItem(
                System.currentTimeMillis(),
                keyCode,
                keyName,
                actionStr
        );

        runOnUiThread(() -> {
            historyItems.add(0, item);
            if (historyItems.size() > 100) {
                historyItems.remove(historyItems.size() - 1);
            }
            adapter.notifyDataSetChanged();
        });
    }

    static class KeyHistoryItem {
        long timestamp;
        int keyCode;
        String keyName;
        String action;

        KeyHistoryItem(long ts, int code, String name, String act) {
            timestamp = ts;
            keyCode = code;
            keyName = name;
            action = act;
        }
    }

    class KeyHistoryAdapter extends RecyclerView.Adapter<KeyHistoryAdapter.ViewHolder> {
        private final List<KeyHistoryItem> items;
        KeyHistoryAdapter(List<KeyHistoryItem> items) { this.items = items; }

        @Override
        public ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_key_history, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            KeyHistoryItem item = items.get(position);
            holder.tvKeyName.setText(item.keyName + " (" + item.keyCode + ")");
            holder.tvAction.setText(item.action);
            holder.tvTime.setText(android.text.format.DateFormat.format("HH:mm:ss", item.timestamp));
        }

        @Override public int getItemCount() { return items.size(); }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvKeyName, tvAction, tvTime;
            ViewHolder(View itemView) {
                super(itemView);
                tvKeyName = itemView.findViewById(R.id.tv_key_name);
                tvAction = itemView.findViewById(R.id.tv_action);
                tvTime = itemView.findViewById(R.id.tv_time);
            }
        }
    }
}
