import java.net.Socket;
import java.io.*;

class receiveEvents extends Thread{
	String event_key;
	Socket connected_socket;
	receiveEvents(Socket sc) {
		connected_socket = sc;
		start();
	}
	public void run() {
		try {
			ObjectInputStream ip_stream = new ObjectInputStream(connected_socket.getInputStream());
			while(true) {
				event_key = (String)ip_stream.readObject();
				switch (event_key) {
					case "SEND_SCREEN":
						new sendCurrentScreen(connected_socket);
						break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}