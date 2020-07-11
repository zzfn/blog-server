create table T_ARTICLE
(
    ID          VARCHAR2(32) not null,
    TITLE       VARCHAR2(64),
    CONTENT     CLOB,
    VIEW_COUNT  NVARCHAR2(32),
    TAG         NVARCHAR2(32),
    CREATE_BY   VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATE_BY   VARCHAR2(32),
    UPDATE_TIME DATE,
    IS_DELETE   CHAR,
    primary key (ID)
)
/

comment on table T_ARTICLE is 'ARTICLE'
/

comment on column T_ARTICLE.ID is '主键'
/

comment on column T_ARTICLE.TITLE is '标题'
/

comment on column T_ARTICLE.CONTENT is '内容'
/

comment on column T_ARTICLE.VIEW_COUNT is '浏览量'
/

comment on column T_ARTICLE.TAG is '标签'
/

comment on column T_ARTICLE.CREATE_BY is '创建人'
/

comment on column T_ARTICLE.CREATE_TIME is '创建时间'
/

comment on column T_ARTICLE.UPDATE_BY is '更新人'
/

comment on column T_ARTICLE.UPDATE_TIME is '更新时间'
/

comment on column T_ARTICLE.IS_DELETE is '删除标识'
/

create table T_COMMENT
(
    ID          VARCHAR2(32) not null,
    ARTICLE_ID  NVARCHAR2(32),
    CONTENT     NVARCHAR2(32),
    USER_ID     NVARCHAR2(32),
    CREATE_BY   VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATE_BY   VARCHAR2(32),
    UPDATE_TIME DATE,
    IS_DELETE   CHAR,
    primary key (ID)
)
/

comment on table T_COMMENT is 'COMMENT'
/

comment on column T_COMMENT.ID is '主键'
/

comment on column T_COMMENT.ARTICLE_ID is '文章id'
/

comment on column T_COMMENT.CONTENT is '内容'
/

comment on column T_COMMENT.USER_ID is '用户id'
/

comment on column T_COMMENT.CREATE_BY is '创建人'
/

comment on column T_COMMENT.CREATE_TIME is '创建时间'
/

comment on column T_COMMENT.UPDATE_BY is '更新人'
/

comment on column T_COMMENT.UPDATE_TIME is '更新时间'
/

comment on column T_COMMENT.IS_DELETE is '删除标识'
/

create table T_USER
(
    ID          VARCHAR2(32) not null,
    USERNAME    NVARCHAR2(32),
    PASSWORD    NVARCHAR2(32),
    CREATE_BY   VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATE_BY   VARCHAR2(32),
    UPDATE_TIME DATE,
    IS_DELETE   CHAR,
    OPENID      VARCHAR2(64),
    primary key (ID)
)
/

comment on table T_USER is 'USER'
/

comment on column T_USER.ID is '主键'
/

comment on column T_USER.USERNAME is '用户名'
/

comment on column T_USER.PASSWORD is '密码'
/

comment on column T_USER.CREATE_BY is '创建人'
/

comment on column T_USER.CREATE_TIME is '创建时间'
/

comment on column T_USER.UPDATE_BY is '更新人'
/

comment on column T_USER.UPDATE_TIME is '更新时间'
/

comment on column T_USER.IS_DELETE is '删除标识'
/

create table T_REPLY
(
    ID          VARCHAR2(32) not null,
    CONTENT     NVARCHAR2(32),
    COMMENT_ID  NVARCHAR2(32),
    USER_ID     NVARCHAR2(32),
    CREATE_BY   VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATE_BY   VARCHAR2(32),
    UPDATE_TIME DATE,
    IS_DELETE   CHAR,
    primary key (ID)
)
/

comment on table T_REPLY is 'REPLY'
/

comment on column T_REPLY.ID is '主键'
/

comment on column T_REPLY.CONTENT is '回复内容'
/

comment on column T_REPLY.COMMENT_ID is '评论id'
/

comment on column T_REPLY.USER_ID is '用户id'
/

comment on column T_REPLY.CREATE_BY is '创建人'
/

comment on column T_REPLY.CREATE_TIME is '创建时间'
/

comment on column T_REPLY.UPDATE_BY is '更新人'
/

comment on column T_REPLY.UPDATE_TIME is '更新时间'
/

comment on column T_REPLY.IS_DELETE is '删除标识'
/

create table SYS_DICT_TYPE
(
    ID          VARCHAR2(32) not null,
    CODE        NVARCHAR2(32),
    NAME        NVARCHAR2(32),
    CREATE_BY   VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATE_BY   VARCHAR2(32),
    UPDATE_TIME DATE,
    IS_DELETE   CHAR,
    primary key (ID)
)
/

comment on table SYS_DICT_TYPE is 'DICT_TYPE'
/

comment on column SYS_DICT_TYPE.ID is '主键'
/

comment on column SYS_DICT_TYPE.CODE is '编码'
/

comment on column SYS_DICT_TYPE.NAME is '类型'
/

comment on column SYS_DICT_TYPE.CREATE_BY is '创建人'
/

comment on column SYS_DICT_TYPE.CREATE_TIME is '创建时间'
/

comment on column SYS_DICT_TYPE.UPDATE_BY is '更新人'
/

comment on column SYS_DICT_TYPE.UPDATE_TIME is '更新时间'
/

comment on column SYS_DICT_TYPE.IS_DELETE is '删除标识'
/

create table SYS_DICT
(
    ID          VARCHAR2(32) not null,
    CODE        NVARCHAR2(32),
    NAME        NVARCHAR2(32),
    TYPE_CODE   NVARCHAR2(32),
    CREATE_BY   VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATE_BY   VARCHAR2(32),
    UPDATE_TIME DATE,
    IS_DELETE   CHAR,
    primary key (ID)
)
/

comment on table SYS_DICT is 'DICT'
/

comment on column SYS_DICT.ID is '主键'
/

comment on column SYS_DICT.CODE is '编码'
/

comment on column SYS_DICT.NAME is '字典名'
/

comment on column SYS_DICT.TYPE_CODE is '类型code'
/

comment on column SYS_DICT.CREATE_BY is '创建人'
/

comment on column SYS_DICT.CREATE_TIME is '创建时间'
/

comment on column SYS_DICT.UPDATE_BY is '更新人'
/

comment on column SYS_DICT.UPDATE_TIME is '更新时间'
/

comment on column SYS_DICT.IS_DELETE is '删除标识'
/

create table T_SYS_CONFIG
(
    ID          VARCHAR2(32) not null,
    VALUE       NVARCHAR2(128),
    KEY         NVARCHAR2(128),
    CREATED_BY  VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATED_BY  VARCHAR2(32),
    UPDATE_TIME DATE,
    IS_DELETE   CHAR,
    primary key (ID)
)
/

comment on table T_SYS_CONFIG is 'T_SYS_CONFIG'
/

comment on column T_SYS_CONFIG.ID is '主键'
/

comment on column T_SYS_CONFIG.VALUE is '值'
/

comment on column T_SYS_CONFIG.KEY is '键'
/

comment on column T_SYS_CONFIG.CREATED_BY is '创建人'
/

comment on column T_SYS_CONFIG.CREATE_TIME is '创建时间'
/

comment on column T_SYS_CONFIG.UPDATED_BY is '更新人'
/

comment on column T_SYS_CONFIG.UPDATE_TIME is '更新时间'
/

create table T_HOME
(
    ID          VARCHAR2(32) not null,
    SUBWAY      NVARCHAR2(32),
    PRICE       NVARCHAR2(32),
    COMMUNITY   NVARCHAR2(32),
    CREATED_BY  VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATED_BY  VARCHAR2(32),
    UPDATE_TIME DATE,
    IS_DELETE   NVARCHAR2(32),
    primary key (ID)
)
/

comment on column T_HOME.ID is '主键'
/

comment on column T_HOME.SUBWAY is '地铁'
/

comment on column T_HOME.PRICE is '价格'
/

comment on column T_HOME.COMMUNITY is '小区'
/

comment on column T_HOME.CREATED_BY is '创建人'
/

comment on column T_HOME.CREATE_TIME is '创建时间'
/

comment on column T_HOME.UPDATED_BY is '更新人'
/

comment on column T_HOME.UPDATE_TIME is '更新时间'
/

comment on column T_HOME.IS_DELETE is '删除标识'
/

create table T_KEEP
(
    ID          VARCHAR2(32) not null,
    KEEP_TYPE   VARCHAR2(32),
    AMOUNT      NUMBER(32, 8),
    REMARK      NVARCHAR2(32),
    CREATED_BY  VARCHAR2(32),
    CREATE_TIME DATE,
    UPDATED_BY  VARCHAR2(32),
    UPDATE_TIME DATE,
    IS_DELETE   NVARCHAR2(32),
    TAG         VARCHAR2(255),
    constraint SYS_C007490
        primary key (ID),
    constraint SYS_C007489
        check ("ID" IS NOT NULL)
)
/

comment on column T_KEEP.ID is '主键'
/

comment on column T_KEEP.KEEP_TYPE is '收支类型'
/

comment on column T_KEEP.AMOUNT is '金额'
/

comment on column T_KEEP.REMARK is '备注'
/

comment on column T_KEEP.CREATED_BY is '创建人'
/

comment on column T_KEEP.CREATE_TIME is '创建时间'
/

comment on column T_KEEP.UPDATED_BY is '更新人'
/

comment on column T_KEEP.UPDATE_TIME is '更新时间'
/

comment on column T_KEEP.IS_DELETE is '删除标识'
/