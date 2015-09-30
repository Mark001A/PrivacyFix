package cn.guo.privacyfix;

import java.io.DataOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class MainFragment extends Fragment implements OnClickListener {
	private TelephonyManager phone;
	private WifiManager wifi;
	private PackageManager pm;
	private WindowManager wm;
	private String[] str_file = new String[100];
	private Button btn1, btn2, button1, button2, button3, button4, button5,
			et_to;
	private SharedPreferencesUtil sp1, sp2, sp, sp3;
	private DBUtil dbUtil;
	private int i = 0, dbnum = 0;
	private ContentValues initialValues;
	private String[] cursor;
	private String[] cursor1;
	private Context context;
	private View view;
	private ContentBean contentBean;
	private ArrayList<ContentBean1> list = new ArrayList<ContentBean1>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// setContentView(R.layout.main);
		view = inflater.inflate(R.layout.main, null);
		context = getActivity();
		contentBean = new ContentBean();
		phone = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		pm = context.getPackageManager();
		wm = getActivity().getWindowManager();
		sp1 = new SharedPreferencesUtil(context, "AppClearList");
		sp2 = new SharedPreferencesUtil(context, "AppNoUninstallList");
		sp = new SharedPreferencesUtil(context, "FileDate");
		sp3 = new SharedPreferencesUtil(context, "user");
		dbUtil = new DBUtil(context);
		initialValues = new ContentValues();
		setHasOptionsMenu(true);
		// creatdb();
		addClick(R.id.rimei, R.id.rimsi, R.id.rnumber, R.id.rsimserial,
				R.id.rwifimac, R.id.rbluemac, R.id.randroidid, R.id.rserial,
				R.id.rbrand, R.id.rmodel, R.id.rsimOperator, R.id.rSSID,
				R.id.rMacAddress, R.id.rversion_codes, R.id.rrelease,
				R.id.rresolution, R.id.rmanufacturer, R.id.rdevice);
		// readData();
		init();
		/**
		 * ' 调用root
		 */
		upgradeRootPermission(context.getPackageCodePath(), true);
		// upgradeRootPermission("pm clear com.yzitel.sdk \n",false);
		// cmd();
		/**
		 * 文件监听
		 */
		Intent intent = new Intent(context, FileService.class);
		context.startService(intent);

		btn1 = (Button) view.findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// query();
				Intent intent = new Intent();
				intent.setClass(context, AppListView.class);
				startActivity(intent);

			}
		});
		btn2 = (Button) view.findViewById(R.id.btn2);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// query();
				Intent intent = new Intent();
				intent.setClass(context, FileChangeView.class);
				startActivity(intent);
			}
		});
		button1 = (Button) view.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fileDelete();
				allDelete();
				allClear();
				FileService.FILEOBSERVER = 0;
				allRandom();
				saveData();
				creatdb();
				new Thread() {
					public void run() {
						try {
							Thread.sleep(3000);
							shelldeletedatabases();
						} catch (Exception e) {
							e.printStackTrace();
						}
					};
				}.start();

			}
		});
		button2 = (Button) view.findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(context, AppListView.class);
				startActivity(intent);
			}
		});
		button3 = (Button) view.findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				creatdb();
			}
		});
		button4 = (Button) view.findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				querydb();
				// fileDelete();
				// allDelete();
				// allClear();
				// FileService.FILEOBSERVER = 0;
				// // allRandom();
				// saveData();
				// creatdb();
				// new Thread() {
				// public void run() {
				// try {
				// Thread.sleep(3000);
				// shelldeletedatabases();
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// };
				// }.start();
			}
		});

		button5 = (Button) view.findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// upgradeRootPermission("/system/bin/newfs_msdos -F 32 -O android -c 8 /dev/block/mmcblk0p1"
				// + "\n", false);
				// int k[] = new int[10];
				// k[11] = 0;
				// FileCopy.copyFile(
				// "/data/data/com.android.providers.settings/databases/settings.db",
				// "/data/data/cn.zhg.notrack/settings.db");

				// upgradeRootPermission("cd /data/data/com.android.providers.settings/databases \n sqlite3 settings.db \n delete from system where _id>110; \n",false);
				new Thread() {
					public void run() {
						try {
							shelldeletedatabases();
						} catch (Exception e) {
							e.printStackTrace();
						}
					};
				}.start();

			}
		});
		et_to = (Button) view.findViewById(R.id.et_to);
		et_to.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				querydbfrom();

			}
		});
		if (MyAppLog.SIGN == 0) {
			Intent intent2 = new Intent();
			intent2.setClass(context, About.class);
			startActivity(intent2);
			MyAppLog.SIGN = -1;
		}
		return view;
	}

	// public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
	// super.onCreateOptionsMenu(menu, inflater);
	// getActivity().getMenuInflater().inflate(R.menu.main, menu);
	// }

	/**
	 * R.id.rSSID, R.id.rMacAddress, R.id.rversion_codes, R.id.rrelease,
	 * R.id.resolution);
	 */

	private void init() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		setEditText(R.id.imei, phone.getDeviceId());
		setEditText(R.id.imsi, phone.getSubscriberId());
		setEditText(R.id.number, phone.getLine1Number());
		setEditText(R.id.simserial, phone.getSimSerialNumber());
		setEditText(R.id.wifimac, wifi.getConnectionInfo().getMacAddress());
		// setEditText(R.id.bluemac, BluetoothAdapter.getDefaultAdapter()
		// .getAddress());
		setEditText(R.id.androidid, Secure.getString(
				context.getContentResolver(), Secure.ANDROID_ID));
		setEditText(R.id.serial, android.os.Build.SERIAL);
		setEditText(R.id.device, android.os.Build.DEVICE);
		setEditText(R.id.brand, android.os.Build.BRAND);
		setEditText(R.id.model, android.os.Build.MODEL);
		setEditText(R.id.simOperator, phone.getSimOperator());
		setEditText(R.id.SSID, wifi.getConnectionInfo().getSSID());
		setEditText(R.id.macAddress, wifi.getConnectionInfo().getBSSID());
		setEditText(R.id.version_codes, android.os.Build.VERSION.SDK_INT + "");
		setEditText(R.id.release, android.os.Build.VERSION.RELEASE);
		setEditText(R.id.resolution, wm.getDefaultDisplay().getWidth() + "x"
				+ wm.getDefaultDisplay().getHeight());
		setEditText(R.id.manufacturer, android.os.Build.MANUFACTURER);
		setEditText(R.id.editText1, date);
		setEditText(R.id.editText2, dbnum + "");
		setEditText(R.id.dt_time, date);
		setEditText(R.id.et_row, dbnum+"");
	}

	private void addClick(int... ids) {
		if (ids != null) {
			for (int id : ids) {
				view.findViewById(id).setOnClickListener(this);
			}
		}
	}

	private void setEditText(int id, String s) {
		((EditText) view.findViewById(id)).setText(s);
	}

	private String getEditText(int id) {
		return ((EditText) view.findViewById(id)).getText().toString();
	}

	/**
	 * 随机生成n位数字
	 * 
	 * @param n
	 * @return
	 */
	private String randomNum(int n) {
		String res = "";
		Random rnd = new Random();
		for (int i = 0; i < n; i++) {
			res = res + rnd.nextInt(10);
		}
		return res;
	}

	/**
	 * 读取imei前8位并随机生成15位imei
	 */
	private String imeiRandom(String res) {
		try {
			String imeiString = res + randomNum(6);// 前14位
			char[] imeiChar = imeiString.toCharArray();
			int resultInt = 0;
			for (int i = 0; i < imeiChar.length; i++) {
				int a = Integer.parseInt(String.valueOf(imeiChar[i]));
				i++;
				final int temp = Integer.parseInt(String.valueOf(imeiChar[i])) * 2;
				final int b = temp < 10 ? temp : temp - 9;
				resultInt += a + b;
			}
			resultInt %= 10;
			resultInt = resultInt == 0 ? 0 : 10 - resultInt;
			System.out.println("imei:" + imeiString + resultInt);
			return imeiString + resultInt;
		} catch (Exception e) {
			return "126248009474424";
		}
	}

	/**
	 * 
	 * @return
	 */
	private String randomPhone() {
		/** 前三为 */
		String head[] = { "+8613", "+8615", "+8618" };
		Random rnd = new Random();
		String res = head[rnd.nextInt(head.length)];
		for (int i = 0; i < 9; i++) {
			res = res + rnd.nextInt(10);
		}
		return res;
	}

	private String randomMac() {
		String chars = "abcde0123456789";
		String res = "";
		Random rnd = new Random();
		int leng = chars.length();
		for (int i = 0; i < 17; i++) {
			if (i % 3 == 2) {
				res = res + ":";
			} else {
				res = res + chars.charAt(rnd.nextInt(leng));
			}

		}
		return res;
	}

	/**
	 * SSID
	 * 
	 * @return
	 */
	private String wifiMac() {
		String chars = "abcde0123456789";
		String res = "";
		Random rnd = new Random();
		int leng = chars.length();
		int j = rnd.nextInt(10);
		for (int i = 0; i < j; i++) {
			res = res + chars.charAt(rnd.nextInt(leng));
		}
		return res;
	}

	private String randomMac1() {
		String chars = "ABCDE0123456789";
		String res = "";
		Random rnd = new Random();
		int leng = chars.length();
		for (int i = 0; i < 17; i++) {
			if (i % 3 == 2) {
				res = res + ":";
			} else {
				res = res + chars.charAt(rnd.nextInt(leng));
			}

		}
		return res;
	}

	private String randomABC(int n) {
		String chars = "abcde0123456789";
		String res = "";
		Random rnd = new Random();
		int leng = chars.length();
		for (int i = 0; i < n; i++) {
			res = res + chars.charAt(rnd.nextInt(leng));

		}
		return res;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		// if (id == R.id.save) {
		// saveData();
		// System.out.println("actionbar");
		// return true;
		// }
		switch (id) {
		case R.id.save:
			saveData();
			System.out.println("save");
			break;
		case R.id.saveto:
			saveData();
			saveto();
			System.out.println("saveto");
			break;
		case R.id.onekey:
			System.out.println("onekey");
			fileDelete();
//			allDelete();
			allClear();
			FileService.FILEOBSERVER = 0;
			allRandom();
			saveData();
			creatdb();
			new Thread() {
				public void run() {
					try {
						Thread.sleep(3000);
						shelldeletedatabases();
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}.start();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 使用SharedPreferences
	 */
	private void saveData() {
		try {
			SharedPreferences sh = context.getSharedPreferences("prefs",
					Context.MODE_WORLD_READABLE);
			Editor pre = sh.edit();
			pre.putString("imei", this.getEditText(R.id.imei));
			initialValues.put("imei", this.getEditText(R.id.imei));
			pre.putString("imsi", this.getEditText(R.id.imsi));
			initialValues.put("imsi", this.getEditText(R.id.imsi));
			pre.putString("number", this.getEditText(R.id.number));
			initialValues.put("number", this.getEditText(R.id.number));
			pre.putString("simserial", this.getEditText(R.id.simserial));
			initialValues.put("simserial", this.getEditText(R.id.simserial));
			pre.putString("wifimac", this.getEditText(R.id.wifimac));
			pre.putString("bluemac", this.getEditText(R.id.bluemac));
			pre.putString("androidid", this.getEditText(R.id.androidid));
			pre.putString("serial", this.getEditText(R.id.serial));
			pre.putString("brand", this.getEditText(R.id.brand));
			pre.putString("model", this.getEditText(R.id.model));
			pre.putString("simOperator", this.getEditText(R.id.simOperator));
			pre.putString("SSID", this.getEditText(R.id.SSID));
			pre.putString("macAddress", this.getEditText(R.id.macAddress));
			pre.putString("version_codes", this.getEditText(R.id.version_codes));
			pre.putString("release", this.getEditText(R.id.release));
			pre.putString("resolution_width", this.getEditText(R.id.resolution)
					.split("x")[0]);
			pre.putString("resolution_heidth", this
					.getEditText(R.id.resolution).split("x")[1]
					.replace(" ", ""));
			pre.putString("manufacturer", this.getEditText(R.id.manufacturer));
			pre.putString("device", this.getEditText(R.id.device));

			initialValues.put("wifimac", this.getEditText(R.id.wifimac));
			initialValues.put("bluemac", this.getEditText(R.id.bluemac));
			initialValues.put("androidid", this.getEditText(R.id.androidid));
			initialValues.put("serial", this.getEditText(R.id.serial));
			initialValues.put("brand", this.getEditText(R.id.brand));
			initialValues.put("model", this.getEditText(R.id.model));
			initialValues
					.put("simOperator", this.getEditText(R.id.simOperator));
			initialValues.put("SSID", this.getEditText(R.id.SSID));
			initialValues.put("macAddress", this.getEditText(R.id.macAddress));
			initialValues.put("version_codes",
					this.getEditText(R.id.version_codes));
			initialValues.put("release", this.getEditText(R.id.release));
			initialValues.put("resolution_width",
					this.getEditText(R.id.resolution).split("x")[0]);
			initialValues.put("resolution_heidth",
					this.getEditText(R.id.resolution).split("x")[1]);
			initialValues.put("manufacturer",
					this.getEditText(R.id.manufacturer));
			initialValues.put("device", this.getEditText(R.id.device));
			pre.apply();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存到服务器
	 */
	private String saveDatato() {
		String str = this.getEditText(R.id.imei) + ","
				+ this.getEditText(R.id.imsi) + ","
				+ this.getEditText(R.id.number) + ","
				+ this.getEditText(R.id.simserial) + ","
				+ this.getEditText(R.id.wifimac) + ","
				+ this.getEditText(R.id.bluemac) + ","
				+ this.getEditText(R.id.androidid) + ","
				+ this.getEditText(R.id.serial) + ","
				+ this.getEditText(R.id.brand) + ","
				+ this.getEditText(R.id.model) + ","
				+ this.getEditText(R.id.simOperator) + ","
				+ this.getEditText(R.id.SSID) + ","
				+ this.getEditText(R.id.macAddress) + ","
				+ this.getEditText(R.id.version_codes) + ","
				+ this.getEditText(R.id.release);
		// contentBean = new ContentBean(this.getEditText(R.id.imei),
		// this.getEditText(R.id.imsi), this.getEditText(R.id.number),
		// this.getEditText(R.id.simserial),
		// this.getEditText(R.id.wifimac),
		// this.getEditText(R.id.bluemac),
		// this.getEditText(R.id.androidid),
		// this.getEditText(R.id.serial),
		// this.getEditText(R.id.brand), this.getEditText(R.id.model),
		// this.getEditText(R.id.simOperator),
		// this.getEditText(R.id.SSID),
		// this.getEditText(R.id.macAddress),
		// this.getEditText(R.id.version_codes),
		// this.getEditText(R.id.release));
		return str;
	}

	/**
	 * 使用SharedPreferences
	 */
	private void saveDataFrom() {
		try {
			SharedPreferences sh = context.getSharedPreferences("prefs",
					Context.MODE_WORLD_READABLE);
			Editor pre = sh.edit();
			pre.putString("imei", this.getEditText(R.id.imei));
			pre.putString("imsi", this.getEditText(R.id.imsi));
			pre.putString("number", this.getEditText(R.id.number));
			pre.putString("simserial", this.getEditText(R.id.simserial));
			pre.putString("wifimac", this.getEditText(R.id.wifimac));
			pre.putString("bluemac", this.getEditText(R.id.bluemac));
			pre.putString("androidid", this.getEditText(R.id.androidid));
			pre.putString("serial", this.getEditText(R.id.serial));
			pre.putString("brand", this.getEditText(R.id.brand));
			pre.putString("model", this.getEditText(R.id.model));
			pre.putString("simOperator", this.getEditText(R.id.simOperator));
			pre.putString("SSID", this.getEditText(R.id.SSID));
			pre.putString("macAddress", this.getEditText(R.id.macAddress));
			pre.putString("version_codes", this.getEditText(R.id.version_codes));
			pre.putString("release", this.getEditText(R.id.release));
			pre.putString("resolution_width", this.getEditText(R.id.resolution)
					.split("x")[0]);
			pre.putString("resolution_heidth", this
					.getEditText(R.id.resolution).split("x")[1]
					.replace(" ", ""));
			pre.putString("manufacturer", this.getEditText(R.id.manufacturer));
			pre.putString("device", this.getEditText(R.id.device));
			pre.apply();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rimei:
			// setEditText(R.id.imei, this.randomNum(15));
			// setEditText(R.id.imei, this.imeiRandom("123"));
			break;
		case R.id.rimsi:
			setEditText(R.id.imsi, this.randomNum(15));
			break;
		case R.id.rnumber:
			setEditText(R.id.number, this.randomPhone());
			break;
		case R.id.rsimserial:
			setEditText(R.id.simserial, this.randomNum(20));
			break;
		case R.id.rwifimac:
			setEditText(R.id.wifimac, this.randomMac());
			break;
		case R.id.rbluemac:
			setEditText(R.id.bluemac, this.randomMac1());
			break;
		case R.id.randroidid:
			setEditText(R.id.androidid, this.randomABC(16));
			break;
		case R.id.rserial:
			setEditText(R.id.serial, this.randomNum(19) + "a");
			break;
		case R.id.rbrand:

			break;
		case R.id.rmodel:
			// setEditText(R.id.model, "lenvov a321t");
		}
	}

	// public void query() {
	// // 查询所有已经安装的应用程序
	// pm = this.getPackageManager();
	// List<ApplicationInfo> listAppcations = pm
	// .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
	// Collections.sort(listAppcations,
	// new ApplicationInfo.DisplayNameComparator(pm));// 排序
	// for (ApplicationInfo app : listAppcations) {
	// getAppInfo(app);
	// str_app[0] = app.packageName;
	// }
	// }
	//
	// // 构造一个AppInfo对象 ，并赋值
	// private AppInfo getAppInfo(ApplicationInfo app) {
	// AppInfo appInfo = new AppInfo();
	// appInfo.setAppLabel((String) app.loadLabel(pm));
	// appInfo.setAppIcon(app.loadIcon(pm));
	// appInfo.setPkgName(app.packageName);
	// str = app.packageName;
	// System.out.println(str);
	// return appInfo;
	// }

	/**
	 * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
	 * 
	 * @return 应用程序是/否获取Root权限
	 */
	public static boolean upgradeRootPermission(String pkgCodePath,
			Boolean boolean1) {
		Process process = null;
		DataOutputStream os = null;
		try {
			String cmd = "chmod 777 " + pkgCodePath;
			process = Runtime.getRuntime().exec("su"); // 切换到root帐号
			os = new DataOutputStream(process.getOutputStream());
			// BufferedReader mReader = new BufferedReader(new
			// InputStreamReader(process.getInputStream()));
			// StringBuffer mRespBuff = new StringBuffer();
			// char[] buff = new char[1024];
			// int ch = 0;
			// while((ch = mReader.read(buff)) != -1){
			// mRespBuff.append(buff, 0, ch);
			// }
			// mReader.close();
			// os.writeBytes("chmod 777 " + "\n");
			// os.writeBytes("chmod 777 " + "/data \n");
			// os.writeBytes("chmod 777 " + "-R /data/data \n");
			// os.writeBytes("chmod 777 " + "/data/data/com.soft.apk008v \n");
			os.writeBytes("chmod 777 "
					+ "/data/data/com.android.providers.settings/databases \n");
			os.writeBytes("chmod 777 "
					+ "/data/data/com.android.providers.settings/databases/settings.db \n");
			// os.writeBytes("pm clear com.yzitel.sdk \n");
			if (boolean1 != true) {
				os.writeBytes(pkgCodePath);
			}
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return true;
	}

	// public void button1(View v) {
	//
	// filedel.performClick();
	// allRandom();
	// }

	public void allRandom() {
		String str = ImeiStore.getFromAssets(context);
		String str1[] = str.split(" ");
		setEditText(R.id.imei, this.imeiRandom(str1[0]));
		setEditText(R.id.model, str1[1] + " " + str1[2]);
		setEditText(R.id.manufacturer, str1[1]);
		setEditText(R.id.device, str1[2]);
		setEditText(R.id.resolution, str1[3]);
		setEditText(R.id.imsi, this.randomNum(15));
		setEditText(R.id.number, this.randomPhone());
		setEditText(R.id.simserial, this.randomNum(20));
		setEditText(R.id.wifimac, this.randomMac());
		setEditText(R.id.bluemac, this.randomMac1());
		setEditText(R.id.androidid, this.randomABC(16));
		setEditText(R.id.serial, this.randomNum(19) + "a");
		setEditText(R.id.macAddress, this.randomMac());
		setEditText(R.id.SSID, this.wifiMac());
		setEditText(R.id.brand, str1[1]);
	}

	public void getdb() {
		setEditText(R.id.imei, cursor[0]);
		setEditText(R.id.imsi, cursor[1]);
		setEditText(R.id.number, cursor[2]);
		setEditText(R.id.simserial, cursor[3]);
		setEditText(R.id.wifimac, cursor[4]);
		setEditText(R.id.bluemac, cursor[5]);
		setEditText(R.id.androidid, cursor[6]);
		setEditText(R.id.serial, cursor[7]);
		setEditText(R.id.device, cursor[18]);
		setEditText(R.id.brand, cursor[8]);
		setEditText(R.id.model, cursor[9]);
		setEditText(R.id.simOperator, cursor[10]);
		setEditText(R.id.SSID, cursor[11]);
		setEditText(R.id.macAddress, cursor[12]);
		setEditText(R.id.version_codes, cursor[13]);
		setEditText(R.id.release, cursor[14]);
		setEditText(R.id.resolution, cursor[15] + "x" + cursor[16]);
		setEditText(R.id.manufacturer, cursor[17]);
	}

	public void getdbfrom() {
		cursor1 = contentBean.getContent().split(",");
		setEditText(R.id.imei, cursor1[0]);
		setEditText(R.id.imsi, cursor1[1]);
		setEditText(R.id.number, cursor1[2]);
		setEditText(R.id.simserial, cursor1[3]);
		setEditText(R.id.wifimac, cursor1[4]);
		setEditText(R.id.bluemac, cursor1[5]);
		setEditText(R.id.androidid, cursor1[6]);
		setEditText(R.id.serial, cursor1[7]);
		setEditText(R.id.device, cursor1[14]);
		setEditText(R.id.brand, cursor1[8]);
		setEditText(R.id.model, cursor1[9]);
		setEditText(R.id.simOperator, cursor1[10]);
		setEditText(R.id.SSID, cursor1[11]);
		setEditText(R.id.macAddress, cursor1[12]);
		setEditText(R.id.version_codes, cursor1[13]);
		setEditText(R.id.release, cursor1[14]);
//		setEditText(R.id.resolution, cursor1[15] + "x" + cursor1[16]);
//		setEditText(R.id.manufacturer, cursor1[17]);
		// setEditText(R.id.resolution, cursor[15] + "x" + cursor[16]);
		// setEditText(R.id.manufacturer, contentBean.getm);
//		System.out.println(contentBean.getImei()+"=========111111111=======");
	}

	/**
	 * 清除缓存
	 */
	public void allDelete() {
		String content = "";
		Map<String, ?> allContent = sp1.getmap();
		for (Map.Entry<String, ?> entry : allContent.entrySet()) {
			content = entry.getKey();
			upgradeRootPermission("pm clear " + content + "\n", false);
			// System.out.println(content);
		}

	}

	public void allClear() {
		String content = "";
		Map<String, ?> allContent = sp2.getmap();
		for (Map.Entry<String, ?> entry : allContent.entrySet()) {
			content = entry.getKey();
			upgradeRootPermission("pm uninstall " + content + "\n", false);
			// System.out.println(content);

		}
	}

	public void fileDelete() {
		try {
			for (int j = 1; j < 100; j++) {
				String str1 = sp.getString(j + "", "");
				String str2 = str1.replace("\n", "").replace(" ", "")
						.replace(".ymtf", "");
				String str3[] = str2.split(":");
				str_file[i] = str3[2];
				i++;
			}
		} catch (Exception e) {

		}
		for (int j = 0; j < i; j++) {
			File file = new File(str_file[j]);
			FileChangeView fileChangeView = new FileChangeView();
			fileChangeView.RecursionDeleteFile(file, context);
		}
		sp.clearData();
	}

	public void creatdb() {

		dbUtil.opendb(context);
		dbUtil.creatTable();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String date = sdf.format(new java.util.Date());
		dbUtil.insert("guo" + date, initialValues);
		dbUtil.closedb(context);
	}

	public void querydb() {
		dbUtil.opendb(context);
		String temp = this.getEditText(R.id.editText2);
		cursor = dbUtil.query("guo" + this.getEditText(R.id.editText1),
				Integer.parseInt(temp));
		// String str[] = cursor.getColumnNames();
		dbnum = Integer.parseInt(temp);
		dbnum++;
		setEditText(R.id.editText2, dbnum + "");
		dbUtil.closedb(context);
		getdb();
		// fileDelete();
		// allDelete();
		// allClear();
		// FileService.FILEOBSERVER = 0;
		saveData();
	}

	public void querydbfrom() {
		String row = this.getEditText(R.id.et_row);
		String time = this.getEditText(R.id.dt_time);
		dbnum = Integer.parseInt(row);
		getto();
		dbnum++;
		setEditText(R.id.et_row, dbnum + "");
	//	saveDataFrom();

	}

	public static boolean shelldeletedatabases() {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su"); // 切换到root帐号
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("cd /data/data/com.android.providers.settings/databases\n");
			os.writeBytes("sqlite3 settings.db\n");
			os.writeBytes("delete from system where _id>110;\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return true;
	}

	public void saveto() {
		new Thread() {
			public void run() {
				try {
					Message msg = Message.obtain();
					HttpDoPost httpDoPost = new HttpDoPost();
					String returnxm = httpDoPost.httpPost("AddContentServlet",
							"name", sp.getString("username", "name"),
							"content", saveDatato(), "", "", "", "", "", "",
							"", "");
					Log.i("xml", returnxm);
					switch (returnxm.subSequence(0, 7) + "") {
					case "success":
						// msg.what = LOGIN_SUCCESS;
						break;
					case "failed":
						// msg.what = FAILED;
						break;
					case "no_thi":
						// msg.what = FAILED_NO_USER;
						break;
					default:
						break;
					}
					Log.i("msg.what", msg.what + "");
					// mainhandler.sendMessage(msg);

				} catch (Exception e) {
					Log.i("error", e.getMessage());

				}
			}
		}.start();
	}

	public void getto() {
		new Thread() {
			public void run() {
				try {
					Message msg = Message.obtain();
					HttpDoPost httpDoPost = new HttpDoPost();
					String returnxm = httpDoPost.httpPost("ContentServlet",
							"name", sp.getString("username", "name"), "row",
							getEditText(R.id.et_row), "time",
							getEditText(R.id.dt_time), "", "", "", "", "", "");
					Log.i("xml", returnxm);
					switch (returnxm.subSequence(0, 7) + "") {

					case "success":
						// msg.what = LOGIN_SUCCESS;
						Gson gson = new Gson();
						contentBean = gson.fromJson(returnxm.substring(8),
								ContentBean.class);
						new Handler(context.getMainLooper())
								.postDelayed(new Runnable() {
									public void run() {
										getdbfrom();
										saveDataFrom();
									}
								},2000);

						break;
					case "failed":
						// msg.what = FAILED;
						break;
					case "no_thi":
						// msg.what = FAILED_NO_USER;
						break;
					default:
						break;
					}
					Log.i("msg.what", msg.what + "");
					// mainhandler.sendMessage(msg);

				} catch (Exception e) {
					Log.i("error", e.getMessage());

				}
			}
		}.start();
	}

}
