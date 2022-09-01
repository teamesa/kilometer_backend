CREATE TABLE IF NOT EXISTS key_visual
(
    id              BIGINT(20)      NOT NULL AUTO_INCREMENT,

    imageUrl        VARCHAR(3000)   NOT NULL,
    upperTitle      VARCHAR(255)    NULL,
    lowerTitle      VARCHAR(255)    NULL,
    linkUrl         VARCHAR(3000)   NULL,

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