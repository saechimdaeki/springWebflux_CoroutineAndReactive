# Java NIO

## 01. Java IO - InputStream

### `함수 호출 모델`

||||
|:--:|:--:|:--:|
||`동기`|`비동기`|
|Blocking|Java IO|X|
|Non-blocking|Java NIO <br/> (File IO는 non-blocking 불가능)|Java AIO|

### `I/O 모델`

||||
|:--:|:--:|:--:|
||`동기`|`비동기`|
|Blocking|Java IO|X|
|Non-blocking|Java NIO, Java AIO|X|


### Java IO
- java 1.0에서 처음 도입
- 파일과 네트워크에 데이터를 읽고 쓸 수 있는 API 제공
- byte 단위로 읽고 쓸 수 있는 stream (InputStream과 OutputStream)
- blocking으로 동작

### InputStream
- closable 구현. 명시적으로 close하거나 try-with-resources 사용가능
- `read`: stream으로 데이터를 읽고, 읽은 값을 반환. -1이라면 끝에 도달했다는 것을 의미
- `close`: stream을 닫고 더 이상 데이터를 읽지 않는다

```java
public abstract class InputStream implements Closeable {
    public abstract int read() throws IOException;
    public void close() throws IOException {}
```

- 어떤 source로부터 데이터를 읽을지에 따라 다양한 구현체 존재
- FileInputStream, ByteArrayInputStream, BufferedInputStream

![image](../../image/chap01/inputstream.png)

#### ByteArrayInputStream
- byte array로부터 값을 읽을 수 있다
- 메모리가 source가 된다

![image](../../image/chap01/bytearrayinputstream.png)

![image](../../image/chap01/ByteArrayInputStreamCode.png)

#### FileInputStream
- file로부터 byte단위로 값을 읽을 수 있다
- File 객체나 path를 통해서 FileInputStream을 열 수 있다
- application에서 blocking이 일어난다

![image](../../image/chap01/fileInputStream.png)
![image](../../image/chap01/FileInputStreamCode.png)

#### BufferedInputStream
- 다른 inputStream과 조합해서 사용
- 임시 저장 공간인 buffer를 사용
- 한 번 read를 호출할 때 buffer 사이즈만큼 미리 조회
- 그 이후 read를 호출할 때 미리 저장한 buffer 데이터 반환

![image](../../image/chap01/BufferedInputStream.png)
![image](../../image/chap01/BufferedInputStreamCode.png)

#### serverSocket accept
- serverSocket을 open하여 외부의 요청을 수신
- bind, accept를 통해서 serverSocket open을 준비

#### socket read
- accept가 끝나면 반환값으로 client의 socket을 전달
- client socket의 getInputStream으로 socket의 inputStream에 접근

#### SocketInputStream
- SocketInputStream은public이 아니기때문에 직접 접근이 불가능
- socket.getInputStream으로만 접근 가능 
- blocking이 발생한다

![image](../../image/chap01/SocketInputStream.png)
![image](../../image/chap01/SocketInputStreamCode.png)

