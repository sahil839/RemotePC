import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.io.File;

class receiveFile {
	Socket file_sc;
	ObjectInputStream ip_stream;
	String path;
	connectedWithClient c_frame;
	receiveFile(Socket sc, String file_name, Long file_size, connectedWithClient cframe) {
		file_sc = sc;
		int bytesRead;
		c_frame = cframe;
    	int current = 0;
    	ip_stream = receiveFileEvent.ip_stream;
    	FileOutputStream fos = null;
        String path = util.getHomeDirectoryPath();
        path = path + "/RemotePCFiles/" + file_name;
        File file = new File(path);
        File dirs = new File(file.getParent());
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        try {
            fos = new FileOutputStream(file);
            byte buffer[] = new byte[4096];
            int read = 0;
            long totalRead = 0;
            int remaining = file_size.intValue();
            while (totalRead < file_size && (read = ip_stream.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                c_frame.file_info("Exchanging " + file_name + "| Remaining: " + remaining);
                fos.write(buffer, 0, read);
            }
            c_frame.file_info("Exchanging " + file_name + "| Recieved");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
