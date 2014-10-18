package com.example.friendcompass;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class CallActivity extends Activity {
	private Client client;
	private double angletonorth;
	double orientation;
	public ImageView image;
	private LocationManager locationManager;
	private LocationListener locationListener;
	
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

	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        image = (ImageView) findViewById(R.id.imageView1);
        setAngletonorth((float) 0);
    }
    
    protected void onResume(){
    	super.onResume();
    	if (client == null || !client.isConnected()) {
    		client = new Client(this);
    		client.start();
    	}
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	client.sendMessage("100,30");
    	
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
    	client.closeSocket();
    	locationManager.removeUpdates(locationListener);
    	System.out.println("close");
    }
    
}
