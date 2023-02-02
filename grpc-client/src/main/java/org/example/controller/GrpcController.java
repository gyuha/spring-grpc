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

    @GetMapping("/sync/{name}")
    public String syncGrpc(@PathVariable String name) {
        return grpcClientService.syncCall(name);
    }

    @GetMapping("/async/{name}")
    public String asyncGrpc(@PathVariable String name) {
        return grpcClientService.asyncCall(name);
    }
}
