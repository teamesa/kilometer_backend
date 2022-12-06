package com.kilometer.domain.search

import com.kilometer.domain.converter.listItem.ListItemAggregateConverter
import com.kilometer.domain.item.ItemService
import com.kilometer.domain.paging.PagingStatusService
import com.kilometer.domain.converter.listItem.dto.ListItem
import com.kilometer.domain.search.request.SearchRequest
import org.junit.platform.commons.PreconditionViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification
import spock.lang.Subject

class SearchServiceTest extends Specification {
    @Subject
    SearchService sut

    ItemService itemService = Mock(ItemService.class)
    ListItemAggregateConverter listItemAggregateConverter = Mock(ListItemAggregateConverter.class)
    PagingStatusService pagingStatusService = Mock(PagingStatusService.class)

    def setup() {
        sut = new SearchService(itemService, listItemAggregateConverter, pagingStatusService)
    }

    def "Search works well"() {
        given:
        def insert = Spy(SearchRequest)
        itemService.getItemBySearchOptions(*_) >> Page.empty()
        1 * pagingStatusService.makePageable(_) >> Mock(Pageable)
        0 * listItemAggregateConverter.convert(_) >> Mock(ListItem)
        when:
        def result = sut.search(insert, 0)
        then:
        result
    }

    def "Search can not be work with null"() {
        given:
        def insert = null
        when:
        sut.search(insert, -1)
        then:
        thrown(PreconditionViolationException.class)
    }
}
