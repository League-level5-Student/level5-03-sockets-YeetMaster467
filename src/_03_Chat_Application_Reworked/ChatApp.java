package _03_Chat_Application_Reworked;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatApp extends JFrame {
	JPanel panel = new JPanel();
	JTextField input = new JTextField();
	JButton send = new JButton("Send");
	ArrayList<String> lastMessages = new ArrayList<String>();
	
	public static void main(String[] args) {
		new ChatApp();
	}
	
	public ChatApp() {
		int response = JOptionPane.showConfirmDialog(null, "Would you like to host a connection?", "Connection", JOptionPane.YES_NO_OPTION);
		
		if (response == JOptionPane.YES_OPTION) {
			
		} else {
			
		}
	}
}
