package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
	private Server server;
	private Socket socket;
	
	private PrintWriter out = null;
	private Scanner in = null;
	
	public Client(Server s, Socket sock) {
		server = s;
		socket = sock;
	}
	
	/**
	 * Start reading from the connection
	 */
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new Scanner(socket.getInputStream());
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		// Start reading from the client
		String inputLine = null;
		while (in.hasNextLine()) {
			inputLine = in.nextLine();
			handleMessage(inputLine);
		}
		closeSocket();
	}
	
	/**
	 * Handle a message
	 * @param message
	 */
	private void handleMessage(String message) {
		System.out.println(message);
		out.println("Message received.");
	}
	
	/**
	 * Send a message to the connected client
	 * @param message
	 */
	public void sendMessage(String message) {
		out.println(message);
	}
	
	/**
	 * Close down communication
	 */
	public void closeSocket() {
		server.removeClient(this);
		out.close();
		in.close();
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
