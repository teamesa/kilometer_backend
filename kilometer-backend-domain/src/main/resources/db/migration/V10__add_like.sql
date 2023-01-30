CREATE TABLE IF NOT EXISTS archive_like
(
    id           BIGINT(20) NOT NULL AUTO_INCREMENT,
    likedUser    BIGINT(20) NOT NULL,
    likedArchiveEntity BIGINT(20) NOT NULL,
    isLiked    TINYINT(1) NOT NULL DEFAULT FALSE,
    createdAt    DATETIME   NOT NULL DEFAULT current_timestamp,
    updatedAt    DATETIME   NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT like_user_fk FOREIGN KEY (likedUser) REFERENCES user (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT like_archive_fk FOREIGN KEY (likedArchiveEntity) REFERENCES archiveEntity (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);