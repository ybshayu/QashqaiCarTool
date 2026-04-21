package com.qashqai.cartool.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogcatReader {

    private static final String TAG = "LogcatReader";

    public interface LogListener {
        void onNewLog(String logLine);
    }

    private LogListener listener;
    private Process logcatProcess;
    private HandlerThread handlerThread;
    private Handler backgroundHandler;
    private volatile boolean isReading = false;

    public void setLogListener(LogListener listener) {
        this.listener = listener;
    }

    public void startReading() {
        if (isReading) return;
        isReading = true;

        handlerThread = new HandlerThread("LogcatThread");
        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper());

        backgroundHandler.post(() -> {
            try {
                ProcessBuilder builder = new ProcessBuilder("logcat", "-v", "time");
                logcatProcess = builder.start();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(logcatProcess.getInputStream()));

                String line;
                while (isReading && (line = reader.readLine()) != null) {
                    if (listener != null) {
                        listener.onNewLog(line);
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Error reading logcat", e);
            } finally {
                cleanupProcess();
            }
        });
    }

    public void stopReading() {
        isReading = false;
        cleanupProcess();
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
    }

    private void cleanupProcess() {
        if (logcatProcess != null) {
            logcatProcess.destroy();
            logcatProcess = null;
        }
    }

    public boolean isReading() {
        return isReading;
    }
}
