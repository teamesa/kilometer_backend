package com.kilometer.domain.archive.userVisitPlace;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVisitPlaceRepository extends JpaRepository<UserVisitPlaceEntity, Long> {

    List<UserVisitPlaceEntity> findAllByArchiveEntityId(Long archiveEntityId);

    void deleteAllByArchiveEntityId(Long archiveEntityId);
}
