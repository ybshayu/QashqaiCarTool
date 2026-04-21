package com.qashqai.cartool;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

public class Utils {
    public static boolean isAccessibilityServiceEnabled(Context context, Class<?> serviceClass) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                String serviceFullName = context.getPackageName() + "/" + serviceClass.getName();
                return services.contains(serviceFullName) || services.contains(serviceClass.getName());
            }
        }
        return false;
    }
}
