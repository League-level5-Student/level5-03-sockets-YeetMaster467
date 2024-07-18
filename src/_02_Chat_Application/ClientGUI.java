package _02_Chat_Application;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
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
	private ArrayList<JLabel> lastMessages = new ArrayList<JLabel>();
	Client c = new Client();
	
	public void showWindow () {
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(inputField);
		panel.add(send);
		for (int i = 0; i < lastMessages.size(); i++) {
			if (lastMessages.get(i) != null && !lastMessages.get(i).getText().isBlank()) {
				panel.add(lastMessages.get(i));
			}
		}
		frame.add(panel);
		frame.setTitle("Client");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.pack();
		send.addActionListener(this);
		
		while (c.socket.isConnected()) {
			try {
				System.out.println(c.dis.readUTF());
				addMessage(new JLabel("Server: " + c.dis.readUTF()));
				frame.pack();
			} catch(EOFException e) {
				addMessage(new JLabel("Connection Lost"));
				frame.pack();
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
			c.dos.writeUTF(inputField.getText());
			addMessage(new JLabel("Client: " + inputField.getText()));
			frame.pack();
			inputField.setText("");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	public void addMessage (JLabel j) {
		lastMessages.add(j);
		if (lastMessages.size() > 4) {
			lastMessages.remove(4);
		}
		frame.repaint();
	}
	
}
