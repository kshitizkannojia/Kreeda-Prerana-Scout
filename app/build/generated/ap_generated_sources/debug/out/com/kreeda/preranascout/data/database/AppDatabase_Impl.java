package com.kreeda.preranascout.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.kreeda.preranascout.data.dao.AthleteDao;
import com.kreeda.preranascout.data.dao.AthleteDao_Impl;
import com.kreeda.preranascout.data.dao.TrialRecordDao;
import com.kreeda.preranascout.data.dao.TrialRecordDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile AthleteDao _athleteDao;

  private volatile TrialRecordDao _trialRecordDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `athletes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `age` INTEGER NOT NULL, `studentClass` TEXT, `school` TEXT, `primarySport` TEXT, `districtLevelReady` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trial_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `athleteId` INTEGER NOT NULL, `eventType` TEXT, `value` REAL NOT NULL, `unit` TEXT, `trialDate` INTEGER NOT NULL, `notes` TEXT, FOREIGN KEY(`athleteId`) REFERENCES `athletes`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_trial_records_athleteId` ON `trial_records` (`athleteId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7b191baf091cd402905d22ced37e0736')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `athletes`");
        db.execSQL("DROP TABLE IF EXISTS `trial_records`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsAthletes = new HashMap<String, TableInfo.Column>(8);
        _columnsAthletes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAthletes.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAthletes.put("age", new TableInfo.Column("age", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAthletes.put("studentClass", new TableInfo.Column("studentClass", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAthletes.put("school", new TableInfo.Column("school", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAthletes.put("primarySport", new TableInfo.Column("primarySport", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAthletes.put("districtLevelReady", new TableInfo.Column("districtLevelReady", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAthletes.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAthletes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAthletes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAthletes = new TableInfo("athletes", _columnsAthletes, _foreignKeysAthletes, _indicesAthletes);
        final TableInfo _existingAthletes = TableInfo.read(db, "athletes");
        if (!_infoAthletes.equals(_existingAthletes)) {
          return new RoomOpenHelper.ValidationResult(false, "athletes(com.kreeda.preranascout.data.entity.Athlete).\n"
                  + " Expected:\n" + _infoAthletes + "\n"
                  + " Found:\n" + _existingAthletes);
        }
        final HashMap<String, TableInfo.Column> _columnsTrialRecords = new HashMap<String, TableInfo.Column>(7);
        _columnsTrialRecords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrialRecords.put("athleteId", new TableInfo.Column("athleteId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrialRecords.put("eventType", new TableInfo.Column("eventType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrialRecords.put("value", new TableInfo.Column("value", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrialRecords.put("unit", new TableInfo.Column("unit", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrialRecords.put("trialDate", new TableInfo.Column("trialDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTrialRecords.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTrialRecords = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTrialRecords.add(new TableInfo.ForeignKey("athletes", "CASCADE", "NO ACTION", Arrays.asList("athleteId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTrialRecords = new HashSet<TableInfo.Index>(1);
        _indicesTrialRecords.add(new TableInfo.Index("index_trial_records_athleteId", false, Arrays.asList("athleteId"), Arrays.asList("ASC")));
        final TableInfo _infoTrialRecords = new TableInfo("trial_records", _columnsTrialRecords, _foreignKeysTrialRecords, _indicesTrialRecords);
        final TableInfo _existingTrialRecords = TableInfo.read(db, "trial_records");
        if (!_infoTrialRecords.equals(_existingTrialRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "trial_records(com.kreeda.preranascout.data.entity.TrialRecord).\n"
                  + " Expected:\n" + _infoTrialRecords + "\n"
                  + " Found:\n" + _existingTrialRecords);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7b191baf091cd402905d22ced37e0736", "8526f4375a931ff32cdc75d273815fe7");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "athletes","trial_records");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `athletes`");
      _db.execSQL("DELETE FROM `trial_records`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AthleteDao.class, AthleteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TrialRecordDao.class, TrialRecordDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public AthleteDao athleteDao() {
    if (_athleteDao != null) {
      return _athleteDao;
    } else {
      synchronized(this) {
        if(_athleteDao == null) {
          _athleteDao = new AthleteDao_Impl(this);
        }
        return _athleteDao;
      }
    }
  }

  @Override
  public TrialRecordDao trialRecordDao() {
    if (_trialRecordDao != null) {
      return _trialRecordDao;
    } else {
      synchronized(this) {
        if(_trialRecordDao == null) {
          _trialRecordDao = new TrialRecordDao_Impl(this);
        }
        return _trialRecordDao;
      }
    }
  }
}
