package org.example.service;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.example.grpc.HelloReply;
import org.example.grpc.HelloRequest;
import org.example.utils.MyServiceGrpcStubFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GrpcClientService {
    @Value("${grpc.client.host}")
    private static final String HOST = "127.0.0.1";
    @Value("${grpc.client.port}")
    private static final int PORT = 9090;

    private final MyServiceGrpcStubFactory stub;

    public GrpcClientService() {
        stub = new MyServiceGrpcStubFactory(HOST, PORT);
    }

    public String syncCall(String name) {
        var request = HelloRequest.newBuilder().setName(name).build();
        try {
            var response = stub.getBlockingStub().sayHello(request);
            return response.getMessage();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String asyncCall(String name) throws InterruptedException {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        var request = HelloRequest.newBuilder().setName(name).build();
        final String[] text = new String[1];
        stub.getAsyncStub().sayHello(request, new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {
                text[0] = value.getMessage();
                log.info(value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                finishLatch.countDown();
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                log.info("GrpcClient - onCompleted");
                finishLatch.countDown();
            }
        });

        finishLatch.await(1, TimeUnit.MINUTES);

        return text[0];
    }

    public String futureCall(String name) throws InterruptedException {
        final CountDownLatch finishLatch = new CountDownLatch(1);
        var request = HelloRequest.newBuilder().setName(name).build();
        var future = stub.getFutureStub().sayHello(request);
        HelloReply response = null;
        try {
            response = future.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response == null) {
            return null;
        }
        return response.getMessage();
    }
}
