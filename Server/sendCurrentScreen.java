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
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.*;


public class sendCurrentScreen {
	Socket connected_socket;
	OutputStream socket_output;
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
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
    		ImageWriter writer = (ImageWriter) writers.next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(byte_array_op_stream);
    		writer.setOutput(ios);
			ImageWriteParam param = writer.getDefaultWriteParam();
    		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
    		param.setCompressionQuality(0.2f);  // Change the quality value you prefer
    		writer.write(null, new IIOImage(screenImage, null, null), param);

            byte_array_ip_stream = new ByteArrayInputStream(byte_array_op_stream.toByteArray());
            int fileSize = byte_array_op_stream.size();
            receiveScreenEvent.op_stream.writeObject(fileSize);
            receiveScreenEvent.op_stream.flush();

            byte[] buffer = new byte[4096];
            int read = 0;
            long totalRead = 0;
            int remaining = (int) fileSize;
            while ((read = byte_array_ip_stream.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                receiveScreenEvent.op_stream.write(buffer, 0, read);
                receiveScreenEvent.op_stream.flush();
            }
            receiveScreenEvent.op_stream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
