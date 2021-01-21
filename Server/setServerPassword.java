import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class setServerPassword extends JFrame implements ActionListener {
	JTextField password_input;
	JPanel password_panel;
	JLabel password_label;
	JButton submit_btn;

	setServerPassword () {
		this.setLayout(new BorderLayout());

		password_label = new JLabel();
		password_input = new JTextField(15);
		password_panel = new JPanel();

		password_label.setText("Set the Password that client will used to connect");
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

		prepareServerConnection frame = new prepareServerConnection();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(300, 100);
		frame.setVisible(true);

		Runnable server_socket = new createServerSocket(frame, password_value);
		new Thread(server_socket).start();
	}
}
