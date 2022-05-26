CREATE TABLE IF NOT EXISTS archive_photo
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
    archiveId   BIGINT(20)   NULL,
    imageUrl   VARCHAR(255) NOT NULL

    PRIMARY KEY (id),
    CONSTRAINT archive_photo-archive-fk FOREIGN KEY (archiveId) REFERENCES archive (id)
    )