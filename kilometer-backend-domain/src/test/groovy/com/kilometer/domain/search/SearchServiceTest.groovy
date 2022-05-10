package com.kilometer.domain.search

import com.kilometer.domain.search.dto.SearchRequest
import spock.lang.Specification
import spock.lang.Subject

class SearchServiceTest extends Specification {
    @Subject
    SearchService sut

    def setup() {
        sut = new SearchService()
    }

    def "Search works well"() {
        given:
        def insert = Spy(SearchRequest)
        when:
        def result = sut.search(insert)
        then:
        result
    }
}
