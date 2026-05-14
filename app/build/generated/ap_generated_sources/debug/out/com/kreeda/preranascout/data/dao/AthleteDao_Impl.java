package com.kreeda.preranascout.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.kreeda.preranascout.data.entity.Athlete;
import java.lang.Class;
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
public final class AthleteDao_Impl implements AthleteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Athlete> __insertionAdapterOfAthlete;

  private final EntityDeletionOrUpdateAdapter<Athlete> __deletionAdapterOfAthlete;

  private final EntityDeletionOrUpdateAdapter<Athlete> __updateAdapterOfAthlete;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDistrictReadyStatus;

  public AthleteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAthlete = new EntityInsertionAdapter<Athlete>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `athletes` (`id`,`name`,`age`,`studentClass`,`school`,`primarySport`,`districtLevelReady`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Athlete entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindLong(3, entity.getAge());
        if (entity.getStudentClass() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStudentClass());
        }
        if (entity.getSchool() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSchool());
        }
        if (entity.getPrimarySport() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPrimarySport());
        }
        final int _tmp = entity.isDistrictLevelReady() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfAthlete = new EntityDeletionOrUpdateAdapter<Athlete>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `athletes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Athlete entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfAthlete = new EntityDeletionOrUpdateAdapter<Athlete>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `athletes` SET `id` = ?,`name` = ?,`age` = ?,`studentClass` = ?,`school` = ?,`primarySport` = ?,`districtLevelReady` = ?,`createdAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Athlete entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        statement.bindLong(3, entity.getAge());
        if (entity.getStudentClass() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStudentClass());
        }
        if (entity.getSchool() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSchool());
        }
        if (entity.getPrimarySport() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPrimarySport());
        }
        final int _tmp = entity.isDistrictLevelReady() ? 1 : 0;
        statement.bindLong(7, _tmp);
        statement.bindLong(8, entity.getCreatedAt());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfUpdateDistrictReadyStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE athletes SET districtLevelReady = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final Athlete athlete) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfAthlete.insertAndReturnId(athlete);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Long> insertAll(final List<Athlete> athletes) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final List<Long> _result = __insertionAdapterOfAthlete.insertAndReturnIdsList(athletes);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Athlete athlete) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAthlete.handle(athlete);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Athlete athlete) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAthlete.handle(athlete);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateDistrictReadyStatus(final int athleteId, final boolean ready) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDistrictReadyStatus.acquire();
    int _argIndex = 1;
    final int _tmp = ready ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, athleteId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfUpdateDistrictReadyStatus.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Athlete>> getAllAthletes() {
    final String _sql = "SELECT * FROM athletes ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"athletes"}, false, new Callable<List<Athlete>>() {
      @Override
      @Nullable
      public List<Athlete> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfStudentClass = CursorUtil.getColumnIndexOrThrow(_cursor, "studentClass");
          final int _cursorIndexOfSchool = CursorUtil.getColumnIndexOrThrow(_cursor, "school");
          final int _cursorIndexOfPrimarySport = CursorUtil.getColumnIndexOrThrow(_cursor, "primarySport");
          final int _cursorIndexOfDistrictLevelReady = CursorUtil.getColumnIndexOrThrow(_cursor, "districtLevelReady");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Athlete> _result = new ArrayList<Athlete>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Athlete _item;
            _item = new Athlete();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            _item.setAge(_tmpAge);
            final String _tmpStudentClass;
            if (_cursor.isNull(_cursorIndexOfStudentClass)) {
              _tmpStudentClass = null;
            } else {
              _tmpStudentClass = _cursor.getString(_cursorIndexOfStudentClass);
            }
            _item.setStudentClass(_tmpStudentClass);
            final String _tmpSchool;
            if (_cursor.isNull(_cursorIndexOfSchool)) {
              _tmpSchool = null;
            } else {
              _tmpSchool = _cursor.getString(_cursorIndexOfSchool);
            }
            _item.setSchool(_tmpSchool);
            final String _tmpPrimarySport;
            if (_cursor.isNull(_cursorIndexOfPrimarySport)) {
              _tmpPrimarySport = null;
            } else {
              _tmpPrimarySport = _cursor.getString(_cursorIndexOfPrimarySport);
            }
            _item.setPrimarySport(_tmpPrimarySport);
            final boolean _tmpDistrictLevelReady;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDistrictLevelReady);
            _tmpDistrictLevelReady = _tmp != 0;
            _item.setDistrictLevelReady(_tmpDistrictLevelReady);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
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
  public List<Athlete> getAllAthletesList() {
    final String _sql = "SELECT * FROM athletes ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfStudentClass = CursorUtil.getColumnIndexOrThrow(_cursor, "studentClass");
      final int _cursorIndexOfSchool = CursorUtil.getColumnIndexOrThrow(_cursor, "school");
      final int _cursorIndexOfPrimarySport = CursorUtil.getColumnIndexOrThrow(_cursor, "primarySport");
      final int _cursorIndexOfDistrictLevelReady = CursorUtil.getColumnIndexOrThrow(_cursor, "districtLevelReady");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final List<Athlete> _result = new ArrayList<Athlete>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Athlete _item;
        _item = new Athlete();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _item.setName(_tmpName);
        final int _tmpAge;
        _tmpAge = _cursor.getInt(_cursorIndexOfAge);
        _item.setAge(_tmpAge);
        final String _tmpStudentClass;
        if (_cursor.isNull(_cursorIndexOfStudentClass)) {
          _tmpStudentClass = null;
        } else {
          _tmpStudentClass = _cursor.getString(_cursorIndexOfStudentClass);
        }
        _item.setStudentClass(_tmpStudentClass);
        final String _tmpSchool;
        if (_cursor.isNull(_cursorIndexOfSchool)) {
          _tmpSchool = null;
        } else {
          _tmpSchool = _cursor.getString(_cursorIndexOfSchool);
        }
        _item.setSchool(_tmpSchool);
        final String _tmpPrimarySport;
        if (_cursor.isNull(_cursorIndexOfPrimarySport)) {
          _tmpPrimarySport = null;
        } else {
          _tmpPrimarySport = _cursor.getString(_cursorIndexOfPrimarySport);
        }
        _item.setPrimarySport(_tmpPrimarySport);
        final boolean _tmpDistrictLevelReady;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDistrictLevelReady);
        _tmpDistrictLevelReady = _tmp != 0;
        _item.setDistrictLevelReady(_tmpDistrictLevelReady);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _item.setCreatedAt(_tmpCreatedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Athlete getAthleteById(final int id) {
    final String _sql = "SELECT * FROM athletes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
      final int _cursorIndexOfStudentClass = CursorUtil.getColumnIndexOrThrow(_cursor, "studentClass");
      final int _cursorIndexOfSchool = CursorUtil.getColumnIndexOrThrow(_cursor, "school");
      final int _cursorIndexOfPrimarySport = CursorUtil.getColumnIndexOrThrow(_cursor, "primarySport");
      final int _cursorIndexOfDistrictLevelReady = CursorUtil.getColumnIndexOrThrow(_cursor, "districtLevelReady");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final Athlete _result;
      if (_cursor.moveToFirst()) {
        _result = new Athlete();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final int _tmpAge;
        _tmpAge = _cursor.getInt(_cursorIndexOfAge);
        _result.setAge(_tmpAge);
        final String _tmpStudentClass;
        if (_cursor.isNull(_cursorIndexOfStudentClass)) {
          _tmpStudentClass = null;
        } else {
          _tmpStudentClass = _cursor.getString(_cursorIndexOfStudentClass);
        }
        _result.setStudentClass(_tmpStudentClass);
        final String _tmpSchool;
        if (_cursor.isNull(_cursorIndexOfSchool)) {
          _tmpSchool = null;
        } else {
          _tmpSchool = _cursor.getString(_cursorIndexOfSchool);
        }
        _result.setSchool(_tmpSchool);
        final String _tmpPrimarySport;
        if (_cursor.isNull(_cursorIndexOfPrimarySport)) {
          _tmpPrimarySport = null;
        } else {
          _tmpPrimarySport = _cursor.getString(_cursorIndexOfPrimarySport);
        }
        _result.setPrimarySport(_tmpPrimarySport);
        final boolean _tmpDistrictLevelReady;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfDistrictLevelReady);
        _tmpDistrictLevelReady = _tmp != 0;
        _result.setDistrictLevelReady(_tmpDistrictLevelReady);
        final long _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
        _result.setCreatedAt(_tmpCreatedAt);
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
  public LiveData<Athlete> getAthleteByIdLive(final int id) {
    final String _sql = "SELECT * FROM athletes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"athletes"}, false, new Callable<Athlete>() {
      @Override
      @Nullable
      public Athlete call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfStudentClass = CursorUtil.getColumnIndexOrThrow(_cursor, "studentClass");
          final int _cursorIndexOfSchool = CursorUtil.getColumnIndexOrThrow(_cursor, "school");
          final int _cursorIndexOfPrimarySport = CursorUtil.getColumnIndexOrThrow(_cursor, "primarySport");
          final int _cursorIndexOfDistrictLevelReady = CursorUtil.getColumnIndexOrThrow(_cursor, "districtLevelReady");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final Athlete _result;
          if (_cursor.moveToFirst()) {
            _result = new Athlete();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            _result.setAge(_tmpAge);
            final String _tmpStudentClass;
            if (_cursor.isNull(_cursorIndexOfStudentClass)) {
              _tmpStudentClass = null;
            } else {
              _tmpStudentClass = _cursor.getString(_cursorIndexOfStudentClass);
            }
            _result.setStudentClass(_tmpStudentClass);
            final String _tmpSchool;
            if (_cursor.isNull(_cursorIndexOfSchool)) {
              _tmpSchool = null;
            } else {
              _tmpSchool = _cursor.getString(_cursorIndexOfSchool);
            }
            _result.setSchool(_tmpSchool);
            final String _tmpPrimarySport;
            if (_cursor.isNull(_cursorIndexOfPrimarySport)) {
              _tmpPrimarySport = null;
            } else {
              _tmpPrimarySport = _cursor.getString(_cursorIndexOfPrimarySport);
            }
            _result.setPrimarySport(_tmpPrimarySport);
            final boolean _tmpDistrictLevelReady;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDistrictLevelReady);
            _tmpDistrictLevelReady = _tmp != 0;
            _result.setDistrictLevelReady(_tmpDistrictLevelReady);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result.setCreatedAt(_tmpCreatedAt);
          } else {
            _result = null;
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
  public LiveData<List<Athlete>> getAthletesBySport(final String sport) {
    final String _sql = "SELECT * FROM athletes WHERE primarySport = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sport == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sport);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"athletes"}, false, new Callable<List<Athlete>>() {
      @Override
      @Nullable
      public List<Athlete> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfStudentClass = CursorUtil.getColumnIndexOrThrow(_cursor, "studentClass");
          final int _cursorIndexOfSchool = CursorUtil.getColumnIndexOrThrow(_cursor, "school");
          final int _cursorIndexOfPrimarySport = CursorUtil.getColumnIndexOrThrow(_cursor, "primarySport");
          final int _cursorIndexOfDistrictLevelReady = CursorUtil.getColumnIndexOrThrow(_cursor, "districtLevelReady");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Athlete> _result = new ArrayList<Athlete>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Athlete _item;
            _item = new Athlete();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            _item.setAge(_tmpAge);
            final String _tmpStudentClass;
            if (_cursor.isNull(_cursorIndexOfStudentClass)) {
              _tmpStudentClass = null;
            } else {
              _tmpStudentClass = _cursor.getString(_cursorIndexOfStudentClass);
            }
            _item.setStudentClass(_tmpStudentClass);
            final String _tmpSchool;
            if (_cursor.isNull(_cursorIndexOfSchool)) {
              _tmpSchool = null;
            } else {
              _tmpSchool = _cursor.getString(_cursorIndexOfSchool);
            }
            _item.setSchool(_tmpSchool);
            final String _tmpPrimarySport;
            if (_cursor.isNull(_cursorIndexOfPrimarySport)) {
              _tmpPrimarySport = null;
            } else {
              _tmpPrimarySport = _cursor.getString(_cursorIndexOfPrimarySport);
            }
            _item.setPrimarySport(_tmpPrimarySport);
            final boolean _tmpDistrictLevelReady;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDistrictLevelReady);
            _tmpDistrictLevelReady = _tmp != 0;
            _item.setDistrictLevelReady(_tmpDistrictLevelReady);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
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
  public LiveData<List<Athlete>> getAthletesBySchool(final String school) {
    final String _sql = "SELECT * FROM athletes WHERE school = ? ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (school == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, school);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"athletes"}, false, new Callable<List<Athlete>>() {
      @Override
      @Nullable
      public List<Athlete> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfStudentClass = CursorUtil.getColumnIndexOrThrow(_cursor, "studentClass");
          final int _cursorIndexOfSchool = CursorUtil.getColumnIndexOrThrow(_cursor, "school");
          final int _cursorIndexOfPrimarySport = CursorUtil.getColumnIndexOrThrow(_cursor, "primarySport");
          final int _cursorIndexOfDistrictLevelReady = CursorUtil.getColumnIndexOrThrow(_cursor, "districtLevelReady");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<Athlete> _result = new ArrayList<Athlete>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Athlete _item;
            _item = new Athlete();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _item.setName(_tmpName);
            final int _tmpAge;
            _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            _item.setAge(_tmpAge);
            final String _tmpStudentClass;
            if (_cursor.isNull(_cursorIndexOfStudentClass)) {
              _tmpStudentClass = null;
            } else {
              _tmpStudentClass = _cursor.getString(_cursorIndexOfStudentClass);
            }
            _item.setStudentClass(_tmpStudentClass);
            final String _tmpSchool;
            if (_cursor.isNull(_cursorIndexOfSchool)) {
              _tmpSchool = null;
            } else {
              _tmpSchool = _cursor.getString(_cursorIndexOfSchool);
            }
            _item.setSchool(_tmpSchool);
            final String _tmpPrimarySport;
            if (_cursor.isNull(_cursorIndexOfPrimarySport)) {
              _tmpPrimarySport = null;
            } else {
              _tmpPrimarySport = _cursor.getString(_cursorIndexOfPrimarySport);
            }
            _item.setPrimarySport(_tmpPrimarySport);
            final boolean _tmpDistrictLevelReady;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfDistrictLevelReady);
            _tmpDistrictLevelReady = _tmp != 0;
            _item.setDistrictLevelReady(_tmpDistrictLevelReady);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item.setCreatedAt(_tmpCreatedAt);
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
  public LiveData<List<String>> getAllSports() {
    final String _sql = "SELECT DISTINCT primarySport FROM athletes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"athletes"}, false, new Callable<List<String>>() {
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
  public LiveData<List<String>> getAllSchools() {
    final String _sql = "SELECT DISTINCT school FROM athletes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"athletes"}, false, new Callable<List<String>>() {
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
  public int getAthleteCount() {
    final String _sql = "SELECT COUNT(*) FROM athletes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
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
