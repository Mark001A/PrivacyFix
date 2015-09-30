package cn.guo.privacyfix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				/* Create an Intent that will start the Main WordPress Activity. */
				Intent mainIntent = new Intent(Splash.this, HomeActivity.class);
				Splash.this.startActivity(mainIntent);
				Splash.this.finish();
			}
		}, 2000);

	}
}
