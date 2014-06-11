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

                    // receive user name from client
                    username = (String) inputName.readObject();

                    // check if user name given by client is taken
                    new DataOutputStream(connection.getOutputStream())
                            .writeBoolean(checkUserTaken(username));

                    System.out.println("Client #" + clientNum
                            + " connected as '" + username + "'.");
                } catch (IOException e) {
                    System.err.println("Error connecting client to server.");
                    e.printStackTrace();
                    continue;
                } catch (ClassNotFoundException e) {
                    System.err.println("Error receiving username from client #"
                            + clientNum + ".");
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
            System.err.println("Error creating new ServerSocket.");
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

    // checks if user name is taken by another client, returns a boolean
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
            System.err.println("Error creating Data Input/Output Streams.");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, String fromUsername)
            throws IOException {
        out.writeUTF(fromUsername + ": " + message);
    }
}