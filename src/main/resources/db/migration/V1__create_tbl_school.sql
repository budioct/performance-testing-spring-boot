CREATE SEQUENCE IF NOT EXISTS school_id_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE tbl_school
(
    id      BIGINT       NOT NULL,
    name    VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    phone   VARCHAR(12)  NOT NULL,
    CONSTRAINT pk_tbl_school PRIMARY KEY (id)
);