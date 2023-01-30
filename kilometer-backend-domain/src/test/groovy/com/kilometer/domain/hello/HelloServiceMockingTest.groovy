package com.kilometer.domain.hello

import org.junit.platform.commons.util.Preconditions
import spock.lang.Specification
import spock.lang.Subject

class HelloServiceMockingTest extends Specification {
    @Subject
    HelloService sut
    HelloRepository helloRepository = Mock(HelloRepository.class)

    def setup() {
        sut = new HelloService(helloRepository)
    }

    def "getHelloLists이 아무것도 안주었을때 안 줌"() {
        given:
        1 * helloRepository.findAll() >> []

        when:
        def result = sut.getHelloLists()

        then:
        !result
        result.size() == 0
    }

    def "getHelloLists이 100개 미만으로 보낼 때 그대로 전달"() {
        given:
        1 * helloRepository.findAll() >> makeHelloList(1)

        when:
        def result = sut.getHelloLists()

        then:
        result
        result.size() == 1
    }

    def "getHelloLists이 100개 이상으로 보낼 때 100개로 전달"() {
        given:
        1 * helloRepository.findAll() >> makeHelloList(200)

        when:
        def result = sut.getHelloLists()

        then:
        result
        result.size() == 100
    }

    def "runInBatch가 제대로 전달 되는지 확인"() {
        given:
        def memo = "test"
        HelloEntity hello = HelloEntity.builder().memo(memo).service("BATCH").build()
        1 * helloRepository.save({ memo == it.memo && "BATCH" == it.service }) >> hello

        when:
        def result = sut.runInBatch(memo)

        then:
        result
        result.memo == memo
        result.service == "BATCH"
    }

    def "runInWEB가 제대로 전달 되는지 확인"() {
        given:
        def memo = "test"
        HelloEntity hello = HelloEntity.builder().memo(memo).service("WEB").build()
        1 * helloRepository.save({ memo == it.memo && "WEB" == it.service }) >> hello

        when:
        def result = sut.runInWeb(memo)

        then:
        result
        result.memo == memo
        result.service == "WEB"
    }

    def makeHelloList(int length) {
        Preconditions.condition(length >= 1, "length cannot be under 1")
        return (0..length - 1).collect { makeHelloMock() }
    }

    def makeHelloMock() {
        return Spy(HelloEntity)
    }
}
