import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Set server password implementation.

class setServerPassword extends JFrame implements ActionListener {
	JTextField password_input;
	JPanel password_panel;
	JLabel password_label;
	JButton submit_btn;

	setServerPassword () {
		this.setLayout(new BorderLayout());

		password_label = new JLabel();
		password_input = new JTextField(15);
		// Container of our label and text field.
		password_panel = new JPanel();
		password_label.setText("Set the Password that client will use to connect");
		// Add the two into the panel
		password_panel.add(password_label);
		password_panel.add(password_input);

		submit_btn = new JButton("SUBMIT");
		password_panel.add(submit_btn);

		add(password_panel, BorderLayout.CENTER);
		setTitle("Set password");

		submit_btn.addActionListener(this);
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent ae){
		String password_value=password_input.getText();
		dispose();
		// Make a frame that should the IP address and other before joining information.
		prepareServerConnection frame = new prepareServerConnection();
		// Close the dialogue box.
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 100);
		frame.setVisible(true);
		// Make a server listen to incoming request.
		Runnable server_socket = new createServerSocket(frame, password_value);
		new Thread(server_socket).start();
	}
}
