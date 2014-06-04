import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {

		try {
			ServerSocket serverSocket = new ServerSocket(8000);
			while (true) {
				Socket clientSocket = serverSocket.accept();

				InetAddress inetAddress = clientSocket.getInetAddress();

				System.out.println("Client: " + inetAddress.getHostName()
						+ " | " + inetAddress.getHostAddress() + " connected.");
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
							+ 8000 + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}