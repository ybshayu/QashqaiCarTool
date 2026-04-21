package com.qashqai.cartool;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class KeyAccessibilityService extends AccessibilityService {

    public interface KeyEventListener {
        void onKeyEvent(int keyCode, int action);
    }

    private static KeyEventListener listener;

    public static void setKeyEventListener(KeyEventListener l) {
        listener = l;
    }

    @Override
    public void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }

    @Override
    public boolean onKeyEvent(KeyEvent event) {
        if (listener != null) {
            listener.onKeyEvent(event.getKeyCode(), event.getAction());
        }
        return false;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {}
}
