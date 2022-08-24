package com.kilometer.domain.like;

import com.kilometer.domain.archive.Archive;
import com.kilometer.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByLikedArchiveAndLikedUser(Archive archiveId, User userId);

    void deleteAllByLikedArchive(Archive likedArchive);
}
