package org.example;

import io.grpc.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class GrpcServerRunner implements ApplicationRunner, DisposableBean {
    private final Server grpcServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        grpcServer.start();
        grpcServer.awaitTermination();
    }

    @Override
    public void destroy() {
        if (!ObjectUtils.isEmpty(grpcServer)) {
            grpcServer.shutdown();
        }
    }
}
