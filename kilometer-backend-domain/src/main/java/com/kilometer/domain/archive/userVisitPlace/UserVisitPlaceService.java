package com.kilometer.domain.archive.userVisitPlace;

import com.kilometer.domain.archive.Archive;
import com.kilometer.domain.archive.request.ArchiveRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserVisitPlaceService {

    private final UserVisitPlaceRepository userVisitPlaceRepository;

    @Transactional
    public void saveAll(ArchiveRequest archiveRequest, Archive archive) {
        if (archiveRequest.getPlaceInfos().isEmpty()) {
            return;
        }
        List<UserVisitPlace> userVisitPlaces = archiveRequest.makeVisitedPlace();
        userVisitPlaces.forEach(userVisitPlace -> userVisitPlace.setArchive(archive));
        userVisitPlaceRepository.saveAll(userVisitPlaces);
    }

    @Transactional
    public void deleteAll(List<UserVisitPlace> userVisitPlaces) {
        if (userVisitPlaces.isEmpty()) {
            return;
        }
        userVisitPlaceRepository.deleteAll(userVisitPlaces);
    }

    public List<UserVisitPlace> findAllByArchiveId(Long archiveId) {
        Preconditions.notNull(archiveId, "Archive id must not be null : " + archiveId);
        return userVisitPlaceRepository.findAllByArchiveId(archiveId);
    }
}