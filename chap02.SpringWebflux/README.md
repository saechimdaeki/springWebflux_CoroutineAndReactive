# Chap02 Spring Webflux

## Spring servlet stack

### Servlet containers 구조
- `request-per-thread` 모델을 사용하여 요청이 들어올때마다 쓰레드를 할당하여 여러 클라이언트 요청을 동시 처리
- Connector를 통해서 HTTP 통신을 수행
- Filter는 각 요청, 응답의 내용과 헤더를 변환
- 클라이언트의 요청을 Servlet의 service메소드에 넘겨주고 그 결과를 응답으로 전달
- Servlet의 init과 destroy 메소드로 생명주기를 담당

### Servlet
- Java EE Servlet에서 정의 (현재는 Jakarta EE Servlet에서 지원)
- init: Servlet을 초기화할 때 사용. Servlet 객 체를 생성할 때 사용되며 이후 Servlet Container에 등록
- service: 클라이언트의 요청에 따라서 비즈니스 로직을 실행하고 응답 반환
- destroy: Servlet 종료시 수행. 리소스 해제 등을 맡는다
```java
public interface Servlet {

    public void init(ServletConfig config) throws ServletException;

    public void service(ServletRequest req, ServletResponse res)
            throws ServletException, IOException;
    public void destroy();
    }
```

### HttpServlet service
- Servlet을 기반으로 HTTP 프로토콜을 지원
- HTTP 요청 처리에 특화된 기능 제공
- GET, POST, PUT, DELETE, HEAD, OPTION, TRACE등을 구현

### DispatcherServlet
- 클라이언트의 요청을 적절한 컨트롤러에게 전달
- 프론트 컨트롤러 패턴을 구현
- HttpServlet을 상속하여 모든 요청을 doDispatch 호출하게 변경
- HandlerMapping, HandlerAdapter, ViewResolver등과 상호작용

## Spring servlet stack 정리
- Thread-per-request모델을 사용하여 요청이 들어올때마다 쓰레드를 할당하여 여러 클라 이언트 요청을 동시에 처리
- SecurityContext를 ThreadLocal에저장
- 쓰레드당하나의db요청을처리
 
    ->요청이 들어올때마다 할당된 쓰레드는 spring mvc flow, 비즈니스 로직 수행, 
    
    response 생성, SecurityContext 관리, db 요청, http 요청 등 긴 활동주기를 갖는다

![image](../image/chap02/spring%20servlet%20stack%20doc.png)


### Question
- 만약 1만개 혹은 그 이상의 클라이언트 요청이 동시에 들어온다면?
  - Servlet container의ㅣ 쓰레드풀 크기 제한
  - 동기 blocking한 연산들로 인해서 작업을 완료하고 반환되는 쓰레드 수보다 요청 수가 더 많아진다

`그렇다면 쓰레드 풀을 사용하지 않는다면?`
- 쓰레드풀을 사용하지 않고 매번 쓰레드를 만든다면?
  - 쓰레드를 생성하고 제거하는 비용이 비싸다. 시스템 자원 소모가 커지고 성능 저하 발생
  - 쓰레드마다 할당되는 스택 메모리로 인해서 메모리 부족 문제 발생
  - 더 많은 쓰레드가 cpu를 점유하기 위해 컨텍스트 스위칭을 하게 된다



