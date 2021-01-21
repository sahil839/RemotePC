import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class connectedWithClient extends JFrame {
	Socket connected_socket;
	JLabel connected_info_label;
	JPanel connected_info_panel;
	connectedWithClient(Socket sc) {
		connected_socket = sc;
		connected_info_label = new JLabel();
		connected_info_panel = new JPanel();
		connected_info_label.setText("Client info: " + connected_socket.toString());
		connected_info_panel.add(connected_info_label);
		add(connected_info_panel, BorderLayout.CENTER);
		setTitle("Connected with client");
		setLocationRelativeTo(null);
	}
}
