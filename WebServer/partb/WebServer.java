package uofm.sectionB._7750064.partb;

import java.net.*;

public final class WebServer {
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("Part B");
        
        final int port = 5555; //Set port number
        ServerSocket server_socket = new ServerSocket(port, 1000);
        Socket clientSocket = null;
        
        // Process HTTP service requests in an infinite loop.
        while (true) {

            clientSocket = server_socket.accept();
            HttpRequest request = new HttpRequest(clientSocket);
            Thread thread = new Thread(request);
            thread.start();
            
        }
    }
}
