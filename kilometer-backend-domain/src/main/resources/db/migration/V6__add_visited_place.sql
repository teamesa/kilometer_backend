CREATE TABLE IF NOT EXISTS visited_place
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
    archiveId   BIGINT(20)   NULL,
    placeType   VARCHAR(255) NOT NULL,
    placeName   VARCHAR(255) NOT NULL,
    address     VARCHAR(255) NOT NULL,
    roadAddress VARCHAR(255) NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT visited_place_archive_fk FOREIGN KEY (archiveId) REFERENCES archive (id)
)