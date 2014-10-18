package com.example.friendcompass;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
	private Socket socket;
	
	private PrintWriter out = null;
	private Scanner in = null;
	
	public Client(Socket sock) {
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
