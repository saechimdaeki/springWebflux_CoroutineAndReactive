package org.example.javanio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class JavaNIoNonBlockingServer {

    public static void main(String[] args) throws InterruptedException {
        try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
            serverSocket.bind(new InetSocketAddress("localhost", 8080));
            serverSocket.configureBlocking(false);
            while (true) {
                SocketChannel clientSocket = serverSocket.accept();
                if (serverSocket == null) {
                    Thread.sleep(100);
                    continue;
                }
                handleRequest(clientSocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(SocketChannel clientSocket) throws IOException, InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        while (clientSocket.read(byteBuffer) == 0) {
            System.out.println("Reading...");
        }

        byteBuffer.flip();
        String requestBody = StandardCharsets.UTF_8.decode(byteBuffer).toString();

        Thread.sleep(10);

        ByteBuffer responseBody = ByteBuffer.wrap("This is server".getBytes());
        clientSocket.write(responseBody);
        clientSocket.close();
    }
}
