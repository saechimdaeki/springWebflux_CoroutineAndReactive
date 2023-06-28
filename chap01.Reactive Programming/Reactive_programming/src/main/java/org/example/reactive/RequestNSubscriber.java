package org.example.reactive;

import java.util.concurrent.Flow;

public class RequestNSubscriber<T> implements Flow.Subscriber<T> {
    private final Integer n;

    public RequestNSubscriber(Integer n) {
        this.n = n;
    }

    private Flow.Subscription subscription;
    private int count = 0;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        System.out.println("item: " + item);
        if (count ++ % n != 0){
            System.out.println("send request");
            this.subscription.request(n);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("error :" + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("complete");
    }
}