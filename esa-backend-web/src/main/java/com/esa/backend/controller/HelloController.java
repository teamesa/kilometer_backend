package com.esa.backend.controller;

import com.esa.domain.hello.HelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello-example")
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;
//    private final HelloRepository helloRepository;

    @GetMapping
    public String getHello1() {
        return helloService.hello();
    }

//    @GetMapping
//    public List<HelloResponse> getHelloList() {
//        return helloService.getHelloLists();
//    }
//
//    @PostMapping
//    public HelloResponse makeHello(@RequestBody String memo) {
//        return helloService.runInBatch(memo);
//    }
}
