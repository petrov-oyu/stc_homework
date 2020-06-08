import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents chat as socket server.
 * Задание 1. Разработать приложение - многопользовательский чат,
 * в котором участвует произвольное количество клиентов.
 * Каждый клиент после запуска отправляет свое имя серверу. После чего начинает отправлять ему сообщения.
 * Каждое сообщение сервер подписывает именем клиента и рассылает всем клиентам (broadcast).
 *
 * @author Petrov_OlegYu
 */
public class Server {
	public static final int SERVER_PORT = 6543;

	/**
	 * Create socket server and waiting a client logging
	 */
	public static void main(String[] args) {
		try (DatagramSocket ds = new DatagramSocket(SERVER_PORT);) {
			ds.setBroadcast(true);

			byte[] buf = new byte[1028];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			ds.receive(packet);
			System.err.println("Start chat::");

			Map<ClientID, String> nameStorage = new HashMap<>();
			while (true) {
				ClientID clientId = new ClientID(packet.getAddress().getHostName(), packet.getPort());
				String name = nameStorage.get(clientId);
				if (name == null) {
					nameStorage.put(clientId, getName(packet));
				} else {
					sendBroadCastMessage(ds, name, new String(packet.getData()));
				}

				ds.receive(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getting a client name from packet
	 *
	 * @return client name
	 */
	private static String getName(DatagramPacket packet) {
		String name = new String(packet.getData());
		System.err.println(name + " enter to chat");

		return name;
	}

	private static void sendBroadCastMessage(DatagramSocket server, String userName, String userMessage) throws IOException {
		String message = "@"
				.concat(userName)
				.concat(" : ")
				.concat(userMessage);
		System.out.println(message);

		byte[] broadCastBuff = message.getBytes();
		DatagramPacket broadCastPacket = new DatagramPacket(broadCastBuff, broadCastBuff.length);
		broadCastPacket.setAddress(InetAddress.getByName("127.0.0.1"));
		broadCastPacket.setPort(ClientFirst.CLIENT_PORT);
		server.send(broadCastPacket);
	}
}

	/**
	 * Unique client identifier which base on hostName and port
	 *
	 * @author Petrov_OlegYu
	 */
	class ClientID {
		private String hostName = "";
		private int port = -1;

		/**
		 * Create unique cient ID. Port must be positive otherwise constructor throw {@link IllegalArgumentException}
		 * @param hostName
		 * @param port
		 */
		ClientID(String hostName, int port) {
			this.hostName = hostName;
			if (port < 0) {
				throw new IllegalArgumentException("incorrect port");
			}
			this.port = port;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof ClientID)) return false;
			ClientID clientID = (ClientID) o;
			return port == clientID.port &&
					hostName.equals(clientID.hostName);
		}

		@Override
		public int hashCode() {
			return Objects.hash(hostName, port);
		}
	}
