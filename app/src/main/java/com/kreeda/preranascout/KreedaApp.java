package com.kreeda.preranascout;

import android.app.Application;
import com.kreeda.preranascout.data.database.AppDatabase;

public class KreedaApp extends Application {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = AppDatabase.getInstance(this);
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
