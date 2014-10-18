package com.example.friendcompass;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
	private CallActivity cActivity;
	private Socket socket;
	
	private PrintWriter out = null;
	private Scanner in = null;
	
	private boolean connected;
	
	public Client(CallActivity cActivity) {
		this.cActivity = cActivity;
		try {
			System.out.println("got here");
			socket = new Socket(Definitions.SERVER_IP, Definitions.SERVER_PORT);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new Scanner(socket.getInputStream());
			System.out.println(socket);
			System.out.println("got here 2");
			connected = true;
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Start reading from the connection
	 */
	public void run() {
		// Start reading from the client
		String inputLine = null;
		while (in.hasNextLine()) {
			inputLine = in.nextLine();
			handleMessage(inputLine);
		}
		connected = false;
	}
	
	/**
	 * Handle a message
	 * @param message
	 */
	private void handleMessage(String message) {
		// Display the message
		
	}
	
	/**
	 * Send a message to the connected client
	 * @param message
	 */
	public void sendMessage(String message) {
		System.out.println(socket);
		System.out.println(out);
		out.println(message);
	}
	
	/**
	 * Close down communication
	 */
	public void closeSocket() {
		if (socket == null) {
			return;
		}
		out.close();
		in.close();
		try {
			socket.close();
			socket = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Are we connected?
	 * @return
	 */
	public boolean isConnected() {
		return connected;
	}
}
