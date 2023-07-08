package org.example.javanio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaIoServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));

            while (true) {
                Socket clientSocket = serverSocket.accept();

                byte[] requestByres = new byte[1024];
                InputStream in = clientSocket.getInputStream();
                in.read(requestByres);

                OutputStream out = clientSocket.getOutputStream();
                String response = "This is server";
                out.write(response.getBytes());
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
