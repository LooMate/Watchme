CREATE SEQUENCE IF NOT EXISTS "user_id_seq";

CREATE TABLE IF NOT EXISTS "user"(
    id                      BIGINT              NOT NULL DEFAULT nextval('user_id_seq'),
    createdAt               TIMESTAMP           NOT NULL,
    lastChangedAt           TIMESTAMP           NOT NULL,
    timeZone                VARCHAR(35)         NOT NULL,
    username                VARCHAR(35)         NOT NULL,
    password                VARCHAR(70)         NOT NULL,
    email                   VARCHAR(40)         NOT NULL,
    usedReferralCode        VARCHAR(25)         ,
    referralCode            VARCHAR(25)         ,
    postsIdsList            BIGINT  ARRAY       NOT NULL,
    invitedUsersIdsList     BIGINT  ARRAY       NOT NULL,
    subscribersIdsList      BIGINT  ARRAY       NOT NULL,
    subscribedPublishersIdsList  BIGINT  ARRAY    NOT NULL,
    isInvited               BOOLEAN             NOT NULL,
    invitedFrom             VARCHAR(20)         ,
    isPremium               BOOLEAN             NOT NULL,
    isEnabled               BOOLEAN             NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (username)
);


CREATE SEQUENCE IF NOT EXISTS user_web_details_id_seq;

CREATE TABLE IF NOT EXISTS "userWebDetails"(
    id                      BIGINT              NOT NULL DEFAULT nextval('user_web_details_id_seq'),
    createdAt               TIMESTAMP           ,
    lastChangedAt           TIMESTAMP           ,
    userBrowser             VARCHAR(50)         ,
    browserLanguage         VARCHAR(50)         ,
    operationSystem         VARCHAR(50)         ,
    location                VARCHAR(30)         ,
    screenSizeAndColorDepth VARCHAR(25)         ,
    timeZoneOffset          INT                 ,
    timeZone                VARCHAR(35)         ,
    webVendorAndRenderGpu   VARCHAR(30)         ,
    cpuName                 VARCHAR(30)         ,
    cpuCoreNum              NUMERIC(4)          ,
    deviceMemory            NUMERIC(4)          ,
    touchSupport            BOOLEAN             ,
    adBlockerUsed           BOOLEAN             ,
    userId                  BIGINT              NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (userId),
    FOREIGN KEY (userId) REFERENCES "user"(id)
);


CREATE SEQUENCE IF NOT EXISTS "user_telegram_details_id_seq";

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
    UNIQUE (userId),
    FOREIGN KEY (userId) REFERENCES "user" (id)
);


CREATE SEQUENCE IF NOT EXISTS "telegram_channel_details_id_seq";

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
    UNIQUE (userId),
    FOREIGN KEY (userId) REFERENCES "user" (id)
)



