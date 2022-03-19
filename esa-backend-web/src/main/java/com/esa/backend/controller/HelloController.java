package com.esa.backend.controller;

import com.esa.domain.hello.HelloRepository;
import com.esa.domain.hello.HelloResponse;
import com.esa.domain.hello.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hello-example")
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;

    @GetMapping
    public List<HelloResponse> getHelloList() {
        return helloService.getHelloLists();
    }

    @PostMapping
    public HelloResponse makeHello(@RequestBody String memo) {
        return helloService.runInWeb(memo);
    }
}
