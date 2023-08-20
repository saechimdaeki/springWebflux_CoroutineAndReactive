## Spring webflux 구조

![image](../image/chap02/springwebflux구조1.png)

![image](../image/chap02/springwebflux구조2.png)

![image](../image/chap02/springwebflux구조3.png)


### AutoConfiguration으로 알아보는 의존 그래프

```java
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.server.HttpServer;
import reactor.netty.resources.LoopResources;
public class NettyReactiveWebServerFactory extends AbstractReactiveWebServerFactory {
   @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        HttpServer httpServer = createHttpServer();
        ReactorHttpHandlerAdapter handlerAdapter = new ReactorHttpHandlerAdapter(httpHandler); 
        
        NettyWebServer webServer = createNettyWebServer(httpServer, handlerAdapter, this.lifecycleTimeout,getShutdown()); 
        
        webServer.setRouteProviders(this.routeProviders); 
        
        return webServer;
}
```

### Reactor Netty
- Reactor 기반으로 Netty를 wrapping
- Netty의 성능과 Reactor의 조합성, 편의성모두 제공

```java
Consumer<HttpServerRoutes> routesConsumer = routes -> routes.get("/hello", (request, response) -> {
    var data = Mono.just("Hello World!");

    return response.sendString(data); 
    }
);

HttpServer.create() .route(routesConsumer)
        .port(8080)
        .bindNow()
        .onDispose()
        .block();
```

#### Q. Spring webflux는 어떻게 RxJava, Mutiny, Coroutine을 지원할까?

![image](../image/chap02/ReactiveAdapterRegistry.png)

`ReactiveAdapter`
- ReactiveAdapterRegistry는 내부적으로 각각 라이브러리의 Publisher에 매칭되는

    변환함수를 Adapter 형태로 저장

```java
public void registerReactiveType(ReactiveTypeDescriptor descriptor, Function<Object, Publisher<?>> toPublisherFunction, 
Function<Publisher<?>, Object> fromPublisherFunction) {
    if (reactorPresent) {
        this.adapters.add(new ReactorAdapter(descriptor, toPublisherFunction, fromPublisherFunction)); }
    else {
        this.adapters.add(new ReactiveAdapter(descriptor, toPublisherFunction, fromPublisherFunction));
    }
}
```

- getAdapter를통해서변환하 고자는 Publisher를 넘기고 해당 adapter의 toPublish를 이용하여, 

    reactive streams 의 Publisher로 변경

```java
ReactiveAdapter adapter = getAdapterRegistry().getAdapter(null, attribute); 

if (adapter != null) {
    return Mono.from(adapter.toPublisher(attribute)); 
} else {
    return Mono.justOrEmpty(attribute); 
}
```

`ReactiveAdapter`

- 여러 라이브러리의 Publisher들을 reactive streams의 Publisher로 변 환가능
- Spring webflux에서는 reactive steams의 Publisher 기준으로 구현을 

    한다면 ReactiveAdapter를 통해서 여 러 라이브러리 지원 가능

---

## HttpHandler


- ServerHttpRequest와 ServerHttpResponse를 인자로 받고
- 응답을 돌려줘야 하는 시점을 반환하는 함수형 인터페이스

```java
public interface HttpHandler {
    Mono<Void> handle(ServerHttpRequest request, ServerHttpResponse response);
}
```

`ServerHttpRequest와 ServerHttpResponse`

![image](./../image/chap02/ServerHttpRequest와%20ServerHttpResponse.png)

`ServerHttpRequest`

```java
public interface ServerHttpRequest extends HttpRequest, ReactiveHttpInputMessage {
    
    RequestPath getPath();

    MultiValueMap<String, String> getQueryParams();

    MultiValueMap<String, HttpCookie> getCookies();

    default ServerHttpRequest.Builder mutate() {
        return new DefaultServerHttpRequestBuilder(this);
    }
}

public interface ReactiveHttpInputMessage extends HttpMessage {
    Flux<DataBuffer> getBody();
}
```

- getPath : query를 포함하지 않는 구조화된 path 정보 제공
- getQueryParams : decode된 query parameter map
- getCookies : 클라이언트가 전달한 읽기만 가능한 쿠키 map 제공
- mutate : uri, path, header 등을 변경할 수 있는 ServerHttpRequest builder 제공
- getBody : 클라이언트가 전달한 requestbody를 Flux< DataBuffer>형태로 수신.

    Flux이기 때문에 여러 개의 DataBuffer 전달 가능

- getHeaders: HttpHeaders 객체에 접근 가 능. HttpHeaders에서 header 추가, 수정, 삭 제기능제공
- getMethod: http 요청 method
- getURI: query string을 포함한 uri 정보를 제공

```java
 public interface HttpMessage {
   HttpHeaders getHeaders();
}

public interface HttpRequest extends HttpMessage {
   
   default HttpMethod getMethod() {
    return HttpMethod.resolve(getMethodValue());
    }

   URI getURI();
}
```

`ServerHttpResponse`

- setStatusCode: status 변경
- addCookie: cookie 추가
- writeWith: response content를 추가
- setComplete: response content를 추가하기 전에 연결을 complete 가능
- getHeaders:HttpHeaders객체에 접근 가능. HttpHeaders에서 header 추가, 수정, 삭제 기능 제공

```java
public interface ServerHttpResponse extends             
ReactiveHttpOutputMessage { 
    boolean setStatusCode(@Nullable HttpStatus status);
    void addCookie(ResponseCookie cookie);
}

public interface ReactiveHttpOutputMessage extends HttpMessage { 
    Mono<Void> writeWith(Publisher<? extends DataBuffer> body);
   
    Mono<Void> setComplete();
}

public interface HttpMessage {
   HttpHeaders getHeaders();
}
```

`HttpHandler`

- ServerHttpRequest와 ServerHttpResponse를 전달
- http요청처리가끝나면Mono< Void>반환 
- ServerHttpResponse의setComplete 혹은 writeWith가 Mono< Void>를 반환하기 때문에 이 결과를 반환하는 경우가많다

```java
public interface HttpHandler {
    Mono<Void> handle(ServerHttpRequest request,
    ServerHttpResponse response)
}
```

### HttpHandler의 한계
- request header나 body를 변경하는 작업을 HttpHandler에 직접 구현해 야한다
- HttpHandler 내에서 모든 error를 직접catch해서 관리해야 한다
- request의 read와 response의 write 모두 HttpHandler에서 구현해야 한다