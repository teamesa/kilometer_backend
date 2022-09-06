CREATE TABLE IF NOT EXISTS key_visual
(
    id              BIGINT(20)      NOT NULL AUTO_INCREMENT,

    imageUrl        VARCHAR(255)   NOT NULL,
    upperTitle      VARCHAR(255)    NULL,
    lowerTitle      VARCHAR(255)    NULL,
    linkUrl         VARCHAR(255)   NULL,

    createdAccount  VARCHAR(255)    NULL,
    createdAt       DATETIME        NOT NULL DEFAULT current_timestamp,

    PRIMARY KEY (id)
);

INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);
INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);
INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);
INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);
INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);
INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);
INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);
INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);
INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);
INSERT INTO key_visual(imageUrl, upperTitle, lowerTitle, linkUrl, createdAccount) VALUES ("", null, null, null, null);

ALTER TABLE item MODIFY listImageUrl VARCHAR(255);
ALTER TABLE item MODIFY thumbnailImageUrl VARCHAR(255);
ALTER TABLE item MODIFY homepageUrl VARCHAR(255);
ALTER TABLE item MODIFY ticketUrl VARCHAR(255);