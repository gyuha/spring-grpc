package org.example.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.example.grpc.HelloReply;
import org.example.grpc.HelloRequest;
import org.example.grpc.MyServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
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

    private final MyServiceGrpc.MyServiceBlockingStub stub;
    private final MyServiceGrpc.MyServiceStub asyncStub;

    public GrpcClientService() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
        stub = MyServiceGrpc.newBlockingStub(channel);
        asyncStub = MyServiceGrpc.newStub(channel);

    }

    public String syncCall(String name) {
        var request = HelloRequest.newBuilder().setName(name).build();
        try {
            var response = stub.sayHello(request);
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
        asyncStub.sayHello(request, new StreamObserver<HelloReply>() {
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
}
