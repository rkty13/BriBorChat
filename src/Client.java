import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private DataInputStream in;
    private DataOutputStream out;
    private Scanner messageInput;

    public Client() {
        messageInput = new Scanner(System.in);
        Socket socket = null;
        try {
            socket = new Socket("localhost", 6066);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread sending = new Thread() {
                public void run() {
                    try {
                        while (true) {
                            send();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            sending.start();

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

    public void send() throws IOException {
        String message = messageInput.nextLine();
        out.writeUTF(message);
    }

    public void receive() {
        try {
            System.out.println(in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
            //System.err.println("error reading");
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}