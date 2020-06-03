import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents chat as socket server.
 *
 * @author Petrov_OlegYu
 */
public class Server {
	public static final int SERVER_PORT = 6543;

	/**
	 * Create socket server and waiting a client logging
	 */
	public static void main(String[] args) throws IOException {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(SERVER_PORT);
			ds.setBroadcast(true);

			byte[] buf = new byte[512];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			ds.receive(packet);
			System.err.println("Start chat::");

			Map<String, String> nameStorage = new HashMap<String, String>();
			String name = "unknown name";
			while (true) {
				String clientId = packet.getAddress().getHostName()+packet.getPort();
				name = nameStorage.get(clientId);
				if (name == null) {
					name = new String(packet.getData());
					nameStorage.put(clientId, name);
					System.err.println(name + " enter to chat");
				} else {
					System.out.println(name
							+ " : "
							+ new String(packet.getData()));
					ds.send(packet);
				}

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
