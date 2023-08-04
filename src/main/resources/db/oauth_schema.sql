

CREATE SEQUENCE IF NOT EXISTS user_ggAcc_id_seq;

CREATE TABLE IF NOT EXISTS "user_ggAcc"(

    googleId            VARCHAR(30)     NOT NULL    DEFAULT nextval('user_ggAcc_id_seq'),
    userId              BIGINT          NOT NULL,

    PRIMARY KEY(googleId),
    FOREIGN KEY(userId) REFERENCES "user"(id)
)


