CREATE TABLE IF NOT EXISTS user
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    imageUrl    VARCHAR(255) NULL,
    role        VARCHAR(255) NOT NULL DEFAULT 'USER',
    phoneNumber VARCHAR(255) NULL,
    birthdate   DATETIME     NULL,
    gender      VARCHAR(255) NULL,
    provider    VARCHAR(255) NULL,
    providerId  VARCHAR(255) NULL,
    PRIMARY KEY (id)
);