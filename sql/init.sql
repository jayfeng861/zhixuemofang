-- ============================================================
-- 智学魔方 (zhixue_mofang) 数据库初始化脚本
-- 数据库: MySQL 8.0+
-- 字符集: utf8mb4
-- ============================================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `zhixue_mofang` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `zhixue_mofang`;

-- ============================================================
-- 1. 用户表
-- ============================================================
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `nickname` VARCHAR(50),
  `avatar` VARCHAR(255),
  `role` VARCHAR(20) NOT NULL DEFAULT 'STUDENT',
  `phone` VARCHAR(20),
  `status` TINYINT DEFAULT 1,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ============================================================
-- 2. 学生档案表
-- ============================================================
DROP TABLE IF EXISTS `student_profile`;
CREATE TABLE `student_profile` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `grade` VARCHAR(20) COMMENT '七年级/八年级/九年级',
  `school` VARCHAR(100),
  `total_study_time` INT DEFAULT 0 COMMENT '总学习分钟',
  `consecutive_days` INT DEFAULT 0 COMMENT '连续打卡天数',
  `cube_fragments` INT DEFAULT 0 COMMENT '魔方碎片数',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生档案表';

-- ============================================================
-- 3. 知识点表
-- ============================================================
DROP TABLE IF EXISTS `knowledge_point`;
CREATE TABLE `knowledge_point` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `subject` VARCHAR(20) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
  `parent_id` BIGINT DEFAULT 0,
  `chapter` VARCHAR(100),
  `sort_order` INT DEFAULT 0,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_subject` (`subject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识点表';

-- ============================================================
-- 4. 错题表
-- ============================================================
DROP TABLE IF EXISTS `wrong_question`;
CREATE TABLE `wrong_question` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `subject` VARCHAR(20) NOT NULL,
  `question_text` TEXT NOT NULL,
  `my_answer` TEXT,
  `correct_answer` TEXT,
  `knowledge_point_id` BIGINT,
  `error_type` VARCHAR(50) COMMENT '概念混淆/计算失误/审题不清/方法错误',
  `analysis` TEXT COMMENT 'AI根因分析结果',
  `status` TINYINT DEFAULT 0 COMMENT '0未消灭/1已消灭',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_user_id` (`user_id`),
  KEY `idx_subject` (`subject`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='错题表';

-- ============================================================
-- 5. 文言文剧本表
-- ============================================================
DROP TABLE IF EXISTS `wenyanwen_script`;
CREATE TABLE `wenyanwen_script` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `author` VARCHAR(50),
  `dynasty` VARCHAR(50),
  `grade` VARCHAR(20),
  `original_text` TEXT NOT NULL,
  `translation` TEXT,
  `background` TEXT COMMENT '写作背景',
  `difficulty` INT DEFAULT 1 COMMENT '1-5难度等级',
  `cover_image` VARCHAR(255),
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文言文剧本表';

-- ============================================================
-- 6. 文言文场景表
-- ============================================================
DROP TABLE IF EXISTS `wenyanwen_scene`;
CREATE TABLE `wenyanwen_scene` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `script_id` BIGINT NOT NULL,
  `scene_order` INT DEFAULT 1,
  `title` VARCHAR(100),
  `description` TEXT,
  `dialogue` TEXT COMMENT 'JSON格式对话数据',
  KEY `idx_script_id` (`script_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文言文场景表';

-- ============================================================
-- 7. 作文提交表
-- ============================================================
DROP TABLE IF EXISTS `essay_submission`;
CREATE TABLE `essay_submission` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `content` TEXT NOT NULL,
  `type` VARCHAR(50) COMMENT '议论文/记叙文/说明文',
  `score_idea` DECIMAL(3,1) COMMENT '立意分',
  `score_structure` DECIMAL(3,1) COMMENT '结构分',
  `score_language` DECIMAL(3,1) COMMENT '语言分',
  `score_logic` DECIMAL(3,1) COMMENT '逻辑分',
  `total_score` DECIMAL(3,1),
  `ai_review` TEXT COMMENT 'AI批改详细JSON',
  `status` TINYINT DEFAULT 0 COMMENT '0待批改/1已批改',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='作文提交表';

-- ============================================================
-- 8. 词汇表
-- ============================================================
DROP TABLE IF EXISTS `vocabulary`;
CREATE TABLE `vocabulary` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `word` VARCHAR(100) NOT NULL,
  `phonetic` VARCHAR(100),
  `meaning` VARCHAR(255) NOT NULL,
  `level` VARCHAR(20) DEFAULT 'REQUIRED' COMMENT 'REQUIRED/HIGH_FREQUENCY/EXTENDED',
  `context_sentences` TEXT COMMENT 'JSON格式例句',
  `grade` VARCHAR(20),
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='词汇表';

-- ============================================================
-- 9. 口语练习表
-- ============================================================
DROP TABLE IF EXISTS `oral_practice`;
CREATE TABLE `oral_practice` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `scene_id` VARCHAR(50),
  `scene_name` VARCHAR(100),
  `dialogue_text` TEXT,
  `pronunciation_score` DECIMAL(3,1),
  `intonation_score` DECIMAL(3,1),
  `fluency_score` DECIMAL(3,1),
  `total_score` DECIMAL(3,1),
  `ai_feedback` TEXT,
  `duration` INT,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='口语练习表';

-- ============================================================
-- 10. 学习记录表
-- ============================================================
DROP TABLE IF EXISTS `learning_record`;
CREATE TABLE `learning_record` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `subject` VARCHAR(20),
  `module_type` VARCHAR(50) COMMENT '文言文剧场/作文批改/几何实验/口语对练等',
  `module_id` BIGINT,
  `duration` INT DEFAULT 0 COMMENT '学习秒数',
  `completed` TINYINT DEFAULT 0,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_user_id` (`user_id`),
  KEY `idx_module` (`module_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习记录表';

-- ============================================================
-- 11. 社区帖子表
-- ============================================================
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `title` VARCHAR(200) NOT NULL,
  `content` TEXT NOT NULL,
  `category` VARCHAR(50) COMMENT '学习心得/解题方法/备考经验/日常分享',
  `likes` INT DEFAULT 0,
  `comment_count` INT DEFAULT 0,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='社区帖子表';

-- ============================================================
-- 12. 评论表
-- ============================================================
DROP TABLE IF EXISTS `post_comment`;
CREATE TABLE `post_comment` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `post_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  KEY `idx_post_id` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- ============================================================
-- 13. 每日打卡表
-- ============================================================
DROP TABLE IF EXISTS `daily_checkin`;
CREATE TABLE `daily_checkin` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `checkin_date` DATE NOT NULL,
  `study_minutes` INT DEFAULT 0,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `uk_user_date` (`user_id`, `checkin_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='每日打卡表';


-- ############################################################
--                    插入测试数据
-- ############################################################

-- ============================================================
-- 测试用户
-- 密码均为 123456，使用 BCrypt 加密
-- ============================================================
INSERT INTO `user` (`username`, `password`, `nickname`, `avatar`, `role`, `phone`, `status`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKtZP9W', '管理员', '/static/avatar/admin.png', 'TEACHER', '13800000001', 1),
('student', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKtZP9W', '小明同学', '/static/avatar/student.png', 'STUDENT', '13800000002', 1);

-- ============================================================
-- 学生档案
-- ============================================================
INSERT INTO `student_profile` (`user_id`, `grade`, `school`, `total_study_time`, `consecutive_days`, `cube_fragments`) VALUES
(2, '八年级', '北京实验中学', 3250, 15, 48);

-- ============================================================
-- 知识点数据（语文5个 + 数学5个 + 英语5个）
-- ============================================================
INSERT INTO `knowledge_point` (`subject`, `name`, `parent_id`, `chapter`, `sort_order`) VALUES
-- 语文
('语文', '文言文实词', 0, '古诗文', 1),
('语文', '文言文虚词', 0, '古诗文', 2),
('语文', '修辞手法', 0, '现代文阅读', 3),
('语文', '议论文论证方法', 0, '写作', 4),
('语文', '记叙文线索与结构', 0, '现代文阅读', 5),
-- 数学
('数学', '一元一次方程', 0, '方程与不等式', 1),
('数学', '一次函数', 0, '函数', 2),
('数学', '全等三角形', 0, '几何', 3),
('数学', '勾股定理', 0, '几何', 4),
('数学', '数据的收集与整理', 0, '统计与概率', 5),
-- 英语
('英语', '一般现在时', 0, '时态', 1),
('英语', '现在进行时', 0, '时态', 2),
('英语', '一般过去时', 0, '时态', 3),
('英语', '定语从句', 0, '从句', 4),
('英语', '被动语态', 0, '语态', 5);

-- ============================================================
-- 文言文剧本（桃花源记、岳阳楼记、醉翁亭记）
-- ============================================================
INSERT INTO `wenyanwen_script` (`title`, `author`, `dynasty`, `grade`, `original_text`, `translation`, `background`, `difficulty`, `cover_image`) VALUES
('桃花源记', '陶渊明', '东晋', '八年级',
'晋太元中，武陵人捕鱼为业。缘溪行，忘路之远近。忽逢桃花林，夹岸数百步，中无杂树，芳草鲜美，落英缤纷。渔人甚异之，复前行，欲穷其林。\n\n林尽水源，便得一山，山有小口，仿佛若有光。便舍船，从口入。初极狭，才通人。复行数十步，豁然开朗。土地平旷，屋舍俨然，有良田、美池、桑竹之属。阡陌交通，鸡犬相闻。其中往来种作，男女衣着，悉如外人。黄发垂髫，并怡然自乐。\n\n见渔人，乃大惊，问所从来。具答之。便要还家，设酒杀鸡作食。村中闻有此人，咸来问讯。自云先世避秦时乱，率妻子邑人来此绝境，不复出焉，遂与外人间隔。问今是何世，乃不知有汉，无论魏晋。此人一一为具言所闻，皆叹惋。余人各复延至其家，皆出酒食。停数日，辞去。此中人语云：不足为外人道也。\n\n既出，得其船，便扶向路，处处志之。及郡下，诣太守，说如此。太守即遣人随其往，寻向所志，遂迷，不复得路。\n\n南阳刘子骥，高尚士也，闻之，欣然规往。未果，寻病终，后遂无问津者。',
'东晋太元年间，武陵郡有个人以打鱼为生。一天，他顺着溪水划船，忘记了路程有多远。忽然遇到一片桃花林，生长在溪水的两岸，长达几百步，中间没有别的树，芳香的野草鲜艳美丽，落花纷纷散在地上。渔人对此感到十分诧异，又向前行进，想要走到林子的尽头。\n\n桃花林在溪水发源的地方就没有了，在那里出现了一座山，山上有个小洞口，洞里隐隐约约好像有光亮。渔人便停下船，从洞口进去。起初洞口很狭窄，仅容一个人通过。又走了几十步，突然变得开阔明亮了。呈现在他眼前的是一片平坦宽广的土地，一排排整齐的房舍，还有肥沃的田地、美丽的池沼，以及桑树、竹林之类。田间小路交错相通，（村落间）能互相听见鸡鸣狗叫的声音。村里面，人们来来往往耕种劳作，男女的穿戴打扮，完全像桃花源外的世人一样。老人和小孩都安闲快乐。\n\n村里的人看到渔人，感到非常惊讶，问他是从哪里来的。渔人详细地回答了他们。村里人就邀请渔人到自己家里去，摆酒杀鸡做饭来款待他。村里的人听说来了这么一个人，全都来打听消息。他们自己说他们的祖先为了躲避秦时的战乱，带领妻子儿女和乡邻来到这个与世隔绝的地方，不再出去了，于是就同外界的人隔绝了。他们问渔人现在是什么朝代，他们竟然不知道有汉朝，更不必说魏朝和晋朝了。渔人把自己知道的事一一详尽地告诉了他们，村里人都感叹惋惜。其余的人各自又把渔人请到自己家中，都拿出美酒佳肴来款待他。渔人停留了几天，辞别离去。这里的人告诉他说："这里的情况不值得对外面的人说啊。"\n\n渔人出来后，找到了他的船，就顺着来时的路划回去，处处都做了记号。到了郡城，去拜见太守，报告了这番经历。太守立即派人跟着他去，寻找先前所做的记号，结果迷了路，再也找不到通往桃花源的路了。\n\n南阳人刘子骥，是个志向高洁的隐士，听说了这件事，高兴地计划前往。但没有实现，不久后就病死了。从此就再也没有人探访桃花源了。',
'本文作于永初二年（公元421年），即刘裕代晋的那一年。当时政治黑暗，战乱频繁，民不聊生。陶渊明因不满现实的黑暗，退隐田园，通过对桃花源的虚构描写，寄托了自己的社会理想——一个没有压迫、没有剥削、人人平等自由的社会。', 3, '/static/images/taohuayuanji.jpg'),

('岳阳楼记', '范仲淹', '北宋', '九年级',
'庆历四年春，滕子京谪守巴陵郡。越明年，政通人和，百废具兴，乃重修岳阳楼，增其旧制，刻唐贤今人诗赋于其上，嘱予作文以记之。\n\n予观夫巴陵胜状，在洞庭一湖。衔远山，吞长江，浩浩汤汤，横无际涯；朝晖夕阴，气象万千。此则岳阳楼之大观也，前人之述备矣。然则北通巫峡，南极潇湘，迁客骚人，多会于此，览物之情，得无异乎？\n\n若夫霪雨霏霏，连月不开，阴风怒号，浊浪排空；日星隐曜，山岳潜形；商旅不行，樯倾楫摧；薄暮冥冥，虎啸猿啼。登斯楼也，则有去国怀乡，忧谗畏讥，满目萧然，感极而悲者矣。\n\n至若春和景明，波澜不惊，上下天光，一碧万顷；沙鸥翔集，锦鳞游泳；岸芷汀兰，郁郁青青。而或长烟一空，皓月千里，浮光跃金，静影沉璧，渔歌互答，此乐何极！登斯楼也，则有心旷神怡，宠辱偕忘，把酒临风，其喜洋洋者矣。\n\n嗟夫！予尝求古仁人之心，或异二者之为，何哉？不以物喜，不以己悲；居庙堂之高则忧其民；处江湖之远则忧其君。是进亦忧，退亦忧。然则何时而乐耶？其必曰"先天下之忧而忧，后天下之乐而乐"乎。噫！微斯人，吾谁与归？\n\n时六年九月十五日。',
'庆历四年（公元1044年）春天，滕子京被贬职到巴陵郡做太守。到了第二年，政事顺利，百姓和乐，各种荒废了的事业都兴办起来了。于是重新修建岳阳楼，扩大它原有的规模，把唐代名家和当代人的诗赋刻在上面，嘱托我写一篇文章来记述这件事。\n\n我观看那巴陵郡的美景，全在洞庭湖上。它连接着远处的山脉，吞吐着长江的流水，浩浩荡荡，宽阔无边；早晴晚阴，气象万千。这就是岳阳楼的雄伟壮丽的景象，前人的记述已经很详尽了。既然这样，那么北面通向巫峡，南面直到潇水和湘水，被降职到外地的官员和诗人，大多在这里聚会，他们观赏自然景物而触发的感情，大概会有所不同吧？\n\n像那阴雨连绵，接连几个月不放晴，阴冷的风怒吼，浑浊的波浪排向天空；太阳和星星隐藏起光辉，山岳隐没了形体；商人和旅客不能通行，桅杆倒下，船桨折断；傍晚天色昏暗，只听到老虎的咆哮和猿猴的悲啼。这时登上这座楼啊，就会产生离开京城、怀念家乡，担心遭到诽谤、害怕受到讥讽的情感，（会觉得）满眼全是萧条的景象，感慨到了极点而十分悲伤。\n\n到了春风和煦，阳光明媚的时候，湖面平静，没有惊涛骇浪，天色湖光相连，一片碧绿，广阔无际；沙洲上的鸥鸟时而飞翔，时而停歇，美丽的鱼儿在水中游来游去；岸上的香草和小洲上的兰花，草木茂盛，青翠欲滴。有时大片烟雾完全消散，皎洁的月光一泻千里，波动的光闪着金色，静静的月影像沉入水中的玉璧，渔夫的歌声互相唱和，这种快乐哪有尽头！这时登上这座楼啊，就会感到心胸开阔、心情愉快，光荣和屈辱一并忘了，在清风中端起酒杯，高兴到了极点。\n\n唉！我曾经探求古代品德高尚的人的思想感情，或许跟上面说的两种思想感情不沾边，为什么呢？不因为外物好坏和自己得失而或喜或悲，在朝廷上做官就为平民百姓担忧；被贬退居江湖就为国君忧虑。这样看来，在朝廷做官也担忧，在僻远的江湖也担忧。既然这样，那么什么时候才快乐呢？他们一定会说"在天下人忧虑之前先忧虑，在天下人快乐之后才快乐"吧。唉！如果没有这种人，我同谁一道呢？\n\n写于庆历六年九月十五日。',
'本文写于庆历六年（1046年）。范仲淹好友滕子京被贬岳州，重修岳阳楼，请范仲淹作记。范仲淹借题发挥，通过描绘洞庭湖的壮丽景色，抒发了"先天下之忧而忧，后天下之乐而乐"的伟大政治抱负，体现了忧国忧民的高尚情操。', 4, '/static/images/yueyanglouji.jpg'),

('醉翁亭记', '欧阳修', '北宋', '九年级',
'环滁皆山也。其西南诸峰，林壑尤美，望之蔚然而深秀者，琅琊也。山行六七里，渐闻水声潺潺而泻出于两峰之间者，酿泉也。峰回路转，有亭翼然临于泉上者，醉翁亭也。作亭者谁？山之僧智仙也。名之者谁？太守自谓也。太守与客来饮于此，饮少辄醉，而年又最高，故自号曰醉翁也。醉翁之意不在酒，在乎山水之间也。山水之乐，得之心而寓之酒也。\n\n若夫日出而林霏开，云归而岩穴暝，晦明变化者，山间之朝暮也。野芳发而幽香，佳木秀而繁阴，风霜高洁，水落而石出者，山间之四时也。朝而往，暮而归，四时之景不同，而乐亦无穷也。\n\n至于负者歌于途，行者休于树，前者呼，后者应，伛偻提携，往来而不绝者，滁人游也。临溪而渔，溪深而鱼肥，酿泉为酒，泉香而酒洌；山肴野蔌，杂然而前陈者，太守宴也。宴酣之乐，非丝非竹，射者中，弈者胜，觥筹交错，起坐而喧哗者，众宾欢也。苍颜白发，颓然乎其间者，太守醉也。\n\n已而夕阳在山，人影散乱，太守归而宾客从也。树林阴翳，鸣声上下，游人去而禽鸟乐也。然而禽鸟知山林之乐，而不知人之乐；人知从太守游而乐，而不知太守之乐其乐也。醉能同其乐，醒能述以文者，太守也。太守谓谁？庐陵欧阳修也。',
'环绕着滁州的都是山。它西南方的各个山峰，树林和山谷尤其优美，远远望去，树木茂盛，又幽深又秀丽的，是琅琊山。沿着山路走六七里，渐渐听到潺潺的水声，（又看到一股水流）从两个山间飞淌下来的，是酿泉。山势回环，路也跟着转弯，有座四角翘起，像鸟张开翅膀一样高踞于泉水之上的，是醉翁亭。建造这亭子的是谁呢？是山上的和尚智仙。给它取名字的是谁呢？是太守用自己的别号（醉翁）来命名的。太守和宾客来这里喝酒，喝一点就醉了，而且年龄又最大，所以自号叫醉翁。醉翁的情趣不在于喝酒，而在于山山水水之间。欣赏山水的乐趣，领会在心里，寄托在喝酒上。\n\n至于太阳升起，树林里的雾气散了，烟云聚拢来，山谷就显得昏暗了，朝则自暗而明，暮则由明而暗，或暗或明，变化不一的，就是山中的早晨和傍晚。野花开了，有一股清幽的香味，好的树木枝叶繁茂，形成一片浓密的绿荫，天高气爽，霜色洁白，水落下去，水底的石头就露出来，这就是山中的四季。早晨进山，傍晚回来，四季的景色不同，乐趣也是无穷无尽的。\n\n至于背负着东西的人在路上欢唱，走路的人在树下休息，前面的人呼喊，后面的人应答，老人弯着腰走，小孩子由大人领着走，来来往往不断的行人，是滁州的游客。到溪边钓鱼，溪水深并且鱼肉肥美，用酿泉造酒，泉水清并且酒也清，野味野菜，杂七杂八地摆放在面前的，是太守的宴席。宴会喝酒的乐趣，不在于音乐，投壶的人中了，下棋的赢了，酒杯和酒筹交互错杂，时起时坐大声喧闹的人，是欢乐的宾客们。脸色苍老，头发花白，醉醺醺地坐在人群中间的，是太守喝醉了。\n\n不久夕阳落到西山上，人的影子散乱一地，太守回去，宾客们也跟着离开。这时树林里变得昏暗了，鸟雀到处鸣叫，游人离开后鸟儿们在欢乐。然而鸟儿们只知道山林中的快乐，却不知道人们的快乐。而人们只知道跟随太守游玩的快乐，却不知道太守以游人的快乐为快乐。喝醉了能够和大家一起欢乐，酒醒后能够用文章记述这乐事的人，是太守。太守是谁？就是庐陵人欧阳修。',
'本文写于庆历六年（1046年）。欧阳修因支持范仲淹的"庆历新政"而被贬到滁州。文章描写了滁州自然景物的优美和百姓和平安定的生活，抒发了作者"与民同乐"的政治理想。全文以"乐"字贯穿，语言优美流畅，是古代散文的典范之作。', 4, '/static/images/zuwengtingji.jpg');

-- ============================================================
-- 文言文场景数据
-- ============================================================
INSERT INTO `wenyanwen_scene` (`script_id`, `scene_order`, `title`, `description`, `dialogue`) VALUES
-- 桃花源记场景
(1, 1, '偶遇桃花林', '渔人沿溪捕鱼，忽然遇到一片美丽的桃花林', '[
  {"role": "旁白", "text": "东晋太元年间，武陵郡有个渔人，每天沿溪水划船捕鱼。"},
  {"role": "渔人", "text": "今天天气不错，顺着溪水再往前走走看。"},
  {"role": "旁白", "text": "渔人忘了路程的远近，忽然遇到一片桃花林。"},
  {"role": "渔人", "text": "咦？这里怎么有这么大一片桃花林！芳草鲜美，落花缤纷，真是美极了！"},
  {"role": "旁白", "text": "渔人十分惊讶，决定继续前行，想走到桃花林的尽头。"}
]'),
(1, 2, '发现山洞', '桃花林尽头，渔人发现一个山洞口', '[
  {"role": "旁白", "text": "桃花林在溪水发源的地方就没有了，那里出现了一座山。"},
  {"role": "渔人", "text": "山上有个小洞口，里面好像有光亮。让我进去看看！"},
  {"role": "旁白", "text": "渔人下了船，从洞口进去。起初洞口很狭窄，仅容一个人通过。"},
  {"role": "渔人", "text": "又走了几十步……哇！突然变得开阔明亮了！"}
]'),
(1, 3, '桃源人家', '渔人走进桃花源，见到了一片世外桃源', '[
  {"role": "旁白", "text": "呈现在渔人眼前的是一片平坦宽广的土地，一排排整齐的房舍。"},
  {"role": "渔人", "text": "这里有良田、美池、桑竹，田间小路交错相通，鸡鸣狗叫，好像一个太平世界！"},
  {"role": "桃源人", "text": "你是谁？从哪里来的？"},
  {"role": "渔人", "text": "我是武陵的渔人，沿溪捕鱼时不小心来到这里的。"},
  {"role": "旁白", "text": "村里人非常惊讶，纷纷邀请渔人到家中，摆酒杀鸡热情款待。"}
]'),

-- 岳阳楼记场景
(2, 1, '登楼远眺', '登上岳阳楼，远眺洞庭湖的壮美景色', '[
  {"role": "旁白", "text": "庆历四年春，滕子京被贬到巴陵郡。第二年，政事顺利，百废俱兴。"},
  {"role": "滕子京", "text": "岳阳楼已重修完毕，范兄，请你为它写一篇记吧！"},
  {"role": "范仲淹", "text": "好，让我先看看洞庭湖的景象。"},
  {"role": "旁白", "text": "只见洞庭湖衔远山、吞长江，浩浩汤汤，横无际涯；朝晖夕阴，气象万千。"}
]'),
(2, 2, '览物之情', '不同天气下登岳阳楼的感受', '[
  {"role": "旁白", "text": "若是阴雨连绵的天气，登上此楼……"},
  {"role": "范仲淹", "text": "浊浪排空，日星隐曜，满目萧然，必定会感到悲伤和忧愁。"},
  {"role": "旁白", "text": "若是春和景明的天气，登上此楼……"},
  {"role": "范仲淹", "text": "上下天光，一碧万顷，心旷神怡，必定会感到无比快乐！"}
]'),
(2, 3, '先忧后乐', '范仲淹抒发伟大的政治抱负', '[
  {"role": "范仲淹", "text": "古仁人之心，不因外物和自身遭遇而或喜或悲。"},
  {"role": "范仲淹", "text": "在朝廷做官，就为百姓担忧；在偏远的地方，就为国君忧虑。"},
  {"role": "范仲淹", "text": "先天下之忧而忧，后天下之乐而乐！"},
  {"role": "旁白", "text": "这就是范仲淹伟大的政治抱负，也是本文千古传诵的名句。"}
]'),

-- 醉翁亭记场景
(3, 1, '山行遇亭', '沿着山路前行，发现醉翁亭', '[
  {"role": "旁白", "text": "环绕着滁州的都是山。西南方的各个山峰，树林和山谷尤其优美。"},
  {"role": "欧阳修", "text": "远远望去，那树木茂盛又幽深秀丽的，就是琅琊山。"},
  {"role": "旁白", "text": "沿着山路走六七里，渐渐听到潺潺的水声，从两峰之间飞泻而下的是酿泉。"},
  {"role": "欧阳修", "text": "峰回路转，有座亭子像鸟张开翅膀一样高踞于泉上，这就是醉翁亭。"}
]'),
(3, 2, '醉翁之意', '太守与宾客在亭中饮酒之乐', '[
  {"role": "旁白", "text": "欧阳修给自己起了个别号叫醉翁，因为喝一点就醉了。"},
  {"role": "欧阳修", "text": "醉翁之意不在酒，在乎山水之间也。山水之乐，得之心而寓之酒也。"},
  {"role": "旁白", "text": "野花开了有幽香，树木茂盛有浓阴，四时之景不同，乐趣无穷。"},
  {"role": "宾客", "text": "太守大人，请再饮一杯！"},
  {"role": "欧阳修", "text": "好！与民同乐，何乐而不为！"}
]'),
(3, 3, '人知与不知', '太守与众人的快乐', '[
  {"role": "旁白", "text": "夕阳西下，太守归去，宾客们也纷纷跟随。"},
  {"role": "旁白", "text": "树林里变得昏暗，鸟雀到处鸣叫。然而鸟儿只知道山林中的快乐，却不知道人们的快乐。"},
  {"role": "旁白", "text": "人们只知道跟随太守游玩的快乐，却不知道太守以游人的快乐为快乐。"},
  {"role": "欧阳修", "text": "醉能同其乐，醒能述以文者，太守也。"}
]');

-- ============================================================
-- 英语词汇（30个初中必考词汇）
-- ============================================================
INSERT INTO `vocabulary` (`word`, `phonetic`, `meaning`, `level`, `context_sentences`, `grade`) VALUES
('achieve', '/əˈtʃiːv/', 'v. 实现；达到', 'REQUIRED',
 '[{"en": "He achieved his dream of becoming a doctor.", "zh": "他实现了成为医生的梦想。"}, {"en": "You can achieve anything if you work hard.", "zh": "只要你努力，什么都能实现。"}]', '八年级'),
('advantage', '/ədˈvɑːntɪdʒ/', 'n. 优势；有利条件', 'REQUIRED',
 '[{"en": "Learning English has many advantages.", "zh": "学习英语有很多优势。"}, {"en": "What is the advantage of this method?", "zh": "这个方法的优势是什么？"}]', '八年级'),
('believe', '/bɪˈliːv/', 'v. 相信；认为', 'REQUIRED',
 '[{"en": "I believe you can do it.", "zh": "我相信你能做到。"}, {"en": "Do you believe in ghosts?", "zh": "你相信有鬼吗？"}]', '七年级'),
('challenge', '/ˈtʃælɪndʒ/', 'n./v. 挑战', 'REQUIRED',
 '[{"en": "This is a big challenge for me.", "zh": "这对我来说是一个大挑战。"}, {"en": "I challenge you to a game of chess.", "zh": "我挑战你下一盘棋。"}]', '九年级'),
('communicate', '/kəˈmjuːnɪkeɪt/', 'v. 交流；沟通', 'REQUIRED',
 '[{"en": "We communicate by email every day.", "zh": "我们每天通过电子邮件交流。"}, {"en": "It is important to communicate with your parents.", "zh": "与父母沟通很重要。"}]', '八年级'),
('decision', '/dɪˈsɪʒn/', 'n. 决定；决策', 'REQUIRED',
 '[{"en": "She made the right decision.", "zh": "她做出了正确的决定。"}, {"en": "I need to make a decision by tomorrow.", "zh": "我需要在明天之前做出决定。"}]', '九年级'),
('environment', '/ɪnˈvaɪrənmənt/', 'n. 环境', 'REQUIRED',
 '[{"en": "We should protect the environment.", "zh": "我们应该保护环境。"}, {"en": "The environment is getting worse.", "zh": "环境正在变得越来越差。"}]', '八年级'),
('experience', '/ɪkˈspɪəriəns/', 'n./v. 经验；经历', 'REQUIRED',
 '[{"en": "He has a lot of experience in teaching.", "zh": "他有丰富的教学经验。"}, {"en": "I experienced many things during the trip.", "zh": "在旅行中我经历了很多事情。"}]', '八年级'),
('fortunate', '/ˈfɔːtʃənət/', 'adj. 幸运的', 'HIGH_FREQUENCY',
 '[{"en": "I was fortunate to meet you.", "zh": "我很幸运遇到你。"}, {"en": "She is fortunate to have such a good teacher.", "zh": "她很幸运有这么好的老师。"}]', '九年级'),
('government', '/ˈɡʌvənmənt/', 'n. 政府', 'REQUIRED',
 '[{"en": "The government made a new law.", "zh": "政府制定了一项新法律。"}, {"en": "The government is trying to solve the problem.", "zh": "政府正在努力解决这个问题。"}]', '九年级'),
('habit', '/ˈhæbɪt/', 'n. 习惯', 'REQUIRED',
 '[{"en": "Reading is a good habit.", "zh": "阅读是一个好习惯。"}, {"en": "He has a habit of getting up early.", "zh": "他有早起的习惯。"}]', '七年级'),
('imagine', '/ɪˈmædʒɪn/', 'v. 想象', 'REQUIRED',
 '[{"en": "Can you imagine life without water?", "zh": "你能想象没有水的生活吗？"}, {"en": "Imagine that you are a bird.", "zh": "想象你是一只鸟。"}]', '八年级'),
('journey', '/ˈdʒɜːni/', 'n. 旅行；旅程', 'REQUIRED',
 '[{"en": "The journey took three hours.", "zh": "这次旅行花了三个小时。"}, {"en": "Life is a long journey.", "zh": "人生是一段漫长的旅程。"}]', '八年级'),
('knowledge', '/ˈnɒlɪdʒ/', 'n. 知识；学问', 'REQUIRED',
 '[{"en": "Knowledge is power.", "zh": "知识就是力量。"}, {"en": "He has a lot of knowledge about history.", "zh": "他有丰富的历史知识。"}]', '九年级'),
('lonely', '/ˈləʊnli/', 'adj. 孤独的；寂寞的', 'HIGH_FREQUENCY',
 '[{"en": "She felt lonely in the new school.", "zh": "她在新学校感到孤独。"}, {"en": "He lives a lonely life.", "zh": "他过着孤独的生活。"}]', '八年级'),
('mention', '/ˈmenʃn/', 'v. 提到；提及', 'HIGH_FREQUENCY',
 '[{"en": "He mentioned your name.", "zh": "他提到了你的名字。"}, {"en": "Don\'t mention it.", "zh": "不用谢（不客气）。"}]', '九年级'),
('necessary', '/ˈnesəsəri/', 'adj. 必要的；必需的', 'REQUIRED',
 '[{"en": "It is necessary to study hard.", "zh": "努力学习是必要的。"}, {"en": "Sleep is necessary for health.", "zh": "睡眠对健康是必要的。"}]', '八年级'),
('opportunity', '/ˌɒpəˈtjuːnəti/', 'n. 机会', 'REQUIRED',
 '[{"en": "This is a great opportunity.", "zh": "这是一个很好的机会。"}, {"en": "Don\'t miss this opportunity.", "zh": "不要错过这个机会。"}]', '九年级'),
('patient', '/ˈpeɪʃnt/', 'adj. 耐心的；n. 病人', 'REQUIRED',
 '[{"en": "Please be patient.", "zh": "请耐心一点。"}, {"en": "The doctor is examining the patient.", "zh": "医生正在检查病人。"}]', '八年级'),
('quality', '/ˈkwɒləti/', 'n. 质量；品质', 'REQUIRED',
 '[{"en": "The quality of this product is excellent.", "zh": "这个产品的质量非常好。"}, {"en": "He is a person of good quality.", "zh": "他是一个品质很好的人。"}]', '九年级'),
('recommend', '/ˌrekəˈmend/', 'v. 推荐', 'HIGH_FREQUENCY',
 '[{"en": "I recommend this book to you.", "zh": "我向你推荐这本书。"}, {"en": "Can you recommend a good restaurant?", "zh": "你能推荐一家好餐厅吗？"}]', '九年级'),
('succeed', '/səkˈsiːd/', 'v. 成功', 'REQUIRED',
 '[{"en": "If you work hard, you will succeed.", "zh": "如果你努力，就会成功。"}, {"en": "She succeeded in passing the exam.", "zh": "她成功通过了考试。"}]', '八年级'),
('technology', '/tekˈnɒlədʒi/', 'n. 技术', 'REQUIRED',
 '[{"en": "Technology changes our life.", "zh": "科技改变了我们的生活。"}, {"en": "He is interested in modern technology.", "zh": "他对现代科技很感兴趣。"}]', '九年级'),
('valuable', '/ˈvæljuəbl/', 'adj. 有价值的；贵重的', 'HIGH_FREQUENCY',
 '[{"en": "Time is the most valuable thing.", "zh": "时间是最宝贵的东西。"}, {"en": "This is a valuable experience.", "zh": "这是一次宝贵的经验。"}]', '九年级'),
('volunteer', '/ˌvɒlənˈtɪə/', 'n./v. 志愿者；自愿做', 'HIGH_FREQUENCY',
 '[{"en": "She works as a volunteer.", "zh": "她是一名志愿者。"}, {"en": "He volunteered to help the old.", "zh": "他自愿帮助老人。"}]', '八年级'),
('wonderful', '/ˈwʌndəfl/', 'adj. 精彩的；奇妙的', 'REQUIRED',
 '[{"en": "What a wonderful day!", "zh": "多么美好的一天！"}, {"en": "The movie was wonderful.", "zh": "这部电影很精彩。"}]', '七年级'),
('excellent', '/ˈeksələnt/', 'adj. 优秀的；极好的', 'REQUIRED',
 '[{"en": "She is an excellent student.", "zh": "她是一名优秀的学生。"}, {"en": "The food here is excellent.", "zh": "这里的食物很棒。"}]', '七年级'),
('consider', '/kənˈsɪdə/', 'v. 考虑；认为', 'REQUIRED',
 '[{"en": "Please consider my advice.", "zh": "请考虑我的建议。"}, {"en": "I consider him a good friend.", "zh": "我认为他是一个好朋友。"}]', '九年级'),
('develop', '/dɪˈveləp/', 'v. 发展；开发', 'REQUIRED',
 '[{"en": "China is developing very fast.", "zh": "中国发展得非常快。"}, {"en": "We need to develop new skills.", "zh": "我们需要发展新技能。"}]', '八年级'),
('pollution', '/pəˈluːʃn/', 'n. 污染', 'REQUIRED',
 '[{"en": "Air pollution is a serious problem.", "zh": "空气污染是一个严重的问题。"}, {"en": "We should reduce pollution.", "zh": "我们应该减少污染。"}]', '八年级'),
('research', '/rɪˈsɜːtʃ/', 'n./v. 研究；调查', 'HIGH_FREQUENCY',
 '[{"en": "He is doing research on space.", "zh": "他正在进行太空研究。"}, {"en": "The research shows interesting results.", "zh": "研究显示了有趣的结果。"}]', '九年级');

-- ============================================================
-- 作文提交（5篇，带AI批改评分）
-- ============================================================
INSERT INTO `essay_submission` (`user_id`, `title`, `content`, `type`, `score_idea`, `score_structure`, `score_language`, `score_logic`, `total_score`, `ai_review`, `status`) VALUES
(2, '坚持的力量', '人生路上，坚持是最可贵的品质。古语有云："锲而不舍，金石可镂。"只有坚持，才能到达成功的彼岸。\n\n我曾经也是一个容易放弃的人。记得小学三年级时，我开始学习钢琴。起初的新鲜感很快就被枯燥的练习取代了。每天重复弹奏同样的曲子，手指酸痛，心里烦闷。有好几次，我都想放弃。但妈妈告诉我："任何技能的掌握都需要时间，坚持下去，你会看到不一样的自己。"\n\n我听进了妈妈的话，坚持每天练习。一年后，我参加了学校的才艺表演，当我流畅地演奏完一首优美的曲子时，台下响起了热烈的掌声。那一刻，我明白了坚持的意义。\n\n坚持不是盲目地固执，而是在明确目标后，不轻言放弃。爱迪生发明电灯，失败了一千多次，但他坚持了下来，最终成功了。司马迁遭受宫刑，仍坚持写完了《史记》。他们的故事告诉我们，坚持的力量是无穷的。\n\n让我们学会坚持，在人生的道路上，用坚持去创造属于自己的精彩。', '议论文', 8.5, 7.5, 8.0, 8.0, 32.0,
 '{"idea":"文章立意明确，围绕坚持展开论述，引用古语恰当，论证角度合理。","structure":"结构完整，有开头引题、中间举例、结尾总结，但过渡略显生硬。","language":"语言流畅，使用了引用和排比等修辞手法，但部分句子可以更精练。","logic":"论证逻辑清晰，举例恰当，但司马迁的例子与中心论点关联度可以更强。","suggestion":"建议在段落间增加过渡句，使文章更加连贯。"}', 1),

(2, '难忘的一件事', '那是一个下雨的傍晚，我撑着伞走在回家的路上。雨下得很大，路面上积水成河。\n\n当我走到一个路口时，我看到一位老奶奶站在路边，手里提着一袋沉重的蔬菜，身上淋着雨。她看起来很焦急，似乎在等什么人。我犹豫了一下，还是走了过去，把伞撑到她的头上。\n\n"奶奶，我送您回家吧。"我说。\n\n老奶奶感激地看着我，说："好孩子，谢谢你。"\n\n就这样，我一手撑着伞，一手扶着老奶奶，在雨中慢慢走着。伞太小，我的半边肩膀都被淋湿了，但看到老奶奶安全的笑容，我觉得这一切都是值得的。\n\n这件事虽然过去很久了，但每次下雨时，我总会想起那个傍晚。它让我明白，帮助别人的快乐是任何东西都无法替代的。从那以后，我养成了乐于助人的习惯，因为我知道，一个小小的善举，可能会温暖一个人的心。', '记叙文', 7.0, 8.0, 8.5, 7.5, 31.0,
 '{"idea":"通过一件小事表达了助人为乐的主题，立意积极向上。","structure":"叙事结构清晰，有起因、经过、结果，但中间部分描写可以更丰富。","language":"语言质朴自然，对话简洁有效，但缺少一些生动的细节描写。","logic":"叙事逻辑合理，情感递进自然，结尾升华主题恰到好处。","suggestion":"可以在雨中送老奶奶的路上增加一些环境描写和内心独白，使文章更生动。"}', 1),

(2, '我的梦想', '每个人都有自己的梦想，我的梦想是成为一名科学家。\n\n小时候，我最喜欢看的就是关于宇宙的纪录片。当我看到那些遥远的星系、神秘的黑洞，我的心中充满了好奇和向往。我想知道宇宙有多大？有没有外星人？黑洞里面是什么？这些问题驱使着我努力学习。\n\n为了实现这个梦想，我给自己制定了一个学习计划。首先，我要学好数学和物理，这是科学的基础。其次，我要大量阅读科学类书籍，拓展自己的知识面。最后，我要积极参加科技竞赛，锻炼自己的实践能力。\n\n我知道，实现梦想并不容易。但我相信，只要我坚持不懈地努力，就一定能离梦想越来越近。正如鲁迅先生所说："世上本没有路，走的人多了，也便成了路。"我要做自己梦想路上的开拓者。\n\n未来，我希望能够为人类的科技进步贡献自己的一份力量。这就是我的梦想，一个关于探索和发现的梦想。', '议论文', 7.5, 8.0, 7.5, 8.0, 31.0,
 '{"idea":"梦想主题明确，表达了成为科学家的愿望，但立意略显空泛，缺少独特的思考角度。","structure":"结构层次分明，先说梦想来源，再说行动计划，最后升华主题，布局合理。","language":"语言表达清楚，引用鲁迅名言恰当，但句式变化不够丰富。","logic":"论证思路清晰，计划具体可行，但梦想与计划的衔接可以更紧密。","suggestion":"建议增加一些个人具体的经历或感悟，使文章更有说服力和感染力。"}', 1),

(2, '科技与生活', '随着科技的快速发展，我们的生活发生了翻天覆地的变化。从智能手机到人工智能，科技正以前所未有的速度改变着我们的生活方式。\n\n首先，科技改变了我们的学习方式。过去，我们只能在教室里学习，获取知识的渠道有限。现在，通过互联网，我们可以随时随地获取海量的学习资源。在线教育平台、电子书、学习APP等工具，让学习变得更加便捷和高效。\n\n其次，科技改变了我们的交流方式。以前，人们只能通过书信或电话联系，而且费用高昂。现在，微信、QQ等社交软件让我们可以免费与远方的朋友视频聊天，距离不再是障碍。\n\n然而，科技也带来了一些问题。很多人沉迷于手机游戏和短视频，影响了学习和健康。此外，网络安全问题也日益严重，个人信息泄露事件时有发生。\n\n因此，我们应该合理利用科技，做科技的主人而不是奴隶。只有这样，科技才能真正造福我们的生活。', '议论文', 8.0, 8.5, 8.0, 8.5, 33.0,
 '{"idea":"辩证地看待科技对生活的影响，立意全面，体现了较强的思辨能力。","structure":"采用先正后反再总结的结构，层次清晰，论证有序。","language":"语言表达准确，使用了对比、举例等论证方法，用词得当。","logic":"正反两面论述逻辑严密，论据充分，结论水到渠成。","suggestion":"可以适当增加一些具体的数据或案例，使论证更有说服力。"}', 1),

(2, '春天的校园', '春天来了，我们的校园变得格外美丽。\n\n走进校门，首先映入眼帘的是一排高大的梧桐树。春天里，梧桐树长出了嫩绿的新叶，在微风中轻轻摇曳，发出沙沙的声响。阳光透过树叶洒下斑驳的光影，犹如一幅美丽的画卷。\n\n再往前走，就是学校的花园了。花园里种满了各种各样的花，有红色的月季、粉色的樱花、白色的玉兰花……它们争奇斗艳，引来蝴蝶和蜜蜂在花间飞舞。花丛中，几只小鸟在枝头欢快地歌唱，好像在赞美这美好的春天。\n\n操场上，同学们在快乐地玩耍。有的在打篮球，有的在踢足球，还有的在跑步。欢笑声和加油声此起彼伏，充满了青春的活力。\n\n我爱春天的校园，它不仅美化了我们的学习环境，更给了我们无限的活力和希望。在这个美丽的校园里，我们要更加努力学习，不辜负这美好的时光。', '记叙文', 7.0, 7.0, 8.0, 7.0, 29.0,
 '{"idea":"文章描写了春天校园的美丽景色，表达了对校园的热爱之情，但立意深度有待提升。","structure":"按照空间顺序组织材料，结构清晰，但各段之间变化较少，缺少节奏感。","language":"语言优美，使用了比喻、排比等修辞手法，描写生动细致。","logic":"描写顺序合理，从校门到花园再到操场，层次分明。","suggestion":"可以在结尾处更深入地写出校园之美的深层意义，使文章立意更加深刻。"}', 1);

-- ============================================================
-- 错题记录
-- ============================================================
INSERT INTO `wrong_question` (`user_id`, `subject`, `question_text`, `my_answer`, `correct_answer`, `knowledge_point_id`, `error_type`, `analysis`, `status`) VALUES
(2, '数学', '解方程：2x + 5 = 13', 'x = 3', 'x = 4', 6, '计算失误',
 '{"root_cause":"在移项过程中，5移到右边应为-5，即2x=13-5=8，x=4。学生可能计算13-5=6，得到x=3。","suggestion":"移项时注意符号变化，建议多做基础练习巩固运算能力。"}', 0),
(2, '数学', '已知三角形两边长分别为3和4，求第三边的取值范围', '1 < c < 7', '1 < c < 7（正确）实际错误：学生最初答成 c > 1', '1 < c < 7', 8, '概念混淆',
 '{"root_cause":"学生混淆了三角形三边关系定理的表述。正确结论应为：|a-b|<c<a+b，即1<c<7。学生只写出了c>1，遗漏了c<7的条件。","suggestion":"复习三角形三边关系定理，注意取值范围的两个边界条件。"}', 1),
(2, '语文', '"落英缤纷"中"落英"的意思是', '落花', '落花（正确）实际错误：学生最初答成"落叶"', '落花', 1, '概念混淆',
 '{"root_cause":"学生将"落英"理解为"落叶"，实际上"英"在古文中通"花"，"落英"即"落花"。","suggestion":"加强文言文词汇积累，注意古今异义词的辨析。"}', 1),
(2, '英语', '选择正确的时态：She ___ (go) to school every day.', 'She goes to school every day（正确）实际错误：学生最初选了 going', 'She goes to school every day', 11, '审题不清',
 '{"root_cause":"学生没有注意到关键词"every day"表示经常性动作，应使用一般现在时第三人称单数形式goes。学生可能因粗心选择了going。","suggestion":"做选择题时先找出时间标志词，确定正确时态后再选答案。"}', 1),
(2, '数学', '已知一次函数y=2x+1，当x=3时，y的值是多少？', 'y = 5', 'y = 7', 7, '计算失误',
 '{"root_cause":"将x=3代入y=2x+1时，计算2*3+1应为7，但学生得到5，可能是将2*3算成了4。","suggestion":"加强基础运算训练，特别是乘法口诀的熟练度。"}', 0);

-- ============================================================
-- 学习记录
-- ============================================================
INSERT INTO `learning_record` (`user_id`, `subject`, `module_type`, `module_id`, `duration`, `completed`) VALUES
(2, '语文', '文言文剧场', 1, 1800, 1),
(2, '语文', '文言文剧场', 2, 2400, 1),
(2, '语文', '文言文剧场', 3, 1500, 0),
(2, '语文', '作文批改', 1, 600, 1),
(2, '语文', '作文批改', 4, 600, 1),
(2, '数学', '错题消灭', 1, 900, 0),
(2, '数学', '错题消灭', 5, 1200, 1),
(2, '英语', '口语对练', 1, 1500, 1),
(2, '英语', '口语对练', 2, 1800, 1),
(2, '英语', '口语对练', 3, 1200, 0);

-- ============================================================
-- 社区帖子
-- ============================================================
INSERT INTO `post` (`user_id`, `title`, `content`, `category`, `likes`, `comment_count`) VALUES
(2, '文言文学习心得分享', '最近在用智学魔方的文言文剧场功能学习文言文，效果真的很好！通过角色扮演的方式理解文言文，比死记硬背有趣多了。特别是桃花源记，我扮演渔人的角色，感觉就像亲身经历了一样，对课文的理解也更深了。建议大家试试这个方法！', '学习心得', 15, 3),
(2, '解一元一次方程的小技巧', '分享一个解一元一次方程的小技巧：移项的时候，可以在方程两边画一个箭头，标明哪个数要从哪边移到哪边，这样就不容易搞错符号了。另外，做完之后一定要验算！把x的值代回原方程，看看等式是否成立。这个习惯帮我避免了很多计算错误。', '解题方法', 22, 5),
(2, '八年级期末考试备考经验', '期末考试快到了，分享一下我的备考经验：1. 语文：重点复习文言文实词虚词，作文多准备几个素材。2. 数学：多做错题，特别是方程和几何证明。3. 英语：背单词不能停，每天至少复习30个。4. 理综：注重实验题和概念理解。祝大家考试顺利！', '备考经验', 38, 8),
(2, '今天连续打卡第15天了', '坚持学习第15天！从开始的每天只学30分钟，到现在每天能学1个小时以上，真的很有成就感。智学魔方的打卡功能很好地激励了我，看着连续打卡的天数不断增加，就不好意思中断了。一起加油吧！', '日常分享', 8, 2),
(2, '英语口语练习心得', '以前我英语口语很差，不敢开口说。用了智学魔方的口语对练功能后，AI会给出发音和语调的评分，还有改进建议，慢慢就不那么紧张了。坚持练习了一个月，感觉口语进步了不少。关键是要大胆开口，不要怕犯错！', '学习心得', 12, 4);

-- ============================================================
-- 评论
-- ============================================================
INSERT INTO `post_comment` (`post_id`, `user_id`, `content`) VALUES
(1, 1, '很好的学习心得！文言文确实需要理解性记忆，死记硬背效率太低了。'),
(1, 2, '谢谢老师鼓励！我继续加油。'),
(2, 1, '移项技巧总结得很到位，验算的好习惯也值得推广。'),
(2, 2, '谢谢大家的支持！希望能帮到更多同学。'),
(3, 1, '备考计划很全面，建议加上错题回顾的时间。'),
(3, 2, '好的，我会注意错题复习的，谢谢老师提醒！'),
(4, 1, '坚持打卡15天很棒，继续保持！'),
(5, 1, '口语练习就是要多开口，你说得很对。');

-- ============================================================
-- 每日打卡记录
-- ============================================================
INSERT INTO `daily_checkin` (`user_id`, `checkin_date`, `study_minutes`) VALUES
(2, '2026-06-20', 45),
(2, '2026-06-21', 60),
(2, '2026-06-22', 30),
(2, '2026-06-23', 75),
(2, '2026-06-24', 50),
(2, '2026-06-25', 90),
(2, '2026-06-26', 40),
(2, '2026-06-27', 65),
(2, '2026-06-28', 55),
(2, '2026-06-29', 80),
(2, '2026-06-30', 70),
(2, '2026-07-01', 45),
(2, '2026-07-02', 95),
(2, '2026-07-03', 60),
(2, '2026-07-04', 55);

-- ============================================================
-- 口语练习记录（3条）
-- ============================================================
INSERT INTO `oral_practice` (`user_id`, `scene_id`, `scene_name`, `dialogue_text`, `pronunciation_score`, `intonation_score`, `fluency_score`, `total_score`, `ai_feedback`, `duration`) VALUES
(2, 'restaurant', '餐厅点餐',
 '{"dialogue": [{"role": "waiter", "en": "Good evening. Welcome to our restaurant. How many people?", "zh": "晚上好，欢迎光临。请问几位？"}, {"role": "student", "en": "Two, please. Can we have a table by the window?", "zh": "两位，请给我靠窗的桌子。"}, {"role": "waiter", "en": "Of course. Here is the menu. Would you like to order now?", "zh": "当然可以。这是菜单，现在点菜吗？"}, {"role": "student", "en": "Yes. I would like a beef steak and a glass of orange juice, please.", "zh": "好的，我要一份牛排和一杯橙汁。"}]}',
 7.5, 7.0, 6.5, 7.0,
 '{"pronunciation":"整体发音较好，但"restaurant"一词的重音位置需要纠正，应重读第一音节。","intonation":"语调较为平淡，疑问句末尾应使用升调。","fluency":"基本流畅，但在长句时有停顿，建议加强日常口语练习。","overall":"餐厅点餐场景完成度良好，继续练习可以提升至更高水平。"}', 180),

(2, 'airport', '机场问路',
 '{"dialogue": [{"role": "student", "en": "Excuse me, could you tell me how to get to the boarding gate?", "zh": "请问登机口怎么走？"}, {"role": "staff", "en": "Sure. Go straight ahead and turn left at the end of the corridor.", "zh": "好的，往前直走，在走廊尽头左转。"}, {"role": "student", "en": "Thank you. How long does it take to walk there?", "zh": "谢谢，走路需要多长时间？"}, {"role": "staff", "en": "About ten minutes on foot.", "zh": "步行大约十分钟。"}]}',
 7.0, 7.5, 7.0, 7.2,
 '{"pronunciation":""boarding"和"corridor"的发音需要加强练习。","intonation":"语调自然，礼貌用语使用恰当。","fluency":"对话较为流畅，停顿较少，表现不错。","overall":"机场问路场景表现良好，建议多练习方位相关的词汇。"}', 150),

(2, 'shopping', '商场购物',
 '{"dialogue": [{"role": "assistant", "en": "Can I help you find anything?", "zh": "需要帮忙找什么吗？"}, {"role": "student", "en": "Yes, I am looking for a birthday gift for my friend.", "zh": "是的，我想给朋友选一个生日礼物。"}, {"role": "assistant", "en": "What does your friend like?", "zh": "你的朋友喜欢什么？"}, {"role": "student", "en": "She likes reading. Do you have any good books?", "zh": "她喜欢看书，你们有什么好书吗？"}, {"role": "assistant", "en": "We have a great selection of novels over here.", "zh": "这边有很棒的小说选择。"}]}',
 8.0, 7.5, 7.5, 7.7,
 '{"pronunciation":"发音清晰准确，词汇量丰富。","intonation":"语调自然，表情达意较好。","fluency":"对话流畅，能够自如应对，有较好的语言组织能力。","overall":"商场购物场景表现优秀，英语口语能力有明显进步！"}', 200);

-- ============================================================
-- 初始化完成
-- ============================================================
SELECT 'zhixue_mofang 数据库初始化完成！' AS message;
SELECT CONCAT('用户表: ', COUNT(*), ' 条记录') AS summary FROM `user`
UNION ALL
SELECT CONCAT('学生档案: ', COUNT(*), ' 条记录') FROM `student_profile`
UNION ALL
SELECT CONCAT('知识点: ', COUNT(*), ' 条记录') FROM `knowledge_point`
UNION ALL
SELECT CONCAT('文言文剧本: ', COUNT(*), ' 条记录') FROM `wenyanwen_script`
UNION ALL
SELECT CONCAT('文言文场景: ', COUNT(*), ' 条记录') FROM `wenyanwen_scene`
UNION ALL
SELECT CONCAT('词汇: ', COUNT(*), ' 条记录') FROM `vocabulary`
UNION ALL
SELECT CONCAT('作文提交: ', COUNT(*), ' 条记录') FROM `essay_submission`
UNION ALL
SELECT CONCAT('错题: ', COUNT(*), ' 条记录') FROM `wrong_question`
UNION ALL
SELECT CONCAT('学习记录: ', COUNT(*), ' 条记录') FROM `learning_record`
UNION ALL
SELECT CONCAT('社区帖子: ', COUNT(*), ' 条记录') FROM `post`
UNION ALL
SELECT CONCAT('评论: ', COUNT(*), ' 条记录') FROM `post_comment`
UNION ALL
SELECT CONCAT('每日打卡: ', COUNT(*), ' 条记录') FROM `daily_checkin`
UNION ALL
SELECT CONCAT('口语练习: ', COUNT(*), ' 条记录') FROM `oral_practice`;
