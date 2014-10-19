package main;

import gps.GPSMath;

public class LocatorSession {
	private Client client1;
	private Client client2;
	private Server server;
	private GPSMath gpsMath;
	
	public LocatorSession(Server server, Client client1, Client client2) {
		this.server = server;
		this.client1 = client1;
		this.client2 = client2;
		this.gpsMath = new GPSMath();
	}
	
	/**
	 * Update a clients location
	 * @param c
	 * @param location
	 */
	public void updateLocation(Client c, double[] location) {
		System.out.println("Location received from client: " + c);
		System.out.println("\t" + location[0] + ", " + location[1]);
		// Calculate distance + angle for each client
		if (c == client1) {
			gpsMath.setLocation2(location[0], location[1]);
		} else {
			gpsMath.setLocation1(location[0], location[1]);
		}
		
		double distance = gpsMath.getDistance();
		double client1Angle = gpsMath.getAngle21();
		double client2Angle = gpsMath.getAngle12();
		
		client1.sendMessage(distance + "," + client1Angle);
		client2.sendMessage(distance + "," + client2Angle);
	}
	
	/**
	 * Client disconnect
	 * @param c
	 */
	public void disconnect(Client c) {
		client1.setLocatorSession(null);
		client2.setLocatorSession(null);
		server.removeLocatorSession(this);
	}
	
	/**
	 * toString
	 */
	public String toString() {
		return "Session: [" + client1 + ", " + client2 + "]";
	}
}
