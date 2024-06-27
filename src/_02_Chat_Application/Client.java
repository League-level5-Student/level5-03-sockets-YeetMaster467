package _02_Chat_Application;

import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client {

	private static JFrame frame = new JFrame();
	private static JPanel panel = new JPanel();
	private static JTextField inputField = new JTextField(20);
	private static JButton send = new JButton("Send");
	private static JLabel[] lastMessages = new JLabel[5];

	public static void main(String[] args) {
		String ip;
		int port = 8080;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			ip = "Error";
		}
		final String nip = ip;

		panel.setLayout(new GridLayout(2, 2));
		panel.add(inputField);
		panel.add(send);
		for (int i = 0; i < lastMessages.length; i++) {
			if (lastMessages[i] != null && !lastMessages[i].getText().isBlank()) {
				panel.add(lastMessages[i]);
			}
		}
		frame.add(panel);
		frame.setTitle("Client");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.pack();
		send.addActionListener((a) -> {
			try {
				Socket socket = new Socket(nip, port);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(inputField.getText());
				inputField.setText("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
