CREATE TABLE IF NOT EXISTS image_swipe
(
    id              BIGINT(20)      NOT NULL AUTO_INCREMENT,

    item            BIGINT(20)      NULL,
    exhibitSequence VARCHAR(255)    NULL,

    updateAccount   VARCHAR(255)    NULL,
    updatedAt       DATETIME        NOT NULL DEFAULT current_timestamp,

    PRIMARY KEY (id),
    CONSTRAINT image_swipe_item_fk FOREIGN KEY (item) REFERENCES item (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 1, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 2, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 3, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 4, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 5, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 6, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 7, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 8, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 9, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 10, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 11, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 12, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 13, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 14, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 15, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 16, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 17, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 18, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 19, null);
INSERT INTO image_swipe(item, exhibitSequence, updateAccount) VALUES (null, 20, null);