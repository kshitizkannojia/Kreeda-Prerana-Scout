package com.kreeda.preranascout.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kreeda.preranascout.data.dao.AthleteDao;
import com.kreeda.preranascout.data.dao.TrialRecordDao;
import com.kreeda.preranascout.data.entity.Athlete;
import com.kreeda.preranascout.data.entity.TrialRecord;

@Database(entities = {Athlete.class, TrialRecord.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract AthleteDao athleteDao();
    public abstract TrialRecordDao trialRecordDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "kreeda_prerana_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
