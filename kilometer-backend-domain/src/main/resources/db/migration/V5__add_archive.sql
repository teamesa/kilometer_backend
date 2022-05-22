CREATE TABLE IF NOT EXISTS archive
(
    id    BIGINT(20) NOT NULL AUTO_INCREMENT,
    userId BIGINT(20) NULL,
    itemId BIGINT(20) NULL,
    comment VARCHAR(1000) NULL,
    starRating INT NOT NULL DEFAULT 5,
    heartCount INT NOT NULL DEFAULT 0,
    isVisibleAtItem BOOLEAN NOT NULL DEFAULT TRUE,
    createdAt DATETIME NOT NULL DEFAULT current_timestamp,
    updatedAt DATETIME NOT NULL DEFAULT current_timestamp,

    primary key (id),
    CONSTRAINT archive_user_fk FOREIGN KEY (userId) REFERENCES user (id),
    CONSTRAINT archive_item_fk FOREIGN KEY (itemId) REFERENCES item_entity (id)
)