package com.kilometer.domain.hello;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.kilometer.common.annotation.SpringTestWithData;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("HelloService 는 ")
@SpringTestWithData
public class HelloServiceNoneMockingTest {

    private static final String SERVICE_NAME = "service";
    public static final String MEMO_NAME = "memo";

    @Autowired
    HelloService helloService;

    @Autowired
    HelloRepository helloRepository;

    @DisplayName("hello list 를 가져올 때")
    @Nested
    class GetHelloListsTest {

        @DisplayName("100개 미만이면 그대로 전달한다.")
        @ParameterizedTest(name = "{displayName} {index}")
        @CsvSource(value = {"0", "99"})
        void getHelloListSmallerThan100(int helloLists) {
            for (int i = 0; i < helloLists; i++) {
                HelloEntity helloEntity = HelloEntity.builder()
                        .service(SERVICE_NAME)
                        .memo(MEMO_NAME)
                        .build();
                helloRepository.save(helloEntity);
            }

            List<HelloResponse> actual = helloService.getHelloLists();

            assertThat(actual.size()).isEqualTo(helloLists);
        }

        @DisplayName("100개 이상이면 100개만 전달한다.")
        @Test
        void getHelloListLargerThan100() {
            for (int i = 0; i < 102; i++) {
                HelloEntity helloEntity = HelloEntity.builder()
                        .service(SERVICE_NAME)
                        .memo(MEMO_NAME)
                        .build();
                helloRepository.save(helloEntity);
            }

            List<HelloResponse> actual = helloService.getHelloLists();

            assertThat(actual.size()).isEqualTo(100);
        }
    }

    @DisplayName("WEB 으로 메모를 전달해야 한다.")
    @Test
    void runInWeb() {
        HelloResponse actual = helloService.runInWeb(MEMO_NAME);

        assertAll(
                () -> assertThat(actual.getMemo()).isEqualTo(MEMO_NAME),
                () -> assertThat(actual.getService()).isEqualTo("WEB")
        );
    }

    @DisplayName("BATCH 로 메모를 전달해야 한다.")
    @Test
    void runInBatch() {
        HelloResponse actual = helloService.runInBatch(MEMO_NAME);

        assertAll(
                () -> assertThat(actual.getMemo()).isEqualTo(MEMO_NAME),
                () -> assertThat(actual.getService()).isEqualTo("BATCH")
        );
    }
}
