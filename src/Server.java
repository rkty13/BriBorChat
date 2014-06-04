import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

	public static ArrayList<ClientThread> users = new ArrayList<ClientThread>();
	public static LinkedBlockingQueue<String> messages = new LinkedBlockingQueue<String>();

	public static void main(String[] args) throws IOException {

		try {

			ServerSocket serverSocket = new ServerSocket(18304);

			while (true) {
				Socket clientSocket = serverSocket.accept();

				InetAddress inetAddress = clientSocket.getInetAddress();

				System.out.println("Client: " + inetAddress.getHostName()
						+ " | " + inetAddress.getHostAddress() + " connected.");

				ClientThread thread = new ClientThread(clientSocket, "rkty13");
				users.add(thread);
				new Thread(thread).start();

				while (!messages.isEmpty()) {
					String message = messages.remove();
					for (ClientThread user : users) {
						user.sendMessage(message);
					}

				}
			}
			/*
			 * PrintWriter out = new PrintWriter(
			 * clientSocket.getOutputStream(), true); BufferedReader in = new
			 * BufferedReader(new InputStreamReader(
			 * clientSocket.getInputStream())); String inputLine; while
			 * ((inputLine = in.readLine()) != null) { out.println(inputLine); }
			 */
		} catch (IOException e) {
			System.out
					.println("Exception caught when trying to listen on port "
							+ 18304 + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}

class ClientThread implements Runnable {

	private Socket socket;
	private String username;
	private BufferedReader in;
	private PrintWriter out;

	public ClientThread(Socket socket, String username) throws IOException {
		this.socket = socket;
		this.username = username;
		out = new PrintWriter(this.socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	}

	@Override
	public void run() {
		try {
			String message;
			while ((message = in.readLine()) != null) {
				Server.messages.add(username + ": " + message);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public void sendMessage(String message) {
		out.println(message);
	}
}