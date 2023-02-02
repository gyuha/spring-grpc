# gRPC
gRPC는 최신 오픈 소스 고성능 원격 프로시저 호출(RPC)입니다. 모든 환경에서 실행할 수 있는 프레임워크. 서비스를 효율적으로 연결할 수 있습니다.

---
## 특징
### 간단한 서비스 정의
강력한 이진 직렬화 도구 세트 및 언어인 프로토콜 버퍼를 사용하여 서비스 정의
### 신속하게 시작 및 확장
한 줄로 런타임 및 개발 환경을 설치하고 프레임워크를 사용하여 초당 수백만 RPC로 확장
### 여러 언어 및 플랫폼에서 작동
다양한 언어 및 플랫폼에서 서비스에 대한 관용적 클라이언트 및 서버 스텁을 자동으로 생성합니다.
### 양방향 스트리밍 및 통합 인증
HTTP/2 기반 전송을 통한 양방향 스트리밍 및 완전히 통합된 플러그형 인증

---
## 프로젝트 설정
![gRPG](https://techdozo.dev/wp-content/uploads/2021/09/grpc-Page-2.png)
프로젝트는 크게 3개의 모듈로 분리 됩니다.
- `인터페이스 모듈` : 원시 protobuf 파일을 포함하고 Java 모델 및 서비스 클래스를 생성합니다. 아마 이 부분을 공유하실 겁니다.
- `서버 모듈` : 프로젝트의 실제 구현을 포함하고 인터페이스 프로젝트를 종속성으로 사용합니다. (스케줄러에 사용 예정)
- `클라이언트` : 미리 생성된 스텁을 사용하여 서버에 액세스하는 모든 클라이언트 프로젝트. (모든 예약이 필요한 어플리케이션)


### gRPC 테스트
[grpcurl](https://github.com/fullstorydev/grpcurl)을 설치 하면 좀 수월 하게 테스트 할 수 있습니다
```bash
$ grpcurl --plaintext localhost:9090 list
$ grpcurl --plaintext localhost:9090 list net.devh.boot.grpc.example.MyService
# Linux (Static content)
$ grpcurl --plaintext -d '{"name": "test"}' localhost:9090 net.devh.boot.grpc.example.MyService/sayHello
# Windows or Linux (dynamic content)
$ grpcurl --plaintext -d "{\"name\": \"test\"}" localhost:9090 net.devh.boot.grpc.example.MyService/sayHello
```

또는 GUI로는 [bloomrpc](https://github.com/bloomrpc/bloomrpc/releases)이 있지만, 현재 개발이 중 단 된 상태입니다.
![bloomrpc](https://github.com/bloomrpc/bloomrpc/raw/master/resources/editor-preview.gif)


---
### Data Types

| type     | use             | default value                     |
| :------- | :-------------- | --------------------------------- |
| int32    | int             | 0                                 |
| int64    | long            | 0                                 |
| float    | -               | 0                                 |
| double   | -               | 0                                 |
| bool     | -               | false                             |
| string   | -               | empty string                      |
| bytes    | byte[]          |                                   |
| repeated | List/Collection |                                   |
| map      | Map             | empty map                         |
| enum     | -               | first value in the list of values |


---
## 참고
- [gRPC 서비스와 HTTP API 비교 - MS](https://learn.microsoft.com/ko-kr/aspnet/core/grpc/comparison?view=aspnetcore-6.0)
- [Protocol Buffers 언어 가이드](https://developers.google.com/protocol-buffers/docs/proto3)

### gPRC를 직접 구현
- [GRPC Quick start](https://grpc.io/docs/languages/java/quickstart/)
- [gRPC: synchronous and asynchronous unary RPC in Java](https://techdozo.dev/grpc-synchronous-and-asynchronous-unary-rpc-in-java/)
- [난세의 영웅 gRPC](https://dealicious-inc.github.io/2022/07/11/applying-grpc.html)
- [gRPC - java gRPC 간단한 사용법](https://coding-start.tistory.com/352)
- [gRPC Implementation With Spring Boot](https://medium.com/turkcell/grpc-implementation-with-spring-boot-7d6f98349d27)
- [Spring Boot Starter Module for GRPC Framework](https://morioh.com/p/e9850526b9df)
    - [Spring boot starter for gRPC framework.(GitHub)](https://github.com/LogNet/grpc-spring-boot-starter)
- [gRPC Server Streaming](https://www.vinsguru.com/grpc-server-streaming/) : spring이 아니라 순수하게 server만 구현

### [gRPC Spring Boot Starter](https://github.com/yidongnan/grpc-spring-boot-starter) 를 사용 한 구현
- [gRPC-Spring-Boot-Starter Documentation](https://yidongnan.github.io/grpc-spring-boot-starter/en/)
    - [Spring-boot로-Grpc를-사용해보자](https://velog.io/@chb1828/Spring-boot%EB%A1%9C-Grpc%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EC%9E%90) :  위 사이트의 간략한 한글 설명
- [Spring Boot gRPC Example (2023)](https://www.techgeeknext.com/spring-boot/spring-boot-grpc-example) : 가장 간단하면서 쓸모 있는 예제
