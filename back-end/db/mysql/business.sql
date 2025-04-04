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
    `id`                 INT UNSIGNED      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `first_category_id`  SMALLINT UNSIGNED NOT NULL,
    `second_category_id` SMALLINT UNSIGNED NULL,
    `account_no`         VARCHAR(20)       NOT NULL,
    `name`               VARCHAR(20)       NOT NULL,
    `created_at`         DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`        DATETIME          NULL     DEFAULT NULL,
    `deleted_at`         DATETIME          NULL     DEFAULT NULL,
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
    `first_category_id`  INT UNSIGNED                                                                     NULL,
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

INSERT INTO business.stores (first_category_id, second_category_id, account_no, name)
VALUES (1, 103, 'ACC000001', '서울택시'),
       (2, 202, 'ACC000002', '빽다방'),
       (3, 303, 'ACC000003', '대성N스쿨'),
       (4, 401, 'ACC000004', '당구장'),
       (5, 504, 'ACC000005', '유니클로'),
       (1, 103, 'ACC000006', 'T-money충전소'),
       (2, 201, 'ACC000007', '교촌치킨'),
       (3, 301, 'ACC000008', 'YBM학원'),
       (4, 403, 'ACC000009', '오락실'),
       (5, 502, 'ACC000010', '이마트'),
       (1, 103, 'ACC000011', '시내버스센터'),
       (2, 203, 'ACC000012', '빽다방'),
       (3, 301, 'ACC000013', '정철어학원'),
       (4, 401, 'ACC000014', '레저샵'),
       (5, 502, 'ACC000015', '생활용품점'),
       (1, 102, 'ACC000016', '카카오택시'),
       (2, 201, 'ACC000017', '이디야커피'),
       (3, 304, 'ACC000018', '대성N스쿨'),
       (4, 404, 'ACC000019', '당구장'),
       (5, 503, 'ACC000020', '생활용품점'),
       (1, 103, 'ACC000021', '교통카드판매점'),
       (2, 201, 'ACC000022', '스타벅스'),
       (3, 303, 'ACC000023', 'EBS서점'),
       (4, 404, 'ACC000024', '오락실'),
       (5, 503, 'ACC000025', '다이소'),
       (1, 102, 'ACC000026', '서울택시'),
       (2, 201, 'ACC000027', '맘스터치'),
       (3, 304, 'ACC000028', '노량진고시학원'),
       (4, 401, 'ACC000029', 'VR방'),
       (5, 504, 'ACC000030', '코스트코'),
       (1, 105, 'ACC000031', '교통카드판매점'),
       (2, 203, 'ACC000032', '빽다방'),
       (3, 304, 'ACC000033', 'YBM학원'),
       (4, 401, 'ACC000034', 'VR방'),
       (5, 501, 'ACC000035', '이마트'),
       (1, 101, 'ACC000036', '지하철역매표소'),
       (2, 202, 'ACC000037', '롯데리아'),
       (3, 301, 'ACC000038', 'EBS서점'),
       (4, 403, 'ACC000039', '여행사'),
       (5, 502, 'ACC000040', '생활용품점'),
       (1, 102, 'ACC000041', '지하철편의점'),
       (2, 201, 'ACC000042', 'CU편의점'),
       (3, 302, 'ACC000043', '하이틴학원'),
       (4, 403, 'ACC000044', '롯데월드'),
       (5, 502, 'ACC000045', 'GS더프레시'),
       (1, 104, 'ACC000046', '따릉이대여소'),
       (2, 201, 'ACC000047', '한솥도시락'),
       (3, 301, 'ACC000048', '대성N스쿨'),
       (4, 403, 'ACC000049', '여행사'),
       (5, 501, 'ACC000050', 'GS더프레시'),
       (1, 102, 'ACC000051', '버스정류장'),
       (2, 204, 'ACC000052', '롯데리아'),
       (3, 302, 'ACC000053', '대성N스쿨'),
       (4, 403, 'ACC000054', '여행사'),
       (5, 502, 'ACC000055', '코스트코'),
       (1, 102, 'ACC000056', '따릉이대여소'),
       (2, 202, 'ACC000057', '김밥천국'),
       (3, 304, 'ACC000058', '서점아울렛'),
       (4, 403, 'ACC000059', '에버랜드'),
       (5, 503, 'ACC000060', '무인양품'),
       (1, 101, 'ACC000061', '시내버스센터'),
       (2, 202, 'ACC000062', '김밥천국'),
       (3, 301, 'ACC000063', '정철어학원'),
       (4, 401, 'ACC000064', '롯데월드'),
       (5, 501, 'ACC000065', '유니클로'),
       (1, 102, 'ACC000066', '지하철역매표소'),
       (2, 204, 'ACC000067', '빽다방'),
       (3, 304, 'ACC000068', 'EBS서점'),
       (4, 403, 'ACC000069', '롯데월드'),
       (5, 504, 'ACC000070', '유니클로'),
       (1, 104, 'ACC000071', '지하철편의점'),
       (2, 204, 'ACC000072', '던킨도너츠'),
       (3, 304, 'ACC000073', '정철어학원'),
       (4, 401, 'ACC000074', '레저샵'),
       (5, 501, 'ACC000075', '생활용품점'),
       (1, 105, 'ACC000076', '서울택시'),
       (2, 203, 'ACC000077', '던킨도너츠'),
       (3, 304, 'ACC000078', 'YBM학원'),
       (4, 401, 'ACC000079', '에버랜드'),
       (5, 502, 'ACC000080', '무인양품'),
       (1, 105, 'ACC000081', '버스정류장'),
       (2, 203, 'ACC000082', '김밥천국'),
       (3, 304, 'ACC000083', '교보문고'),
       (4, 401, 'ACC000084', '여행사'),
       (5, 504, 'ACC000085', '유니클로'),
       (1, 104, 'ACC000086', '버스정류장'),
       (2, 203, 'ACC000087', '스타벅스'),
       (3, 301, 'ACC000088', '하이틴학원'),
       (4, 402, 'ACC000089', '롯데월드'),
       (5, 502, 'ACC000090', '생활용품점'),
       (1, 103, 'ACC000091', '타다택시'),
       (2, 204, 'ACC000092', '버거킹'),
       (3, 302, 'ACC000093', '교보문고'),
       (4, 401, 'ACC000094', 'VR방'),
       (5, 504, 'ACC000095', '홈플러스'),
       (1, 103, 'ACC000096', 'T-money충전소'),
       (2, 201, 'ACC000097', 'BBQ치킨'),
       (3, 303, 'ACC000098', '토즈스터디카페'),
       (4, 401, 'ACC000099', '여행사'),
       (5, 504, 'ACC000100', '무인양품'),
       (1, 105, 'ACC000101', '지하철편의점'),
       (2, 202, 'ACC000102', '한솥도시락'),
       (3, 303, 'ACC000103', '하이틴학원'),
       (4, 403, 'ACC000104', '레저샵'),
       (5, 501, 'ACC000105', '유니클로'),
       (1, 101, 'ACC000106', '타다택시'),
       (2, 203, 'ACC000107', '롯데리아'),
       (3, 302, 'ACC000108', '하이틴학원'),
       (4, 403, 'ACC000109', '에버랜드'),
       (5, 503, 'ACC000110', 'H&M'),
       (1, 104, 'ACC000111', '서울택시'),
       (2, 201, 'ACC000112', 'CU편의점'),
       (3, 302, 'ACC000113', 'YBM학원'),
       (4, 404, 'ACC000114', '당구장'),
       (5, 503, 'ACC000115', '롯데마트'),
       (1, 105, 'ACC000116', '서울택시'),
       (2, 203, 'ACC000117', '던킨도너츠'),
       (3, 301, 'ACC000118', '대성N스쿨'),
       (4, 403, 'ACC000119', '펀시티'),
       (5, 503, 'ACC000120', '유니클로'),
       (1, 104, 'ACC000121', '시내버스센터'),
       (2, 203, 'ACC000122', '버거킹'),
       (3, 304, 'ACC000123', '정철어학원'),
       (4, 402, 'ACC000124', 'VR방'),
       (5, 503, 'ACC000125', '무인양품'),
       (1, 105, 'ACC000126', '교통카드판매점'),
       (2, 201, 'ACC000127', '김밥천국'),
       (3, 304, 'ACC000128', 'EBS서점'),
       (4, 401, 'ACC000129', 'CGV'),
       (5, 503, 'ACC000130', '코스트코'),
       (1, 104, 'ACC000131', '카카오택시'),
       (2, 204, 'ACC000132', '김밥천국'),
       (3, 303, 'ACC000133', '하이틴학원'),
       (4, 402, 'ACC000134', '펀시티'),
       (5, 504, 'ACC000135', 'GS더프레시'),
       (1, 102, 'ACC000136', '타다택시'),
       (2, 203, 'ACC000137', '한솥도시락'),
       (3, 303, 'ACC000138', '서점아울렛'),
       (4, 402, 'ACC000139', '롯데월드'),
       (5, 504, 'ACC000140', '롯데마트'),
       (1, 101, 'ACC000141', '교통카드판매점'),
       (2, 204, 'ACC000142', '던킨도너츠'),
       (3, 304, 'ACC000143', 'YBM학원'),
       (4, 404, 'ACC000144', '여행사'),
       (5, 503, 'ACC000145', '무인양품'),
       (1, 104, 'ACC000146', '지하철역매표소'),
       (2, 204, 'ACC000147', '한솥도시락'),
       (3, 303, 'ACC000148', '대성N스쿨'),
       (4, 404, 'ACC000149', '에버랜드'),
       (5, 504, 'ACC000150', 'H&M');

INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (1, 1, 1, null, '1111@naver.com', '$2a$10$rS6BiYs1Wt1xltdJ2qQ9O.ML0AJdO2WJKaHYwKusmSfxfj8AB1egq', '1111부모',
        '2025-04-24', null, 'PARENT', '2025-04-03 04:29:21', '2025-04-03 04:29:21', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (2, 1, 2, null, '2222@naver.com', '$2a$10$foe0Vd0b.4Ich1g0jeRfU.SeJOTIYp3PJZUFYhbRWNssowMD/BuTC', '2222자녀',
        '2025-04-24', null, 'CHILD', '2025-04-03 04:29:26', '2025-04-03 04:29:26', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (3, 3, 3, null, '3333@naver.com', '$2a$10$c8AwkspPobIYRRwHMKFM/.Ei/Dy2xZo34DYt2RlhPeTkYf6wuW0dO', '3333부모',
        '2025-04-24', null, 'PARENT', '2025-04-03 04:29:29', '2025-04-03 04:29:29', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (4, 3, 4, null, '4444@naver.com', '$2a$10$ntSUUkqmCzm/.ng/ziKyD.UQbJgx/hmlkI3y2yRn9cWdKzdzuqvd6', '4444자녀',
        '2025-04-24', null, 'CHILD', '2025-04-03 04:29:32', '2025-04-03 04:29:32', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (5, 5, 5, null, '5555@naver.com', '$2a$10$lcpkHySBOkfk/Bg92Bq0RuBXGBHSRzXSJ2wcJJ12rkQjuChzZ7EHy', '5555부모',
        '2025-04-24', null, 'PARENT', '2025-04-03 04:29:35', '2025-04-03 04:29:35', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (6, 5, 6, null, '6666@naver.com', '$2a$10$Exz4LNiJ/xni6COvOD5Yw.hlrn3AZIjXGAdmpxZ3EzqryYfi8YAOi', '6666자녀',
        '2025-04-24', null, 'CHILD', '2025-04-03 04:29:40', '2025-04-03 04:29:40', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (7, 7, 7, null, '7777@naver.com', '$2a$10$WXRSUdFXUyIm9jyvbtH/mOPuPgGq4X3pqHSYLcsxiBnxaxK2sRR96', '7777부모',
        '2025-04-24', null, 'PARENT', '2025-04-03 04:29:46', '2025-04-03 04:29:46', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (8, 7, 8, null, '8888@naver.com', '$2a$10$LUk9b1NtnZaBaK4uSzLOKu2lS6aCQI58ocHpJMFeip7dk83bCzOzG', '8888자녀',
        '2025-04-24', null, 'CHILD', '2025-04-03 04:29:51', '2025-04-03 04:29:51', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (9, 9, 9, null, '9999@naver.com', '$2a$10$F1prz8HFHufpg6EuxxvDv.NWi4OiRb12KMhdpn5dqS6ooT5MYtVoS', '9999부모',
        '2025-04-24', null, 'PARENT', '2025-04-03 04:29:55', '2025-04-03 04:29:55', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (10, 9, 10, null, '1010@naver.com', '$2a$10$YQaJRyWD.ELxiq9.IKgdOeq9FMWUZOmucKXxfQ1tXSd8G1ug4jjMW', '1010부모',
        '2025-04-24', null, 'CHILD', '2025-04-03 04:29:59', '2025-04-03 04:29:59', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (11, 11, 11, null, '11@naver.com', '$2a$10$SlA1DLTz0ghNhUdW/lZAy.eBhkV4AKMue7kWqiVWLBCbltwGnh44y', '11부모',
        '2025-04-24', null, 'PARENT', '2025-04-03 04:30:03', '2025-04-03 04:30:03', null);
INSERT INTO business.members (id, manager_id, bank_member_id, account_no, email, password, name, birth, profile_url,
                              type, created_at, modified_at, deleted_at)
VALUES (12, 11, 12, null, '12@naver.com', '$2a$10$XJEmGHUQWO/k8EO3tfcbUuX1WieEWx30QEKsnWW4UmgpAUNq9Iq16', '1212부모',
        '2025-04-24', null, 'CHILD', '2025-04-03 04:30:06', '2025-04-03 04:30:06', null);