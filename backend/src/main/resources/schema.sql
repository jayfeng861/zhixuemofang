CREATE TABLE IF NOT EXISTS sys_user (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(200) NOT NULL,
  nickname VARCHAR(100) DEFAULT '',
  email VARCHAR(200) DEFAULT '',
  avatar VARCHAR(500) DEFAULT '',
  role VARCHAR(20) DEFAULT 'student',
  grade VARCHAR(50) DEFAULT '',
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS wenyanwen_script (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  author VARCHAR(100) DEFAULT '',
  dynasty VARCHAR(50) DEFAULT '',
  description TEXT,
  original_text LONGTEXT,
  translation LONGTEXT,
  scenes TEXT,
  annotations TEXT,
  difficulty_level INT DEFAULT 1,
  cover_image VARCHAR(500) DEFAULT '',
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS vocabulary (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  word VARCHAR(100) NOT NULL,
  part_of_speech VARCHAR(50) DEFAULT '',
  meaning VARCHAR(500) DEFAULT '',
  phonetic_uk VARCHAR(100) DEFAULT '',
  phonetic_us VARCHAR(100) DEFAULT '',
  example_sentence TEXT,
  example_translation TEXT,
  category VARCHAR(50) DEFAULT 'core',
  difficulty_level INT DEFAULT 1,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS essay_submission (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  title VARCHAR(200) NOT NULL,
  requirement TEXT,
  content LONGTEXT,
  word_count INT DEFAULT 0,
  total_score INT DEFAULT 0,
  theme_score INT DEFAULT 0,
  structure_score INT DEFAULT 0,
  language_score INT DEFAULT 0,
  logic_score INT DEFAULT 0,
  ai_review TEXT,
  review_status INT DEFAULT 0,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS wrong_question (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  subject VARCHAR(50) NOT NULL,
  content TEXT NOT NULL,
  question_type VARCHAR(50) DEFAULT '',
  student_answer TEXT,
  correct_answer TEXT,
  knowledge_point_id BIGINT DEFAULT 0,
  knowledge_point_name VARCHAR(200) DEFAULT '',
  error_reason TEXT,
  mastered INT DEFAULT 0,
  error_count INT DEFAULT 1,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS oral_practice (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  scene_name VARCHAR(200) DEFAULT '',
  scene_prompt TEXT,
  speech_text TEXT,
  reference_text TEXT,
  pronunciation_score INT DEFAULT 0,
  intonation_score INT DEFAULT 0,
  fluency_score INT DEFAULT 0,
  total_score INT DEFAULT 0,
  ai_feedback TEXT,
  duration INT DEFAULT 0,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS geometry_lab (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  name VARCHAR(200) DEFAULT '',
  lab_type VARCHAR(50) DEFAULT '',
  config_data TEXT,
  notes TEXT,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS post (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  parent_id BIGINT DEFAULT 0,
  title VARCHAR(200) DEFAULT '',
  content TEXT,
  author_name VARCHAR(100) DEFAULT '',
  author_avatar VARCHAR(500) DEFAULT '',
  post_type VARCHAR(50) DEFAULT 'post',
  tags VARCHAR(200) DEFAULT '',
  images VARCHAR(1000) DEFAULT '',
  like_count INT DEFAULT 0,
  comment_count INT DEFAULT 0,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS learning_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  study_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  subject VARCHAR(50) DEFAULT '',
  module VARCHAR(100) DEFAULT '',
  duration INT DEFAULT 0,
  content TEXT,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS daily_checkin (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  checkin_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  checkin_type VARCHAR(50) DEFAULT 'daily',
  remark VARCHAR(500) DEFAULT '',
  continuous_days INT DEFAULT 1,
  points INT DEFAULT 0,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS student_profile (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE,
  nickname VARCHAR(100) DEFAULT '',
  avatar VARCHAR(500) DEFAULT '',
  grade VARCHAR(50) DEFAULT '',
  school VARCHAR(200) DEFAULT '',
  class_name VARCHAR(100) DEFAULT '',
  gender VARCHAR(10) DEFAULT '',
  birthday DATE DEFAULT NULL,
  chinese_score INT DEFAULT 0,
  math_score INT DEFAULT 0,
  english_score INT DEFAULT 0,
  learning_preference TEXT,
  learning_goal TEXT,
  study_days INT DEFAULT 0,
  total_points INT DEFAULT 0,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS knowledge_point (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  subject VARCHAR(50) NOT NULL,
  name VARCHAR(200) NOT NULL,
  parent_id BIGINT DEFAULT 0,
  level INT DEFAULT 1,
  description TEXT,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sys_admin (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(200) NOT NULL,
  nickname VARCHAR(100) DEFAULT '',
  email VARCHAR(200) DEFAULT '',
  avatar VARCHAR(500) DEFAULT '',
  role VARCHAR(50) DEFAULT 'admin',
  status INT DEFAULT 1,
  last_login_time TIMESTAMP DEFAULT NULL,
  deleted INT DEFAULT 0,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS sys_setting (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  setting_key VARCHAR(100) NOT NULL UNIQUE,
  setting_value TEXT,
  setting_desc VARCHAR(500) DEFAULT '',
  setting_group VARCHAR(50) DEFAULT 'basic',
  update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
