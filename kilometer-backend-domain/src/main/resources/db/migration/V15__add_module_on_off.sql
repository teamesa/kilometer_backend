CREATE TABLE IF NOT EXISTS module_on_off
(
    id                  BIGINT(20)      NOT NULL AUTO_INCREMENT,

    moduleName          VARCHAR(255)    NOT NULL,
    isExposure          BOOLEAN         NOT NULL DEFAULT FALSE,

    createdAccount      VARCHAR(255)    NULL,
    createdAt           DATETIME        NOT NULL DEFAULT current_timestamp,

    PRIMARY KEY (id)
);

INSERT INTO module_on_off(moduleName) VALUES ("key-visual");
INSERT INTO module_on_off(moduleName) VALUES ("swipe-item");
INSERT INTO module_on_off(moduleName) VALUES ("monthly-free-item");
INSERT INTO module_on_off(moduleName) VALUES ("real-time-archive");
INSERT INTO module_on_off(moduleName) VALUES ("upcomming-item");
