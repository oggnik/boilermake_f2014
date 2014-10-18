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
	
	public Client(CallActivity cActivity) {
		this.cActivity = cActivity;
	}
	
	/**
	 * Start reading from the connection
	 */
	public void run() {
		try {
			socket = new Socket(Definitions.SERVER_IP, Definitions.SERVER_PORT);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new Scanner(socket.getInputStream());
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Close down communication
	 */
	public void closeSocket() {
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
