package com.kilometer.domain.archive.like;

import com.kilometer.domain.archive.Archive;
import com.kilometer.domain.archive.like.dto.LikeDto;
import com.kilometer.domain.archive.like.dto.LikeResponse;
import com.kilometer.domain.user.User;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeDto findByArchiveIdAndUserId(Long archiveId, Long userId) {
        Preconditions.notNull(archiveId, "Archive id must not be null");
        Preconditions.notNull(userId, "User id must not be null");

        Archive likedArchive = Archive.builder().id(archiveId).build();
        User likedUser = User.builder().id(userId).build();

        return LikeDto.of(likeRepository.findByLikedArchiveAndLikedUser(likedArchive, likedUser)
            .orElse(newLike(likedArchive, likedUser)));
    }

    @Transactional
    public void deleteAll(Long archiveId) {
        Preconditions.notNull(archiveId, "Archive id must not be null");
        Archive archive = Archive.builder().id(archiveId).build();
        likeRepository.deleteAllByLikedArchive(archive);
    }

    private Like newLike(Archive archive, User user) {
        Like like = Like.builder().isLiked(false).likedArchive(archive).likedUser(user).build();
        return likeRepository.save(like);
    }

    @Transactional
    public LikeDto updateLikeStatus(boolean status, Long likeId) {
        Like like = Like.builder().id(likeId).isLiked(status).build();
        return LikeDto.of(likeRepository.save(like));
    }
}
