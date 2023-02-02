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
import org.springframework.stereotype.Service;

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

    public String asyncCall(String name) {
        var request = HelloRequest.newBuilder().setName(name).build();
        asyncStub.sayHello(request, new StreamObserver<HelloReply>() {
            @Override
            public void onNext(HelloReply value) {
                log.info(value.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                log.info("GrpcClient - onCompleted");
            }
        });
        return "";
    }
}
