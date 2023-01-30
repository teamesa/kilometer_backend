package com.kilometer.domain.archive.like;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByLikedArchiveEntityAndLikedUser(ArchiveEntity archiveEntityId, User userId);

    void deleteAllByLikedArchiveEntity(ArchiveEntity likedArchiveEntity);
}
