package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.example.dto.UserRequest;
import org.example.grpc.User;
import org.example.grpc.UserIdx;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ObjectMapper mapper;

    @GetMapping("/user/{idx}")
    public String getUser(@PathVariable  Long idx) throws InvalidProtocolBufferException {
        return JsonFormat.printer().print(userService.getUser(idx));
    }

    @PostMapping("/user/set")
    public Long setUser(@RequestBody @Valid UserRequest user) {
        return userService.setUser(user);
    }
}
