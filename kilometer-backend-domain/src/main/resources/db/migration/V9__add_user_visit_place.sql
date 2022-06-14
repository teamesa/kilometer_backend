CREATE TABLE IF NOT EXISTS user_visit_place
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
    archive     BIGINT(20)   NULL,
    placeType   VARCHAR(255) NOT NULL DEFAULT 'FOOD',
    placeName   VARCHAR(255) NOT NULL,
    address     VARCHAR(255) NOT NULL,
    roadAddress VARCHAR(255) NOT NULL,

    createdAt   DATETIME     NOT NULL DEFAULT current_timestamp,
    updatedAt   DATETIME     NOT NULL DEFAULT current_timestamp,

    isDeleted   TINYINT(1)   NOT NULL DEFAULT FALSE,

    PRIMARY KEY (id),
    CONSTRAINT archive_visit_place_archive_fk FOREIGN KEY (archive) REFERENCES archive (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

