package org.weiwei.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class MoreOperation extends Activity implements OnClickListener {

	private RelativeLayout layout;
	private RelativeLayout about,setting,conectPc,myPhone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_operation);
		
		layout = (RelativeLayout) findViewById(R.id.id_more_op_ll);
		about = (RelativeLayout) findViewById(R.id.id_more_op_about);
		setting = (RelativeLayout) findViewById(R.id.id_more_op_setting);
		conectPc= (RelativeLayout) findViewById(R.id.id_more_op_connect_pc);
		myPhone = (RelativeLayout) findViewById(R.id.id_more_op_my_phone);
		

		layout.setOnClickListener(this);
		about.setOnClickListener(this);
		setting.setOnClickListener(this);
		conectPc.setOnClickListener(this);
		myPhone.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more_operation, menu);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_more_op_about:
			Intent i1 = new Intent(this,AboutActivity.class);
			startActivity(i1);
			break;
		case R.id.id_more_op_setting:
			Intent i2 = new Intent(this,SettingActivity.class);
			startActivity(i2);
			break;
		case R.id.id_more_op_connect_pc:
			break;
		case R.id.id_more_op_my_phone:
			break;
		case R.id.id_more_op_ll:
			break;
		default:

		}
		finish();
	}

}
