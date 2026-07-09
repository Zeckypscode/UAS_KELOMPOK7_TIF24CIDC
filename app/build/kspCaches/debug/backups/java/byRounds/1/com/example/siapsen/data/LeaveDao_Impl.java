package com.example.siapsen.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LeaveDao_Impl implements LeaveDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<LeaveEntity> __insertionAdapterOfLeaveEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  public LeaveDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLeaveEntity = new EntityInsertionAdapter<LeaveEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `leave_request` (`id`,`username`,`nama`,`type`,`reason`,`startDate`,`endDate`,`attachmentPath`,`status`,`submittedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final LeaveEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getUsername());
        statement.bindString(3, entity.getNama());
        final String _tmp = __converters.fromLeaveType(entity.getType());
        statement.bindString(4, _tmp);
        statement.bindString(5, entity.getReason());
        statement.bindLong(6, entity.getStartDate());
        statement.bindLong(7, entity.getEndDate());
        if (entity.getAttachmentPath() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAttachmentPath());
        }
        final String _tmp_1 = __converters.fromLeaveStatus(entity.getStatus());
        statement.bindString(9, _tmp_1);
        statement.bindLong(10, entity.getSubmittedAt());
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE leave_request SET status=? WHERE id=?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final LeaveEntity leave, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfLeaveEntity.insertAndReturnId(leave);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final long id, final LeaveStatus status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __converters.fromLeaveStatus(status);
        _stmt.bindString(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<LeaveEntity>> getAll() {
    final String _sql = "SELECT * FROM leave_request ORDER BY submittedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"leave_request"}, new Callable<List<LeaveEntity>>() {
      @Override
      @NonNull
      public List<LeaveEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfAttachmentPath = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentPath");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final List<LeaveEntity> _result = new ArrayList<LeaveEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LeaveEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpNama;
            _tmpNama = _cursor.getString(_cursorIndexOfNama);
            final LeaveType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toLeaveType(_tmp);
            final String _tmpReason;
            _tmpReason = _cursor.getString(_cursorIndexOfReason);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpAttachmentPath;
            if (_cursor.isNull(_cursorIndexOfAttachmentPath)) {
              _tmpAttachmentPath = null;
            } else {
              _tmpAttachmentPath = _cursor.getString(_cursorIndexOfAttachmentPath);
            }
            final LeaveStatus _tmpStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toLeaveStatus(_tmp_1);
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            _item = new LeaveEntity(_tmpId,_tmpUsername,_tmpNama,_tmpType,_tmpReason,_tmpStartDate,_tmpEndDate,_tmpAttachmentPath,_tmpStatus,_tmpSubmittedAt);
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
  public Flow<List<LeaveEntity>> getPending() {
    final String _sql = "SELECT * FROM leave_request WHERE status='PENDING'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"leave_request"}, new Callable<List<LeaveEntity>>() {
      @Override
      @NonNull
      public List<LeaveEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfNama = CursorUtil.getColumnIndexOrThrow(_cursor, "nama");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfAttachmentPath = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentPath");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfSubmittedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "submittedAt");
          final List<LeaveEntity> _result = new ArrayList<LeaveEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final LeaveEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpUsername;
            _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            final String _tmpNama;
            _tmpNama = _cursor.getString(_cursorIndexOfNama);
            final LeaveType _tmpType;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfType);
            _tmpType = __converters.toLeaveType(_tmp);
            final String _tmpReason;
            _tmpReason = _cursor.getString(_cursorIndexOfReason);
            final long _tmpStartDate;
            _tmpStartDate = _cursor.getLong(_cursorIndexOfStartDate);
            final long _tmpEndDate;
            _tmpEndDate = _cursor.getLong(_cursorIndexOfEndDate);
            final String _tmpAttachmentPath;
            if (_cursor.isNull(_cursorIndexOfAttachmentPath)) {
              _tmpAttachmentPath = null;
            } else {
              _tmpAttachmentPath = _cursor.getString(_cursorIndexOfAttachmentPath);
            }
            final LeaveStatus _tmpStatus;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            _tmpStatus = __converters.toLeaveStatus(_tmp_1);
            final long _tmpSubmittedAt;
            _tmpSubmittedAt = _cursor.getLong(_cursorIndexOfSubmittedAt);
            _item = new LeaveEntity(_tmpId,_tmpUsername,_tmpNama,_tmpType,_tmpReason,_tmpStartDate,_tmpEndDate,_tmpAttachmentPath,_tmpStatus,_tmpSubmittedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
