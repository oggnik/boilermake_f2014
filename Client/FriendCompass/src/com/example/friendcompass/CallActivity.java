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
	private double modifiedAngleToNorth = 0.0;
	private double trueAngleToNorth = 0.0;

	private double rotation = 0.0;
	private double distance = 0.0;
	double orientation;
	public ImageView image;
	private LocationManager locationManager;
	private LocationListener locationListener;
	private SensorManager mSensorManager;
	private Sensor compass;
	
	public double getModifiedAngleToNorth() {
		return modifiedAngleToNorth;
	}
	
	public ImageView getImageView() {
		return image;
	}

	public void setModifiedAngleToNorth(double angletonorth) {
		this.modifiedAngleToNorth = angletonorth;
	}

	public double getTrueAngleToNorth() {
		return trueAngleToNorth;
	}

	public void setTrueAngleToNorth(double trueAngleToNorth) {
		this.trueAngleToNorth = trueAngleToNorth;
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
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        image = (ImageView) findViewById(R.id.imageView1);
        setModifiedAngleToNorth((float) 0);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        compass = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }
    
    protected void onResume(){
    	super.onResume();
    	mSensorManager.registerListener(this, compass, SensorManager.SENSOR_DELAY_GAME);
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
    	System.out.println("sensor changed");
		System.out.println(event.sensor.getType() == Sensor.TYPE_ORIENTATION);
	    if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
	    	float azimuth = event.values[0];
	    	rotation = azimuth;
            System.out.println(rotation);
            runOnUiThread(new UIUpdater(this, distance + "_o," + trueAngleToNorth));
	    }
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
    
}
