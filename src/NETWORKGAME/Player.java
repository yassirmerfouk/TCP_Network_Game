package NETWORKGAME;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Player {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 2222);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			System.out.println(bufferedReader.readLine());
			System.out.println(bufferedReader.readLine());
			Scanner scanner = new Scanner(System.in);
			String firstName = scanner.nextLine();
			String lastName = scanner.nextLine();
			printWriter.println(firstName);
			printWriter.println(lastName);
			System.out.println(bufferedReader.readLine());
			while(true) {
				String s = scanner.next();
				printWriter.println(s);
				System.out.println(bufferedReader.readLine());
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
