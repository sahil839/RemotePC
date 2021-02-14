import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class connectedWithClient extends JFrame {
	Socket connected_socket;
	JLabel connected_info_label;
	JLabel file_info;
	JLabel events_info;
	JPanel connected_info_panel;
	connectedWithClient(Socket sc) {
		connected_socket = sc;
		connected_info_label = new JLabel();
		connected_info_panel = new JPanel();
		file_info = new JLabel();
		file_info.setText("<html>File sharing Status: <br></html>");
		connected_info_label.setText("Client info: " + connected_socket.toString());
		connected_info_panel.add(connected_info_label);
		connected_info_panel.add(file_info);
		add(connected_info_panel, BorderLayout.CENTER);
		setTitle("Connected with client");
		setLocationRelativeTo(null);
	}
	public void file_info(String str)
	{
		file_info.setText("<html>File Sharing Status: " + str + "<br></html>");
		add(connected_info_panel, BorderLayout.CENTER);
		connected_info_panel.doLayout();
	}
}
