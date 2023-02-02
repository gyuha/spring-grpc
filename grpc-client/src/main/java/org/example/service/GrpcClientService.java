package org.example.service;

import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.example.grpc.HelloRequest;
import org.example.grpc.MyServiceGrpc;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GrpcClientService {
    private static final int PORT = 9090;
    public static final String HOST = "127.0.0.1";

    public String sampleCall(String name) {
        var channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
        var client = MyServiceGrpc.newBlockingStub(channel);
        var request = HelloRequest.newBuilder().setName(name).build();
        try {
            var response = client.sayHello(request);
            return response.getMessage();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
