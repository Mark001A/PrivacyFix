package cn.guo.privacyfix;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.guo.privacyfix.R;

public class FileChangeView extends Fragment {
	private TextView tv1;
	private Button btn1, btn2;
	private View view;
	private String[] str = new String[1000];
	private SharedPreferencesUtil sp;
	public static FileChangeViewHandler mFileChangeViewHandle;
	private int i = 0;
	private int k = 1;
	private Context context;

	public class FileChangeViewHandler extends Handler {
		public FileChangeViewHandler() {

		}

		public FileChangeViewHandler(Looper L) {
			super(L);
		}

		public void handleMessage(Message msg) {

			super.handleMessage(msg);
		}
	}

	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.file_change_view, null);
		context = getActivity();
		sp = new SharedPreferencesUtil(context, "FileDate");
		mFileChangeViewHandle = new FileChangeViewHandler();
		tv1 = (TextView) view.findViewById(R.id.textView1);
		btn1 = (Button) view.findViewById(R.id.btn1);
		btn2 = (Button) view.findViewById(R.id.btn2);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int j = 0; j < i; j++) {
					File file = new File(str[j]);
					RecursionDeleteFile(file, context);
				}
				sp.clearData();
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sp.clearData();
			}
		});
		try {
			for (int j = 1; j < 100; j++) {
				tv1.append(sp.getString(j + "", ""));
				tv1.append(j + "------------------------- \n");
				String str1 = sp.getString(j + "", "");
				String str2 = str1.replace("\n", "").replace(" ", "")
						.replace(".ymtf", "");
				String str3[] = str2.split(":");
				str[i] = str3[2];
				i++;
			}
		} catch (Exception e) {

		}
		return view;
	}

	/**
	 * µÝ¹éÉ¾³ýÎÄ¼þºÍÎÄ¼þ¼Ð
	 * 
	 * @param file
	 *            ÒªÉ¾³ýµÄ¸ùÄ¿Â¼
	 */
	public void RecursionDeleteFile(File file, Context context) {
		if (file.exists()) {
			if (file.isFile()) {
				try {
					file.delete();
					Toast.makeText(context, "É¾³ý³É¹¦---" + k, Toast.LENGTH_SHORT)
							.show();
					k++;
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(context, "É¾³ýÊ§°Ü",
							Toast.LENGTH_SHORT).show();
				}
				return;
			}
			if (file.isDirectory()) {
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					try {
						file.delete();
						Toast.makeText(context, "É¾³ý³É¹¦---" + k,
								Toast.LENGTH_SHORT).show();
						k++;
					} catch (Exception e) {
						// TODO: handle exception
						Toast.makeText(context, "É¾³ýÊ§°Ü", Toast.LENGTH_SHORT)
								.show();
					}
					return;
				}
				for (File f : childFile) {
					RecursionDeleteFile(f, context);
				}
				try {
					file.delete();
					Toast.makeText(context, "É¾³ý³É¹¦----" + k, Toast.LENGTH_SHORT)
							.show();
					k++;
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(context, "É¾³ýÊ§°Ü", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

}
