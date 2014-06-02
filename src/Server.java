import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public Server(){
	    
	}
	
	public static void main(String[]args){
	    try {
			ServerSocket server = new ServerSocket(4);
			Socket client = server.accept();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
