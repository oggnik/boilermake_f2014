package com.example.friendcompass;

import android.view.animation.RotateAnimation;
import android.widget.TextView;


public class UIUpdater implements Runnable{
	private String message;
	private CallActivity cActivity;
	private double angle;
	
	public UIUpdater(CallActivity cAct, String mes) {
		cActivity = cAct;
		message = mes;
		String[] parts = mes.split(",");
		double trueAngle = Double.parseDouble(parts[1]);
		cAct.setTrueAngleToNorth(trueAngle);
		angle = (trueAngle - cActivity.getRotation() + 360) % 360;

	}
	
	public void run() {
		TextView tv = (TextView)cActivity.findViewById(R.id.distanceLabel);
		tv.setText(message);
		RotateAnimation r = new RotateAnimation((float) cActivity.getModifiedAngleToNorth(),
				(float) angle,
				(float) cActivity.getImageView().getWidth()/2, 
				(float) (cActivity.getImageView().getHeight()/2));
		cActivity.setModifiedAngleToNorth((float) angle);
		r.setRepeatCount(RotateAnimation.INFINITE);
		cActivity.getImageView().startAnimation(r);
		
	}
	
}
