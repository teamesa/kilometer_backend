CREATE TABLE IF NOT EXISTS item_detail_entity
(
    detailId        BIGINT          NOT NULL AUTO_INCREMENT,
    introduce       VARCHAR(1000)   NULL,
    createdAt       DATETIME        NULL,
    updateAt        DATETIME        NULL,
    PRIMARY KEY (detailId)
);

ALTER TABLE item_entity ADD exhibitionType VARCHAR(255) NULL;
ALTER TABLE item_entity ADD itemDetailEntity BIGINT NULL;
ALTER TABLE item_entity ADD CONSTRAINT item_detail_fk FOREIGN KEY (itemDetailEntity) REFERENCES item_detail_entity (detailId);
