import java.io.*;
import java.net.Socket;

public class receiveFileEvent extends Thread{
	Socket file_socket;
	//public static ObjectOutputStream op_stream;
	public static ObjectInputStream ip_stream;
	String event_key;
	private volatile Boolean receive_events = true;
	receiveFileEvent (Socket sc) {
		file_socket = sc;
		try {
			//op_stream = new ObjectOutputStream(sc.getOutputStream());
			ip_stream = new ObjectInputStream(sc.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		start();
	}
	public void run() {
		try {
			while(receive_events) {
				event_key = (String)ip_stream.readObject();
				switch (event_key) {
					case "RECEIVE_FILE":
						String file_name = (String) ip_stream.readObject();
						Long file_size = (Long)ip_stream.readObject();
						new receiveFile(file_socket, file_name, file_size);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}