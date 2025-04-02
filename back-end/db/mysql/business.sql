USE business;

CREATE TABLE `members`
(
    `id`             INT UNSIGNED             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `manager_id`     INT UNSIGNED             NOT NULL,
    `bank_member_id` INT UNSIGNED             NOT NULL,
    `account_no`     VARCHAR(20)              NULL     DEFAULT NULL,
    `email`          VARCHAR(255)             NOT NULL,
    `password`       CHAR(60)                 NOT NULL,
    `name`           VARCHAR(20)              NOT NULL,
    `birth`          DATE                     NOT NULL,
    `profile_url`    VARCHAR(255)             NULL,
    `type`           ENUM ('PARENT', 'CHILD') NOT NULL,
    `created_at`     DATETIME                 NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`    DATETIME                 NULL     DEFAULT NULL,
    `deleted_at`     DATETIME                 NULL     DEFAULT NULL,
    UNIQUE KEY (account_no),
    UNIQUE KEY (email)
);

CREATE TABLE `voice_phishings`
(
    `id`           INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`    INT UNSIGNED NOT NULL,
    `manager_id`   INT UNSIGNED NOT NULL,
    `phone_number` VARCHAR(11)  NOT NULL,
    `score`        FLOAT        NOT NULL,
    `created_at`   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`  DATETIME     NULL     DEFAULT NULL,
    `deleted_at`   DATETIME     NULL     DEFAULT NULL
);

CREATE TABLE `bad_urls`
(
    `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`   INT UNSIGNED NOT NULL,
    `manager_id`  INT UNSIGNED NOT NULL,
    `url`         VARCHAR(255) NOT NULL,
    `score`       FLOAT        NOT NULL,
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at` DATETIME     NULL     DEFAULT NULL,
    `deleted_at`  DATETIME     NULL     DEFAULT NULL
);

CREATE TABLE `stores`
(
    `id`                INT UNSIGNED      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_category_id` SMALLINT UNSIGNED NOT NULL,
    `account_no`        VARCHAR(20)       NOT NULL,
    `name`              VARCHAR(20)       NOT NULL,
    `created_at`        DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`       DATETIME          NULL     DEFAULT NULL,
    `deleted_at`        DATETIME          NULL     DEFAULT NULL,
    UNIQUE KEY (account_no)
);

CREATE TABLE `first_categories`
(
    `id`           SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
--     `name` VARCHAR(30) NOT NULL,
    `display_name` VARCHAR(30)       NOT NULL
);

CREATE TABLE `second_categories`
(
    `id`                SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_category_id` SMALLINT UNSIGNED NOT NULL,
--     `name`              VARCHAR(30) NOT NULL,
    `display_name`      VARCHAR(30)       NOT NULL
);

CREATE TABLE `allowance_requests`
(
    `id`                   INT UNSIGNED                             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_id`            INT UNSIGNED                             NOT NULL,
    `child_id`             INT UNSIGNED                             NOT NULL,
    `amount`               MEDIUMINT UNSIGNED                       NOT NULL,
    `first_category_name`  VARCHAR(30)                              NOT NULL,
    `second_category_name` VARCHAR(30)                              NOT NULL,
    `status`               ENUM ('ACCEPTED', 'REJECTED', 'WAITING') NOT NULL DEFAULT 'WAITING',
    `description`          VARCHAR(255)                             NULL,
    `created_at`           DATETIME                                 NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`          DATETIME                                 NULL     DEFAULT NULL,
    `deleted_at`           DATETIME                                 NULL     DEFAULT NULL
);

CREATE TABLE `allowance_increase_requests`
(
    `id`            INT UNSIGNED                             NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_id`     INT UNSIGNED                             NOT NULL,
    `child_id`      INT UNSIGNED                             NOT NULL,
    `before_amount` MEDIUMINT UNSIGNED                       NOT NULL,
    `after_amount`  MEDIUMINT UNSIGNED                       NOT NULL,
    `report_id`     INT UNSIGNED                             NULL,
    `status`        ENUM ('ACCEPTED', 'REJECTED', 'WAITING') NOT NULL DEFAULT 'WAITING',
    `summary`       VARCHAR(255)                             NULL,
    `description`   VARCHAR(255)                             NULL,
    `created_at`    DATETIME                                 NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`   DATETIME                                 NULL     DEFAULT NULL,
    `deleted_at`    DATETIME                                 NULL     DEFAULT NULL
);

CREATE TABLE `transaction_records`
(
    `id`                 INT UNSIGNED                                                                     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`          INT UNSIGNED                                                                     NOT NULL,
    `store_id`           INT UNSIGNED                                                                     NULL,
    `first_category_id`  INT UNSIGNED                                                                     NOT NULL,
    `second_category_id` INT UNSIGNED                                                                     NULL,
    `display_name`       VARCHAR(20)                                                                      NOT NULL,
    `type`               ENUM ('PAYMENT', 'DEPOSIT', 'WITHDRAW', 'INBOUND_TRANSFER', 'OUTBOUND_TRANSFER') NOT NULL,
    `amount`             INT UNSIGNED                                                                     NOT NULL,
    `description`        VARCHAR(60)                                                                      NULL,
    `rating`             TINYINT UNSIGNED                                                                 NULL     DEFAULT NULL,
    `created_at`         DATETIME                                                                         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`        DATETIME                                                                         NULL     DEFAULT NULL,
    `deleted_at`         DATETIME                                                                         NULL     DEFAULT NULL
);
use business;
INSERT INTO first_categories (id, display_name)
VALUES (1, '교통'),
       (2, '식비'),
       (3, '교육'),
       (4, '여가'),
       (5, '생활');

INSERT INTO second_categories (id, first_category_id, display_name)
VALUES
-- TRANSPORTATION
(101, 1, '버스'),
(102, 1, '지하철'),
(103, 1, '택시'),
(104, 1, '자전거'),
(105, 1, '기타'),

-- FOOD
(201, 2, '식사'),
(202, 2, '간식'),
(203, 2, '음료'),
(204, 2, '기타'),

-- EDUCATION
(301, 3, '교재'),
(302, 3, '문구'),
(303, 3, '학용품'),
(304, 3, '기타'),

-- LEISURE
(401, 4, '오락'),
(402, 4, '여행'),
(403, 4, '문화생활'),
(404, 4, '기타'),

-- LIVING
(501, 5, '의류'),
(502, 5, '선물'),
(503, 5, '생활용품'),
(504, 5, '기타');
