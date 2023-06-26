package org.example.completablefuture;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
public class Helper {
    public static CompletionStage<Integer> finishedStage() throws InterruptedException {
        var future = CompletableFuture.supplyAsync(() ->{
            return 1;
        });
        Thread.sleep(100);
        return future;
    }

    public static CompletionStage<Integer> runningStage() {
        return CompletableFuture.supplyAsync(() ->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });
    }

    public static CompletableFuture<Integer> waitAndReturn(long millis , int value) {
        return CompletableFuture.supplyAsync(() ->{
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return value;
        });
    }
}
