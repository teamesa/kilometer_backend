package com.kilometer.domain.archive.userVisitPlace;

import com.google.common.base.Preconditions;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserVisitPlaceService {

    private final UserVisitPlaceRepository userVisitPlaceRepository;

    @Transactional
    public void deleteAllByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        userVisitPlaceRepository.deleteAllByArchiveEntityId(archiveId);
    }

    public List<UserVisitPlaceEntity> findAllByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        return userVisitPlaceRepository.findAllByArchiveEntityId(archiveId);
    }
}