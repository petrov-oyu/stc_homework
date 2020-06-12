import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * Усовершенствовать задание 1:
 *
 * a.      добавить возможность отправки личных сообщений (unicast).
 *
 * b.      добавить возможность выхода из чата с помощью написанной в чате команды «quit»
 *
 * @author Petrov_OlegYu
 */
public class Server {
	public static final int SERVER_PORT = 6543;

	/**
	 * Create socket server and waiting a client logging
	 */
	public static void main(String[] args) {
		try (DatagramSocket ds = new DatagramSocket(SERVER_PORT)) {
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
					ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
					InputStreamReader isr = new InputStreamReader(bis);
					JsonReader jsonReader = new JsonReader(isr);
					jsonReader.setLenient(true);
					JsonElement jsonPacket = JsonParser.parseReader(jsonReader);
					if (jsonPacket.isJsonObject()) {
						System.err.println("receiving message: " + jsonPacket.toString());
						if (isPrivateMessage(jsonPacket.getAsJsonObject())) {
							sendPrivateMessage(ds, name, nameStorage, jsonPacket.getAsJsonObject());
						} else {
							sendBroadCastMessage(ds, name, jsonPacket.getAsJsonObject());
						}
					} else {
						System.err.println("incorrect message format");
					}
				}

				ds.receive(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sendPrivateMessage(DatagramSocket server, String fromWho, Map<ClientID, String> nameStorage, JsonObject jsonPacket) throws IOException {
		server.setBroadcast(false);

		String destinationClientName = jsonPacket.get("destination").getAsString();

		nameStorage.entrySet().stream()
				.filter(entry -> entry.getValue().equals(destinationClientName))
				.findFirst()
				.map(entry -> entry.getKey())
				.ifPresent(clientID -> {
					String privateMessage = createMessage(fromWho, jsonPacket.get("message").getAsString()).toString();
					System.out.println("privateMessage : " + privateMessage);

					byte[] privatePacketBuff = privateMessage.getBytes();
					DatagramPacket privatePacket = new DatagramPacket(privatePacketBuff, privatePacketBuff.length);
					try {
						privatePacket.setAddress(InetAddress.getByName(clientID.getHostName()));
						privatePacket.setPort(clientID.getPort());
						server.send(privatePacket);
					} catch (IOException e) {
						System.err.println("error occurped when sending message");
					}
				});
	}

	private static JsonElement createMessage(String clientName, String clientMessage) {
		JsonObject packet = new JsonObject();
		packet.addProperty("clientName", clientName);
		packet.addProperty("clientMessage", clientMessage);

		return packet;
	}

	private static boolean isPrivateMessage(JsonObject jsonPacket) {
		return jsonPacket.has("destination");
	}

	/**
	 * Getting a client name from packet
	 *
	 * @return client name
	 */
	private static String getName(DatagramPacket packet) {
		ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
		InputStreamReader isr = new InputStreamReader(bis);
		JsonReader jsonReader = new JsonReader(isr);
		jsonReader.setLenient(true);
		JsonElement jsonPacket = JsonParser.parseReader(jsonReader);
		String name = jsonPacket.getAsJsonObject().get("message").getAsString();

		System.err.println(name + " enter to chat");

		return name;
	}

	private static void sendBroadCastMessage(DatagramSocket server, String fromWho, JsonObject jsonPacket) throws IOException {
		server.setBroadcast(true);
		String broadCastMessage = createMessage(fromWho, jsonPacket.get("message").getAsString()).toString();
		System.out.println("broadCastMessage : " + broadCastMessage);
		byte[] broadCastBuff = broadCastMessage.getBytes();

		DatagramPacket broadCastPacket = new DatagramPacket(broadCastBuff, broadCastBuff.length);
		broadCastPacket.setAddress(InetAddress.getByName("255.255.255.255"));
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

		public String getHostName() {
			return hostName;
		}

		public int getPort() {
			return port;
		}
	}
