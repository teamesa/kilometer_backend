package com.kilometer.domain.archive.service;

import com.kilometer.domain.archive.Archive;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.archiveImage.ArchiveImageRepository;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceRepository;
import com.kilometer.domain.archive.like.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArchiveDeleteService {

    private final ArchiveRepository archiveRepository;
    private final ArchiveImageRepository archiveImageRepository;
    private final UserVisitPlaceRepository userVisitPlaceRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public void deleteArchived(Long archiveId, Long userId) throws IllegalAccessException {
        Preconditions.notNull(archiveId, "id must not be null");

        Archive archive = archiveRepository.findById(archiveId)
            .orElseThrow(() -> new IllegalArgumentException("Archive does not exists."));

        if (!userId.equals(archive.getUser().getId())) {
            throw new IllegalAccessException("Archives can only be deleted by the writer.");
        }

        // archive liked delete all
        likeRepository.deleteAllByLikedArchive(
            Archive.builder().id(archiveId).build());

        // archive image delete all
        archiveImageRepository.deleteAll(archive.getArchiveImages());
        userVisitPlaceRepository.deleteAll(archive.getUserVisitPlaces());
        archiveRepository.delete(archive);
    }
}