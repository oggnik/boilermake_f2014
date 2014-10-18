package main;

public class LocatorSession {
	private Client client1;
	private Client client2;
	
	public LocatorSession(Client client1, Client client2) {
		this.client1 = client1;
		this.client2 = client2;
	}
	
	public void updateLocation(Client c, double[] location) {
		// Calculate distance + angle for each client
		client1.sendMessage("hullo");
		client2.sendMessage("hmm");
	}
}
