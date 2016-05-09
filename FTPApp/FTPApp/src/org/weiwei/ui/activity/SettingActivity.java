package org.weiwei.ui.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SettingActivity extends Activity {

	private LinearLayout l ;
	private Button b;
	private HorizontalScrollView scroll;
	private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
	private static final int WARP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		l = (LinearLayout) findViewById(R.id.my);
		b = (Button) findViewById(R.id.my1);
		scroll = new HorizontalScrollView(this);
		LayoutParams lp1 = new LayoutParams(MATCH_PARENT,WARP_CONTENT);
		final LinearLayout ll = new LinearLayout(this);
		scroll.addView(ll,lp1);

//		scroll.setVerticalScrollbarPosition(HorizontalScrollView.SCROLLBAR_POSITION_LEFT);
		l.addView(scroll);
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView t1 = new TextView(SettingActivity.this);
				LayoutParams lp = new LayoutParams(WARP_CONTENT,WARP_CONTENT);
				t1.setText("1909");
				ll.addView(t1,lp);
				scroll.post(new Runnable() {
					
					@Override
					public void run() {
						scroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
						
					}
				});
			}
		});
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
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
