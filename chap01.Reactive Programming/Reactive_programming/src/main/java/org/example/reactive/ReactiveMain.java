package org.example.reactive;

import java.util.concurrent.Flow;

public class ReactiveMain {

    public static void main(String[] args) throws InterruptedException {
        Flow.Publisher publisher = new FixedIntPublisher();
        Flow.Subscriber subscriber = new RequestNSubscriber<>(1);
        publisher.subscribe(subscriber);
        Thread.sleep(100);
    }
}
