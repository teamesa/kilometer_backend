CREATE TABLE IF NOT EXISTS item_detail
(
    id              BIGINT(20)      NOT NULL AUTO_INCREMENT,
    introduce       VARCHAR(3000)   NULL,
    createdAt       DATETIME        NOT NULL DEFAULT current_timestamp,
    updatedAt        DATETIME        NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS detail_image
(
    id                BIGINT(20)      NOT NULL AUTO_INCREMENT,
    itemDetailEntity  BIGINT(20)      NULL,
    url               VARCHAR(3000)   NULL,
    createdAt         DATETIME        NOT NULL DEFAULT current_timestamp,
    updateAt          DATETIME        NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (id),
    CONSTRAINT detail_image_fk FOREIGN KEY (itemDetailEntity) REFERENCES item_detail (id)
);

ALTER TABLE item_entity ADD exhibitionType VARCHAR(255) NULL;
ALTER TABLE item_entity ADD itemDetailEntity BIGINT NULL;
ALTER TABLE item_entity ADD CONSTRAINT item_detail_fk FOREIGN KEY (itemDetailEntity) REFERENCES item_detail (id);
ALTER TABLE item_entity MODIFY price varchar(500);