CREATE TABLE IF NOT EXISTS module
(
    id                  BIGINT(20)    NOT NULL AUTO_INCREMENT,
    exposureOrderNumber INT           NOT NULL,
    moduleName          VARCHAR(255)  NOT NULL,
    upperModuleTitle    VARCHAR(255)  NULL,
    lowerModuleTitle    VARCHAR(255)  NULL,
    extraData           VARCHAR(1000) NULL,

    isDelete            TINYINT(1)    NOT NULL DEFAULT false,
    createdAccount      BIGINT(20)    NULL,
    createdAt           DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    CONSTRAINT module_account_fk FOREIGN KEY (createdAccount) REFERENCES account (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);