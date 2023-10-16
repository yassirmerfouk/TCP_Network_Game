package NETWORKGAME;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerGame extends Thread{

	private ServerSocket serverSocket;
	private int port = 1234;
	private int players = 0;
	private int secretNumber;
	private boolean isRunning = true;
	private boolean end = false;
	private Person winner;
	public static void main(String[] args) {
		(new ServerGame()).start();
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(2222);
			secretNumber = (int)(Math.random() * 1000 );
			System.out.println("Game start, secret number is "+secretNumber);
			while(isRunning) {
				Socket socket = serverSocket.accept(); 
				players++;
				System.out.println("New player is here, number " +players+ ", ip : "+socket.getRemoteSocketAddress().toString());
				(new MyThread(players,socket)).start();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class MyThread extends Thread{
		private int number;
		private Socket socket;
		private Person person;
		
		public MyThread(int number, Socket socket) {
			super();
			this.number = number;
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
				printWriter.println("Welcome to the Secret Game");
				printWriter.println("Choose your first name and last name");
				String firstName = bufferedReader.readLine();
				String lastName = bufferedReader.readLine();
				person = new Person(firstName,lastName);
				printWriter.println("Choose a number : ");
				while(true) {
//					printWriter.println("Choose a number : ");
					int nb = Integer.parseInt(bufferedReader.readLine());
					System.out.println("A new request try sended by player number : "+number+ " , ip : "+socket.getRemoteSocketAddress().toString());
					if(end) {
						printWriter.println("The game is finish, the winner is player "+ winner.getFirstName() + " " + winner.getLastName());
					}else {
						if(nb > secretNumber)
							printWriter.println("Your number is bigger than secret number");
						else if(nb < secretNumber)
							printWriter.println("Your number is smaller than secret number");
						else {
							end = true;
							winner = person;
							System.out.println("The game is finish, the winner is player "+ winner.getFirstName() + " " + winner.getLastName());
							printWriter.println("Goooooood, you are the winner");
						}
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
