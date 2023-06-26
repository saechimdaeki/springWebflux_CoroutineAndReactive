package org.example.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class CompletableFutureAllOfExample {

    static Logger logger = Logger.getLogger(CompletableFutureAllOfExample.class.getName());

    public static void main(String[] args) {
        var startTime = System.currentTimeMillis();
        var firstFuture = Helper.waitAndReturn(100, 1);
        var secondFuture = Helper.waitAndReturn(500, 2);
        var thirdFuture = Helper.waitAndReturn(1000, 3);


        CompletableFuture.allOf(firstFuture, secondFuture, thirdFuture)
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
