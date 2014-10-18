package com.example.friendcompass;

import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;


public class UIUpdater implements Runnable, SensorEventListener{
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
	
	public void onSensorChanged(SensorEvent event) {
		float[] mGravity = null;
		float[] mGeomagnetic = null;
	    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
	        mGravity = event.values;

	    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
	        mGeomagnetic = event.values;

	    if (mGravity != null && mGeomagnetic != null) {
	        float R[] = new float[9];
	        float I[] = new float[9];

	        if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {

	            // orientation contains azimut, pitch and roll
	            float orientation[] = new float[3];
	            SensorManager.getOrientation(R, orientation);

	            float azimut = orientation[0];
	            float rotation = -azimut * 360 / (2 * 3.14159f);
	            Matrix matrix=new Matrix();
	    		cActivity.getImageView().setScaleType(ScaleType.MATRIX);   //required
	    		matrix.postRotate((float) cActivity.getAngletonorth() + 10, 
	    				cActivity.getImageView().getDrawable().getBounds().width()/2, 
	    				cActivity.getImageView().getDrawable().getBounds().height()/2);
	    		cActivity.getImageView().setImageMatrix(matrix);
	    		cActivity.setAngletonorth((cActivity.getAngletonorth() - rotation + 360) % 360);
	        }
	    }
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
