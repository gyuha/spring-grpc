package org.example.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.UserRequest;
import org.example.grpc.User;
import org.example.grpc.UserIdx;
import org.example.grpc.UserServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Value("${grpc.client.host}")
    private static final String HOST = "127.0.0.1";
    @Value("${grpc.client.port}")
    private static final int PORT = 9090;

    private final UserServiceGrpc.UserServiceBlockingStub stub;

    public UserService() {
        log.info("#### " + HOST);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
        stub = UserServiceGrpc.newBlockingStub(channel);
    }

    public User getUser(Long idx) {
        var request = UserIdx.newBuilder().setIdx(idx).build();

        try {
            return stub.getUser(request);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long setUser(UserRequest userRequest) {
        User user = User.newBuilder()
                .setIdx(userRequest.getIdx())
                .setUsername(userRequest.getUsername())
                .setEmail(userRequest.getEmail())
                .addAllRoles(userRequest.getRoles())
                .build();
        var request = UserIdx.newBuilder().setIdx(1L).build();
        return stub.setUser(user).getIdx();
    }

}
