package cn.guo.privacyfix;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * sharedpreferences工具类
 */
public class SharedPreferencesUtil {
	private Context context;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;

	public SharedPreferencesUtil(Context context, String mName) {
		this.context = context;
		sharedPreferences = context.getSharedPreferences(mName,
				Activity.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}

	public void setString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 如果是空，返回空""
	 */
	public String getString(String key, String defaule) {
		return sharedPreferences.getString(key, defaule);
	}

	public Map<String, ?> getmap() {
		// String content = "";
		Map<String, ?> allContent = sharedPreferences.getAll();
		// 注意遍历map的方法
		// for (Map.Entry<String, ?> entry : allContent.entrySet()) {
		// content += (entry.getKey() + entry.getValue());
		// }
		return allContent;

	}
	public String getmap1() {
	    String content = "";
		Map<String, ?> allContent = sharedPreferences.getAll();
		// 注意遍历map的方法
		 for (Map.Entry<String, ?> entry : allContent.entrySet()) {
		 content += (entry.getValue()+"\n");
		 }
		return content;

	}

	public void clearData() {
		editor.clear().commit();
	}

	public void remove(String str) {
		editor.remove(str);
		editor.commit();
//		Log.i("success", "=====" + str);
	}
}
