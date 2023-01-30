package com.kilometer.domain.archive.like;

import com.google.common.base.Preconditions;
import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.like.dto.LikeDto;
import com.kilometer.domain.user.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;

    public LikeDto findByArchiveIdAndUserId(Long archiveId, Long userId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null");
        Preconditions.checkNotNull(userId, "User id must not be null");

        ArchiveEntity likedArchiveEntity = ArchiveEntity.builder().id(archiveId).build();
        User likedUser = User.builder().id(userId).build();

        return LikeDto.of(likeRepository.findByLikedArchiveEntityAndLikedUser(likedArchiveEntity, likedUser)
            .orElseGet(() -> newLike(likedArchiveEntity, likedUser)));
    }

    @Transactional
    public void deleteAll(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null");
        ArchiveEntity archiveEntity = ArchiveEntity.builder().id(archiveId).build();
        likeRepository.deleteAllByLikedArchiveEntity(archiveEntity);
    }

    private Like newLike(ArchiveEntity archiveEntity, User user) {
        Like like = Like.builder().isLiked(false).likedArchiveEntity(archiveEntity).likedUser(user).build();
        return likeRepository.save(like);
    }

    @Transactional
    public LikeDto updateLikeStatus(boolean status, Long likeId, Long archiveId, Long userId) {
        ArchiveEntity likedArchiveEntity = ArchiveEntity.builder().id(archiveId).build();
        User likedUser = User.builder().id(userId).build();

        Like like = Like.builder().id(likeId).isLiked(status).likedArchiveEntity(likedArchiveEntity)
            .likedUser(likedUser).build();
        return LikeDto.of(likeRepository.save(like));
    }
}
