INSERT INTO sys_user (username, password, nickname, email, role) VALUES
('student001', 'e10adc3949ba59abbe56e057f20f883e', '学霸小明', 'student001@zhixue.com', 'student'),
('student002', 'e10adc3949ba59abbe56e057f20f883e', '努力小红', 'student002@zhixue.com', 'student'),
('teacher001', 'e10adc3949ba59abbe56e057f20f883e', '张老师', 'teacher001@zhixue.com', 'teacher');

INSERT INTO wenyanwen_script (title, author, dynasty, description, original_text, translation, difficulty_level) VALUES
('桃花源记', '陶渊明', '东晋', '描绘了一个与世隔绝的理想世界，展现了作者对美好生活的向往。',
'晋太元中，武陵人捕鱼为业。缘溪行，忘路之远近。忽逢桃花林，夹岸数百步，中无杂树，芳草鲜美，落英缤纷。渔人甚异之，复前行，欲穷其林。',
'东晋太元年间，武陵郡有个人以捕鱼为生。一天，他顺着溪水划船前行，忘记了路程的远近。忽然遇到一片桃花林，生长在溪水的两岸，长达几百步，中间没有别的树，花草鲜嫩美丽，落花纷纷。渔人对此感到非常诧异，继续往前走，想走到林子的尽头。', 1),
('岳阳楼记', '范仲淹', '北宋', '通过描绘岳阳楼的壮丽景色，表达了作者先天下之忧而忧，后天下之乐而乐的崇高情怀。',
'庆历四年春，滕子京谪守巴陵郡。越明年，政通人和，百废具兴。乃重修岳阳楼，增其旧制，刻唐贤今人诗赋于其上。属予作文以记之。',
'庆历四年春天，滕子京被贬官到巴陵郡做太守。到了第二年，政事顺利，百姓和乐，各种荒废了的事业都兴办起来了。于是重新修建岳阳楼，扩大它原有的规模，把唐代名人和当代人的诗赋刻在上面。嘱托我写一篇文章来记述这件事情。', 2),
('出师表', '诸葛亮', '三国', '诸葛亮在北伐前写给刘禅的表文，表达了对蜀汉的忠诚和兴复汉室的决心。',
'先帝创业未半而中道崩殂，今天下三分，益州疲弊，此诚危急存亡之秋也。然侍卫之臣不懈于内，忠志之士忘身于外者，盖追先帝之殊遇，欲报之于陛下也。',
'先帝开创的事业还没有完成一半，就中途去世了。现在天下分为三国，我们蜀汉国力衰弱，这确实是国家危急存亡的时刻啊。然而侍卫大臣们在宫廷里毫不懈怠，忠诚有志的将士们在战场上舍生忘死，大概是追念先帝对他们的特殊待遇，想要报答给陛下吧。', 3),
('醉翁亭记', '欧阳修', '北宋', '通过描写醉翁亭的景色和游人的欢乐，表达了作者与民同乐的思想。',
'环滁皆山也。其西南诸峰，林壑尤美，望之蔚然而深秀者，琅琊也。山行六七里，渐闻水声潺潺而泻出于两峰之间者，酿泉也。峰回路转，有亭翼然临于泉上者，醉翁亭也。',
'环绕滁州的都是山。那西南的几座山峰，树林和山谷尤其优美。一眼望去树木茂盛，又幽深又秀丽的，那是琅琊山。沿着山路走六七里，渐渐听到潺潺的水声，看到流水从两座山峰之间倾泻而出的，那是酿泉。泉水沿着山峰折绕，沿着山路拐弯，有一座亭子像飞鸟展翅似地，飞架在泉上，那就是醉翁亭。', 2),
('陋室铭', '刘禹锡', '唐代', '通过描写简陋的居室，表达了作者安贫乐道的高尚情操。',
'山不在高，有仙则名。水不在深，有龙则灵。斯是陋室，惟吾德馨。苔痕上阶绿，草色入帘青。',
'山不在于高，有了仙人就成了名山。水不在于深，有了龙就显得有了灵气。这是简陋的屋子，只是我（住屋的人）的品德好（就不觉得简陋了）。苔痕碧绿，长到阶上；草色青葱，映入帘里。', 1);

INSERT INTO vocabulary (word, part_of_speech, meaning, phonetic_uk, phonetic_us, example_sentence, example_translation, category, difficulty_level) VALUES
('knowledge', 'noun', '知识；学问', '/ˈnɒlɪdʒ/', '/ˈnɑːlɪdʒ/', 'Knowledge is power.', '知识就是力量。', 'core', 1),
('success', 'noun', '成功；胜利', '/səkˈses/', '/səkˈses/', 'Success comes from hard work.', '成功来自努力。', 'core', 1),
('challenge', 'noun', '挑战；难题', '/ˈtʃælɪndʒ/', '/ˈtʃælɪndʒ/', 'Life is full of challenges.', '生活充满挑战。', 'core', 1),
('understand', 'verb', '理解；懂得', '/ˌʌndəˈstænd/', '/ˌʌndərˈstænd/', 'I understand what you mean.', '我理解你的意思。', 'core', 1),
('teacher', 'noun', '教师；老师', '/ˈtiːtʃər/', '/ˈtiːtʃər/', 'My teacher inspired me to learn.', '我的老师激励我学习。', 'core', 1),
('opportunity', 'noun', '机会；时机', '/ˌɒpəˈtjuːnəti/', '/ˌɑːpərˈtuːnəti/', 'This is a great opportunity for growth.', '这是一个很好的成长机会。', 'core', 2),
('persevere', 'verb', '坚持；坚持不懈', '/ˌpɜːsəˈvɪə/', '/ˌpɜːrsəˈvɪr/', 'You must persevere if you want to succeed.', '如果你想成功，就必须坚持不懈。', 'core', 2),
('excellent', 'adjective', '优秀的；卓越的', '/ˈeksələnt/', '/ˈeksələnt/', 'She did an excellent job on the project.', '她在这个项目上做得非常出色。', 'core', 1),
('imagine', 'verb', '想象；设想', '/ɪˈmædʒɪn/', '/ɪˈmædʒɪn/', 'Can you imagine a world without technology?', '你能想象一个没有科技的世界吗？', 'core', 1),
('journey', 'noun', '旅程；旅途', '/ˈdʒɜːni/', '/ˈdʒɜːrni/', 'Life is a long journey.', '人生是一段漫长的旅程。', 'core', 1);

INSERT INTO knowledge_point (subject, name, parent_id, level) VALUES
('chinese', '文言文', 0, 1),
('chinese', '桃花源记', 1, 2),
('chinese', '岳阳楼记', 1, 2),
('chinese', '作文写作', 0, 1),
('chinese', '主题立意', 4, 2),
('chinese', '文章结构', 4, 2),
('math', '代数', 0, 1),
('math', '一次函数', 7, 2),
('math', '几何', 0, 1),
('math', '三角形', 9, 2),
('english', '词汇', 0, 1),
('english', '核心词汇', 11, 2),
('english', '口语', 0, 1),
('english', '日常对话', 13, 2);

INSERT INTO post (user_id, title, content, author_name, post_type, tags, like_count, comment_count) VALUES
(1, '中考语文文言文复习攻略', '文言文是中考语文的重点，建议同学们平时多读多背，理解文章的主旨和情感。', '学霸小明', 'post', '语文,中考', 128, 32),
(2, '数学错题本使用心得', '错题本是学习数学的神器！可以帮你找到知识薄弱点，针对性复习。', '努力小红', 'post', '数学,学习技巧', 96, 24),
(1, '英语口语练习经验分享', '英语口语需要大量练习，每天坚持练习效果更好。', '学霸小明', 'post', '英语,口语', 85, 18);

INSERT INTO student_profile (user_id, nickname, grade, school, class_name) VALUES
(1, '学霸小明', '初三', '第一中学', '初三(1)班'),
(2, '努力小红', '初二', '第二中学', '初二(3)班');

INSERT INTO sys_admin (username, password, nickname, email, role, status) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', '超级管理员', 'admin@zhixue.com', 'super_admin', 1);

INSERT INTO sys_setting (setting_key, setting_value, setting_desc, setting_group) VALUES
('site_name', '智学魔方', '网站名称', 'basic'),
('site_description', 'AI驱动的智能学习平台', '网站描述', 'basic'),
('register_enabled', '1', '是否允许注册', 'basic'),
('default_role', 'student', '默认注册角色', 'basic'),
('api_rate_limit', '100', 'API速率限制（次/分钟）', 'api'),
('jwt_expiration', '86400000', 'JWT过期时间（毫秒）', 'api'),
('upload_max_size', '10', '上传文件大小限制（MB）', 'api'),
('ai_enabled', '1', '是否启用AI功能', 'feature'),
('essay_review_enabled', '1', '是否启用作文批改', 'feature'),
('oral_practice_enabled', '1', '是否启用口语练习', 'feature');