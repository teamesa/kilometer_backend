package com.kilometer.domain.like;

import com.kilometer.domain.archive.Archive;
import com.kilometer.domain.archive.ArchiveService;
import com.kilometer.domain.like.dto.LikeResponse;
import com.kilometer.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ArchiveService archiveService;
    @Transactional
    public LikeResponse makeLikeStatus(Long archiveId, Long userId, boolean status) {
        Preconditions.notNull(archiveId, "Archive id must not be null");
        Preconditions.notNull(userId, "User id must not be null");

        Archive likedArchive = Archive.builder().id(archiveId).build();
        User likedUser = User.builder().id(userId).build();

        Like like = likeRepository.findByLikedArchiveAndLikedUser(likedArchive, likedUser)
            .map(it -> it.changeIsHearted(status))
            .orElse(
                Like.builder()
                    .isHearted(status)
                    .likedArchive(likedArchive)
                    .likedUser(likedUser)
                    .build()
            );

        likeRepository.save(like);

        archiveService.updateArchiveLikeCount(status, archiveId);

        return LikeResponse.builder()
            .content(like.isHearted())
            .build();
    }
}
