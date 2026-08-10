package com.qashqai.cartool;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;

/**
 * Base activity that applies the saved day/night theme and provides a
 * shared "theme toggle" helper used by every screen.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static int sAppliedMode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyGlobalNightMode(SharedPrefsHelper.getNightMode(this));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int target = SharedPrefsHelper.getNightMode(this)
                ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        if (sAppliedMode != target) {
            applyGlobalNightMode(SharedPrefsHelper.getNightMode(this));
            recreate();
        }
    }

    /** Apply night mode globally (shared by all activities and the toggle). */
    public static void applyGlobalNightMode(boolean night) {
        int mode = night ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(mode);
        sAppliedMode = mode;
    }

    /** Wire the standard header theme-toggle button (shows the icon of the mode it switches TO). */
    protected void setupThemeToggle(int buttonId) {
        ImageButton btn = findViewById(buttonId);
        if (btn == null) return;
        updateToggleIcon(btn);
        btn.setOnClickListener(v -> {
            boolean night = SharedPrefsHelper.getNightMode(this);
            SharedPrefsHelper.setNightMode(this, !night);
            applyGlobalNightMode(!night);
            recreate();
        });
    }

    protected void updateToggleIcon(ImageButton btn) {
        boolean night = SharedPrefsHelper.getNightMode(this);
        btn.setImageDrawable(AppCompatResources.getDrawable(
                this, night ? R.drawable.ic_sun : R.drawable.ic_moon));
    }
}
