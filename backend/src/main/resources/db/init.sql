-- 智学魔方数据库初始化脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS zhixue_mofang DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE zhixue_mofang;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码（MD5加密）',
    `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `role` VARCHAR(20) DEFAULT 'student' COMMENT '角色：student/teacher/admin',
    `grade` VARCHAR(20) DEFAULT NULL COMMENT '年级',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 学生档案表
CREATE TABLE IF NOT EXISTS `student_profile` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '档案ID',
    `user_id` BIGINT NOT NULL UNIQUE COMMENT '用户ID',
    `chinese_score` INT DEFAULT NULL COMMENT '语文成绩',
    `math_score` INT DEFAULT NULL COMMENT '数学成绩',
    `english_score` INT DEFAULT NULL COMMENT '英语成绩',
    `learning_preference` TEXT DEFAULT NULL COMMENT '学习偏好（JSON）',
    `learning_goal` VARCHAR(500) DEFAULT NULL COMMENT '学习目标',
    `study_days` INT DEFAULT 0 COMMENT '学习天数',
    `total_points` INT DEFAULT 0 COMMENT '总积分',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生档案表';

-- 错题表
CREATE TABLE IF NOT EXISTS `wrong_question` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '错题ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `subject` VARCHAR(20) NOT NULL COMMENT '科目',
    `content` TEXT NOT NULL COMMENT '题目内容',
    `question_type` VARCHAR(20) NOT NULL COMMENT '题目类型',
    `student_answer` TEXT NOT NULL COMMENT '学生答案',
    `correct_answer` TEXT NOT NULL COMMENT '正确答案',
    `knowledge_point_id` BIGINT DEFAULT NULL COMMENT '知识点ID',
    `knowledge_point_name` VARCHAR(100) DEFAULT NULL COMMENT '知识点名称',
    `error_reason` TEXT DEFAULT NULL COMMENT '错误原因',
    `mastered` TINYINT DEFAULT 0 COMMENT '是否已掌握',
    `error_count` INT DEFAULT 1 COMMENT '错误次数',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_subject` (`subject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='错题表';

-- 作文提交表
CREATE TABLE IF NOT EXISTS `essay_submission` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '作文ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `title` VARCHAR(200) NOT NULL COMMENT '作文标题',
    `requirement` VARCHAR(500) DEFAULT NULL COMMENT '作文要求',
    `content` TEXT NOT NULL COMMENT '作文内容',
    `word_count` INT DEFAULT 0 COMMENT '字数',
    `total_score` INT DEFAULT NULL COMMENT '总分',
    `theme_score` INT DEFAULT NULL COMMENT '立意评分',
    `structure_score` INT DEFAULT NULL COMMENT '结构评分',
    `language_score` INT DEFAULT NULL COMMENT '语言评分',
    `logic_score` INT DEFAULT NULL COMMENT '逻辑评分',
    `ai_review` TEXT DEFAULT NULL COMMENT 'AI批改评语（JSON）',
    `review_status` TINYINT DEFAULT 0 COMMENT '批改状态',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='作文提交表';

-- 词汇表
CREATE TABLE IF NOT EXISTS `vocabulary` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '词汇ID',
    `word` VARCHAR(100) NOT NULL COMMENT '单词',
    `part_of_speech` VARCHAR(20) DEFAULT NULL COMMENT '词性',
    `meaning` VARCHAR(500) NOT NULL COMMENT '中文释义',
    `phonetic_uk` VARCHAR(100) DEFAULT NULL COMMENT '英式音标',
    `phonetic_us` VARCHAR(100) DEFAULT NULL COMMENT '美式音标',
    `example_sentence` TEXT DEFAULT NULL COMMENT '例句',
    `example_translation` TEXT DEFAULT NULL COMMENT '例句翻译',
    `category` VARCHAR(20) DEFAULT 'core' COMMENT '分类',
    `difficulty_level` INT DEFAULT 1 COMMENT '难度等级',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='词汇表';

-- 口语练习表
CREATE TABLE IF NOT EXISTS `oral_practice` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '练习ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `scene_name` VARCHAR(100) NOT NULL COMMENT '场景名称',
    `scene_prompt` TEXT DEFAULT NULL COMMENT '场景提示',
    `speech_text` TEXT NOT NULL COMMENT '口语文本',
    `reference_text` TEXT DEFAULT NULL COMMENT '参考文本',
    `pronunciation_score` INT DEFAULT NULL COMMENT '发音评分',
    `intonation_score` INT DEFAULT NULL COMMENT '语调评分',
    `fluency_score` INT DEFAULT NULL COMMENT '语速评分',
    `total_score` INT DEFAULT NULL COMMENT '总分',
    `ai_feedback` TEXT DEFAULT NULL COMMENT 'AI评价（JSON）',
    `duration` INT DEFAULT NULL COMMENT '练习时长（秒）',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='口语练习表';

-- 学习记录表
CREATE TABLE IF NOT EXISTS `learning_record` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `study_date` DATETIME NOT NULL COMMENT '学习日期',
    `subject` VARCHAR(20) NOT NULL COMMENT '科目',
    `module` VARCHAR(50) NOT NULL COMMENT '学习模块',
    `duration` INT DEFAULT NULL COMMENT '学习时长（分钟）',
    `content` VARCHAR(500) DEFAULT NULL COMMENT '学习内容',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_study_date` (`study_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习记录表';

-- 文言文剧本表
CREATE TABLE IF NOT EXISTS `wenyanwen_script` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '剧本ID',
    `title` VARCHAR(200) NOT NULL COMMENT '剧本标题',
    `author` VARCHAR(100) DEFAULT NULL COMMENT '作者',
    `dynasty` VARCHAR(50) DEFAULT NULL COMMENT '朝代',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '简介',
    `original_text` TEXT NOT NULL COMMENT '原文',
    `translation` TEXT DEFAULT NULL COMMENT '译文',
    `scenes` TEXT DEFAULT NULL COMMENT '场景列表（JSON）',
    `annotations` TEXT DEFAULT NULL COMMENT '重点词汇注释（JSON）',
    `difficulty_level` INT DEFAULT 1 COMMENT '难度等级',
    `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图片',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文言文剧本表';

-- 几何实验室表
CREATE TABLE IF NOT EXISTS `geometry_lab` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '实验ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `name` VARCHAR(200) NOT NULL COMMENT '实验名称',
    `lab_type` VARCHAR(50) NOT NULL COMMENT '实验类型',
    `config_data` TEXT NOT NULL COMMENT '图形配置（JSON）',
    `notes` TEXT DEFAULT NULL COMMENT '实验笔记',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='几何实验室表';

-- 社区帖子表
CREATE TABLE IF NOT EXISTS `post` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '帖子ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `author_name` VARCHAR(50) DEFAULT NULL COMMENT '发帖人昵称',
    `author_avatar` VARCHAR(500) DEFAULT NULL COMMENT '发帖人头像',
    `title` VARCHAR(200) DEFAULT NULL COMMENT '帖子标题',
    `content` TEXT NOT NULL COMMENT '帖子内容',
    `post_type` VARCHAR(20) DEFAULT 'discussion' COMMENT '帖子类型',
    `tags` VARCHAR(500) DEFAULT NULL COMMENT '标签（JSON数组）',
    `images` TEXT DEFAULT NULL COMMENT '图片列表（JSON数组）',
    `like_count` INT DEFAULT 0 COMMENT '点赞数',
    `comment_count` INT DEFAULT 0 COMMENT '评论数',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父帖子ID',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='社区帖子表';

-- 每日打卡表
CREATE TABLE IF NOT EXISTS `daily_checkin` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '打卡ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `checkin_date` DATETIME NOT NULL COMMENT '打卡日期',
    `checkin_type` VARCHAR(20) DEFAULT 'daily' COMMENT '打卡类型',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    `points` INT DEFAULT 0 COMMENT '获得积分',
    `continuous_days` INT DEFAULT 1 COMMENT '连续打卡天数',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_user_id` (`user_id`),
    UNIQUE INDEX `idx_user_date` (`user_id`, `checkin_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日打卡表';

-- 知识点表
CREATE TABLE IF NOT EXISTS `knowledge_point` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '知识点ID',
    `subject` VARCHAR(20) NOT NULL COMMENT '科目',
    `name` VARCHAR(200) NOT NULL COMMENT '知识点名称',
    `description` TEXT DEFAULT NULL COMMENT '知识点描述',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父知识点ID',
    `level` INT DEFAULT 1 COMMENT '层级深度',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `difficulty_level` INT DEFAULT 1 COMMENT '难度等级',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX `idx_subject` (`subject`),
    INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识点表';

-- 插入初始文言文数据
INSERT INTO `wenyanwen_script` (`title`, `author`, `dynasty`, `description`, `original_text`, `translation`, `difficulty_level`) VALUES
('桃花源记', '陶渊明', '东晋', '描写一个与世隔绝的理想世界，表达对和平生活的向往。', '晋太元中，武陵人捕鱼为业。缘溪行，忘路之远近。忽逢桃花林，夹岸数百步，中无杂树，芳草鲜美，落英缤纷。渔人甚异之，复前行，欲穷其林。', '东晋太元年间，武陵有个人以打鱼为生。一天他顺着溪水划船，忘记了路程有多远。忽然遇到一片桃花林，生长在溪水的两岸，长达几百步，中间没有别的树，芳香的野草鲜艳美丽，落花纷纷散在地上。渔人对此感到十分诧异，继续往前走，想走到这片林子的尽头。', 2),
('陋室铭', '刘禹锡', '唐代', '通过对陋室的描写，表达作者高洁傲岸的情操和安贫乐道的情趣。', '山不在高，有仙则名。水不在深，有龙则灵。斯是陋室，惟吾德馨。苔痕上阶绿，草色入帘青。谈笑有鸿儒，往来无白丁。可以调素琴，阅金经。', '山不在于有多高，有了仙人就成了名山。水不在于有多深，有了龙就成为有灵异的水了。这是一间简陋的屋子，只因为我品德高尚就不觉得简陋了。苔痕蔓延到台阶上，一片碧绿；草色映入帘子里，也是青葱的。', 1),
('出师表', '诸葛亮', '三国', '诸葛亮北伐前向后主刘禅上书的表文，表达忠诚和治国建议。', '先帝创业未半而中道崩殂，今天下三分，益州疲弊，此诚危急存亡之秋也。然侍卫之臣不懈于内，忠志之士忘身于外者，盖追先帝之殊遇，欲报之于陛下也。', '先帝创建的大业还未完成一半就中途去世了。现在天下分为三国，益州地区民力匮乏，这确实是国家危急存亡的关键时刻。然而朝中侍卫之臣不懈怠于内，忠诚的将士们奋不顾身于外，都是因为追念先帝的殊遇，想要报答给陛下。', 3);

-- 插入初始词汇数据
INSERT INTO `vocabulary` (`word`, `part_of_speech`, `meaning`, `phonetic_uk`, `phonetic_us`, `example_sentence`, `example_translation`, `category`, `difficulty_level`) VALUES
('adventure', 'noun', '冒险；奇遇', '/ədˈventʃə/', '/ədˈventʃər/', 'He had many adventures during his trip to Africa.', '他在非洲旅行期间有很多奇遇。', 'core', 1),
('beautiful', 'adj', '美丽的；出色的', '/ˈbjuːtɪfl/', '/ˈbjuːtɪfl/', 'The sunset was incredibly beautiful.', '日落景色美极了。', 'core', 1),
('communication', 'noun', '交流；沟通', '/kəˌmjuːnɪˈkeɪʃn/', '/kəˌmjuːnɪˈkeɪʃn/', 'Good communication is important in a team.', '良好的沟通在团队中很重要。', 'extend', 2),
('environment', 'noun', '环境', '/ɪnˈvaɪrənmənt/', '/ɪnˈvaɪrənmənt/', 'We should protect the environment.', '我们应该保护环境。', 'exam', 2),
('enthusiasm', 'noun', '热情；热忱', '/ɪnˈθjuːziæzəm/', '/ɪnˈθuːziæzəm/', 'She showed great enthusiasm for the project.', '她对项目表现出极大的热情。', 'extend', 3);

-- 插入测试用户（密码：123456，MD5：e10adc3949ba59abbe56e057f20f883e）
INSERT INTO `user` (`username`, `password`, `nickname`, `grade`, `role`) VALUES
('testuser', 'e10adc3949ba59abbe56e057f20f883e', '测试同学', '初二', 'student');

-- 插入测试用户的学生档案
INSERT INTO `student_profile` (`user_id`, `chinese_score`, `math_score`, `english_score`, `study_days`, `total_points`, `learning_goal`) VALUES
(1, 85, 78, 82, 15, 320, '期末考试各科达到90分以上');
