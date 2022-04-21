DROP TABLE IF EXISTS account;
CREATE TABLE account
(
    id     serial,
    name   character varying(100) NOT NULL,
    email  character varying(100) NOT NULL,
    amount bigint                 NOT NULL,
    CONSTRAINT account_pk PRIMARY KEY (id)
);
CREATE TABLE transfer
(
    id           uuid      NOT NULL,
    account_from bigint    NOT NULL,
    account_to   bigint    NOT NULL,
    amount   bigint    NOT NULL,
    date         timestamp NOT NULL,
    CONSTRAINT transfer_pk PRIMARY KEY (id),
    CONSTRAINT transfer_from_fk FOREIGN KEY (account_from) REFERENCES account (id),
    CONSTRAINT transfer_to_fk FOREIGN KEY (account_to) REFERENCES account (id)
);
INSERT INTO account
VALUES (DEFAULT, 'John Smith', '555@gmail.com', 1000);
INSERT INTO account
VALUES (DEFAULT, 'Jane Doe', '777@gmail.com', 2000);