import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Represents client for chatting
 *
 * @author Petrov_OlegYuc
 */
public class ClientSecond {
	public static final int CLIENT_PORT = 65443;

	/**
	 * run the method for starting program and enter to chat
	 */
	public static void main(String[] args) {
		new ChatListener(CLIENT_PORT).start();

		try (DatagramSocket ds = new DatagramSocket()) {
			InetAddress addr = InetAddress.getByName("127.0.0.1");

			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if (nextLine.equals("quit")) {
					ds.close();
				}

				byte[] data = nextLine.getBytes();
				DatagramPacket pack = new DatagramPacket(data, data.length, addr, Server.SERVER_PORT);
				ds.send(pack);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
