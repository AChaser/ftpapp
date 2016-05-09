package org.weiwei.ui.activity;

import org.weiwei.ui.activity.R;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		SlidingMenu menu = new SlidingMenu(this);
      menu.setMode(SlidingMenu.LEFT);
      menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
      menu.setShadowWidthRes(R.dimen.shadow_width);
//      menu.setShadowDrawable(R.drawable.shadow);
      menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
      menu.setFadeDegree(0.35f);
      menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
      menu.setMenu(R.layout.sliding_menu);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
