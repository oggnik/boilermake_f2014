package com.example.friendcompass;

import android.view.animation.RotateAnimation;
import android.widget.ImageView;
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
		RotateAnimation r = new RotateAnimation((float) cActivity.getAngletonorth(),
				(float) (cActivity.getAngletonorth() + 10), 
				(float) cActivity.getImageView().getLeft() + 75, 
				(float) (cActivity.getImageView().getBottom() - 75));
		cActivity.setAngletonorth((float) cActivity.getAngletonorth() + 10);
		cActivity.getImageView().startAnimation(r);
		
	}
	
	
	
}
