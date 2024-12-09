package it.itismeucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(8080);
        while (true) {
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            String[] firstLine = in.readLine().split(" ");
            String method = firstLine[0];
            String resource = firstLine[1];
            String version = firstLine[2];
            String header;
            System.out.println(method + ", " + resource);

            do {
                header = in.readLine();
            } while (header != null && !header.isEmpty());
            System.out.println("Richiesta Terminata");

            String responseBody = "Ciao a tutti"; 
            out.writeBytes("HTTP/1.1 200 OK \n");
            out.writeBytes("Content-Type: text/plain \n"); 
            out.writeBytes("Content-Length: " + responseBody.length() + "\n");
            out.writeBytes("\n"); 
            out.writeBytes(responseBody);
            out.writeBytes("\n"); 



            out.close();
            in.close();
            s.close();
        }
    }
}
