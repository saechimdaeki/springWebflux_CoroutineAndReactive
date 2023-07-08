package org.example.javanio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JavaIoMultiClient {
    private static ExecutorService executorService = Executors.newFixedThreadPool(50);

    public static void main(String[] args) {

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress("localhost", 8080));

                    OutputStream out = socket.getOutputStream();
                    String requestBody = "This is clinet";
                    out.write(requestBody.getBytes());
                    out.flush();

                    InputStream in = socket.getInputStream();
                    byte[] responseBytes = new byte[1024];
                    in.read(responseBytes);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
