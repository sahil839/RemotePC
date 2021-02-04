import java.net.Socket;
import java.io.*;
import java.awt.Dimension;
import java.awt.Toolkit;

class receiveEvents extends Thread{
	String event_key;
	Socket connected_socket, screen_socket;
	private volatile Boolean receive_events;
	ObjectInputStream ip_stream;
	int screenWidth, screenHeight;
	mouseControl mouse_control;
	receiveEvents(Socket sc, Socket screen_sc) {
		connected_socket = sc;
		screen_socket = screen_sc;
		receive_events = true;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        mouse_control = new mouseControl();
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
					case "MOUSE_MOVE_LIVE":
                        String xCord = (String) ip_stream.readObject();
                        String yCord = (String) ip_stream.readObject();
                        float finalXCord = Float.parseFloat(xCord);
                        float finalYCord = Float.parseFloat(yCord);
                        finalXCord = finalXCord * screenWidth;
                        finalYCord = finalYCord * screenHeight;
                        mouseControl.mouseMove((int) finalXCord, (int) finalYCord);
                        break;
                    case "LEFT_CLICK":
                        mouseControl.leftClick();
                        break;
                    case "MOUSE_WHEEL":
						int scrollAmt = Integer.parseInt((String)ip_stream.readObject());
                        mouseControl.mouseWheel(scrollAmt);
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
