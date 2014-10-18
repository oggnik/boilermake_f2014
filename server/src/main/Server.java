package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread {
	private ServerSocket serverSocket = null;
	private ArrayList<Client> clients;
	private ArrayList<Client> availableClients;
	private boolean listening = false;
	
	public Server() {
		clients = new ArrayList<Client>();
		availableClients = new ArrayList<Client>();
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
				System.out.println("Client connected.");
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
		
		if (availableClients.size() == 2) {
			Client client1 = availableClients.remove(0);
			Client client2 = availableClients.remove(0);
			LocatorSession locSes = new LocatorSession(client1, client2);
			client1.setLocatorSession(locSes);
			client2.setLocatorSession(locSes);
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
	}
}
