package main;

import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
		Scanner input = new Scanner(System.in);
		while (input.hasNext()) {
			String command = input.next();
			if (command.equalsIgnoreCase("stop")) {
				server.stopServer();
				break;
			} else if (command.equalsIgnoreCase("print")) {
				server.print();
			}
		}
	}
}
