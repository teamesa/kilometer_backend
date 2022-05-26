CREATE TABLE IF NOT EXISTS archive_photo
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
    userId   BIGINT(20)   NULL,
    imageUrl   VARCHAR(255) NOT NULL

    PRIMARY KEY (id),
    CONSTRAINT archive_photo-user-fk FOREIGN KEY (userId) REFERENCES user (id)
    )