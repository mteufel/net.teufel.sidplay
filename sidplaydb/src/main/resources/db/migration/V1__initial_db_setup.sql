create table SID (
    ID int not null,
    FILE_ID int not null,
    TITLE varchar(200),
    AUTHOR varchar(200),
    RELEASE varchar(100),
    NO_SUBTUNES int,
    PREFERRED_MODEL varchar(20)
);

create table SID_FILES (
    ID int not null,
    PATH varchar(200),
    FILE_NAME varchar(50)
);

create table TYPE (
    ID int not null,
    TYPE varchar(20) not null
);

create table SID_INDEX (
    SID_ID int not null,
    TYPE_ID int not null,
    VALUE varchar(200) not null
);

ALTER TABLE PUBLIC.SID ADD CONSTRAINT SID_ID_pk PRIMARY KEY (ID);
ALTER TABLE PUBLIC.SID_FILES ADD FILE BLOB NULL;
ALTER TABLE PUBLIC.SID_FILES ADD CONSTRAINT SID_FILES_ID_pk PRIMARY KEY (ID);
