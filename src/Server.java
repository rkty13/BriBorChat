import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public static ArrayList<HandleClient> clients;

	public static void main(String[] args) {
		new Server();
	}

	@SuppressWarnings("resource")
	public Server() {
		clients = new ArrayList<HandleClient>();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(18304);

			int clientNum = 1;

			while (true) {
				Socket connection = null;
				String username = null;
				ObjectInputStream inputName = null;
				try {
					connection = serverSocket.accept();
					inputName = new ObjectInputStream(
							connection.getInputStream());
					username = (String) inputName.readObject();
					System.out.println(username);
					// System.out.println(o.toString());
					System.out.println("Client #" + clientNum + " connected");
				} catch (IOException e) {
					continue;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					continue;
				}

				HandleClient task = new HandleClient(connection, clientNum,
						username);
				clients.add(task);
				new Thread(task).start();
				clientNum++;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void remove(int clientNum) {
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).clientNum == clientNum) {
				clients.remove(i);
			}
		}
	}

	public static void writeToAll(String message, int clientNum) {
		for (HandleClient client : clients) {
			try {
				if (!(client.clientNum == clientNum)) {
					client.sendMessage(message);
				}

			} catch (IOException e) {
				System.err.println("Error sending to client #"
						+ client.clientNum);
				continue;
			}
		}
	}
}

class HandleClient implements Runnable {
	private Socket connection;
	public int clientNum;
	private DataInputStream in;
	private DataOutputStream out;
	private String username;

	public HandleClient(Socket connection, int clientNum, String username) {
		this.connection = connection;
		this.clientNum = clientNum;
		this.username = username;
	}

	@Override
	public void run() {
		try {

			in = new DataInputStream(connection.getInputStream());
			out = new DataOutputStream(connection.getOutputStream());

			while (true) {
				try {
					String message = in.readUTF();
					System.out.println("Message: '" + message + "' received.");
					Server.writeToAll(message, clientNum);
				} catch (Exception e) {
					System.err.println("Client #" + clientNum
							+ " disconnected.");
					Server.remove(clientNum);
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) throws IOException {
		out.writeUTF(username + ": " + message);
	}
}