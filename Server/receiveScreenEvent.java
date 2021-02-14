import java.io.*;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class receiveScreenEvent extends Thread{
	Socket screen_socket;
	public static ObjectOutputStream op_stream;
	ObjectInputStream ip_stream;
	String event_key;
	private volatile Boolean receive_events = true;
	connectedWithClient c_frame;
	receiveScreenEvent (Socket sc, connectedWithClient cframe) {
		screen_socket = sc;
		c_frame = cframe;
		try {
			op_stream = new ObjectOutputStream(sc.getOutputStream());
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
					case "SEND_SCREEN":
					{
						System.out.print("Screen sending");
						c_frame.info("Sending screen");
						new sendCurrentScreen(screen_socket);

					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
