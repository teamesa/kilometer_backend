package com.kilometer.backend.controller;

import com.kilometer.domain.hello.HelloResponse;
import com.kilometer.domain.hello.HelloService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated
@RestController
@RequestMapping("/hello-example")
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;

    @GetMapping
    @ApiOperation(value = "Hello List", notes = "기본적인 list 가져오기")
    public List<HelloResponse> getHelloList() {
        return helloService.getHelloLists();
    }

    @PostMapping
    public HelloResponse makeHello(@RequestBody String memo) {
        return helloService.runInWeb(memo);
    }
}
