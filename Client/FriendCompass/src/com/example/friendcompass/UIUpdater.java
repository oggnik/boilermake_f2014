package com.example.friendcompass;

import android.view.animation.RotateAnimation;
import android.widget.TextView;

public class UIUpdater implements Runnable {
	private String message;
	private CallActivity cActivity;
	
	
	public UIUpdater(CallActivity cAct, String mes) {
		cActivity = cAct;
		message = mes;

	}
	
	public void run() {
		TextView tv = (TextView)cActivity.findViewById(R.id.distanceLabel);
		tv.setText(message);
	//	RotateAnimation r = new RotateAnimation((float) 1.2, (float) 2.3);
	}
	
	
	
}
