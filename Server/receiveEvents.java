import java.net.Socket;
import java.io.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;

// General Events like left click, right click, cursor movement, mouse scroll, double left click, right click, close connection happen here.
class receiveEvents extends Thread{
	String event_key;
	Socket connected_socket, screen_socket;
	private volatile Boolean receive_events;
	ObjectInputStream ip_stream;
	int screenWidth, screenHeight;
	mouseControl mouse_control;
	// Constructor
	receiveEvents(Socket sc, Socket screen_sc) {
		connected_socket = sc; 
		screen_socket = screen_sc;
		receive_events = true;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = (int) screenSize.getWidth();
        screenHeight = (int) screenSize.getHeight();
        // Create mouse control object ( see mouseControl.java )
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
					case "KEY_PRESS":
						String key = (String) ip_stream.readObject();

						if (key.equals("LEFT")) {
							mouseControl.doType(VK_LEFT);
						} else if (key.equals("RIGHT")) {
							mouseControl.doType(VK_RIGHT);
						} else if (key.equals("UP")) {
							mouseControl.doType(VK_UP);
						}  else if (key.equals("DOWN")) {
							mouseControl.doType(VK_DOWN);
						} else if (key.equals("F5")) {
							mouseControl.doType(VK_CONTROL, VK_F5);
						}  else {
							mouseControl.typeCharacter(key.charAt(0));
						}
						break;
					case "RIGHT_CLICK":
                    	mouseControl.rightClick();
                    	break;
                    case "DOUBLE_LEFT_CLICK":
                    	mouseControl.doubleLeftClick();
                    	break;
                    case "SHUTDOWN_PC":
						String password = (String) ip_stream.readObject();
						String os = System.getProperty("os.name");
						if (os.equals("Windows 10") || os.equals("Windows 8.0") || os.equals("Windows 8.1")) {
							Runtime runtime = Runtime.getRuntime();
							runtime.exec("shutdown -s");
						} else {
							// considering only linux distributions if not windows.
							mouseControl.openTerminal();

							mouseControl.typeString("sudo shutdown -P now");
							mouseControl.typeCharacter('\n');
							mouseControl.typeString(password);
							mouseControl.typeCharacter('\n');
						}
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
