CREATE TABLE IF NOT EXISTS image_swipe
(
    id                  BIGINT(20)      NOT NULL AUTO_INCREMENT,

    itemEntityId        BIGINT(20)      NULL,
    exposureOrderNumber VARCHAR(255)    NOT NULL,

    createdAccount      VARCHAR(255)    NULL,
    createdAt           DATETIME        NOT NULL DEFAULT current_timestamp,

    PRIMARY KEY (id),
    CONSTRAINT image_swipe_item_fk FOREIGN KEY (itemEntityId) REFERENCES item (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 1, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 2, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 3, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 5, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 4, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 6, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 7, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 8, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 9, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 10, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 11, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 12, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 13, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 14, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 15, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 16, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 17, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 18, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 19, null);
INSERT INTO image_swipe(itemEntityId, exposureOrderNumber, createdAccount) VALUES (null, 20, null);