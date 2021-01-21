import javax.swing.*;
import java.awt.*;

public class prepareServerConnection extends JFrame{
	JLabel waiting_label;
	JLabel ip_label;
	JPanel waiting_panel;

	prepareServerConnection() {
		String ipAddress = util.getMyIPAddress();
		this.setLayout(new BorderLayout());

		ip_label = new JLabel();
		waiting_label = new JLabel();
		waiting_panel = new JPanel();

		ip_label.setText("Your IP address is: " + ipAddress);
		waiting_label.setText("Waiting for Connection from client");
		waiting_panel.add(ip_label);
		waiting_panel.add(waiting_label);
		add(waiting_panel, BorderLayout.CENTER);
		setTitle("Waiting for Connection");
		setLocationRelativeTo(null);
	}
}
