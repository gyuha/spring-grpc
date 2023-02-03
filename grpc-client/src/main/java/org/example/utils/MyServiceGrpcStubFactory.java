package org.example.utils;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.example.grpc.MyServiceGrpc;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MyServiceGrpcStubFactory {
    private final ManagedChannel channel;
    private final MyServiceGrpc.MyServiceBlockingStub blockingStub;
    private final MyServiceGrpc.MyServiceStub asyncStub;
    private final MyServiceGrpc.MyServiceFutureStub futureStub;

    public MyServiceGrpcStubFactory(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        blockingStub = MyServiceGrpc.newBlockingStub(channel);
        asyncStub = MyServiceGrpc.newStub(channel);
        futureStub = MyServiceGrpc.newFutureStub(channel);
    }

    public void shutdownChannel() throws InterruptedException {
        log.info("gRPC Channel shutdown...");
        channel.shutdown().awaitTermination(2, TimeUnit.SECONDS);
    }

    public MyServiceGrpc.MyServiceBlockingStub getBlockingStub() {
        return blockingStub;
    };
    public MyServiceGrpc.MyServiceStub getAsyncStub() {
        return asyncStub;
    };
    public MyServiceGrpc.MyServiceFutureStub getFutureStub() {
        return futureStub;
    };
}
