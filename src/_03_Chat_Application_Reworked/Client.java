package _03_Chat_Application_Reworked;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Client {
	private String ip;
	private int port;

	Socket connection;

	ObjectOutputStream os;
	ObjectInputStream is;

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			connection = new Socket(ip, port);
			
			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void sendMessage(String message) {
		System.out.println("Client sending message in class");
		try {
			if (os != null && connection.isConnected()) {
				os.writeObject(message);
				os.flush();
				System.out.println("Client sent message in class");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Message is not printing or showing up on gui
	
	public ArrayList<String> readMessage () {
		System.out.println("Client reading message in class");
		if (connection.isConnected()) {
			try {
				os.flush();
				String message = (String) is.readObject();
				String message2 = (String) is.readObject();
				System.out.println(message + " " + message2);
				System.out.println("Client read message in class");
				return addMessage("Server: " + message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			System.out.println("Disconnected");
			System.exit(0);
			return null;
		}
	}
	
	public ArrayList<String> addMessage(String message) {
		ArrayList<String> lastMessages = new ArrayList<String>();
		lastMessages.add(message);
		if (lastMessages.size() > 5) {
			lastMessages.remove(5);
		}
		return lastMessages;
	}
}
