package org.example.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.GrpcService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class GrpcServerConfig {
    @Value("${grpc.server.port}")
    Integer grpcPort;

    private final GrpcService grpcService;

    @Bean
    public Server grpcServer() {
        log.info("### Grpc port : " + grpcPort);
        return ServerBuilder.forPort(grpcPort).addService(grpcService).build();
    }
}
