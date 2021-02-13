import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class createServerSocket implements Runnable{
	ServerSocket socket = null, screen_socket = null, file_socket = null;
	DataInputStream socket_input;
	DataOutputStream socket_output;
	String server_password;
	int port = 8000, screen_port = 8001, file_port = 8002;
	JFrame waiting_frame;
	JFrame connected_frame;
	private volatile Boolean wait_for_client;
	createServerSocket(JFrame frame, String password) {
		waiting_frame = frame;
		server_password = password;
		wait_for_client = true;
	}
	public void run() {
		try {
			socket = new ServerSocket(port);
			screen_socket = new ServerSocket(screen_port);
			file_socket = new ServerSocket(file_port);
		} catch (Exception e) {
			wait_for_client = false;
			e.printStackTrace();
		}
		while(wait_for_client) {
			try {
				Socket sc = socket.accept();
				Socket screen_sc = screen_socket.accept();
				Socket file_sc = file_socket.accept();

				socket_output = new DataOutputStream(sc.getOutputStream());
				socket_input = new DataInputStream(sc.getInputStream());
				String received_password = socket_input.readUTF();
				if (received_password.equals(server_password)) {
					socket_output.writeUTF("Password verified.");
					connected_frame = new connectedWithClient(sc);
					connected_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					connected_frame.setSize(600, 100);
					connected_frame.setVisible(true);
					waiting_frame.dispose();
					new receiveScreenEvent(screen_sc);
					new receiveEvents(sc, screen_sc);
				} else {
					socket_output.writeUTF("Invalid password.");
					sc.close();
					screen_sc.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
