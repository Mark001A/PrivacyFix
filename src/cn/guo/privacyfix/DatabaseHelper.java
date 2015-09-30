package cn.guo.privacyfix;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 98; // 数据库版本号
	private final static String DATABASE_NAME = "Test111"; // 数据库名称

	public DatabaseHelper(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		// Long date = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		String sqldept = "create table guo"
				+ date
				+ "(imei String PRIMARY KEY ,imsi text,number text,simserial text,wifimac text,bluemac text,androidid text,serial text,brand text,model text,simOperator text,SSID text,macAddress text,version_codes text,release text,resolution_width text,resolution_heidth text,manufacturer text,device text)";
		db.execSQL(sqldept);
		Log.i("oncreat", "111111111111111");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void newTable(SQLiteDatabase db) {
		// db.execSQL("select count(*) from user_tables where table_name = 'TABLE_NAME'");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		Boolean boolean1 = tabIsExist("guo" + date, db);
		if (!boolean1) {
			String sqldept = "create table guo"
					+ date
					+ "(imei String PRIMARY KEY ,imsi text,number text,simserial text,wifimac text,bluemac text,androidid text,serial text,brand text,model text,simOperator text,SSID text,macAddress text,version_codes text,release text,resolution_width text,resolution_heidth text,manufacturer text,device text)";
			db.execSQL(sqldept);
		}
		Log.i("newTable", "111111111111111" + boolean1);
	}

	public boolean tabIsExist(String tabName, SQLiteDatabase db) {
		boolean result = false;
		if (tabName == null) {
			return false;
		}
		Cursor cursor = null;
		try {

			String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"
					+ tabName.trim() + "' ";
			cursor = db.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
}
