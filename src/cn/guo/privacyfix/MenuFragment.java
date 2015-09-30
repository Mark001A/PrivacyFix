package cn.guo.privacyfix;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MenuFragment extends Fragment implements OnClickListener {

	private toggleMenu tMenu;

	private LinearLayout menuname_btn;
	private LinearLayout sign_btn;
	private LinearLayout sunmti_btn;
	private LinearLayout exit_btn;
	private LinearLayout loginandreg_btn;
	private FragmentManager fm;
	private ProgressDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.menu_fragment_layout, null);
		menuname_btn = (LinearLayout) view.findViewById(R.id.menu_name_text);
		sunmti_btn = (LinearLayout) view.findViewById(R.id.menu_submit_text);
		exit_btn = (LinearLayout) view.findViewById(R.id.menu_exit_btn);
		loginandreg_btn = (LinearLayout) view.findViewById(R.id.menu_loginandreg_btn);
		
		menuname_btn.setOnClickListener(this);
		sunmti_btn.setOnClickListener(this);
		exit_btn.setOnClickListener(this);
		loginandreg_btn.setOnClickListener(this);
		fm = getFragmentManager();
		dialog = new ProgressDialog(getActivity());
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("Ìø×ªÖÐ....");
		dialog.setCancelable(true);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_name_text:
			fm.beginTransaction().replace(R.id.home_layout, new MainFragment())
					.commit();
			dialog.show();
			tMenu.selectMenu(true, dialog);
			break;
		case R.id.menu_submit_text:
			fm.beginTransaction().replace(R.id.home_layout, new AppListView())
					.commit();
			dialog.show();
			tMenu.selectMenu(true, dialog);
			break;
		case R.id.menu_exit_btn:
			fm.beginTransaction()
					.replace(R.id.home_layout, new FileChangeView()).commit();
			dialog.show();
			tMenu.selectMenu(true, dialog);
			break;
		case R.id.menu_loginandreg_btn:
			fm.beginTransaction()
					.replace(R.id.home_layout, new LoginAndReg()).commit();
			dialog.show();
			tMenu.selectMenu(true, dialog);
		default:
			break;
		}
	}

	public void setToggleMenu(toggleMenu tMenu) {
		this.tMenu = tMenu;
	}

	public interface toggleMenu {
		void selectMenu(boolean b, ProgressDialog dialog);

		// void selectMenu(boolean b);
	}

}
