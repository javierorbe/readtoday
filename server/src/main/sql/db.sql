DROP SCHEMA IF EXISTS readtoday;
DROP USER IF EXISTS 'readtoday_user'@'localhost';

CREATE SCHEMA readtoday;
CREATE USER IF NOT EXISTS 'readtoday_user'@'localhost' IDENTIFIED BY 's3curep4ssw0rd';
GRANT ALL ON readtoday.* TO 'readtoday_user'@'localhost';
