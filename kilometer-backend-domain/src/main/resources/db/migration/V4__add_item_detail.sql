CREATE TABLE IF NOT EXISTS item_detail
(
    id        BIGINT(20)    NOT NULL AUTO_INCREMENT,
    item      BIGINT(20)    NULL,
    introduce VARCHAR(3000) NOT NULL,

    createdAt DATETIME      NOT NULL DEFAULT current_timestamp,
    updatedAt DATETIME      NOT NULL DEFAULT current_timestamp,

    PRIMARY KEY (id),
    CONSTRAINT item_detail_item_fk FOREIGN KEY (item) REFERENCES item (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);