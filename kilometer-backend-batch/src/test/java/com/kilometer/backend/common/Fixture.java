package com.kilometer.backend.common;

import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Fixture {

    public static final CrawledItemDto crawledItemDto = CrawledItemDto.builder()
            .exhibitionType("MUSICAL")
            .exposureType("ON")
            .regionType("SEOUL")
            .feeType("COST")
            .listImageUrl("http://tkfile.yes24.com/upload2/PerfBlog/202307/20230707/20230707-46037.jpg")
            .thumbnailImageUrl("http://tkfile.yes24.com/upload2/PerfBlog/202307/20230707/20230707-46037.jpg")
            .title("뮤지컬 <알로하, 나의 엄마들>")
            .startDate(LocalDate.parse("2023-07-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .endDate(LocalDate.parse("2023-08-19", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
            .placeName("국립극장 해오름극장")
            .price("R석 100,000원\n"
                    + "    S석 80,000원\n"
                    + "    A석 50,000원\n"
                    + "    B석 30,000원")
            .operatingTime("170분 (인터미션 15분 포함)\n(7월) 수-금 19:30 | 토 14:00, 19:00 | 일 15:00 (월,화 쉼) (8월) 화,목,금 19:30 | 수 15:00 | 토,일 14:00, 19:00 (월 공연없음) (단, 8/6(일)14:00, 8/15(화)15:00 1회공연 진행)")
            .ticketUrl("http://ticket.yes24.com/Perf/46037?Gcode=009_403")
            .itemDetailImages(List.of("http://tkfile.yes24.com/Upload2/Board/202307/20230707/46037_2_01.jpg"
                    , "http://tkfile.yes24.com/Upload2/Board/202307/20230707/46037_2_02.jpg"
                    , "http://tkfile.yes24.com/Upload2/Board/202307/20230707/46037_2_03.jpg"
                    , "http://tkfile.yes24.com/Upload2/Board/202307/20230707/46037_2_04.jpg"
                    , "http://tkfile.yes24.com/Upload2/Board/202307/20230707/46037_2_05.jpg"))
            .build();
}
