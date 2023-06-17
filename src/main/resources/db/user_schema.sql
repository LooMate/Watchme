
DROP TABLE "user" IF EXISTS;

DROP SEQUENCE "user_id_seq" IF EXISTS;

CREATE SEQUENCE "user_id_seq" START WITH 0;

CREATE TABLE IF NOT EXISTS "user"(
    id                      BIGINT              NOT NULL DEFAULT nextval('user_id_seq'),
    createdAt               TIMESTAMP           NOT NULL,
    lastChangedAt           TIMESTAMP           NOT NULL,
    timeZone                VARCHAR(35)         NOT NULL,
    username                VARCHAR(35)         NOT NULL,
    email                   VARCHAR(40)         NOT NULL,
    usedReferralCode        VARCHAR(25)         ,
    referalCode             VARCHAR(25)         NOT NULL,
    postIdList              BIGINT  ARRAY       NOT NULL,
    invitedUsersIdList      BIGINT  ARRAY       NOT NULL,
    subscribersIdList       BIGINT  ARRAY       NOT NULL,
    subscribedPublisherIdList  BIGINT  ARRAY    NOT NULL,
    isInvited               BOOLEAN             NOT NULL,
    invitedFrom             VARCHAR(20)         NOT NULL,
    isPremium               BOOLEAN             NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (username)
);




DROP TABLE "userWebDetails" IF EXISTS;

DROP SEQUENCE user_web_details_id_seq IF EXISTS;

CREATE SEQUENCE user_web_details_id_seq START WITH 0;

CREATE TABLE IF NOT EXISTS "userWebDetails"(
    id                      BIGINT              NOT NULL DEFAULT nextval('user_web_details_id_seq'),
    createdAt               TIMESTAMP           NOT NULL,
    lastChangedAt           TIMESTAMP           NOT NULL,
    userBrowser             VARCHAR(50)         NOT NULL,
    browserLanguage         VARCHAR(50)         NOT NULL,
    operationSystem         VARCHAR(50)         NOT NULL,
    location                VARCHAR(30)         NOT NULL,
    screenSizeAndColorDepth VARCHAR(25)         NOT NULL,
    timeZoneOffset          INT                 NOT NULL,
    timeZone                VARCHAR(35)         NOT NULL,
    webVendorAndRenderGpu   VARCHAR(30)         NOT NULL,
    cpuName                 VARCHAR(15)         NOT NULL,
    cpuCoreNum              NUMERIC(4)          NOT NULL,
    deviceMemory            NUMERIC(4)          NOT NULL,
    touchSupport            BOOLEAN             NOT NULL,
    adBlockerUsed           BOOLEAN             NOT NULL,
    userId                  BIGINT              NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES "user"(id)
);

DROP TABLE "userTelegramDetails" IF EXISTS;

DROP SEQUENCE "user_telegram_details_id_seq" IF EXISTS;


CREATE SEQUENCE "user_telegram_details_id_seq" START WITH 0;

CREATE TABLE IF NOT EXISTS "userTelegramDetails"(
    id                      BIGINT              NOT NULL DEFAULT nextval('user_telegram_details_id_seq'),
    createdAt               TIMESTAMP           NOT NULL,
    lastChangedAt           TIMESTAMP           NOT NULL,
    timeZone                VARCHAR(35)         NOT NULL,
    firstName               VARCHAR(20)         NOT NULL,
    lastName                VARCHAR(20)         NOT NULL,
    username                VARCHAR(20)         NOT NULL,
    chatId                  BIGINT              NOT NULL,
    isReachableByPrivateForwards BOOLEAN        NOT NULL,
    isPremium               BOOLEAN             NOT NULL,
    userId                  BIGINT              NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES "user" (id)
);


DROP TABLE "telegramChannelDetails" IF EXISTS;
DROP SEQUENCE "telegram_channel_details_id_seq" IF EXISTS;


CREATE SEQUENCE "telegram_channel_details_id_seq" START WITH 0;

CREATE TABLE IF NOT EXISTS "telegramChannelDetails"(
    id                      BIGINT              NOT NULL DEFAULT nextval('telegram_channel_details_id_seq'),
    createdAt               TIMESTAMP           NOT NULL,
    lastChangedAt           TIMESTAMP           NOT NULL,
    timeZone                VARCHAR(35)         NOT NULL,
    channelName             VARCHAR(40)         NOT NULL,
    channelBio              VARCHAR(65)         NOT NULL,
    channelLink             VARCHAR(55)         NOT NULL,
    numberOfMembers         NUMERIC(10)         NOT NULL,
    typeOfChat              VARCHAR(20)         NOT NULL,
    adminsOfChannelIds      BIGINT ARRAY        NOT NULL,
    userId                  BIGINT              NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (userId) REFERENCES "user" (id)
)



