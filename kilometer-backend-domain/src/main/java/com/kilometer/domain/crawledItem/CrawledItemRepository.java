package com.kilometer.domain.crawledItem;

import com.kilometer.domain.item.enumType.RegionType;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawledItemRepository extends JpaRepository<CrawledItem, Long> {

    Optional<CrawledItem> findByRegionTypeAndPlaceNameAndStartDateAndTitle(RegionType regionType, String placeName,
                                                                           LocalDate startDate, String title);
}
