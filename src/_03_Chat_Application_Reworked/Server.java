package _03_Chat_Application_Reworked;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

			while (connection.isConnected()) {
				try {
					JOptionPane.showMessageDialog(null, is.readObject());
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
}
