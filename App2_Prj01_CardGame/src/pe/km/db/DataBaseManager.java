package pe.km.db;

import java.util.ArrayList;

import pe.km.game.UserInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
	private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS user_info ("
			+ " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ " name VARCHAR(256) NOT NULL,"
			+ " score INTEGER NOT NULL,"
			+ " time INTEGER NOT NULL" + ")";
	private static final String DB_FILENAME = "user_info.db";
	private static final String FILED_ID = "_id";
	private static final String FILED_NAME = "name";
	private static final String FILED_SCORE = "score";
	private static final String FILED_TIME = "time";
	private static final String TABLE_NAME = "user_info";

	private SQLiteDatabase db;

	public DataBaseManager(Context context) {
		db = context.openOrCreateDatabase(DB_FILENAME, Context.MODE_PRIVATE,
				null);
		db.execSQL(CREATE_TABLE_SQL);
	}

	public boolean add(UserInfo userInfo) {
		ContentValues values = new ContentValues();

		values.put(FILED_NAME, userInfo.getName());
		values.put(FILED_SCORE, userInfo.getScore());
		values.put(FILED_TIME, userInfo.getClearTime());

		long rowId = db.insert(TABLE_NAME, null, values);
		if (0 > rowId) {
			return false;
		} else {
			userInfo.setDbId(rowId);
			return true;
		}
	}

	public Cursor getAllUserInfoCursor() {
		return (db.query(TABLE_NAME, null, null, null, null, null, FILED_SCORE + " desc"));
	}

	public void delete(UserInfo userInfo) {
		String whereClause = "_id = " + userInfo.getDbId();
		db.delete(TABLE_NAME, whereClause, null);
	}

	public UserInfo getUserInfo(Cursor cursor) {
		long dbId = cursor.getLong(cursor.getColumnIndex(FILED_ID));
		String name = cursor.getString(cursor.getColumnIndex(FILED_NAME));
		int score = cursor.getInt(cursor.getColumnIndex(FILED_SCORE));
		int clearTime = cursor.getInt(cursor.getColumnIndex(FILED_TIME));

		UserInfo userInfo = new UserInfo(name, score, clearTime);
		userInfo.setDbId(dbId);

		return userInfo;
	}
	
	public ArrayList<UserInfo> getAllUserData(Cursor cursor) {
		ArrayList<UserInfo> allUserData = new ArrayList<UserInfo>();
		while(cursor.moveToNext()) {
			allUserData.add(getUserInfo(cursor));
		}
		return allUserData;
	}

}
