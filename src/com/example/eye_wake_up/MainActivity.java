package com.example.eye_wake_up;

import com.example.homeautomation.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	Button stopButton;
	int p = 0;
	public Vibrator vibrator;
	String address, message, title;

	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			address = extras.getString("address");
			message = extras.getString("message");
			title = extras.getString("TITLE");
			p = extras.getInt("VALUE_OF_P");
			TextView addressField = (TextView) findViewById(R.id.address);
			TextView messageField = (TextView) findViewById(R.id.message);
			addressField.setText(address);
			messageField.setText(message);
		}
		if (p == 1) {
			// ////////Vibration/////////
			long pattern[] = { 0, 100, 200, 300, 400 };
			vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(pattern, 0);

			AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
					.create();

			// Setting Dialog Title
			alertDialog.setTitle(title);

			// Setting Dialog Message
			alertDialog.setMessage("Mob:" + address + "\n" + "Msg:" + message);
			alertDialog.setIcon(R.drawable.images);
			// Create a New Intent for Vibration and start the service

			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					SMSReceiver.mp1.stop();
					vibrator.cancel();
					MainActivity.this.finish();

				}
			});

			// Showing Alert Message
			alertDialog.show();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.settings:
			startActivity(new Intent(this, SettingsActivity.class));
		}
		return super.onOptionsItemSelected(item);
	}
}
