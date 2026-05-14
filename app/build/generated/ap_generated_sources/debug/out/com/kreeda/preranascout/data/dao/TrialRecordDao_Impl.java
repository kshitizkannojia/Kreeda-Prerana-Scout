package com.kreeda.preranascout.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.kreeda.preranascout.data.entity.TrialRecord;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TrialRecordDao_Impl implements TrialRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TrialRecord> __insertionAdapterOfTrialRecord;

  private final EntityDeletionOrUpdateAdapter<TrialRecord> __deletionAdapterOfTrialRecord;

  public TrialRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTrialRecord = new EntityInsertionAdapter<TrialRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `trial_records` (`id`,`athleteId`,`eventType`,`value`,`unit`,`trialDate`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TrialRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getAthleteId());
        if (entity.getEventType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEventType());
        }
        statement.bindDouble(4, entity.getValue());
        if (entity.getUnit() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getUnit());
        }
        statement.bindLong(6, entity.getTrialDate());
        if (entity.getNotes() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getNotes());
        }
      }
    };
    this.__deletionAdapterOfTrialRecord = new EntityDeletionOrUpdateAdapter<TrialRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `trial_records` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TrialRecord entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public long insert(final TrialRecord record) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTrialRecord.insertAndReturnId(record);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertAll(final List<TrialRecord> records) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final List<Long> _result = __insertionAdapterOfTrialRecord.insertAndReturnIdsList(records);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final TrialRecord record) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTrialRecord.handle(record);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<TrialRecord>> getTrialsByAthlete(final int athleteId) {
    final String _sql = "SELECT * FROM trial_records WHERE athleteId = ? ORDER BY trialDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, athleteId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"trial_records"}, false, new Callable<List<TrialRecord>>() {
      @Override
      @Nullable
      public List<TrialRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAthleteId = CursorUtil.getColumnIndexOrThrow(_cursor, "athleteId");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfTrialDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<TrialRecord> _result = new ArrayList<TrialRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrialRecord _item;
            _item = new TrialRecord();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpAthleteId;
            _tmpAthleteId = _cursor.getInt(_cursorIndexOfAthleteId);
            _item.setAthleteId(_tmpAthleteId);
            final String _tmpEventType;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmpEventType = null;
            } else {
              _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
            }
            _item.setEventType(_tmpEventType);
            final double _tmpValue;
            _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
            _item.setValue(_tmpValue);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            _item.setUnit(_tmpUnit);
            final long _tmpTrialDate;
            _tmpTrialDate = _cursor.getLong(_cursorIndexOfTrialDate);
            _item.setTrialDate(_tmpTrialDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item.setNotes(_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<TrialRecord> getTrialsByAthleteList(final int athleteId) {
    final String _sql = "SELECT * FROM trial_records WHERE athleteId = ? ORDER BY trialDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, athleteId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAthleteId = CursorUtil.getColumnIndexOrThrow(_cursor, "athleteId");
      final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfTrialDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialDate");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<TrialRecord> _result = new ArrayList<TrialRecord>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TrialRecord _item;
        _item = new TrialRecord();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpAthleteId;
        _tmpAthleteId = _cursor.getInt(_cursorIndexOfAthleteId);
        _item.setAthleteId(_tmpAthleteId);
        final String _tmpEventType;
        if (_cursor.isNull(_cursorIndexOfEventType)) {
          _tmpEventType = null;
        } else {
          _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
        }
        _item.setEventType(_tmpEventType);
        final double _tmpValue;
        _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
        _item.setValue(_tmpValue);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        _item.setUnit(_tmpUnit);
        final long _tmpTrialDate;
        _tmpTrialDate = _cursor.getLong(_cursorIndexOfTrialDate);
        _item.setTrialDate(_tmpTrialDate);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TrialRecord> getTrialsByAthleteAndEvent(final int athleteId, final String eventType) {
    final String _sql = "SELECT * FROM trial_records WHERE athleteId = ? AND eventType = ? ORDER BY trialDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, athleteId);
    _argIndex = 2;
    if (eventType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventType);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAthleteId = CursorUtil.getColumnIndexOrThrow(_cursor, "athleteId");
      final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfTrialDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialDate");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<TrialRecord> _result = new ArrayList<TrialRecord>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TrialRecord _item;
        _item = new TrialRecord();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpAthleteId;
        _tmpAthleteId = _cursor.getInt(_cursorIndexOfAthleteId);
        _item.setAthleteId(_tmpAthleteId);
        final String _tmpEventType;
        if (_cursor.isNull(_cursorIndexOfEventType)) {
          _tmpEventType = null;
        } else {
          _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
        }
        _item.setEventType(_tmpEventType);
        final double _tmpValue;
        _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
        _item.setValue(_tmpValue);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        _item.setUnit(_tmpUnit);
        final long _tmpTrialDate;
        _tmpTrialDate = _cursor.getLong(_cursorIndexOfTrialDate);
        _item.setTrialDate(_tmpTrialDate);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<TrialRecord>> getTrialsByAthleteAndEventLive(final int athleteId,
      final String eventType) {
    final String _sql = "SELECT * FROM trial_records WHERE athleteId = ? AND eventType = ? ORDER BY trialDate ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, athleteId);
    _argIndex = 2;
    if (eventType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventType);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"trial_records"}, false, new Callable<List<TrialRecord>>() {
      @Override
      @Nullable
      public List<TrialRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAthleteId = CursorUtil.getColumnIndexOrThrow(_cursor, "athleteId");
          final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
          final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
          final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
          final int _cursorIndexOfTrialDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<TrialRecord> _result = new ArrayList<TrialRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TrialRecord _item;
            _item = new TrialRecord();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpAthleteId;
            _tmpAthleteId = _cursor.getInt(_cursorIndexOfAthleteId);
            _item.setAthleteId(_tmpAthleteId);
            final String _tmpEventType;
            if (_cursor.isNull(_cursorIndexOfEventType)) {
              _tmpEventType = null;
            } else {
              _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
            }
            _item.setEventType(_tmpEventType);
            final double _tmpValue;
            _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
            _item.setValue(_tmpValue);
            final String _tmpUnit;
            if (_cursor.isNull(_cursorIndexOfUnit)) {
              _tmpUnit = null;
            } else {
              _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
            }
            _item.setUnit(_tmpUnit);
            final long _tmpTrialDate;
            _tmpTrialDate = _cursor.getLong(_cursorIndexOfTrialDate);
            _item.setTrialDate(_tmpTrialDate);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item.setNotes(_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Double getBestPerformance(final int athleteId, final String eventType) {
    final String _sql = "SELECT MIN(value) FROM trial_records WHERE athleteId = ? AND eventType = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, athleteId);
    _argIndex = 2;
    if (eventType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventType);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Double _result;
      if (_cursor.moveToFirst()) {
        final Double _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getDouble(0);
        }
        _result = _tmp;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> getEventTypesForAthlete(final int athleteId) {
    final String _sql = "SELECT DISTINCT eventType FROM trial_records WHERE athleteId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, athleteId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final String _item;
        if (_cursor.isNull(0)) {
          _item = null;
        } else {
          _item = _cursor.getString(0);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<String>> getAllEventTypes() {
    final String _sql = "SELECT DISTINCT eventType FROM trial_records";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"trial_records"}, false, new Callable<List<String>>() {
      @Override
      @Nullable
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<TrialRecord> getLeaderboardForTimedEvent(final String eventType) {
    final String _sql = "SELECT t.* FROM trial_records t INNER JOIN (SELECT athleteId, MIN(value) as minVal FROM trial_records WHERE eventType = ? GROUP BY athleteId) best ON t.athleteId = best.athleteId AND t.value = best.minVal AND t.eventType = ? GROUP BY t.athleteId ORDER BY t.value ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (eventType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventType);
    }
    _argIndex = 2;
    if (eventType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventType);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAthleteId = CursorUtil.getColumnIndexOrThrow(_cursor, "athleteId");
      final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfTrialDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialDate");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<TrialRecord> _result = new ArrayList<TrialRecord>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TrialRecord _item;
        _item = new TrialRecord();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpAthleteId;
        _tmpAthleteId = _cursor.getInt(_cursorIndexOfAthleteId);
        _item.setAthleteId(_tmpAthleteId);
        final String _tmpEventType;
        if (_cursor.isNull(_cursorIndexOfEventType)) {
          _tmpEventType = null;
        } else {
          _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
        }
        _item.setEventType(_tmpEventType);
        final double _tmpValue;
        _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
        _item.setValue(_tmpValue);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        _item.setUnit(_tmpUnit);
        final long _tmpTrialDate;
        _tmpTrialDate = _cursor.getLong(_cursorIndexOfTrialDate);
        _item.setTrialDate(_tmpTrialDate);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<TrialRecord> getLeaderboardForDistanceEvent(final String eventType) {
    final String _sql = "SELECT t.* FROM trial_records t INNER JOIN (SELECT athleteId, MAX(value) as maxVal FROM trial_records WHERE eventType = ? GROUP BY athleteId) best ON t.athleteId = best.athleteId AND t.value = best.maxVal AND t.eventType = ? GROUP BY t.athleteId ORDER BY t.value DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (eventType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventType);
    }
    _argIndex = 2;
    if (eventType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, eventType);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfAthleteId = CursorUtil.getColumnIndexOrThrow(_cursor, "athleteId");
      final int _cursorIndexOfEventType = CursorUtil.getColumnIndexOrThrow(_cursor, "eventType");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final int _cursorIndexOfUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "unit");
      final int _cursorIndexOfTrialDate = CursorUtil.getColumnIndexOrThrow(_cursor, "trialDate");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<TrialRecord> _result = new ArrayList<TrialRecord>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TrialRecord _item;
        _item = new TrialRecord();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpAthleteId;
        _tmpAthleteId = _cursor.getInt(_cursorIndexOfAthleteId);
        _item.setAthleteId(_tmpAthleteId);
        final String _tmpEventType;
        if (_cursor.isNull(_cursorIndexOfEventType)) {
          _tmpEventType = null;
        } else {
          _tmpEventType = _cursor.getString(_cursorIndexOfEventType);
        }
        _item.setEventType(_tmpEventType);
        final double _tmpValue;
        _tmpValue = _cursor.getDouble(_cursorIndexOfValue);
        _item.setValue(_tmpValue);
        final String _tmpUnit;
        if (_cursor.isNull(_cursorIndexOfUnit)) {
          _tmpUnit = null;
        } else {
          _tmpUnit = _cursor.getString(_cursorIndexOfUnit);
        }
        _item.setUnit(_tmpUnit);
        final long _tmpTrialDate;
        _tmpTrialDate = _cursor.getLong(_cursorIndexOfTrialDate);
        _item.setTrialDate(_tmpTrialDate);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
