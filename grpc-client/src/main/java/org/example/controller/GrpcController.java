package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.GrpcClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GrpcController {
    private final GrpcClientService grpcClientService;

    @GetMapping("/{name}")
    public String grpc(@PathVariable String name) {
        return grpcClientService.sampleCall(name);
    }
}
