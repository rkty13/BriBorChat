import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	
	public Server(){
	    
	}
	
	public static void main(String[]args){
	    try {
			ServerSocket s = new ServerSocket(4);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
