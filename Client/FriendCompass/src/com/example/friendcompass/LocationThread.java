package com.example.friendcompass;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class LocationThread extends Thread {
	private CallActivity cActivity;
	private Client client;
	private boolean running;
	
	// Acquire a reference to the system Location Manager
	LocationManager locationManager = (LocationManager)cActivity.getSystemService(Context.LOCATION_SERVICE);

	// Define a listener that responds to location updates
	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			// Called when a new location is found by the network location
			// provider.
			makeUseOfNewLocation(location);
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

	public LocationThread(CallActivity cActivity, Client client, boolean running) {
		this.cActivity = cActivity;
		this.client = client;
		this.running = running;
	}
	
	/**
	 * Start reading from the connection
	 */
	public void run() {
		try {
			while (running) {
				String locationString = null;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				locationString = "lat, long";
				
				client.sendMessage(locationString);
			}
		} catch (IllegalStateException ise) {
			// Socket closed, don't worry
		}
	}
	
	private void setRunning(boolean run){
		running = run;
	}
}