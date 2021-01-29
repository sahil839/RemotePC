import java.net.Socket;
import java.io.*;

class receiveEvents extends Thread{
	String event_key;
	Socket connected_socket;
	private volatile Boolean receive_events;
	receiveEvents(Socket sc) {
		connected_socket = sc;
		receive_events = true;
		start();
	}
	public void run() {
		try {
			ObjectInputStream ip_stream = new ObjectInputStream(connected_socket.getInputStream());
			while(receive_events) {
				event_key = (String)ip_stream.readObject();
				switch (event_key) {
					case "SEND_SCREEN":
						new sendCurrentScreen(connected_socket);
						break;
					case "CLOSE_CONNECTION":
						receive_events = false;
						connected_socket.close();
						break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}