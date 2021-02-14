import java.io.*;
import java.net.Socket;

// Recieving files through remote connection.
public class receiveFileEvent extends Thread{
	Socket file_socket;
	//public static ObjectOutputStream op_stream;
	public static ObjectInputStream ip_stream;
	String event_key;
	private volatile Boolean receive_events = true;
	connectedWithClient c_frame;
	receiveFileEvent (Socket sc, connectedWithClient cframe) {
		file_socket = sc;
		c_frame = cframe;
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
					case "RECEIVE_FILE": // If the event is of recieve_file type
					{
						// Get file name
						String file_name = (String) ip_stream.readObject();
						// Get file size, this is the amount of data we iteratively take from the socket.
						Long file_size = (Long)ip_stream.readObject();
						c_frame.file_info("Exchanging " + file_name);
						// recieveFile provides the access point through which we can input the file from the socket, check for directories and make them if doesn't exist.
						new receiveFile(file_socket, file_name, file_size, c_frame);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
