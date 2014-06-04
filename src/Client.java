import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println("Usage: java Client <host> <port>");
            System.exit(1);
        }

        try {
            Socket echoSocket = new Socket(args[0], Integer.parseInt(args[1]));
            System.out.println("Connection successful!");
            System.out.println("write something");
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(),
                    true);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(
                    System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown Host");
            System.exit(1);
        } catch (IOException e) {
            System.err
                    .println("Couldn't get I/O for the connection to BBServer");
            System.exit(1);
        }
    }
}