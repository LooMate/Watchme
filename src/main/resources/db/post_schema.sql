


DROP TABLE "postAnalyticsSnap" IF EXISTS;

DROP SEQUENCE "post_analytics_snap_id_seq" IF EXISTS;

CREATE SEQUENCE "post_analytics_snap_id_seq" START WITH 0;

CREATE TABLE IF NOT EXISTS "postAnalyticsSnap"(
    id                      BIGINT          NOT NULL DEFAULT nextval('post_analytics_snap_id_seq'),
    createAt                TIMESTAMP       NOT NULL,
    lastChangedAt           TIMESTAMP       NOT NULL,
    timeZone                VARCHAR(35)     NOT NULL,
    numOfSpreads            BIGINT          NOT NULL,
    viewedNum               BIGINT          NOT NULL,
    viewedByReferralNum     BIGINT          NOT NULL,
    viewersId               BIGINT ARRAY    NOT NULL,
    refViewersId            BIGINT ARRAY    NOT NULL,
    postRates               BIGINT          NOT NULL,

    PRIMARY KEY (id)
);



DROP TABLE "post" IF EXISTS;

DROP SEQUENCE "post_id_seq" IF EXISTS;

CREATE SEQUENCE "post_id_seq" START WITH 0;

CREATE TABLE IF NOT EXISTS "post" (
    id                      BIGINT          NOT NULL DEFAULT nextval('post_id_seq'),
    createdAt               TIMESTAMP       NOT NULL,
    lastChangedAt           TIMESTAMP       NOT NULL,
    timeZone                VARCHAR(35)     NOT NULL,
    postName                VARCHAR(60)     NOT NULL,
    postLink                VARCHAR(60)     NOT NULL,
    priority                NUMERIC(3)      NOT NULL,
    isExclusive             BOOLEAN         NOT NULL,
    usersIdWithAccess       BIGINT ARRAY    NOT NULL,
    isHot                   BOOLEAN         NOT NULL,
    previewImage            BYTEA           NOT NULL,
    postAnalyticsInfoId     BIGINT          NOT NULL,
    postAnalyticsSnapId     BIGINT          NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (postAnalyticsSnapId) REFERENCES "postAnalyticsSnap" (id)
);

DROP TABLE "postAnalyticsInfo" IF EXISTS;

DROP SEQUENCE "post_analytics_info_id_seq" IF EXISTS;

CREATE SEQUENCE "post_analytics_info_id_seq" START WITH 0;

CREATE TABLE IF NOT EXISTS "postAnalyticsInfo"(
    id                      BIGINT          NOT NULL DEFAULT nextval('post_analytics_info_id_seq'),
    createAt                TIMESTAMP       NOT NULL,
    lastChangedAt           TIMESTAMP       NOT NULL,
    isActive                BOOLEAN         NOT NULL,
    analyticsFrequencyInHour NUMERIC(2)     NOT NULL,
    postId                  BIGINT          NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (postId) REFERENCES "post" (id)
);

DROP TABLE "postDetails" IF EXISTS;

DROP SEQUENCE "post_details_id_seq" IF EXISTS;

CREATE SEQUENCE "post_details_id_seq" START WITH 0;

CREATE TABLE IF NOT EXISTS "postDetails"(
    id                      BIGINT          NOT NULL DEFAULT nextval('post_details_id_seq'),
    createdAt               TIMESTAMP       NOT NULL,
    lastChangedAt           TIMESTAMP       NOT NULL,
    description             VARCHAR         NOT NULL,
    image                   BYTEA           NOT NULL,
    postId                  BIGINT          NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (postId) REFERENCES "post" (id)
);



