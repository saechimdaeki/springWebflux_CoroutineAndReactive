package org.example.reactive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Flow;

public class FixedIntPublisher
        implements Flow.Publisher<FixedIntPublisher.Result> {


    public static class Result {
        private final Integer value;
        private final Integer requestCount;

        public Result(Integer value, Integer requestCount) {
            this.value = value;
            this.requestCount = requestCount;
        }

        public Integer getValue() {
            return value;
        }

        public Integer getRequestCount() {
            return requestCount;
        }
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Result> subscriber) {
        var numbers = Collections.synchronizedList(
                new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7))
        );
        Iterator<Integer> iterator = numbers.iterator();
        var subscription = new IntSubscription(subscriber, iterator);
        subscriber.onSubscribe(subscription);
    }
}