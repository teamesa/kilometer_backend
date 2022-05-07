CREATE TABLE IF NOT EXISTS hello_entity
(
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    service   VARCHAR(255)  NULL,
    memo      VARCHAR(1000) NULL,
    create_at DATETIME      NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS item_entity
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    progressType VARCHAR(255) NULL,
    image        VARCHAR(255) NULL,
    title        VARCHAR(255) NULL,
    term         VARCHAR(255) NULL,
    place        VARCHAR(255) NULL,
    latitude     DOUBLE       NULL,
    longitude    DOUBLE       NULL,
    regionType   VARCHAR(255) NULL,
    fee          VARCHAR(255) NULL,
    price        INTEGER      NULL,
    url          VARCHAR(255) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NULL,
    email VARCHAR(255) NOT NULL UNIQUE ,
    imageUrl VARCHAR(255) NULL,
    role VARCHAR(255) NULL,
    phoneNumber VARCHAR(255) NULL,
    birthdate DATETIME NULL,
    gender VARCHAR(255) NULL,
    provider VARCHAR(255) NULL,
    providerId VARCHAR(255) NULL,
    PRIMARY KEY (id)
);