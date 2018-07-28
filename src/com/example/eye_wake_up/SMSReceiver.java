package com.example.eye_wake_up;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.example.homeautomation.R;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {
	Context context;
	static MediaPlayer mp1;

	@Override
	public void onReceive(Context context, Intent intent) {

		String phoneNumbers = PreferenceManager.getDefaultSharedPreferences(
				context).getString("phone_entries", "");
		StringTokenizer tokenizer = new StringTokenizer(phoneNumbers, ",");
		Set<String> phoneEnrties = new HashSet<String>();
		while (tokenizer.hasMoreTokens()) {
			phoneEnrties.add(tokenizer.nextToken().trim());
		}
		Bundle bundle = intent.getExtras();
		Object[] pdus = (Object[]) bundle.get("pdus");
		SmsMessage[] messages = new SmsMessage[pdus.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
			String address = messages[i].getOriginatingAddress();
			Log.e("Cell No.==>", phoneNumbers);
			if (phoneEnrties.contains(address)) {
//				if (messages[i].getDisplayMessageBody().contains(
//						"Person is Sleeping")) {
					Intent newintent = new Intent(context, MainActivity.class);
					newintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					newintent.putExtra("address", address);
					newintent.putExtra("VALUE_OF_P", 1);
					newintent.putExtra("TITLE", "PERSON IS SLEEPING....");
					newintent.putExtra("message",
							messages[i].getDisplayMessageBody());
					context.startActivity(newintent);

//				}

				// //////Sound Alarm//////////
				mp1 = MediaPlayer.create(context, R.raw.police);
				mp1.start();

			}
		}
	}
}