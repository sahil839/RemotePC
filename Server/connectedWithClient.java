import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class connectedWithClient extends JFrame {
	Socket connected_socket;
	JLabel connected_info_label;
	JLabel other_info;
	JLabel file_info;
	JLabel events_info;
	JPanel connected_info_panel;
	connectedWithClient(Socket sc) {
		connected_socket = sc;
		connected_info_label = new JLabel();
		connected_info_panel = new JPanel();
		other_info = new JLabel();
		file_info = new JLabel();
		events_info = new JLabel();
		other_info.setText("<html>Screen Status: <br></html>");
		file_info.setText("<html>File sharing Status: <br></html>");
		events_info.setText("<html>Other events Status: <br></html>");
		connected_info_label.setText("Client info: " + connected_socket.toString());
		connected_info_panel.add(connected_info_label);
		connected_info_panel.add(other_info);
		connected_info_panel.add(events_info);
		connected_info_panel.add(file_info);
		add(connected_info_panel, BorderLayout.CENTER);
		setTitle("Connected with client");
		setLocationRelativeTo(null);
	}
	public void info(String str)
	{
		other_info.setText("<html>Status: " + str + "<br></html>");
		add(connected_info_panel, BorderLayout.CENTER);
		connected_info_panel.doLayout();
	} 
	public void file_info(String str)
	{
		file_info.setText("<html>File Sharing Status: " + str + "<br></html>");
		add(connected_info_panel, BorderLayout.CENTER);
		connected_info_panel.doLayout();
	} 
	public void events_info(String str)
	{
		events_info.setText("<html>Other events Status: " + str + "<br></html>");
		add(connected_info_panel, BorderLayout.CENTER);
		connected_info_panel.doLayout();
	} 
	
}
