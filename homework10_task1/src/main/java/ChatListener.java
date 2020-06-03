import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class ChatListener extends Thread {
	private int port;

	public ChatListener(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(port);

			byte[] buf = new byte[512];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			ds.receive(packet);
			System.err.println("Start chat::");

			Map<String, String> nameStorage = new HashMap<String, String>();
			String name = "unknown name";
			while (true) {
				System.out.println(name
						+ " : "
						+ new String(packet.getData()));
				ds.receive(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ds != null) {
				ds.close();
			}
		}
	}
}
