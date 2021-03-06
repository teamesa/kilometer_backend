CREATE TABLE IF NOT EXISTS item
(
    id                BIGINT(20)    NOT NULL AUTO_INCREMENT,

    exposureType      VARCHAR(255)  NOT NULL,
    exhibitionType    VARCHAR(255)  NOT NULL,
    regionType        VARCHAR(255)  NOT NULL,
    feeType           VARCHAR(255)  NOT NULL,

    listImageUrl      VARCHAR(3000) NOT NULL,
    thumbnailImageUrl VARCHAR(3000) NOT NULL,
    title             VARCHAR(255)  NOT NULL,
    placeName         VARCHAR(255)  NOT NULL,

    latitude          DOUBLE        NULL,
    longitude         DOUBLE        NULL,
    price             VARCHAR(500)  NULL,
    homepageUrl       VARCHAR(3000) NULL,
    ticketUrl         VARCHAR(3000) NULL,
    operatingTime     VARCHAR(500)  NULL,

    pickCount         INT           NOT NULL DEFAULT 0,

    startDate         DATE          NOT NULL,
    endDate           DATE          NOT NULL,

    createdAt         datetime      NOT NULL DEFAULT current_timestamp,
    updatedAt         datetime      NOT NULL DEFAULT current_timestamp,

    isDeleted         TINYINT(1)    NOT NULL DEFAULT FALSE,

    PRIMARY KEY (id)
);
