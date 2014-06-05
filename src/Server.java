import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
            serverSocket = new ServerSocket(6066);

            int sessionNum = 1;

            while (true) {
                Socket connection = null;
                try {
                    connection = serverSocket.accept();
                    System.out.println("Person 1 connected to session"
                            + sessionNum);
                } catch (IOException e) {
                    continue;
                }

                HandleClient task = new HandleClient(connection, sessionNum);
                clients.add(task);
                new Thread(task).start();
                sessionNum++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void remove(int sessionNum) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).sessionNum == sessionNum) {
                clients.remove(i);
            }
        }
    }

    public static void writeToAll(String message) {
        for (HandleClient client : clients) {
            try {
                client.sendMessage(message);
            } catch (IOException e) {
                System.err.println("Error sending to client #"
                        + client.sessionNum);
                continue;
            }
        }
    }
}

class HandleClient implements Runnable {
    private Socket connection;
    public int sessionNum;
    private DataInputStream in;
    private DataOutputStream out;

    public HandleClient(Socket connection, int sessionNum) {
        this.connection = connection;
        this.sessionNum = sessionNum;
    }

    @Override
    public void run() {
        try {

            in = new DataInputStream(connection.getInputStream());
            out = new DataOutputStream(connection.getOutputStream());

            while (true) {
                try {
                    String message = in.readUTF();
                    Server.writeToAll(message);
                } catch (Exception e) {
                    System.err.println("Client #" + sessionNum
                            + " disconnected.");
                    Server.remove(sessionNum);
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }
}