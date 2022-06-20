CREATE TABLE IF NOT EXISTS archive
(
    id              BIGINT(20)    NOT NULL AUTO_INCREMENT,
    user            BIGINT(20)    NULL,
    item            BIGINT(20)    NULL,

    comment         VARCHAR(1000) NULL,
    starRating      INT           NOT NULL DEFAULT 5,
    heartCount      INT           NOT NULL DEFAULT 0,

    isVisibleAtItem TINYINT(1)    NOT NULL DEFAULT TRUE,

    createdAt       DATETIME      NOT NULL DEFAULT current_timestamp,
    updatedAt       DATETIME      NOT NULL DEFAULT current_timestamp,

    isDeleted       TINYINT(1)    NOT NULL DEFAULT FALSE,

    primary key (id),
    CONSTRAINT archive_user_fk FOREIGN KEY (user) REFERENCES user (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT archive_item_fk FOREIGN KEY (item) REFERENCES item (id) ON DELETE NO ACTION ON UPDATE NO ACTION
)