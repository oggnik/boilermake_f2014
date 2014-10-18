package com.example.friendcompass;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class CallActivity extends Activity {
	private Client client;
	private double angletonorth;
	double orientation;
	public ImageView image;
	
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
    	System.out.println("close");
    }
    
}
