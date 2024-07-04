package _02_Chat_Application;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientGUI implements ActionListener {
	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JTextField inputField = new JTextField(20);
	private JButton send = new JButton("Send");
	private JLabel[] lastMessages = new JLabel[5];
	
	public void showWindow () {
		panel.setLayout(new GridLayout(0, 1));
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
		send.addActionListener(this);
		
		while (Client.socket.isConnected()) {
			try {
				lastMessages[0] = new JLabel("Server: " + Client.dis.readUTF());
			} catch(EOFException e) {
				lastMessages[0] = new JLabel("Connection Lost");
				System.exit(0);
			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Client.dos.writeUTF(inputField.getText());
			lastMessages[0] = new JLabel("Client: " + inputField.getText());
			inputField.setText("");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	public void addMessage (JLabel j) {
		for (int i = 0; i < lastMessages.length; i++) {
			List<JLabel> l = Arrays.stream(lastMessages).toList();
			l.add(j);
			l.remove(4);
			lastMessages = (JLabel[]) l.stream().toArray();
		}
	}
	
}
