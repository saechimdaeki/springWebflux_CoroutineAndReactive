## Reactive streams 구조
- 데이터 혹은 이벤트를 제공하는 Publisher
- 데이터 혹은 이벤트를 제공받는 Subscriber
- 데이터 흐름을 조절하는 Subscription

### Publisher
```java
@FunctionalInterface
public static interface Publisher<T> {
    public void subscribe(Subscribe<? super T> subscriber);
}
```

- subscribe 함수 제공해서 publisher에 다수의 subscriber 등록 지원
- subscription을 포함하고 Subscriber가 추가되면 subscription 제공

### Subscriber
```java
public static interface Subscriber<T> {
public void onSubscribe(Subscription subscription);
public void onNext(T item);
public void onError(Throwable throwable);
    public void onComplete();
}
```
- subscribe하는시점에 publisher로부터 subscription을 받을 수 있는 인자 제공
- onNext, onError, onComplete를 통해서 값 이나 이벤트를 받을수있다
- onNext는 여러 번, onError나 onComplete 는 딱한번만 호출된다

### Subscription
```java
public static interface Subscription {
    public void request(long n);
    public void cancel();
}
```

- back-pressure를 조절할 수 있는request 함수
- Publisher가onNext를 통해서 값을 전달하는 것을 취소할 수 있는 cancel 함수


