package _02_Chat_Application;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread {
	ServerSocket s;
	
	public Server () throws IOException {
		s = new ServerSocket(8080);
		s.setSoTimeout(10_000);
	}
	
	@Override
	public void run() {
		boolean bool = true;
		
		while (bool) {
			
		}
	}
}
