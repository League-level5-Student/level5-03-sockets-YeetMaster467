package _02_Chat_Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.swing.JOptionPane;

import _01_Intro_To_Sockets.server.ServerGreeter;

public class Server extends Thread {
	static ServerSocket s = null;
	static DataInputStream dis = null;
	static DataOutputStream dos = null;
	
	public Server () throws IOException {
		s = new ServerSocket(8080);
		s.setSoTimeout(10_000);
	}
	
	@Override
	public void run() {
		boolean bool = true;
		
		while (bool) {
			try {
				JOptionPane.showMessageDialog(null, "Server is waiting for client to connect.");
				Socket socket = s.accept();
				JOptionPane.showMessageDialog(null, "Client has connected.");
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				new ServerGUI().showWindow();
			} catch (SocketTimeoutException s) {
				JOptionPane.showMessageDialog(null, "Socket Timed Out");
				bool = false;
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "IO Exception Occured");
				bool = false;
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new Thread(new Server()).start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
