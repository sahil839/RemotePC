import java.net.Socket;
import java.io.*;

class receiveEvents extends Thread{
	String event_key;
	Socket connected_socket, screen_socket;
	private volatile Boolean receive_events;
	ObjectInputStream ip_stream;
	receiveEvents(Socket sc, Socket screen_sc) {
		connected_socket = sc;
		screen_socket = screen_sc;
		receive_events = true;
		try {
			ip_stream = new ObjectInputStream(connected_socket.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		start();
	}
	public void run() {
		try {
			while(receive_events) {
				event_key = (String)ip_stream.readObject();
				switch (event_key) {
					case "CLOSE_CONNECTION":
						receive_events = false;
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connected_socket.close();
				screen_socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
