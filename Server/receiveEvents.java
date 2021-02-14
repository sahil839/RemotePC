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
	connectedWithClient c_frame;
	// Constructor
	receiveEvents(Socket sc, Socket screen_sc, connectedWithClient cframe) {
		connected_socket = sc; 
		screen_socket = screen_sc;
		c_frame = cframe;
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
						c_frame.events_info("Closing connection");
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
                        c_frame.events_info("Live remote mouse");
                        break;
                    case "LEFT_CLICK":
                        mouseControl.leftClick();
                        c_frame.events_info("Mouse left click");
                        break;
                    case "MOUSE_WHEEL":
						int scrollAmt = Integer.parseInt((String)ip_stream.readObject());
                        mouseControl.mouseWheel(scrollAmt);
                        break;
					case "KEY_PRESS":
						String key = (String) ip_stream.readObject();
<<<<<<< HEAD
						mouseControl.typeCharacter(key.charAt(0));
						c_frame.events_info("Key pressed");
=======
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
>>>>>>> bf420a2328a563d3f326053cf0df2b86971731df
						break;
					case "RIGHT_CLICK":
                    	mouseControl.rightClick();
                    	c_frame.events_info("Mouse right click");
                    	break;
                    case "DOUBLE_LEFT_CLICK":
                    	mouseControl.doubleLeftClick();
                    	c_frame.events_info("Mouse double left click");
                    	break;
                    case "SHUTDOWN_PC":
						String password = (String) ip_stream.readObject();
						mouseControl.openTerminal();

						mouseControl.typeString("sudo shutdown -P now");
						mouseControl.typeCharacter('\n');
						mouseControl.typeString(password);
						mouseControl.typeCharacter('\n');
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
