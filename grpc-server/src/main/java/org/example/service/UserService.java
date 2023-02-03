package org.example.service;

import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.example.grpc.User;
import org.example.grpc.UserIdx;
import org.example.grpc.UserServiceGrpc;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    private final Map<Long, User> userMap = new HashMap<>();
    private long idxCounter = 1;

    @Override
    public void setUser(User request,
                        StreamObserver<UserIdx> responseObserver) {
        request = request.toBuilder().setIdx(idxCounter++).build();
        userMap.put(request.getIdx(), request);
        UserIdx response = UserIdx.newBuilder().setIdx(request.getIdx()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getUser(UserIdx request,
                        StreamObserver<User> responseObserver) {
        Long userIdx = request.getIdx();
        if (userMap.containsKey(userIdx)) {
            responseObserver.onNext(userMap.get(userIdx));
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
        }
    }

}
