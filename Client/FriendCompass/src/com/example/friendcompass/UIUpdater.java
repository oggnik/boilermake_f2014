package com.example.friendcompass;

import android.graphics.Matrix;
import android.widget.ImageView.ScaleType;
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
		/*RotateAnimation r = new RotateAnimation((float) cActivity.getAngletonorth(),
				(float) (cActivity.getAngletonorth() + 10), 
				(float) cActivity.getImageView().getWidth()/2, 
				(float) (cActivity.getImageView().getHeight()/2));
		cActivity.setAngletonorth((float) cActivity.getAngletonorth() + 10);
		cActivity.getImageView().startAnimation(r);*/
		Matrix matrix=new Matrix();
		cActivity.getImageView().setScaleType(ScaleType.MATRIX);   //required
		matrix.postRotate((float) cActivity.getAngletonorth() + 10, 
				cActivity.getImageView().getDrawable().getBounds().width()/2, 
				cActivity.getImageView().getDrawable().getBounds().height()/2);
		cActivity.getImageView().setImageMatrix(matrix);
		cActivity.setAngletonorth(cActivity.getAngletonorth() + 10);
		
	}
	
	
	
}
