CREATE TABLE IF NOT EXISTS pick
(
    id                BIGINT(20)      NOT NULL AUTO_INCREMENT,
    pickedUser        BIGINT(20)      NOT NULL,
    pickedItem        BIGINT(20)      NOT NULL,
    isHearted         TINYINT(1)      NOT NULL DEFAULT FALSE,
    createdAt         DATETIME        NOT NULL DEFAULT current_timestamp,
    updatedAt         DATETIME        NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT user_fk FOREIGN KEY (pickedUser) REFERENCES user (id),
    CONSTRAINT item_fk FOREIGN KEY (pickedItem) REFERENCES item_entity (id)
);