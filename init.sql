create table sys_dict
(
    ID          varchar(32)   not null comment '主键'
        primary key,
    CODE        varchar(32)   null comment '编码',
    NAME        varchar(32)   null comment '字典名',
    ORDER_NUM   int default 0 null comment '排序号',
    TYPE_CODE   varchar(32)   null comment '类型code',
    CREATE_BY   varchar(32)   null comment '创建人',
    CREATE_TIME datetime      null comment '创建时间',
    UPDATE_BY   varchar(32)   null comment '更新人',
    UPDATE_TIME datetime      null comment '更新时间',
    IS_DELETE   char          null comment '删除标识'
)
    comment 'DICT' collate = utf8mb4_unicode_ci;

create table sys_dict_type
(
    ID          varchar(32) not null comment '主键'
        primary key,
    CODE        varchar(32) null comment '编码',
    NAME        varchar(32) null comment '类型',
    CREATE_BY   varchar(32) null comment '创建人',
    CREATE_TIME datetime    null comment '创建时间',
    UPDATE_BY   varchar(32) null comment '更新人',
    UPDATE_TIME datetime    null comment '更新时间',
    IS_DELETE   char        null comment '删除标识'
)
    comment 'DICT_TYPE' collate = utf8mb4_unicode_ci;

create table t_article
(
    ID          bigint(19)                not null comment '主键'
        primary key,
    LOGO        varchar(128) charset utf8 null comment '图片',
    TITLE       varchar(64)               null comment '标题',
    SUMMARY     varchar(128)              null comment '摘要',
    CONTENT     longtext                  null comment '内容',
    TAG         varchar(32)               null comment '标签',
    ORDER_NUM   tinyint(32)               null comment '排序号',
    IS_RELEASE  tinyint(1)                null comment '是否发布',
    CREATE_BY   varchar(32)               null comment '创建人',
    CREATE_TIME datetime                  null comment '创建时间',
    UPDATE_BY   varchar(32)               null comment '更新人',
    UPDATE_TIME datetime                  null comment '更新时间',
    IS_DELETE   tinyint(1)                null comment '删除标识'
)
    comment 'ARTICLE' collate = utf8mb4_unicode_ci;

create fulltext index CONTENT_INDEX
    on t_article (CONTENT);

create table t_device
(
    ID          varchar(32) not null comment '主键',
    NAME        varchar(32) null comment '资源名称',
    TYPE        varchar(32) null comment '资源类型',
    URL         varchar(32) null comment '链接地址',
    REMARK      varchar(32) null comment '备注',
    DUE_DATE    datetime    null comment '到期时间',
    PANEL       varchar(32) null,
    CREATE_BY   varchar(32) null,
    CREATE_TIME datetime    null comment '创建时间',
    UPDATE_BY   varchar(32) null comment '更新人',
    UPDATE_TIME datetime    null comment '更新时间',
    IS_DELETE   char        null comment '删除标识'
)
    comment 'T_RESOURCE' collate = utf8mb4_unicode_ci;

create table t_discuss
(
    ID          varchar(32) not null comment '主键'
        primary key,
    CONTENT     varchar(32) null comment '内容',
    ARTICLE_ID  varchar(32) null comment '文章id',
    PARENT_ID   varchar(32) null comment '一級回復id',
    REPLY_ID    varchar(32) null comment '回复的评论id',
    ADDRESS     varchar(32) null comment '地区',
    CREATE_BY   varchar(32) null comment '创建人',
    CREATE_TIME datetime    null comment '创建时间',
    UPDATE_BY   varchar(32) null comment '更新人',
    UPDATE_TIME datetime    null comment '更新时间',
    IS_DELETE   char        null comment '删除标识'
)
    comment '评论表' collate = utf8mb4_unicode_ci;

create table t_friend
(
    ID          varchar(32) not null comment '主键'
        primary key,
    LOGO        varchar(64) null comment 'logo',
    URL         varchar(32) null comment '链接',
    TITLE       varchar(32) null comment '标题',
    DESCRIPTION varchar(32) null comment '描述',
    IS_RELEASE  tinyint(1)  null comment '是否发布',
    CREATE_BY   varchar(32) null comment '创建人',
    CREATE_TIME datetime    null comment '创建时间',
    UPDATE_BY   varchar(32) null comment '更新人',
    UPDATE_TIME datetime    null comment '更新时间',
    IS_DELETE   char        null comment '删除标识'
)
    comment '友链' collate = utf8mb4_unicode_ci;

create table t_log_user
(
    ID          varchar(32) not null comment '主键'
        primary key,
    TYPE        varchar(32) null comment '类型',
    VALUE       int(32)     null comment '值',
    TIME        datetime    null comment '日期',
    CREATE_BY   varchar(32) null comment '创建人',
    CREATE_TIME datetime    null comment '创建时间',
    UPDATE_BY   varchar(32) null comment '更新人',
    UPDATE_TIME datetime    null comment '更新时间',
    IS_DELETE   char        null comment '删除标识'
)
    comment 'LOG_SYSTEM' collate = utf8mb4_unicode_ci;

create table t_menu
(
    ID          varchar(32) not null comment '主键'
        primary key,
    NAME        varchar(32) null comment '菜单中文名',
    PATH        varchar(32) null comment '菜单路由',
    COMPONENT   varchar(32) null comment '菜单组件地址',
    ICON        varchar(32) null comment '菜单ICON',
    ORDER_NUM   tinyint(32) null comment '排序号',
    IS_SHOW     tinyint(1)  null comment '是否显示在左侧',
    PARENT_ID   varchar(32) null comment '菜单ICON',
    CREATE_BY   varchar(32) null comment '创建人',
    CREATE_TIME datetime    null comment '创建时间',
    UPDATE_BY   varchar(32) null comment '更新人',
    UPDATE_TIME datetime    null comment '更新时间',
    IS_DELETE   char        null comment '删除标识'
)
    comment 'MENU' collate = utf8mb4_unicode_ci;

create table t_role
(
    ID         varchar(32)  not null comment '主键'
        primary key,
    ROLE_VALUE varchar(255) null,
    ROLE_NAME  varchar(255) null,
    IS_DELETE  char         null
)
    collate = utf8mb4_unicode_ci;

create table t_sys_config
(
    ID          varchar(32)  not null comment '主键'
        primary key,
    FIELD       varchar(128) null comment '键',
    VALUE       varchar(128) null comment '值',
    CREATED_BY  varchar(32)  null comment '创建人',
    CREATE_TIME datetime     null comment '创建时间',
    UPDATED_BY  varchar(32)  null comment '更新人',
    UPDATE_TIME datetime     null comment '更新时间',
    IS_DELETE   char         null,
    NAME        varchar(32)  null
)
    comment 'T_SYS_CONFIG' collate = utf8mb4_unicode_ci;

create table t_task
(
    ID          varchar(32) not null comment '主键'
        primary key,
    CONTENT     varchar(64) null comment '任務',
    STATUS      int(32)     null comment '狀態',
    CREATE_BY   varchar(32) null comment '创建人',
    CREATE_TIME datetime    null comment '创建时间',
    UPDATE_BY   varchar(32) null comment '更新人',
    UPDATE_TIME datetime    null comment '更新时间',
    IS_DELETE   char        null comment '删除标识'
)
    comment 'TASK' collate = utf8mb4_unicode_ci;

create table t_user
(
    ID          varchar(32)  not null comment '主键'
        primary key,
    USERNAME    varchar(32)  null comment '用户名',
    PASSWORD    varchar(64)  null comment '密码',
    AVATAR      varchar(64)  null comment '头像',
    NICK_NAME   varchar(255) null comment '昵称',
    CREATE_TIME datetime     null comment '创建时间',
    UPDATE_BY   varchar(32)  null comment '更新人',
    UPDATE_TIME datetime     null comment '更新时间',
    CREATE_BY   varchar(32)  null comment '创建人',
    IS_DELETE   tinyint(1)   null comment '删除标识'
)
    comment 'USER' collate = utf8mb4_unicode_ci;

create table t_user_role
(
    ID        varchar(32) not null
        primary key,
    USER_ID   varchar(32) null,
    ROLE_ID   varchar(32) null,
    IS_DELETE char        null
)
    collate = utf8mb4_unicode_ci;

