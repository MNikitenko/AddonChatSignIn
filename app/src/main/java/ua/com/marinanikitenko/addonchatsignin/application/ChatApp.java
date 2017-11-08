package ua.com.marinanikitenko.addonchatsignin.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by Marina on 11.07.2017.
 */

public class ChatApp extends Application {
    private static ChatApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
