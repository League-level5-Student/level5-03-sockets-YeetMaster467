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
	}
	
	public void start(){
		try {
			server = new ServerSocket(port, 100);

			connection = server.accept();

			os = new ObjectOutputStream(connection.getOutputStream());
			is = new ObjectInputStream(connection.getInputStream());

			os.flush();
			
			// Messages are not printing or showing up on thingy

			while (connection.isConnected()) {
				try {
					addMessage("Client: " + is.readObject());
					System.out.println(is.readObject());
				}catch(EOFException e) {
					JOptionPane.showMessageDialog(null, "Connection Lost");
					System.exit(0);
				}
			}

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
		try {
			if (os != null) {
				os.writeObject(message);
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
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
