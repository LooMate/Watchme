

CREATE TABLE IF NOT EXISTS "role"
(
    id                  BIGINT              NOT NULL,
    name                VARCHAR(20)         NOT NULL,

     UNIQUE (name)
);

INSERT INTO "role" VALUES (1, 'USER')  ON CONFLICT DO NOTHING;
INSERT INTO "role" VALUES (2, 'MODER') ON CONFLICT DO NOTHING;
INSERT INTO "role" VALUES (3, 'ADMIN') ON CONFLICT DO NOTHING;



CREATE TABLE IF NOT EXISTS "user_roles"
(
    userId              BIGINT          PRIMARY KEY         NOT NULL,
    username            VARCHAR(35)                         NOT NULL,
    authoritiesArray    BIGINT ARRAY                        NOT NULL,

    UNIQUE(userId),
    UNIQUE(username),
    FOREIGN KEY(userId) REFERENCES "user" (id) ON DELETE CASCADE
)