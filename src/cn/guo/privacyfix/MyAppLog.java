package cn.guo.privacyfix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Application;
import android.os.Looper;
import android.widget.Toast;

public class MyAppLog extends Application {
	public static final String DIR = "/data/data/cn.guo.privacyfix/log/";
	public static final String NAME = getCurrentDateString() + ".txt";
	public static  int SIGN = 0;

	@Override
	public void onCreate() {
		super.onCreate();
//		Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
	}

	/**
	 * 捕获错误信息的handler
	 */
	private UncaughtExceptionHandler uncaughtExceptionHandler = new UncaughtExceptionHandler() {

		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			// LogUtil.showLog("我崩溃了");
			ex.printStackTrace();
	        new Thread() {  
	            @Override  
	            public void run() {  
	                Looper.prepare();  
	                Toast.makeText(MyAppLog.this, "应用遇到了一点点小问题 T.T",  Toast.LENGTH_LONG).show();
	                Looper.loop();  
	            }  
	        }.start();
					
			String info = null;
			ByteArrayOutputStream baos = null;
			PrintStream printStream = null;
			try {
				baos = new ByteArrayOutputStream();
				printStream = new PrintStream(baos);
				ex.printStackTrace(printStream);
				byte[] data = baos.toByteArray();
				info = new String(data);
				data = null;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (printStream != null) {
						printStream.close();
					}
					if (baos != null) {
						baos.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			writeErrorLog(info);
//			Timer timer = new Timer();// 实例化Timer类
//			timer.schedule(new TimerTask() {
//				public void run() {
//					System.exit(0);
//					this.cancel();
//				}
//			}, 1500);// 五百毫秒
//			System.exit(0);
			// Intent intent = new Intent(getApplicationContext(),
			// CollapseActivity.class);
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// startActivity(intent);
		}
	};

	/**
	 * 向文件中写入错误信息
	 * 
	 * @param info
	 */
	protected void writeErrorLog(String info) {
		File dir = new File(DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, NAME);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file, true);
			fileOutputStream.write(info.getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	private static String getCurrentDateString() {
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		Date nowDate = new Date(0, 0, 0);
		result = sdf.format(nowDate);
		return result;
	}
}