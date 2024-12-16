package it.itismeucci;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main2 {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        System.out.println("Server in ascolto sulla porta 8080...");
        
        while (true) {
            Socket s = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String[] firstLine = in.readLine().split(" ");
            String method = firstLine[0]; 
            String resource = firstLine[1];
            String version = firstLine[2];

            String header;
            while ((header = in.readLine()) != null && !header.isEmpty()) {
                System.out.println(header);
            }

                if (resource.equals("/")) {
                    File file = new File("src/main/java/it/httpdocs/index.html");
                    if (file.exists() && file.isFile()) {
                        out.writeBytes("HTTP/1.1 200 OK\r\n");
                        out.writeBytes("Content-Type:" + getContentType(file) + "\r\n");
                        out.writeBytes("Content-Length: " + file.length() + "\r\n");
                        out.writeBytes("Connection: close\r\n");
                        out.writeBytes("\r\n");

                        try (InputStream input = new FileInputStream(file)) {
                            byte[] buf = new byte[8192];
                            int n;
                            while ((n = input.read(buf)) != -1) {
                                out.write(buf, 0, n);
                            }
                        }
                    } else {
                        String errorBody = "<html><body><h1>404 Not Found</h1><p>The resource you requested could not be found.</p></body></html>";
                        out.writeBytes("HTTP/1.1 404 Not Found\r\n");
                        out.writeBytes("Content-Type: text/html\r\n");
                        out.writeBytes("Content-Length: " + errorBody.length() + "\r\n");
                        out.writeBytes("\r\n");
                        out.writeBytes(errorBody);
                    }
                }


            out.close();
            in.close();
            s.close();
        }
    }

        private static String getContentType(File file) {
            String[] s = file.getName().split("\\.");
            String ext = s[s.length - 1];
            switch (ext) {
                case "html":
                case "htm" :
                return "text/html";
                case "png" :
                return "image/png";
                case "jpg":
                case "jpeg":
                return "image/jpeg";
                case "css" :
                return "text/css";
                case "js":
                return "application/javascript";
                default:
                return "";
            }

        }

}
