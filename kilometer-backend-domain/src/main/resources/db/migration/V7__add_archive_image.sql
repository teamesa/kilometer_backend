CREATE TABLE IF NOT EXISTS archive_image
(
    id        BIGINT(20)    NOT NULL AUTO_INCREMENT,
    archive   BIGINT(20)    NULL,
    imageUrl  VARCHAR(3000) NOT NULL,

    createdAt DATETIME      NOT NULL DEFAULT current_timestamp,
    updatedAt DATETIME      NOT NULL DEFAULT current_timestamp,

    PRIMARY KEY (id),
    CONSTRAINT archive_image_archive_fk FOREIGN KEY (archive) REFERENCES archive (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);