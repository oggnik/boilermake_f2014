package com.example.friendcompass;

import android.view.animation.RotateAnimation;
import android.widget.TextView;

public class UIUpdater implements Runnable {
	private String message;
	private CallActivity cActivity;
	private BufferedImage image;
	
	public UIUpdater(CallActivity cAct, String mes, BufferedImage img) {
		cActivity = cAct;
		message = mes;
		image = img;
	}
	
	public void run() {
		TextView tv = (TextView)cActivity.findViewById(R.id.distanceLabel);
		tv.setText(message);
		RotateAnimation r = new RotateAnimation((float) 1.2, (float) 2.3);
		image.s
	}
	
	
	
}
