import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class createServerSocket implements Runnable{
	ServerSocket socket = null;
	DataInputStream socket_input;
	DataOutputStream socket_output;
	String server_password;
	int port = 8000;
	JFrame waiting_frame;
	JFrame connected_frame;
	createServerSocket(JFrame frame, String password) {
		waiting_frame = frame;
		server_password = password;
	}
	public void run() {
		try {
			socket = new ServerSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while(true) {
			try {
				Socket sc = socket.accept();
				socket_input = new DataInputStream(sc.getInputStream());
				socket_output = new DataOutputStream(sc.getOutputStream());
				String received_password = socket_input.readUTF();
				if (received_password.equals(server_password)) {
					socket_output.writeUTF("Password verified.");
					connected_frame = new connectedWithClient(sc);
					connected_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					connected_frame.setSize(600, 100);
					connected_frame.setVisible(true);
					waiting_frame.dispose();
					new receiveEvents(sc);
				} else {
					socket_output.writeUTF("Invalid password.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
