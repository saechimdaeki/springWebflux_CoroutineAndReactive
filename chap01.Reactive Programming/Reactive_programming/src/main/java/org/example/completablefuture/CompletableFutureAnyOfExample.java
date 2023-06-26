package org.example.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class CompletableFutureAnyOfExample {

    static Logger logger = Logger.getLogger(CompletableFutureAnyOfExample.class.getName());

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();
        var firstFuture = Helper.waitAndReturn(100, 1);
        var secondFuture = Helper.waitAndReturn(500, 2);
        var thirdFuture = Helper.waitAndReturn(1000, 3);


        CompletableFuture.anyOf(firstFuture, secondFuture, thirdFuture)
                .thenAcceptAsync(v -> {
                    logger.info("after allOf");
                    try{
                        logger.info("firstFuture: "  +firstFuture.get());
                        logger.info("secondFuture: " + secondFuture.get());
                        logger.info("thirdFuture: " + thirdFuture.get());
                        logger.info("total time: " + (System.currentTimeMillis() - startTime));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).join();
    }
}
