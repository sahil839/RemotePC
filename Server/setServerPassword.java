import javax.swing.*;
import java.awt.*;

class setServerPassword extends JFrame {
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
	}
}
