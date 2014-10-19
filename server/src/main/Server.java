package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread {
	private ServerSocket serverSocket = null;
	private ArrayList<Client> clients;
	private ArrayList<Client> availableClients;
	private ArrayList<LocatorSession> sessions;
	private boolean listening = false;
	
	public Server() {
		clients = new ArrayList<Client>();
		availableClients = new ArrayList<Client>();
		sessions = new ArrayList<LocatorSession>();
	}
	
	/**
	 * Accept incoming connections and create a client for each
	 */
	public void run() {
		// Open the server socket
		try {
			serverSocket = new ServerSocket(Definitions.SERVER_PORT);
		} catch (IOException e) {
			System.out.println("Socket creation failed.");
			System.out.println(e);
			System.exit(1);
		}
		System.out.println("Server running");
		
		// Start listening for connections
		listening = true;
		while (listening) {
			Client client;
			try {
				client = new Client(this, serverSocket.accept());
				System.out.println("Client connected: " + client);
				client.start();
			} catch (IOException e) {
				if (listening) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Add a client
	 * @param client
	 */
	public void addClient(Client client) {
		clients.add(client);
		addClientToAvailable(client);
	}
	
	/**
	 * Stop the server and sever any connections
	 */
	public void stopServer() {
		System.out.println("Server shutting down");
		listening = false;
		try {
			System.out.println("Closing socket");
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	/**
	 * Remove a client
	 * @param client
	 */
	public void removeClient(Client client) {
		clients.remove(client);
		availableClients.remove(client);
		System.out.println("Client remove: " + client);
		System.out.println("\tTotal clients: " + clients.size());
		System.out.println("\tAvailable clients: " + availableClients.size());
		System.out.println("\tSessions: " + sessions.size());
	}
	
	/**
	 * Add a client to available
	 * Only adds if in the client list
	 * @param client
	 */
	public void addClientToAvailable(Client client) {
		if (!availableClients.contains(client) && clients.contains(client)) {
			availableClients.add(client);
			System.out.println("Client added.");
			System.out.println("\tTotal clients: " + clients.size());
			System.out.println("\tAvailable clients: " + availableClients.size());
			System.out.println("\tSessions: " + sessions.size());
			
			if (availableClients.size() == 2) {
				System.out.println("Starting session");
				Client client1 = availableClients.remove(0);
				Client client2 = availableClients.remove(0);
				LocatorSession locSes = new LocatorSession(this, client1, client2);
				client1.setLocatorSession(locSes);
				client2.setLocatorSession(locSes);
				sessions.add(locSes);
			}
		}
	}
	
	/**
	 * Remove a locator session
	 * @param locSes
	 */
	public void removeLocatorSession(LocatorSession locSes) {
		System.out.println("Session removed");
		sessions.remove(locSes);
		System.out.println("\tSessions: " + sessions.size());
	}
	
	/**
	 * Print the state of the server
	 */
	public void print() {
		System.out.println("------Server Status------");
		System.out.println("Connected Clients: " + clients.size());
		for (Client c : clients) {
			System.out.println("\t" + c);
		}
		System.out.println("Connected Clients: " + availableClients.size());
		for (Client c : availableClients) {
			System.out.println("\t" + c);
		}
		System.out.println("Sessions: " + sessions.size());
		for (LocatorSession s : sessions) {
			
		}
		System.out.println("-------------------------");
	}
}
