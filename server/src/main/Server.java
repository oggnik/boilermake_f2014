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
		availableClients.add(client);
		System.out.println("Client added.");
		System.out.println("Total clients: " + clients.size());
		System.out.println("Available clients: " + availableClients.size());
		System.out.println("Sessions: " + sessions.size());
		
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
	}
	
	/**
	 * Remove a client
	 * @param client
	 */
	public void removeClient(Client client) {
		clients.remove(client);
		availableClients.remove(client);
		System.out.println("Client remove.");
		System.out.println("Total clients: " + clients.size());
		System.out.println("Available clients: " + availableClients.size());
		System.out.println("Sessions: " + sessions.size());
	}
	
	/**
	 * Add a client to available
	 * Only adds if in the client list
	 * @param client
	 */
	public void addClientToAvailable(Client client) {
		if (!availableClients.contains(client) && clients.contains(client)) {
			availableClients.add(client);
		}
	}
	
	public void removeLocatorSession(LocatorSession locSes) {
		sessions.remove(locSes);
	}
}
