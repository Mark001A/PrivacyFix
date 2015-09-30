package cn.guo.privacyfix;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AppListView extends Fragment {

	private PackageManager pm;
	private ArrayAdapter<String> adapter;
	private ListView appListView;
	private ActivityManager am;
	private TextView tv;
	private SharedPreferencesUtil sp1, sp2;
	private Button btn1, btn2;
	private View view;
	private Context context;

	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		view =  inflater.inflate(R.layout.app_list_view, null);
		appListView = (ListView) view.findViewById(R.id.applistview);
		context = getActivity();
		List<String> planets = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(context, /* context */
		android.R.layout.simple_list_item_1, planets); /* 数据源： String[] */
		appListView.setAdapter(adapter);
		sp1 = new SharedPreferencesUtil(context, "AppClearList");
		sp2 = new SharedPreferencesUtil(context, "AppNoUninstallList");
		queryApp();
		appListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// System.out.println(position + "");
				tv = (TextView) view.findViewById(android.R.id.text1);
				String temp = tv.getText().toString();
				final String temp1[] = temp.split("=");
				AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				builder.setTitle("清除缓存or卸载软件");
				final String[] arrayFruit = new String[] { "清除缓存", "删除软件",
						"设置为需要被清除缓存", "设置为不需要被删除" };
				builder.setItems(arrayFruit,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0: {
									// Toast.makeText(AppListView.this,
									// arrayFruit[0],
									// Toast.LENGTH_SHORT).show();
									clearApp();
									break;
								}
								case 1: {
									// Toast.makeText(AppListView.this,
									// arrayFruit[1],
									// Toast.LENGTH_SHORT).show();
									uninstall();
									break;
								}
								case 2: {
									sp1.setString(temp1[0], temp1[0]);
								}
								case 3: {
									sp2.remove(temp1[0]);
								}
								}
							}
						});
				builder.create().show();
			}
		});
		btn1 = (Button) view.findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sp2.clearData();
				queryApp1();
			}
		});
		btn2 = (Button) view.findViewById(R.id.button2);
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(
						context);
				builder.setTitle("");
				builder.setMessage(sp2.getmap1());
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@SuppressLint("ShowToast")
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						});
				builder.create().show();
			}
		});
		return view;
	}

	public void queryApp() {
		// 查询所有已经安装的应用程序
		pm = context.getPackageManager();
		am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));// 排序
		for (ApplicationInfo app : listAppcations) {
			if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
				adapter.add(app.packageName + "==="
						+ app.loadLabel(pm).toString());
			}
		}
		adapter.notifyDataSetChanged();
	}

	public void queryApp1() {
		// 查询所有已经安装的应用程序
		pm = context.getPackageManager();
		am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ApplicationInfo> listAppcations = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations,
				new ApplicationInfo.DisplayNameComparator(pm));// 排序
		for (ApplicationInfo app : listAppcations) {
			if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
				// adapter.add(app.packageName + "==="
				// + app.loadLabel(pm).toString());
				sp2.setString(app.packageName, app.packageName);
			}
		}
		// adapter.notifyDataSetChanged();
	}

	public void clear() {
		try {
			Method method = PackageManager.class.getMethod(
					"getPackageSizeInfo", new Class[] { String.class,
							IPackageStatsObserver.class });
			method.invoke(pm, new Object[] { "com.yzitel.sdk",
					new IPackageStatsObserver.Stub() {

						@Override
						public void onGetStatsCompleted(PackageStats pStats,
								boolean succeeded) throws RemoteException {
							long cachesize = pStats.cacheSize;
							long codesize = pStats.codeSize;
							long datasize = pStats.dataSize;
							System.out.println("cachesize:" + cachesize);
							System.out.println("codesize:" + codesize);
							System.out.println("datasize" + datasize);
						}
					} });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void uninstall() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				try {
					MainFragment.upgradeRootPermission(
							"pm uninstall " + tv.getText() + "\n", false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	public void clearApp() {
		new Thread() {
			public void run() {
				try {
					MainFragment.upgradeRootPermission(
							"pm clear " + tv.getText() + "\n", false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

}
