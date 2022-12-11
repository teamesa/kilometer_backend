package com.kilometer.domain.archive.userVisitPlace;

import com.google.common.base.Preconditions;
import com.kilometer.domain.archive.Archive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserVisitPlaceService {

    private final UserVisitPlaceRepository userVisitPlaceRepository;

    @Transactional
    public List<UserVisitPlace> saveAll(List<UserVisitPlace> userVisitPlaces, Long archiveId) {
        if (!userVisitPlaces.isEmpty()) {
            Archive archive = Archive.builder().id(archiveId).build();
            userVisitPlaces.forEach(userVisitPlace -> userVisitPlace.setArchive(archive));
            userVisitPlaceRepository.saveAll(userVisitPlaces);
        }
        return userVisitPlaces;
    }

    @Transactional
    public void deleteAllByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        userVisitPlaceRepository.deleteAllByArchiveId(archiveId);
    }

    public List<UserVisitPlace> findAllByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        return userVisitPlaceRepository.findAllByArchiveId(archiveId);
    }
}