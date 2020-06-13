import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Listener for server broadcasting messages
 *
 * @author Petrov_OlegYu
 */
public class ChatListener extends Thread {
	private int port;

	public ChatListener(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try (DatagramSocket ds = new DatagramSocket(port)) {

			byte[] buf = new byte[1028];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			ds.receive(packet);
			System.err.println("Start chat::");

			while (true) {
				System.out.println(new String(packet.getData()));
				ds.receive(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
