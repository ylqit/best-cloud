/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : authorize

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 12/11/2021 16:24:52
*/

CREATE SCHEMA IF NOT EXISTS `authorize` DEFAULT CHARACTER SET utf8mb4;
USE
`authorize` ;

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth2_authorization
-- 授权信息表
-- 当前客户端的授权范围，但不是所有应用的范围
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_authorization`;
CREATE TABLE `oauth2_authorization`
(
    `id`                            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `registered_client_id`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `principal_name`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `authorization_grant_type`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `attributes`                    varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `state`                         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `authorization_code_value`      blob NULL,
    `authorization_code_issued_at`  timestamp NULL DEFAULT NULL,
    `authorization_code_expires_at` timestamp NULL DEFAULT NULL,
    `authorization_code_metadata`   varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `access_token_value`            blob NULL,
    `access_token_issued_at`        timestamp NULL DEFAULT NULL,
    `access_token_expires_at`       timestamp NULL DEFAULT NULL,
    `access_token_metadata`         varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `access_token_type`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `access_token_scopes`           varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `oidc_id_token_value`           blob NULL,
    `oidc_id_token_issued_at`       timestamp NULL DEFAULT NULL,
    `oidc_id_token_expires_at`      timestamp NULL DEFAULT NULL,
    `oidc_id_token_metadata`        varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `refresh_token_value`           blob NULL,
    `refresh_token_issued_at`       timestamp NULL DEFAULT NULL,
    `refresh_token_expires_at`      timestamp NULL DEFAULT NULL,
    `refresh_token_metadata`        varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth2_authorization_consent
-- 授权信息表
-- 您应用的所有范围
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_authorization_consent`;
CREATE TABLE `oauth2_authorization_consent`
(
    `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `principal_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `authorities`          varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`registered_client_id`, `principal_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth2_registered_client
-- client用户表
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_registered_client`;
CREATE TABLE `oauth2_registered_client`
(
    `id`                            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `client_id`                     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `client_id_issued_at`           timestamp                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `client_secret`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `client_secret_expires_at`      timestamp NULL DEFAULT NULL,
    `client_name`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `authorization_grant_types`     varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `redirect_uris`                 varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `scopes`                        varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `client_settings`               varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `token_settings`                varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;


-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
    id                      BIGINT       NOT NULL COMMENT '主键ID',
    username                VARCHAR(30)  NOT NULL UNIQUE COMMENT '用户名',
    password                VARCHAR(255) NOT NULL COMMENT '密码',
    dep_id                  BIGINT NULL DEFAULT NULL COMMENT '部门ID',
    account_non_expired     bit(1)       NOT NULL COMMENT '账户是否过期,过期无法验证',
    account_non_locked      bit(1)       NOT NULL COMMENT '指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证',
    credentials_non_expired bit(1)       NOT NULL COMMENT '指示是否已过期的用户的凭据(密码),过期的凭据防止认证',
    enabled                 bit(1)       NOT NULL COMMENT '是否被禁用,禁用的用户不能身份验证',
    name                    VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    nickname                VARCHAR(30) NULL DEFAULT NULL COMMENT '昵称',
    sex                     INT NULL DEFAULT NULL COMMENT '性别',
    birthday                DATE NULL DEFAULT NULL COMMENT '生日',
    avatar                  VARCHAR(255) NULL DEFAULT NULL COMMENT '头像',
    email                   VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    phone_number            VARCHAR(11) NULL DEFAULT NULL COMMENT '手机号码',
    address_code            VARCHAR(255) NULL DEFAULT NULL COMMENT '地址编号',
    detailed_address        VARCHAR(255) NULL DEFAULT NULL COMMENT '详细地址',
    introduction            VARCHAR(255) NULL DEFAULT NULL COMMENT '个人介绍',
    created_by              BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date            datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by        BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date      datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);


-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS sys_role;

CREATE TABLE sys_role
(
    id                 BIGINT      NOT NULL COMMENT '主键ID',
    name               VARCHAR(30) NOT NULL COMMENT '名称',
    code               VARCHAR(30) NOT NULL UNIQUE COMMENT '角色代码',
    description        VARCHAR(255) COMMENT '描述',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

-- ----------------------------
-- 菜单表
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;

CREATE TABLE sys_menu
(
    id                    BIGINT      NOT NULL COMMENT '主键ID',
    name                  VARCHAR(30) NOT NULL COMMENT '菜单名称',
    locale                VARCHAR(255) COMMENT '菜单名称（本地化）',
    path                  VARCHAR(255) COMMENT '菜单路由',
    pid                   BIGINT NULL DEFAULT NULL COMMENT '上级ID',
    icon                  VARCHAR(255) COMMENT '图标',
    priority              INT(11) NULL DEFAULT NULL COMMENT '排序等级',
    hide_in_menu          bit(1)      NOT NULL DEFAULT 0 COMMENT '是否隐藏菜单',
    hide_children_in_menu bit(1)      NOT NULL DEFAULT 0 COMMENT '是否隐藏子节点',
    props                 VARCHAR(255) COMMENT '参数',
    description           VARCHAR(255) COMMENT '菜单描述',
    created_by            BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date          datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by      BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date    datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);


-- ----------------------------
-- 资源表
-- ----------------------------
DROP TABLE IF EXISTS sys_resource;

CREATE TABLE sys_resource
(
    id                 BIGINT      NOT NULL COMMENT '主键ID',
    name               VARCHAR(30) NOT NULL COMMENT '资源名称',
    path               VARCHAR(255) COMMENT '资源路由',
    type               INT(11) NOT NULL COMMENT '资源类型',
    pid                BIGINT NULL DEFAULT NULL COMMENT '上级ID',
    priority           INT(11) NULL DEFAULT NULL COMMENT '排序等级',
    description        VARCHAR(255) COMMENT '资源描述',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

-- ----------------------------
-- 用户-角色关系表
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;

CREATE TABLE sys_user_role
(
    id                 BIGINT NOT NULL COMMENT '主键ID',
    user_id            BIGINT NOT NULL COMMENT '用户ID',
    role_id            BIGINT NOT NULL COMMENT '角色ID',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

-- ----------------------------
-- 角色-菜单关系表
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;

CREATE TABLE sys_role_menu
(
    id                 BIGINT NOT NULL COMMENT '主键ID',
    role_id            BIGINT NOT NULL COMMENT '角色ID',
    menu_id            BIGINT NOT NULL COMMENT '权限ID',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);


-- ----------------------------
-- 角色-资源关系表
-- ----------------------------
DROP TABLE IF EXISTS sys_role_resource;

CREATE TABLE sys_role_resource
(
    id                 BIGINT NOT NULL COMMENT '主键ID',
    role_id            BIGINT NOT NULL COMMENT '角色ID',
    resource_id        BIGINT NOT NULL COMMENT '权限ID',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);
