#!/bin/bash
set -e

echo "[Init] MySQL 초기화 시작..."

# 기본 환경 변수 출력 (디버깅 용도)
echo "[Init] MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}"
echo "[Init] MYSQL_USER=${MYSQL_USER}"
echo "[Init] MYSQL_PASSWORD=${MYSQL_PASSWORD}"

# 기본 DB/사용자/권한 생성
echo "[Init] 데이터베이스 및 사용자 생성..."

mysql -u root -p"${MYSQL_ROOT_PASSWORD}" <<-EOSQL
  CREATE DATABASE IF NOT EXISTS alarm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  CREATE DATABASE IF NOT EXISTS bank DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  CREATE DATABASE IF NOT EXISTS batch DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  CREATE DATABASE IF NOT EXISTS business DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  CREATE DATABASE IF NOT EXISTS chatbot DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';
  GRANT ALL PRIVILEGES ON alarm.* TO '${MYSQL_USER}'@'%';
  GRANT ALL PRIVILEGES ON bank.* TO '${MYSQL_USER}'@'%';
  GRANT ALL PRIVILEGES ON batch.* TO '${MYSQL_USER}'@'%';
  GRANT ALL PRIVILEGES ON business.* TO '${MYSQL_USER}'@'%';
  GRANT ALL PRIVILEGES ON chatbot.* TO '${MYSQL_USER}'@'%';

  FLUSH PRIVILEGES;
EOSQL

echo "[Init] 데이터베이스 및 사용자 설정 완료 ✅"

# 테이블 생성 SQL 실행
echo "[Init] 테이블 생성 시작..."

mysql -u root -p"${MYSQL_ROOT_PASSWORD}" alarm < /docker-entrypoint-initdb.d/alarm.sql
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" bank < /docker-entrypoint-initdb.d/bank.sql
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" batch < /docker-entrypoint-initdb.d/batch.sql
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" business < /docker-entrypoint-initdb.d/business.sql
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" chatbot < /docker-entrypoint-initdb.d/chatbot.sql

echo "[Init] 테이블 생성 완료 ✅"