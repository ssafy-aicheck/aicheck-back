#!/bin/bash
set -euo pipefail

# 초기화 대상 DB 리스트
DATABASES=(alarm bank batch business chatbot)

# 1. 데이터베이스 생성
for DB in "${DATABASES[@]}"; do
  echo "[Init] 생성 중: ${DB}"
  mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "
    CREATE DATABASE IF NOT EXISTS \`${DB}\`
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;"
done

# 2. 사용자 생성 및 권한 부여
echo "[Init] 사용자 생성 및 권한 부여"
mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "
  CREATE USER IF NOT EXISTS '${MYSQL_USER}'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';"

for DB in "${DATABASES[@]}"; do
  echo "[Init] 권한 부여: ${DB}"
  mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "
    GRANT ALL PRIVILEGES ON \`${DB}\`.*
    TO '${MYSQL_USER}'@'%';"
done

mysql -u root -p"${MYSQL_ROOT_PASSWORD}" -e "FLUSH PRIVILEGES;"

# 3. 테이블 SQL 실행
for DB in "${DATABASES[@]}"; do
  SQL_FILE="/docker-entrypoint-initdb.d/${DB}.sql"
  if [ -f "$SQL_FILE" ]; then
    echo "[Init] Importing: ${SQL_FILE} → ${DB}"

    # ✅ 에러 메시지를 캡처해서 로그 출력
    if mysql -u root -p"${MYSQL_ROOT_PASSWORD}" "$DB" < "$SQL_FILE"; then
      echo "[OK] ${DB}.sql import 성공"
    else
      echo "[ERROR] ❌ ${DB}.sql import 실패" >&2
    fi
  else
    echo "[Skip] ${SQL_FILE} 없음"
  fi
done
