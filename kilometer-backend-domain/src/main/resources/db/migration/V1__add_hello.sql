CREATE TABLE IF NOT EXISTS hello_entity
(
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    service   VARCHAR(255)  NULL,
    memo      VARCHAR(1000) NULL,
    createdAt DATETIME      NULL,
    PRIMARY KEY (id)
);