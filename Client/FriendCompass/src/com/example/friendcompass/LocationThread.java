package com.example.friendcompass;


public class LocationThread extends Thread {
	private Client client;
	private boolean running;
	
	public LocationThread(Client client, boolean running) {
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