create table crawled_item
(
    id                BIGINT(20)   NOT NULL AUTO_INCREMENT,
    exposureType      VARCHAR(255) NOT NULL,
    exhibitionType    VARCHAR(255) NOT NULL,
    regionType        VARCHAR(255) NOT NULL,
    feeType           VARCHAR(255) NOT NULL,

    listImageUrl      VARCHAR(255) NOT NULL,
    thumbnailImageUrl VARCHAR(255) NOT NULL,
    title             VARCHAR(255) NOT NULL,
    placeName         VARCHAR(255) NOT NULL,

    latitude          double,
    longitude         double,
    price             VARCHAR(255),
    homepageUrl       VARCHAR(3000),
    ticketUrl         VARCHAR(3000),
    operatingTime     VARCHAR(500),

    endDate           DATE NOT NULL,
    startDate         DATE NOT NULL,
    introduce         VARCHAR(255),
    source            VARCHAR(255),

    createdAt         datetime NOT NULL DEFAULT current_timestamp,
    isDeleted         TINYINT(1) NOT NULL DEFAULT FALSE,
    updatedAt         datetime NOT NULL DEFAULT current_timestamp,

    primary key (id)
);

create table crawled_item_image
(
    id          BIGINT(20)   NOT NULL AUTO_INCREMENT,

    url         VARCHAR(255),
    crawledItem bigint,

    createdAt   datetime NOT NULL DEFAULT current_timestamp,
    isDeleted   TINYINT(1) NOT NULL,
    updatedAt   datetime NOT NULL DEFAULT current_timestamp,

    primary key (id),
    CONSTRAINT crawled_item_fk FOREIGN KEY (crawledItem) REFERENCES crawled_item (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
