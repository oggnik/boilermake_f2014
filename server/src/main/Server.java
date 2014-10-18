package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread {
	private ServerSocket serverSocket = null;
	private ArrayList<Client> clients;
	private boolean listening = false;
	
	public Server() {
		clients = new ArrayList<Client>();
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
				client.start();
			} catch (IOException e) {
				if (listening) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Stop the server and sever any connections
	 */
	public void stopServer() {
		System.out.println("Server shutting down");
		listening = false;
		for (Client c : clients) {
			c.closeSocket();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
