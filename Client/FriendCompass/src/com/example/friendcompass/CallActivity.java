package com.example.friendcompass;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class CallActivity extends Activity implements SensorEventListener {
	private Client client;
	private double angletonorth = 0.0;
	private double rotation = 0.0;
	double orientation;
	public ImageView image;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private SensorManager mSensorManager;
	private Sensor accelerometer;
	private Sensor magnetometer;
	
	public double getAngletonorth() {
		return angletonorth;
	}
	
	public ImageView getImageView() {
		return image;
	}

	public void setAngletonorth(double angletonorth) {
		this.angletonorth = angletonorth;
	}

	public double getOrientation() {
		return orientation;
	}

	public double getRotation(){
		return rotation;
	}
	
	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        image = (ImageView) findViewById(R.id.imageView1);
        setAngletonorth((float) 0);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }
    
    protected void onResume(){
    	super.onResume();
    	mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    	if (client == null || !client.isConnected()) {
    		client = new Client(this);
    		client.start();
    	}
    	
    	// Acquire a reference to the system Location Manager
    	locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

    	// Define a listener that responds to location updates
    	locationListener = new LocationListener() {
    		public void onLocationChanged(Location location) {
    			// Called when a new location is found by the network location
    			// provider.
    			client.sendMessage(location.getLatitude() + "," + location.getLongitude());
    		}

    		@Override
    		public void onProviderDisabled(String provider) {
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void onProviderEnabled(String provider) {
    			// TODO Auto-generated method stub
    			
    		}

    		@Override
    		public void onStatusChanged(String provider, int status, Bundle extras) {
    			// TODO Auto-generated method stub
    			
    		}
    	};
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.call, menu);
        return true;
    }
    
    protected void onPause(){
    	super.onPause();
    	mSensorManager.unregisterListener(this);
    	client.closeSocket();
    	locationManager.removeUpdates(locationListener);
    	System.out.println("close");
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
	            rotation = -azimut * 360 / (2 * 3.1415926535);
	        }
	    }
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
    
}
