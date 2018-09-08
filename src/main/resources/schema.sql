DROP TABLE IF EXISTS application_user;
CREATE TABLE application_user(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  reference_code VARCHAR(12) NOT NULL,
  username VARCHAR(15),
  password VARCHAR(32),
  mobile_no VARCHAR(10) NOT NULL,
  member_type VARCHAR(10),
  salary DECIMAL(14,2)
);