package com.kilometer.domain.archive.userVisitPlace;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVisitPlaceRepository extends JpaRepository<UserVisitPlace, Long> {

    List<UserVisitPlace> findAllByArchiveId(Long archiveId);
}
