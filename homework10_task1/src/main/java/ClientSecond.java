import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents client for chatting
 *
 * @author Petrov_OlegYuc
 */
public class ClientSecond {
	private static final Pattern privateMessagePattern = Pattern.compile("/\\$\\{\\w+\\}/");

	public static final int CLIENT_PORT = 65444;

	/**
	 * run the method for starting program and enter to chat
	 */
	public static void main(String[] args) {
		Thread chatListener = new ChatListener(CLIENT_PORT);
		chatListener.start();

		try (DatagramSocket ds = new DatagramSocket()) {
			InetAddress addr = InetAddress.getByName("127.0.0.1");

			Scanner scanner = new Scanner(System.in);
			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if (nextLine.equals("quit")) {
					ds.close();
					chatListener.interrupt();
				}

				JsonElement packet = JsonNull.INSTANCE;
				if (isPrivateMessage(nextLine)) {
					packet = createPrivateMessage(nextLine);
				} else {
					packet = createCommonMessage(nextLine);
				}

				if (!packet.isJsonNull()) {
					byte[] data = packet.getAsString().getBytes();
					DatagramPacket pack = new DatagramPacket(data, data.length, addr, Server.SERVER_PORT);
					ds.send(pack);
				} else {
					System.err.println("incorrect  message");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static JsonElement createCommonMessage(String nextLine) {
		JsonObject packet = new JsonObject();
		packet.addProperty("message", nextLine);

		return packet;
	}

	private static JsonElement createPrivateMessage(String nextLine) {
		Matcher matcher = privateMessagePattern.matcher(nextLine);
		JsonObject packet = new JsonObject();
		packet.addProperty("destination", matcher.group());
		packet.addProperty("message", matcher.replaceFirst(""));

		return packet;
	}

	private static boolean isPrivateMessage(String nextLine) {
		return privateMessagePattern.matcher(nextLine).find();
	}
}
