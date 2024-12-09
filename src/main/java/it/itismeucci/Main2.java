package it.itismeucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main2 {
    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(8080);
        while (true) {
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.  getOutputStream());
            String[] firstLine = in.readLine().split(" ");
            String method = firstLine[0];
            String resource = firstLine[1];
            String version = firstLine[2];
            String header;

            do {
                header = in.readLine();
                System.out.println(header);
            } while (!header.isEmpty());



            switch (resource) {
                case "/index.html":
                    String indexBody = "<html><body><h1>Ciao a tutti</h1></body></html>"; 
                    out.writeBytes("HTTP/1.1 200 OK \n");
                    out.writeBytes("Content-Type: " + resource + "\n"); 
                    out.writeBytes("Content-Length: " + indexBody.length() +  "\n");
                    out.writeBytes("\n");
                    out.writeBytes(indexBody);
                    break;

                case "/pagina.html":
                    String pageBody = "<html><body><h1>Ciao a tutti dalla pagina</h1></body></html>"; 
                    out.writeBytes("HTTP/1.1 200 OK \n");
                    out.writeBytes("Content-Type: text/html \n"); 
                    out.writeBytes("Content-Length: " + pageBody.length() +  "\n");
                    out.writeBytes("\n");
                    out.writeBytes(pageBody);
                    break;

                case "/file.txt":
                File file = new File(resource);
                if(file.exists()) {
                    out.writeBytes("HTTP/1.1 200 OK \n");
                    out.writeBytes("Content-Type: text/html \n"); 
                    out.writeBytes("Content-Type: " + resource + "\n"); 
                    out.writeBytes("Content-Length: " + file.length() +  "\n");
                    out.writeBytes("\n");
                    InputStream input = new FileInputStream(file);
                    byte[] buf = new byte[8192];
                    int n;
                    while ((n=input.read(buf)) != 1) {
                        out.write(buf,0,n);
                    }
                    input.close();
                }
                    break;
            
                default:
                    String errorBody = "<html><body><h1>404 Not Found</h1><p>The resource you requested could not be found.</p></body></html>";
                    out.writeBytes("HTTP/1.1 404 Not Found \n");
                    out.writeBytes("Content-Type: text/html \n"); 
                    out.writeBytes("Content-Length: " + errorBody.length() + "\n");
                    out.writeBytes("\n");
                    out.writeBytes(errorBody);
                    break;
            }

            out.close();
            in.close();
            s.close();
        }
    }
}
