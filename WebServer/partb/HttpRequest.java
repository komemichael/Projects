package uofm.sectionB._7750064.partb;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


final class HttpRequest implements Runnable {
    
    final static String CRLF = "\r\n";
    Socket socket = null;
    
    // Constructor
    public HttpRequest(Socket socket) throws Exception {
        this.socket = socket;
    }
    
    // Implement the run() method of the Runnable interface
    public void run() {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // Does the actual processing of the request
    private void processRequest() {
        
        InputStream is = null;
        try {
            // Get a reference to the socket's input and output streams
            is = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            DataOutputStream os = new DataOutputStream(out);
            // Set up input stream filters
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String requestLine = br.readLine();
            System.out.println();
            System.out.println(requestLine);
            // Get and display the header lines
            String headerLine = null;
            while ((headerLine = br.readLine()).length() != 0) {
                System.out.println(headerLine);
            }
            // Extract the filename from the request line
            StringTokenizer tokens = new StringTokenizer(requestLine);
            tokens.nextToken();  // skip over the method, which should be "GET"
            String fileName = tokens.nextToken();
            // Prepend a "." so that file request is within the current directory
            fileName = "." + fileName;
            // Open the requested file
            FileInputStream fis = null;
            boolean fileExists = true;
            try {
                fis = new FileInputStream(fileName);
            } catch (FileNotFoundException e) {
                fileExists = false;
            }
            // Construct the response message
            String statusLine = null;
            String contentTypeLine = null;  // response headers
            String entityBody = null;
            if (fileExists) {
                statusLine = "HTTP/1.1 200 OK" + CRLF;;
                contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;//"Content-type: " + contentType( fileName ) + "; charset=UTF-8" + CRLF;
            } else {
                statusLine = "HTTP/1.1 404 NOT FOUND"+ CRLF;;
                contentTypeLine = "Content-type: text/html"+ CRLF;
                entityBody = "<HTML><HEAD><TITLE>Not Found</TITLE></HEAD><BODY>Not Found</BODY></HTML>" + CRLF;
            }
            // Send the status line
            os.writeBytes(statusLine);
            // Send the content type line
            os.writeBytes(contentTypeLine);
            //Send a blank line to indicate the end of the header lines
            os.writeBytes(CRLF);
            // Send the entity body
            if (fileExists) {
                sendBytes(fis, os);
                System.out.println("cont type: " + contentTypeLine);
                fis.close();
            } else {
                System.out.println("file not found" + entityBody);
                os.writeBytes(entityBody);
            }
            // Close the streams and socket
            os.close();
            br.close();
            socket.close();
        } catch (IOException ex) {
            //Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void sendBytes(FileInputStream fis, OutputStream os){
        try {
            // Construct a 1K buffer to hold bytes on their way to the socket
            byte[] buffer = new byte[1024];
            int bytes = 0;
            
            // Copy requested file into the socket's output stream
            while ((bytes = fis.read(buffer)) != -1 ) {
                os.write(buffer, 0, bytes);
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String contentType(String fileName) {
        if(fileName.endsWith(".htm") || fileName.endsWith(".html")) 
        {
            return "text/html";
        }
        if (fileName.endsWith(".xml"))
        {
            return "application/xml";
        }
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
        {
            return "image/jpg";
        }
        if (fileName.endsWith(".gif"))
        {
            return "image/gif";
        }
        return "application/octet-stream";
    }
}
