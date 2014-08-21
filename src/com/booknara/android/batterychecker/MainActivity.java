package com.booknara.android.batterychecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	private TextView mPlugged;
	private TextView mStatus;
	private TextView mLevel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mPlugged = (TextView) findViewById(R.id.plugged_text);
		mStatus = (TextView) findViewById(R.id.status_text);
		mLevel = (TextView) findViewById(R.id.level_text);
		
		registerReceiver(batterReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(batterReceiver);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	private BroadcastReceiver batterReceiver = new BroadcastReceiver() {
		Intent intent = null;
		
		@Override
		public void onReceive(Context context, Intent intent) {
			this.intent = intent;
			
			mPlugged.setText("Battery Plugged : " + getPlugged());
			mStatus.setText("Battery Status : " + getStatus());
			mLevel.setText("Battery Level : " + getLevel());
		}
		
		public String getPlugged() {
			int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
			
			String pluggedStr = "";
			switch (plugged) {
			case BatteryManager.BATTERY_PLUGGED_AC:
				pluggedStr = "BATTERY_PLUGGED_AC";
				break;
			case BatteryManager.BATTERY_PLUGGED_USB:
				pluggedStr = "BATTERY_PLUGGED_USB";
				break;
			default:
				break;
			}
			
			return pluggedStr;
		}
		
		public String getStatus() {
			int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
			
			String statusStr = "";
			switch (status) {
			case BatteryManager.BATTERY_STATUS_CHARGING:
				statusStr = "BATTERY_STATUS_CHARGING";
				break;
			case BatteryManager.BATTERY_STATUS_DISCHARGING:
				statusStr = "BATTERY_STATUS_DISCHARGING";
				break;
			case BatteryManager.BATTERY_STATUS_FULL:
				statusStr = "BATTERY_STATUS_FULL";
				break;
			case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
				statusStr = "BATTERY_STATUS_NOT_CHARGING";
				break;
			case BatteryManager.BATTERY_STATUS_UNKNOWN:
				statusStr = "BATTERY_STATUS_UNKNOWN";
				break;
			default:
				break;
			}
			return statusStr;			
		}
		
		public String getLevel() {
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
			
			if(level == -1 || scale == -1) {
		        return "0%";
		    }

		    return "" + ((float)level / (float)scale) * 100.0f + "%"; 
		}
	};
}