import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
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
		
		// initialize ServerSocket and listen for connections
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(18304);

			int clientNum = 1;

			while (true) {
			    // listen for client connection
				Socket connection = null;
				String username = null;
				ObjectInputStream inputName = null;
				try {
				    // accept client connection
					connection = serverSocket.accept();
					inputName = new ObjectInputStream(
							connection.getInputStream());
					
					// receive username from client
					username = (String) inputName.readObject();

					// check if username given by client is taken
					new DataOutputStream(connection.getOutputStream())
							.writeBoolean(checkUserTaken(username));

					System.out.println("Client #" + clientNum
							+ " connected as '" + username + "'.");
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
				// updateUserList();
				clientNum++;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// removes client from ArrayList of connected clients
	public static void remove(int clientNum) {
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).clientNum == clientNum) {
				clients.remove(i);
			}
		}
		// updateUserList();
	}
	
	// writes messages to all clients
	public static void writeToAll(String message, int clientNum,
			String fromUsername) {
		for (HandleClient client : clients) {
			try {
				if (!(client.clientNum == clientNum)) {
					client.sendMessage(message, fromUsername);
				}

			} catch (IOException e) {
				System.err.println("Error sending to client #"
						+ client.clientNum);
				continue;
			}
		}
	}

	// updates client ArrayList
	public static void updateUserList() {
		StringBuilder sb = new StringBuilder();
		for (HandleClient name : clients) {
			System.out.println(name);
			sb.append(name.username + "\n");
		}
		for (HandleClient client : clients) {
			System.out.println(2);
			try {
				System.out.println(sb.toString());
				client.sendMessage(sb.toString(),
						"82a0ca8043d31417a307bb3627ec135b74f36d0b7f41a8410616fb593fdf6c42");
			} catch (IOException e) {
				System.err.println("Error sending user list to client # "
						+ client.clientNum);
				continue;
			}
		}
	}

	// checks if username is taken by another client, returns a boolean
	public boolean checkUserTaken(String username) {
		for (HandleClient client : clients) {
			if (client.username.equalsIgnoreCase(username)) {
				return false;
			}
		}
		return true;
	}
}

class HandleClient implements Runnable {
	private Socket connection;
	public int clientNum;
	private DataInputStream in;
	private DataOutputStream out;
	public String username;

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
					System.out.println("Message: '" + message
							+ "' received from " + username + ".");
					Server.writeToAll(message, clientNum, username);
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

	public void sendMessage(String message, String fromUsername)
			throws IOException {
		out.writeUTF(fromUsername + ": " + message);
	}
}

@SuppressWarnings("serial")
class Username implements Serializable {
	public String username;

	public Username(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}

@SuppressWarnings("serial")
class ServerMessage implements Serializable {
	public Object packet;

	public ServerMessage(Object packet) {
		this.packet = packet;
	}

	public Object getPacket() {
		return packet;
	}
}