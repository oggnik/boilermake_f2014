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
	
	private LocatorSession locatorSession = null;
	
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
		server.addClient(this);
		try {
			// Start reading from the client
			String inputLine = null;
			while (in.hasNextLine()) {
				inputLine = in.nextLine();
				handleMessage(inputLine);
			}
		} catch (IllegalStateException ise) {
			// Socket closed, don't worry
		}
		closeSocket();
	}
	
	/**
	 * Handle a message
	 * @param message
	 */
	private void handleMessage(String message) {
		if (locatorSession != null) {
			String[] parts = message.split(",");
			double[] location = new double[2];
			location[0] = Double.parseDouble(parts[0]);
			location[1] = Double.parseDouble(parts[1]);
			locatorSession.updateLocation(this, location);
		}
	}
	
	/**
	 * Send a message to the connected client
	 * @param message
	 */
	public void sendMessage(String message) {
		out.println(message);
	}
	
	/**
	 * Set a locator session
	 * @param loc
	 */
	public void setLocatorSession(LocatorSession loc) {
		locatorSession = loc;
		if (loc == null) {
			server.addClientToAvailable(this);
		}
	}
	
	/**
	 * Close down communication
	 */
	public void closeSocket() {
		if (locatorSession != null) {
			locatorSession.disconnect(this);
		}
		server.removeClient(this);
		if (out != null) {
			out.close();
		}
		if (in != null) {
			in.close();
		}
		try {
			if (socket != null) {
				socket.close();
			}
			socket = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "Client " + socket.getRemoteSocketAddress();
	}
}
