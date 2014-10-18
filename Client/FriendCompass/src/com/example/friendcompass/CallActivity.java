package com.example.friendcompass;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CallActivity extends Activity {
	private Client client;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
    }
    
    protected void onResume(){
    	super.onResume();
    	if (client == null || !client.isConnected()) {
    		client = new Client(this);
    		client.start();
    	}
    	client.sendMessage("hi");
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
