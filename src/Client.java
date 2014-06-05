import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

	private DataInputStream in;
	private DataOutputStream out;
	private ChatGUI gui;

	public String username;

	public Client(String username, ChatGUI gui) {
		this.username = username;
		this.gui = gui;
		Socket socket = null;
		try {
			socket = new Socket("67.81.222.76", 18304);
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			/*
			 * Thread sending = new Thread() { public void run() { try { while
			 * (true) { send(); } } catch (IOException e) { e.printStackTrace();
			 * } } }; sending.start();
			 */

			Thread receiving = new Thread() {
				public void run() {
					while (true) {
						receive();
					}
				}
			};
			receiving.start();
		} catch (IOException e) {
			System.err.println("hi");
		}
	}

	public void send(String message) {
		try {
			out.writeUTF(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void receive() {
		try {
			gui.recieveMessage(username + ": " + in.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}