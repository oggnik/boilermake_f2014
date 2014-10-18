package main;

import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
		Scanner input = new Scanner(System.in);
		while (input.hasNext()) {
			if (input.next().equalsIgnoreCase("stop")) {
				server.stopServer();
				break;
			}
		}
	}
}
