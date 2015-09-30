package cn.guo.privacyfix;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import cn.guo.privacyfix.MenuFragment.toggleMenu;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * 
 * @author guo
 */
public class HomeActivity extends SlidingFragmentActivity implements toggleMenu {

	private SlidingMenu mMenu;
	private FragmentManager fm;
	public static String NAME, password;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
		initSlidingMenu();
		setContentView(R.layout.home_layout);
		setBehindContentView(R.layout.menu_layout);
		fm = getSupportFragmentManager();
		fm.beginTransaction().add(R.id.home_layout, new MainFragment())
				.commit();
		MenuFragment menuFragment = new MenuFragment();
		menuFragment.setToggleMenu(this);
		fm.beginTransaction().add(R.id.menu_layout, menuFragment).commit();
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// this.getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.more, menu);
		return true;
	}

	private void initSlidingMenu() {
		int width = getWindowManager().getDefaultDisplay().getWidth();

		mMenu = getSlidingMenu();
		mMenu.setMode(SlidingMenu.LEFT);
		mMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		mMenu.setShadowWidth(100);
		mMenu.setBehindOffset(width - 400);
	}

	// @Override
	// public void selectMenu(boolean b) {
	// // TODO Auto-generated method stub
	// if(b){
	// mMenu.toggle();
	// }
	// }

	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {

		case android.R.id.home:
			System.out.println("icon");
			mMenu.showMenu();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void selectMenu(boolean b, final ProgressDialog dialog) {
		// TODO Auto-generated method stub
		if (b) {
			mMenu.toggle();
			new Handler(this.getMainLooper()).postDelayed(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			}, 500);

		}
	}

}
