package main;

public class LocatorSession {
	private Client client1;
	private Client client2;
	private Server server;
	
	public LocatorSession(Server server, Client client1, Client client2) {
		this.server = server;
		this.client1 = client1;
		this.client2 = client2;
	}
	
	/**
	 * Update a clients location
	 * @param c
	 * @param location
	 */
	public void updateLocation(Client c, double[] location) {
		System.out.println("Location received from client");
		// Calculate distance + angle for each client
		client1.sendMessage("hullo");
		client2.sendMessage("hmm");
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
}
