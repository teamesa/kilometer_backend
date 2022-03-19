package com.esa.domain.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HelloService {
    private final HelloRepository helloRepository;

    public HelloResponse runInBatch(String memo) {
        return saveHelloByService(memo, "BATCH");
    }

    public HelloResponse runInWeb(String memo) {
        return saveHelloByService(memo, "WEB");
    }

    public List<HelloResponse> getHelloLists() {
        return helloRepository.findAll().stream().limit(100)
                .map(HelloEntity::makeResponse)
                .collect(Collectors.toList());
    }

    private HelloResponse saveHelloByService(String memo, String service) {
        HelloEntity helloEntity = HelloEntity.builder()
                .service(service)
                .memo(memo)
                .build();
        HelloEntity hello = helloRepository.save(helloEntity);
        return hello.makeResponse();
    }
}
