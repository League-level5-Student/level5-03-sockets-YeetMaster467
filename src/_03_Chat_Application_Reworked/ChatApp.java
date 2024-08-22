package _03_Chat_Application_Reworked;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import _00_Click_Chat.networking.Client;

public class ChatApp extends JFrame implements ActionListener {
	Timer t = new Timer(1000, this);
	JPanel panel = new JPanel();
	JTextField input = new JTextField(20);
	JButton send = new JButton("Send");
	ArrayList<String> lastMessages = new ArrayList<String>();
	
	Server server;
	_03_Chat_Application_Reworked.Client client;
	
	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp() {
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a connection?", "Connection", JOptionPane.YES_NO_OPTION);
		
		if (response == JOptionPane.YES_OPTION) {
			setTitle("Server");
			send.addActionListener(e -> {
				System.out.println("Server sending message");
				server.sendMessage(input.getText());
				lastMessages = server.addMessage("Server: " + input.getText());
				for (int i = 0; i < lastMessages.size(); i++) {
					if (lastMessages.get(i) != null && !lastMessages.get(i).isBlank()) {
						System.out.println(lastMessages.get(i));
						panel.add(new JLabel(lastMessages.get(i)));
					}
				}
				input.setText("");
				pack();
				repaint();
				System.out.println("Server sent message");
			});
			panel.add(input);
			panel.add(send);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			add(panel);
			pack();
			setLocationRelativeTo(null);
			server = new Server(8080);
			JOptionPane.showMessageDialog(null, "Server started at: " + server.getIPAddress() + "\nPort: " + server.getPort());
		} else {
			setTitle("Client");
			String ipStr = JOptionPane.showInputDialog("Enter the IP Address");
			String prtStr = JOptionPane.showInputDialog("Enter the port number");
			int port = Integer.parseInt(prtStr);
			client = new _03_Chat_Application_Reworked.Client(ipStr, port);
			send.addActionListener(e -> {
				System.out.println("Client sending message");
				client.sendMessage(input.getText());
				lastMessages = client.addMessage("Client: " + input.getText());
				for (int i = 0; i < lastMessages.size(); i++) {
					if (lastMessages.get(i) != null && !lastMessages.get(i).isBlank()) {
						panel.add(new JLabel(lastMessages.get(i)));
					}
				}
				input.setText("");
				pack();
				repaint();
				System.out.println("Client sent message");
			});
			panel.add(input);
			panel.add(send);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			add(panel);
			pack();
			setLocationRelativeTo(null);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == t) {
			if (server != null) {
				System.out.println("Server reading message");
				lastMessages = server.readMessage();
				for (int i = 0; i < lastMessages.size(); i++) {
					if (lastMessages.get(i) != null && !lastMessages.get(i).isBlank()) {
						panel.add(new JLabel(lastMessages.get(i)));
					}
				}
				panel.repaint();
				System.out.println("Server read message");
			} else if (client != null) {
				System.out.println("Client reading message");
				lastMessages = client.readMessage();
				for (int i = 0; i < lastMessages.size(); i++) {
					if (lastMessages.get(i) != null && !lastMessages.get(i).isBlank()) {
						panel.add(new JLabel(lastMessages.get(i)));
					}
				}
				panel.repaint();
				
			}
		}
		//*/
	}
	
	
}
