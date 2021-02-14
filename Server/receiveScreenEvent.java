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
	receiveScreenEvent (Socket sc) {
		screen_socket = sc;
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
						new sendCurrentScreen(screen_socket);
						break;

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
