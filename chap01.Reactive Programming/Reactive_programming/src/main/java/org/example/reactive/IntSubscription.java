package org.example.reactive;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class IntSubscription implements Flow.Subscription{
    private final Flow.Subscriber<? super FixedIntPublisher.Result> subscriber;
    private final Iterator<Integer> numbers;
    private final ExecutorService executor = Executors.newSingleThreadExecutor(); private final AtomicInteger count = new AtomicInteger(1);
    private final AtomicBoolean isCompleted = new AtomicBoolean(false);

    public IntSubscription(Flow.Subscriber<? super FixedIntPublisher.Result> subscriber, Iterator<Integer> numbers) {
        this.subscriber = subscriber;
        this.numbers = numbers;
    }

    @Override
    public void request(long n) { executor.submit(() -> {
        for (int i = 0; i < n; i++) { if (numbers.hasNext()) {
            int number = numbers.next();
            numbers.remove();
            subscriber.onNext(new FixedIntPublisher.Result(number, count.get()));
        } else {
            var isChanged = isCompleted.compareAndSet(false, true);
            if (isChanged) {
                executor.shutdown();
                subscriber.onComplete();
                isCompleted.set(true);
            }
            break; }
        }
        count.incrementAndGet();
    });
    }
    @Override
    public void cancel() {
        subscriber.onComplete();
    }
}
