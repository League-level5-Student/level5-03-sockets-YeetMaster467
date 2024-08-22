package _03_Chat_Application_Reworked;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Server {
	private int port;
	
	private ServerSocket server;
	private Socket connection;
	
	ObjectOutputStream os;
	ObjectInputStream is;

	public Server(int port) {
		this.port = port;
		try {
			server = new ServerSocket(port, 1000);
			
			connection = server.accept();

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public String getIPAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "ERROR!!!!!";
		}
	}

	public int getPort() {
		return port;
	}
	
	public void sendMessage(String message) {
		System.out.println("Server sending message in class");
		try {
			if (os != null && connection.isConnected()) {
				os.writeObject(message);
				os.flush();
				System.out.println("Server sent message in class");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Message is not printing or showing up on gui
	
	public ArrayList<String> readMessage () {
		System.out.println("Server reading message in class");
		if (connection.isConnected()) {
			try {
				os.flush();
				String message = (String) is.readObject();
				String message2 = (String) is.readObject();
				System.out.println(message + " " + message2);
				System.out.println("Server read message in class");
				return addMessage("Client: " + message);
			//}catch(EOFException e) {
				//JOptionPane.showMessageDialog(null, "Connection Lost");
				//System.exit(0);
				//return null;
			} catch (ClassNotFoundException e) {
				// XXX Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				// XXX Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			System.out.println("Dissconected.");
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
