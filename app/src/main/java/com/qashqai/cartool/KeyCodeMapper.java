package com.qashqai.cartool;

import android.util.SparseArray;

public class KeyCodeMapper {
    private static final SparseArray<String> KEY_MAP = new SparseArray<>();

    static {
        KEY_MAP.put(3, "HOME");
        KEY_MAP.put(4, "BACK");
        KEY_MAP.put(19, "DPAD_UP");
        KEY_MAP.put(20, "DPAD_DOWN");
        KEY_MAP.put(21, "DPAD_LEFT");
        KEY_MAP.put(22, "DPAD_RIGHT");
        KEY_MAP.put(23, "DPAD_CENTER");
        KEY_MAP.put(24, "VOLUME_UP");
        KEY_MAP.put(25, "VOLUME_DOWN");
        KEY_MAP.put(26, "POWER");
        KEY_MAP.put(82, "MENU");

        KEY_MAP.put(220, "NISSAN_VOICE");
        KEY_MAP.put(221, "NISSAN_PHONE");
        KEY_MAP.put(222, "NISSAN_MEDIA");
        KEY_MAP.put(223, "NISSAN_NAV");
        for (int i = 224; i <= 253; i++) {
            KEY_MAP.put(i, "NISSAN_KEY_" + i);
        }
    }

    public static String getKeyName(int keyCode) {
        String name = KEY_MAP.get(keyCode);
        return name != null ? name : "KEY_" + keyCode;
    }
}
