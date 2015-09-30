package cn.guo.privacyfix;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginAndReg extends Fragment {
	public static MainHandler mainhandler;
	private EditText username, password;
	private ProgressDialog dialog;
	private final int SUCCESS = 1;
	private final int FAILED = 2;
	private final int LOGIN_SUCCESS = 3;
	private final int FAILED_NO_USER = 4;
	private Context context;
	private View view;
	private TextView tv_login,tv_regist;
	private SharedPreferencesUtil sp;

	public class MainHandler extends Handler {
		public MainHandler() {

		}

		public MainHandler(Looper L) {
			super(L);
		}

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUCCESS:
				dialog.dismiss();
				maketoast(context, "注册成功");
				break;
			case FAILED:
				dialog.dismiss();
				maketoast(context, "注册失败");
				break;
			case LOGIN_SUCCESS:
				dialog.dismiss();
				maketoast(context, "登陆成功");
				Intent intent = new Intent();
				intent.putExtra("name", username.getText().toString());
				intent.setClass(context, HomeActivity.class);
				startActivity(intent);
				break;
			case FAILED_NO_USER:
				dialog.dismiss();
				maketoast(context, "该用户不存在或用户名密码错误");
				break;
			default:
				break;
			}

			super.handleMessage(msg);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.loginandreg, null);
		mainhandler = new MainHandler();
		context = getActivity();
		init();	
		sp = new SharedPreferencesUtil(context, "user");
		tv_login = (TextView) view.findViewById(R.id.tv_login);
		tv_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(MainActivity.this, HomeActivity.class);
				// startActivity(intent);
				login();
				dialog = ProgressDialog.show(context, "",
						"Loading. Please wait...", true);
				dialog.setCancelable(true);
			}
		});
		tv_regist = (TextView) view.findViewById(R.id.tv_regist);
		tv_regist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				regist();
			}
		});
		return view;
	}

	private void init() {
		this.username = (EditText) view.findViewById(R.id.username);
		this.password = (EditText) view.findViewById(R.id.password);
	}

	public void regist() {

		new Thread() {
			public void run() {
				try {
					Message msg = Message.obtain();
					HttpDoPost httpDoPost = new HttpDoPost();
					String returnxm = httpDoPost.httpPost("RegisterServlet",
							"name", username.getText().toString(), "password",
							password.getText().toString(), "", "", "", "", "",
							"", "", "");
					Log.i("xml", returnxm);
					// if(returnxm.equals("success") ){
					//
					// }
					switch (returnxm.subSequence(0, 7) + "") {
					case "success":
						msg.what = SUCCESS;
						sp.setString("username", username.getText().toString());
						sp.setString("password", password.getText().toString());
						break;
					case "failed":
						msg.what = FAILED;
					default:
						break;
					}
					Log.i("msg.what", msg.what + "");
					mainhandler.sendMessage(msg);

				} catch (Exception e) {
					Log.i("error", e.getMessage());

				}
			}
		}.start();
	}

	public void login() {
		new Thread() {
			public void run() {
				try {
					Message msg = Message.obtain();
					HttpDoPost httpDoPost = new HttpDoPost();
					String returnxm = httpDoPost.httpPost("LoginServlet",
							"name", username.getText().toString(), "password",
							password.getText().toString(), "", "", "", "", "",
							"", "", "");
					Log.i("xml", returnxm);
					switch (returnxm.subSequence(0, 7) + "") {
					case "success":
						msg.what = LOGIN_SUCCESS;
						sp.setString("username", username.getText().toString());
						sp.setString("password", password.getText().toString());
						break;
					case "failed":
						msg.what = FAILED;
						break;
					case "no_thi":
						msg.what = FAILED_NO_USER;
						break;
					default:
						break;
					}
					Log.i("msg.what", msg.what + "");
					mainhandler.sendMessage(msg);

				} catch (Exception e) {
					Log.i("error", e.getMessage());

				}
			}
		}.start();
	}

	private void maketoast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
	}

}
