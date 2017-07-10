create table SID (
    ID int not null,
    TITLE varchar(200),
    AUTHOR varchar(200),
    RELEASE varchar(200),
    NO_SUBTUNES int,
    PREFERRED_MODEL varchar(20)
);

create table SID_FILES (
    ID int not null,
    PATH varchar(200),
    FILE_NAME varchar(200)
);

create table TYPE (
    ID int not null,
    TYPE varchar(20) not null
);

create table SID_IDX (
    ID int not null,
    TYPE_ID int not null,
    VALUE varchar(500) not null
);

create table SID_SONGLENGTHS (
	ID int not null,
	NO_SUBTUNE int not null,
	LENGTH varchar(20) not null
);

ALTER TABLE PUBLIC.SID ADD CONSTRAINT SID_ID_pk PRIMARY KEY (ID);
ALTER TABLE PUBLIC.SID_FILES ADD FILE BLOB NULL;
ALTER TABLE PUBLIC.SID_FILES ADD CONSTRAINT SID_FILES_ID_pk PRIMARY KEY (ID);
ALTER TABLE PUBLIC.TYPE ADD CONSTRAINT TYPE_ID_pk PRIMARY KEY (ID);
ALTER TABLE PUBLIC.SID_IDX ADD CONSTRAINT SID_IDX_ID_pk PRIMARY KEY (ID);
