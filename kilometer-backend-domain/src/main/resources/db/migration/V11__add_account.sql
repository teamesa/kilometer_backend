CREATE TABLE IF NOT EXISTS account
(
    id          BIGINT(20)      NOT NULL AUTO_INCREMENT,
    username    VARCHAR(255)    NOT NULL,
    password    VARCHAR(255)    NOT NULL,
    role        VARCHAR(255)    NOT NULL,
    createdAt   DATETIME        NOT NULL DEFAULT current_timestamp,
    updatedAt   DATETIME        NOT NULL DEFAULT current_timestamp,
    PRIMARY KEY (id)
);