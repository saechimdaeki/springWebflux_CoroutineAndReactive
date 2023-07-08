package org.example.javanio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class JavaNIoBlockingServer {

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));

            while (true) {
                SocketChannel clientSocket = serverSocket.accept();

                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
                clientSocket.read(byteBuffer);
                byteBuffer.flip();
                String requestBody = StandardCharsets.UTF_8.decode(byteBuffer).toString();

                ByteBuffer responseBody = ByteBuffer.wrap("This is server".getBytes());
                clientSocket.write(responseBody);
                clientSocket.close();


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
