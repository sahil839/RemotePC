import java.net.Socket;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.OutputStream;
import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.awt.MouseInfo;
import java.awt.Graphics2D;

public class sendCurrentScreen {
	Socket connected_socket;
	OutputStream socket_output;
	ObjectOutputStream socket_object_output;
	ByteArrayOutputStream byte_array_op_stream;
	ByteArrayInputStream byte_array_ip_stream;
	sendCurrentScreen(Socket sc) {
		connected_socket = sc;
		sendScreen();
	}
	public void sendScreen() {
		try {
			Robot robot = new Robot();
			Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle rect = new Rectangle(dim);
			BufferedImage screenImage = robot.createScreenCapture(rect);
			ByteArrayOutputStream byte_array_op_stream = new ByteArrayOutputStream();
			Image cursor = ImageIO.read(new File("assets/cursor.png"));
			int x = MouseInfo.getPointerInfo().getLocation().x;
			int y = MouseInfo.getPointerInfo().getLocation().y;
			Graphics2D graphics2D = screenImage.createGraphics();
			graphics2D.drawImage(cursor, x, y, 16, 16, null);
            ImageIO.write(screenImage, "png", byte_array_op_stream);
            byte_array_ip_stream = new ByteArrayInputStream(byte_array_op_stream.toByteArray());
            int fileSize = byte_array_op_stream.size();
            socket_object_output = new ObjectOutputStream(connected_socket.getOutputStream());
            socket_object_output.writeObject(fileSize);
            socket_object_output.flush();

            byte[] buffer = new byte[4096];
            int read = 0;
            long totalRead = 0;
            int remaining = (int) fileSize;
            while ((read = byte_array_ip_stream.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                socket_object_output.write(buffer, 0, read);
                socket_object_output.flush();
            }
            socket_object_output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}