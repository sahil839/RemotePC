import java.net.*;
import java.io.*;
import java.net.InetAddress;

public class util {
	static String ipAddress = null;
	public static String getMyIPAddress() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            ipAddress = localhost.getHostAddress().trim();
        } catch (Exception e) {
            ipAddress = null;
        }
        return ipAddress;
	}
}
