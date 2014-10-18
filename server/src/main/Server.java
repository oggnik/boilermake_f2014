package main;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread {
	private ServerSocket serverSocket = null;
//	private ArrayList<Client> clients;
	
	public Server() {
		
	}
	
	public void run() {
		try {
			serverSocket = new ServerSocket(Definitions.SERVER_PORT);
		} catch (IOException e) {
			System.out.println("Socket creation failed.");
			System.out.println(e);
			System.exit(1);
		}
	}
	
	/**
	 * Stop the server and sever any connections
	 */
	public void stopServer() {
		
	}
}
