USE business;

CREATE TABLE `members`
(
    `id`             INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `manager_id`     INT UNSIGNED NOT NULL,
    `bank_member_id` INT UNSIGNED NOT NULL,
    `account_no`     VARCHAR(20)           DEFAULT NULL,
    `email`          VARCHAR(255) NOT NULL,
    `password`       CHAR(60)     NOT NULL,
    `name`           VARCHAR(20)  NOT NULL,
    `birth`          DATE         NOT NULL,
    `profile_url`    VARCHAR(255) NULL,
    `type`           ENUM('PARENT', 'CHILD') NOT NULL,
    `created_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`    DATETIME NULL DEFAULT NULL,
    `deleted_at`     DATETIME NULL DEFAULT NULL,
    UNIQUE KEY (account_no),
    UNIQUE KEY (email)
);

CREATE TABLE `voice_phishings`
(
    `id`           INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`    INT UNSIGNED NOT NULL,
    `manager_id`   INT UNSIGNED NOT NULL,
    `phone_number` VARCHAR(11) NOT NULL,
    `score`        FLOAT       NOT NULL,
    `created_at`   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`  DATETIME NULL DEFAULT NULL,
    `deleted_at`   DATETIME NULL DEFAULT NULL
);

CREATE TABLE `bad_urls`
(
    `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`   INT UNSIGNED NOT NULL,
    `manager_id`  INT UNSIGNED NOT NULL,
    `url`         VARCHAR(255) NOT NULL,
    `score`       FLOAT        NOT NULL,
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at` DATETIME NULL DEFAULT NULL,
    `deleted_at`  DATETIME NULL DEFAULT NULL
);

CREATE TABLE `stores`
(
    `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_category_id` SMALLINT UNSIGNED NOT NULL,
    `account_no`        VARCHAR(20) NOT NULL,
    `name`              VARCHAR(20) NOT NULL,
    `created_at`        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`       DATETIME NULL DEFAULT NULL,
    `deleted_at`        DATETIME NULL DEFAULT NULL,
    UNIQUE KEY (account_no)
);

CREATE TABLE `first_categories`
(
    `id`   SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(30) NOT NULL
);

CREATE TABLE `second_categories`
(
    `id`                SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_category_id` SMALLINT UNSIGNED NOT NULL,
    `name`              VARCHAR(30) NOT NULL
);

CREATE TABLE `allowance_requests`
(
    `id`                   INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_id`            INT UNSIGNED NOT NULL,
    `child_id`             INT UNSIGNED NOT NULL,
    `amount`               MEDIUMINT UNSIGNED NOT NULL,
    `first_category_name`  VARCHAR(30) NOT NULL,
    `second_category_name` VARCHAR(30) NOT NULL,
    `status`               ENUM('ACCEPTED', 'REJECTED', 'WAITING') NOT NULL DEFAULT 'WAITING',
    `description`          VARCHAR(255) NULL,
    `created_at`           DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`          DATETIME NULL DEFAULT NULL,
    `deleted_at`           DATETIME NULL DEFAULT NULL
);

CREATE TABLE `allowance_increase_requests`
(
    `id`            INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `parent_id`     INT UNSIGNED NOT NULL,
    `child_id`      INT UNSIGNED NOT NULL,
    `before_amount` MEDIUMINT UNSIGNED NOT NULL,
    `after_amount`  MEDIUMINT UNSIGNED NOT NULL,
    `report_id`     INT UNSIGNED NULL,
    `status`        ENUM('ACCEPTED', 'REJECTED', 'WAITING') NOT NULL DEFAULT 'WAITING',
    `summary`       VARCHAR(255) NULL,
    `description`   VARCHAR(255) NULL,
    `created_at`    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`   DATETIME NULL DEFAULT NULL,
    `deleted_at`    DATETIME NULL DEFAULT NULL
);

CREATE TABLE `transaction_records`
(
    `id`                   INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_id`            INT UNSIGNED NOT NULL,
    `store_id`             INT UNSIGNED NULL,
    `first_category_name`  VARCHAR(30) NOT NULL,
    `second_category_name` VARCHAR(30) NULL,
    `is_dutch_pay`         TINYINT(1) NOT NULL DEFAULT 0,
    `display_name`         VARCHAR(20) NOT NULL,
    `type`                 ENUM('PAYMENT', 'DEPOSIT', 'WITHDRAW', 'INBOUND_TRANSFER', 'OUTBOUND_TRANSFER') NOT NULL,
    `amount`               INT UNSIGNED NOT NULL,
    `description`          VARCHAR(60) NULL,
    `rating`               TINYINT UNSIGNED NULL DEFAULT NULL,
    `created_at`           DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`          DATETIME NULL DEFAULT NULL,
    `deleted_at`           DATETIME NULL DEFAULT NULL
);

CREATE TABLE `dutch_pays`
(
    `id`                    INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `transaction_record_id` INT UNSIGNED NOT NULL,
    `transfer_record_id`    INT UNSIGNED NOT NULL,
    `created_at`            DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`           DATETIME NULL DEFAULT NULL,
    `deleted_at`            DATETIME NULL DEFAULT NULL
);