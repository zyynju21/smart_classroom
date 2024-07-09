/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : wx_seat

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 12/02/2023 17:47:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_read
-- ----------------------------
DROP TABLE IF EXISTS `book_read`;
CREATE TABLE `book_read`  (
  `id` varchar(39) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '订单号',
  `user_id` varchar(39) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户',
  `library_id` int(11) NULL DEFAULT NULL COMMENT '图书id',
  `library_name` varchar(26) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '图书名称',
  `order_status` varchar(17) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '订单状态',
  `book_remark` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '借阅时间',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '归还时间',
  `create_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_read
-- ----------------------------
INSERT INTO `book_read` VALUES ('1484079982799122434', '1', 1, NULL, '1', '备注1', '2022-01-19 00:00:00', '2022-01-31 00:00:00', '2022-01-20 16:26:55');
INSERT INTO `book_read` VALUES ('1484080387989860354', '1', 1, NULL, '0', '备注1', '2022-01-18 00:00:00', '2022-01-28 00:00:00', '2022-01-20 16:28:32');
INSERT INTO `book_read` VALUES ('1624628238606319618', '1', 2, NULL, '0', '1234564', '2023-02-16 00:00:00', '2023-02-24 00:00:00', '2023-02-12 12:35:30');
INSERT INTO `book_read` VALUES ('1624628657755701250', '1624619865030729730', 3, NULL, '1', '测试借书', '2023-02-12 00:00:00', '2023-02-28 00:00:00', '2023-02-12 12:37:10');
INSERT INTO `book_read` VALUES ('1624668166769737729', '1624619865030729730', 3, NULL, '1', NULL, '2023-02-13 00:00:00', '2023-02-24 00:00:00', '2023-02-12 15:14:10');
INSERT INTO `book_read` VALUES ('1624706074130157569', '1', 3, NULL, '1', NULL, '2023-02-14 00:00:00', '2023-03-01 00:00:00', '2023-02-12 17:44:48');

-- ----------------------------
-- Table structure for books_manger
-- ----------------------------
DROP TABLE IF EXISTS `books_manger`;
CREATE TABLE `books_manger`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `book_name` varchar(17) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '书名',
  `book_img` varchar(210) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `book_text` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '内容',
  `use_state` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '借阅状态(0,可借，1已借)',
  `book_mark` varchar(29) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of books_manger
-- ----------------------------
INSERT INTO `books_manger` VALUES (1, '中国古代语言教育科学', 'upload/2022-01/4fa57070-63a4-4a7d-b54b-f4489c82620e.png', '内容消息测试项目管理2', '1', '备注2', '2022-01-19 21:42:21');
INSERT INTO `books_manger` VALUES (2, '平凡的世界内容消息测', 'upload/2022-01/a24217f5-d80e-41e7-87a7-1addbb6544f4.png', '内容消息测试项目管理2', '1', '备注2', '2022-01-19 21:50:42');
INSERT INTO `books_manger` VALUES (3, '骆驼祥子人们教育出版社', 'upload/2022-01/a468633e-e39c-4e36-a7b2-849a03c8df36.png', '内容消息测试项目管理3', '0', '备注3', '2022-01-20 14:22:05');

-- ----------------------------
-- Table structure for library_list
-- ----------------------------
DROP TABLE IF EXISTS `library_list`;
CREATE TABLE `library_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `library` int(11) NULL DEFAULT NULL COMMENT '图书馆分类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of library_list
-- ----------------------------

-- ----------------------------
-- Table structure for library_type
-- ----------------------------
DROP TABLE IF EXISTS `library_type`;
CREATE TABLE `library_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `library_img` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '图片',
  `library_name` varchar(17) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '分类',
  `add_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '位置',
  `business_date` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '营业时间',
  `library_info` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '介绍',
  `seat_id` int(11) NULL DEFAULT NULL COMMENT '座位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of library_type
-- ----------------------------
INSERT INTO `library_type` VALUES (10, 'upload/2022-01/61ad7b34-7765-4141-9941-f1e6515002fa.png', '计算机系教学电子图书馆', '图书馆位置地址值信息1', '9：00-11：00', '介绍图书管开业时间，欢迎新老学生来', 2);
INSERT INTO `library_type` VALUES (5, 'upload/2022-01/1c436e21-78fa-4510-9e42-d50f781c68c3.png', '科教图书馆', '湖北江汉西北路地区', '8：30-9：30', '图书馆参数，集中存放教课书籍，电子', 3);
INSERT INTO `library_type` VALUES (11, 'upload/2022-01/ed83fc75-af80-4955-9ae1-cbbb7259d20c.png', '图书管理大学交流', '位置地址东北路教课', '9：00-10：00', '存放历史数据、计算书籍、医学书籍', 0);

-- ----------------------------
-- Table structure for notice_advise
-- ----------------------------
DROP TABLE IF EXISTS `notice_advise`;
CREATE TABLE `notice_advise`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `title` varchar(60) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `notice_content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '内容',
  `create_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice_advise
-- ----------------------------
INSERT INTO `notice_advise` VALUES (3, '图书馆信息公告通知信息', '今天图书馆报告营业休息，暂缓营业操作，请各个同学，做好相互传达，带来不变，请您谅解', '2022-01-16 11:09:50');

-- ----------------------------
-- Table structure for order_mange
-- ----------------------------
DROP TABLE IF EXISTS `order_mange`;
CREATE TABLE `order_mange`  (
  `id` varchar(39) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '序号',
  `order_id` varchar(39) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `user_id` varchar(39) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户',
  `library_id` int(11) NULL DEFAULT NULL COMMENT '图书馆id',
  `library_name` varchar(26) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '图书馆',
  `seat_id` int(11) NULL DEFAULT NULL COMMENT '座位id',
  `seat_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '座位',
  `order_status` varchar(17) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '0' COMMENT '订单状态（0预约；1确认；）',
  `plan_time` datetime(0) NULL DEFAULT NULL COMMENT '预约时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `create_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_mange
-- ----------------------------
INSERT INTO `order_mange` VALUES ('1624687393685831681', NULL, '1', 10, NULL, 14, NULL, '1', '2023-02-16 16:38:28', NULL, '2023-02-12 16:30:34');
INSERT INTO `order_mange` VALUES ('1624684903888257026', NULL, '1624619865030729730', 10, NULL, 8, NULL, '1', '2023-02-14 16:20:25', NULL, '2023-02-12 16:20:40');

-- ----------------------------
-- Table structure for seat_status
-- ----------------------------
DROP TABLE IF EXISTS `seat_status`;
CREATE TABLE `seat_status`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态（0空闲；1预约；2占用）',
  `seat_name` varchar(19) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '座位',
  `library_type` int(11) NULL DEFAULT NULL COMMENT '图书馆',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seat_status
-- ----------------------------
INSERT INTO `seat_status` VALUES (6, 0, '座位2', 10);
INSERT INTO `seat_status` VALUES (7, 0, '座位6', 10);
INSERT INTO `seat_status` VALUES (5, 0, '座位7', 5);
INSERT INTO `seat_status` VALUES (8, 1, '座位22', 10);
INSERT INTO `seat_status` VALUES (9, 1, '座位1', 11);
INSERT INTO `seat_status` VALUES (10, 1, '座位22', 10);
INSERT INTO `seat_status` VALUES (11, 0, '座位33', 10);
INSERT INTO `seat_status` VALUES (12, 0, '座位a', 10);
INSERT INTO `seat_status` VALUES (13, 0, '座位b', 10);
INSERT INTO `seat_status` VALUES (14, 1, '座位11', 10);
INSERT INTO `seat_status` VALUES (15, 0, '座位12', 10);
INSERT INTO `seat_status` VALUES (16, 0, '座位15', 10);
INSERT INTO `seat_status` VALUES (17, 1, '座位16', 10);
INSERT INTO `seat_status` VALUES (18, 0, '座位17', 10);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `parent_menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `select_menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_level` int(11) NULL DEFAULT NULL,
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_component` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `hidden_children` int(11) NULL DEFAULT NULL COMMENT '0:非叶子节点  1:叶子节点',
  `menu_show` int(11) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `menu_state` int(11) NULL DEFAULT NULL COMMENT '0:新增 -1:禁用',
  `menu_no` double(11, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('100100', '0', '', 0, 'Dashboard', '/dashboard', 'RouteView', 'home', 0, 0, '2022-01-15 22:05:03', 0, 1.00);
INSERT INTO `sys_menu` VALUES ('100101', '100100', NULL, 1, '工作台', '/dashboard/workplace', 'dashboard/Workplace', 'build', 0, 1, '2021-09-02 16:33:24', 0, 1.20);
INSERT INTO `sys_menu` VALUES ('100102', '100100', NULL, 1, '分析页', '/dashboard/analysis', 'dashboard/Analysis', 'area-chart', 0, 1, '2021-07-23 14:51:42', 0, 1.10);
INSERT INTO `sys_menu` VALUES ('1436942308019752961', '900000', '', 1, '组织管理', '/system/organ/list', 'system/OrganList', 'share-alt', 1, 1, '2021-09-12 14:38:38', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1436942716075200513', '900000', '1436942308019752961', 2, '新增组织', '/system/organ/detail/create', 'system/OrganDetail', '', 0, 0, '2021-09-12 14:45:11', 0, 11.10);
INSERT INTO `sys_menu` VALUES ('1436943895643508737', '900000', '1436942308019752961', 2, '修改组织', '/system/organ/detail/edit/:id', 'system/OrganDetail', '', 0, 0, '2021-09-12 14:44:57', 0, 11.20);
INSERT INTO `sys_menu` VALUES ('1481241023693848578', '0', '', 0, '通知管理', '/notice', 'RouteView', 'wallet', 0, 1, '2022-01-12 20:25:55', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481241317559369729', '1481241023693848578', '', 1, '通知列表', '/wx/notice/advise/list', 'wx/NoticeAdviseList', 'copy', 0, 1, '2022-01-12 20:27:05', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481241499562803202', '1481241023693848578', '1481241317559369729', 2, '新增', '/wx/notice/advise/detail/create', 'wx/NoticeAdviseDetail', 'plus', 0, 0, '2022-01-12 20:28:39', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481241674498834434', '1481241023693848578', '1481241317559369729', 2, '修改', '/wx/notice/advise/detail/edit/:id', 'wx/NoticeAdviseDetail', 'edit', 0, 0, '2022-01-12 20:28:48', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481246908730179585', '0', '', 0, '图书馆管理', '/library', 'RouteView', 'pic-center', 0, 1, '2022-01-12 20:49:18', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481247123407241217', '1481246908730179585', '', 1, '图书馆列表', '/wx/library/type/list', 'wx/LibraryTypeList', 'strikethrough', 0, 1, '2022-01-12 20:50:09', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481247266097463298', '1481246908730179585', '1481247123407241217', 2, '新增', '/wx/library/type/detail/create', 'wx/LibraryTypeDetail', 'rollback', 0, 0, '2022-01-12 20:50:43', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481247413585969154', '1481246908730179585', '1481247123407241217', 2, '修改', '/wx/library/type/detail/edit/:id', 'wx/LibraryTypeDetail', 'form', 0, 0, '2022-01-12 20:51:18', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481565201303244801', '0', '', 0, '座位管理', '/seat', 'RouteView', 'diff', 0, 1, '2022-01-13 17:54:05', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481565455226408961', '1481565201303244801', '', 1, '座位列表', '/wx/seat/status/list', 'wx/SeatStatusList', 'folder-open', 0, 1, '2022-01-13 17:55:05', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481565622646247426', '1481565201303244801', '1481565455226408961', 2, '新增', '/wx/seat/status/detail/create', 'wx/SeatStatusDetail', 'file-add', 0, 0, '2022-01-13 17:55:45', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481565756666843137', '1481565201303244801', '1481565455226408961', 2, '修改', '/wx/seat/status/detail/edit/:id', 'wx/SeatStatusDetail', 'file-ppt', 0, 0, '2022-01-13 17:56:17', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481903211635003394', '0', '', 0, '预约管理', '/order', 'RouteView', 'bar-chart', 0, 1, '2022-01-19 21:19:31', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481903435195600898', '1481903211635003394', '', 1, '订单列表', '/wx/order/mange/list', 'wx/OrderMangeList', 'radar-chart', 0, 1, '2022-01-14 16:18:06', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481903636069208066', '1481903211635003394', '1481903435195600898', 2, '新增', '/wx/order/mange/detail/create', 'wx/OrderMangeDetail', 'plus-circle', 0, 0, '2022-01-14 16:18:54', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1481903778658766849', '1481903211635003394', '1481903435195600898', 2, '修改', '/wx/order/mange/detail/edit/:id', 'wx/OrderMangeDetail', 'form', 0, 0, '2022-01-14 16:19:28', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1483791366801846274', '0', '', 0, '图书管理', '/', 'RouteView', 'copy', 0, 1, '2022-01-19 21:20:04', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1483791559450423298', '1483791366801846274', '', 1, '图书列表', '/wx/books/manger/list', 'wx/BooksMangerList', 'diff', 0, 1, '2022-01-19 21:20:50', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1483791830373101569', '1483791366801846274', '1483791559450423298', 2, '新增', '/wx/books/manger/detail/create', 'wx/BooksMangerDetail', 'plus', 0, 0, '2022-01-19 21:21:55', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1483791998862487553', '1483791366801846274', '1483791559450423298', 2, '修改', '/wx/books/manger/detail/edit/:id', 'wx/BooksMangerDetail', 'minus', 0, 0, '2022-01-19 21:22:35', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1483800746670682113', '0', '', 0, '借阅管理', '/aa', 'RouteView', 'bar-chart', 0, 1, '2022-01-20 08:43:48', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1483800980528295938', '1483800746670682113', '', 1, '借阅列表', '/wx/book/read/list', 'wx/BookReadList', 'align-center', 0, 1, '2022-01-19 21:58:16', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1483801174221254657', '1483800746670682113', '1483800980528295938', 2, '新增', '/wx/book/read/detail/create', 'wx/BookReadDetail', 'plus', 0, 0, '2022-01-19 21:59:02', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1483801333042769921', '1483800746670682113', '1483800980528295938', 2, '修改', '/wx/book/read/detail/edit/:id', 'wx/BookReadDetail', 'minus', 0, 0, '2022-01-19 21:59:40', 0, 11.00);
INSERT INTO `sys_menu` VALUES ('1624621462150389762', '0', '', 0, '测试222', '111111', 'RouteView', 'up-square', 0, 1, '2023-02-12 12:08:50', -1, 11.00);
INSERT INTO `sys_menu` VALUES ('1624621743915343874', '1624621462150389762', '', 1, '22222333', '2222', '22213', 'login', 0, 0, '2023-02-12 12:09:55', -1, 11.00);
INSERT INTO `sys_menu` VALUES ('900000', '0', NULL, 0, '系统管理', '/system', 'RouteView', 'setting', 0, 1, '2021-07-21 15:33:43', 0, 9.00);
INSERT INTO `sys_menu` VALUES ('900100', '900000', NULL, 1, '用户管理', '/system/user/list', 'system/UserList', 'user', 1, 1, '2021-07-21 15:34:35', 0, 9.10);
INSERT INTO `sys_menu` VALUES ('900102', '900000', '900100', 2, '新增用户', '/system/user/detail/create', 'system/UserDetail', '', 0, 0, '2021-09-07 17:14:35', 0, 9.12);
INSERT INTO `sys_menu` VALUES ('900103', '900000', '900100', 2, '修改用户', '/system/user/detail/edit/:id', 'system/UserDetail', '', 0, 0, '2021-07-21 15:35:47', 0, 9.13);
INSERT INTO `sys_menu` VALUES ('900104', '900000', '900100', 2, '用户配置角色', '/system/user/detail/setting/:id/:userName', 'system/UserSetting', '', 0, 0, '2021-09-10 16:55:56', 0, 9.14);
INSERT INTO `sys_menu` VALUES ('900200', '900000', '', 1, '角色管理', '/system/role/list', 'system/RoleList', 'audit', 1, 1, '2021-09-12 11:28:35', 0, 9.20);
INSERT INTO `sys_menu` VALUES ('900202', '900000', '900200', 2, '新增角色', '/system/role/detail/create', 'system/RoleDetail', '', 0, 0, '2021-07-21 15:37:28', 0, 9.22);
INSERT INTO `sys_menu` VALUES ('900203', '900000', '900200', 2, '修改角色', '/system/role/detail/edit/:id', 'system/RoleDetail', '', 0, 0, '2021-07-21 15:37:53', 0, 9.23);
INSERT INTO `sys_menu` VALUES ('900204', '900000', '900200', 2, '角色配置菜单', '/system/role/detail/setting/:id/:roleName', 'system/RoleSetting', '', 0, 0, '2021-09-09 11:35:21', 0, 9.24);
INSERT INTO `sys_menu` VALUES ('900300', '900000', NULL, 1, '菜单管理', '/system/menu/list', 'system/MenuList', 'menu', 1, 1, '2021-07-21 15:52:06', 0, 9.30);
INSERT INTO `sys_menu` VALUES ('900302', '900000', '900300', 2, '新增菜单', '/system/menu/detail/create', 'system/MenuDetail', '', 0, 0, '2021-09-12 11:11:57', 0, 9.32);
INSERT INTO `sys_menu` VALUES ('900303', '900000', '900300', 2, '修改菜单', '/system/menu/detail/edit/:id', 'system/MenuDetail', '', 0, 0, '2021-07-23 15:00:00', 0, 9.33);
INSERT INTO `sys_menu` VALUES ('900400', '900000', '', 1, '系统日志', '/system/log', 'system/SystemLogList', 'file-text', 1, 1, '2021-09-12 14:41:12', -1, 12.30);

-- ----------------------------
-- Table structure for sys_organ
-- ----------------------------
DROP TABLE IF EXISTS `sys_organ`;
CREATE TABLE `sys_organ`  (
  `org_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `org_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `org_logo` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `org_person` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `org_phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `org_type` int(11) NULL DEFAULT NULL,
  `org_state` int(11) NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`org_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_organ
-- ----------------------------
INSERT INTO `sys_organ` VALUES ('1', '计算机系1', NULL, '黄能', '15812345678', 0, 0, '2021-09-12 15:35:15');
INSERT INTO `sys_organ` VALUES ('1436956619547049985', 'asdasd', NULL, 'asdasd', '1212112', 1, -1, '2021-09-12 15:35:30');
INSERT INTO `sys_organ` VALUES ('1436963765940629506', '计算机系2', NULL, '李云', '123456', 1, 0, '2021-09-12 16:03:54');
INSERT INTO `sys_organ` VALUES ('1624622025424445441', '11122', NULL, '22233', '333444', 1, -1, '2023-02-12 12:10:50');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_identity` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_desc` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `role_state` int(11) NULL DEFAULT NULL COMMENT '0:新增 -1:禁用',
  `org_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('10000', '超级管理员', 'super_admin', '', '2021-07-21 15:55:20', 0, '1');
INSERT INTO `sys_role` VALUES ('1435874729092218881', '测试角色1', 'aaa', NULL, '2021-09-09 15:56:27', -1, '1');
INSERT INTO `sys_role` VALUES ('1437677468801519617', '组织机构管理员', 'org_admin', NULL, '2021-09-14 15:19:54', 0, '1436963765940629506');
INSERT INTO `sys_role` VALUES ('1437688116734304258', '操作员', 'op1', NULL, '2021-09-14 16:02:13', 0, '1436963765940629506');
INSERT INTO `sys_role` VALUES ('1624620577886248961', '测试角色222', NULL, '2222', '2023-02-12 12:05:04', -1, '1');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `menu_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1435874821278826498', '1435874729092218881', '100100');
INSERT INTO `sys_role_menu` VALUES ('1435874821283020801', '1435874729092218881', '100102');
INSERT INTO `sys_role_menu` VALUES ('1435874821287215106', '1435874729092218881', '100101');
INSERT INTO `sys_role_menu` VALUES ('1624620806958161921', '1437677468801519617', '100102');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939138', '1437677468801519617', '100100');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939139', '1437677468801519617', '100101');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939140', '1437677468801519617', '900100');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939141', '1437677468801519617', '900102');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939142', '1437677468801519617', '900103');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939143', '1437677468801519617', '900104');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939144', '1437677468801519617', '900200');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939145', '1437677468801519617', '900202');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939146', '1437677468801519617', '900203');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939147', '1437677468801519617', '900204');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939148', '1437677468801519617', '900300');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939149', '1437677468801519617', '900302');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939150', '1437677468801519617', '900303');
INSERT INTO `sys_role_menu` VALUES ('1624620806974939151', '1437677468801519617', '900000');
INSERT INTO `sys_role_menu` VALUES ('1624626548259864578', '10000', '100102');
INSERT INTO `sys_role_menu` VALUES ('1624626548259864579', '10000', '100100');
INSERT INTO `sys_role_menu` VALUES ('1624626548280836097', '10000', '100101');
INSERT INTO `sys_role_menu` VALUES ('1624626548280836098', '10000', '900300');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613314', '10000', '900302');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613315', '10000', '900303');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613316', '10000', '900100');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613317', '10000', '900102');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613318', '10000', '900103');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613319', '10000', '900104');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613320', '10000', '900200');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613321', '10000', '900202');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613322', '10000', '900203');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613323', '10000', '900204');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613324', '10000', '1436942308019752961');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613325', '10000', '1436942716075200513');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613326', '10000', '1436943895643508737');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613327', '10000', '1481903435195600898');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613328', '10000', '1481903636069208066');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613329', '10000', '1481903778658766849');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613330', '10000', '1481903211635003394');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613331', '10000', '1481241317559369729');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613332', '10000', '1481241499562803202');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613333', '10000', '1481241674498834434');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613334', '10000', '1481241023693848578');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613335', '10000', '1481247123407241217');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613336', '10000', '1481247266097463298');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613337', '10000', '1481247413585969154');
INSERT INTO `sys_role_menu` VALUES ('1624626548297613338', '10000', '1481246908730179585');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636738', '10000', '1481565455226408961');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636739', '10000', '1481565622646247426');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636740', '10000', '1481565756666843137');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636741', '10000', '1481565201303244801');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636742', '10000', '1483791559450423298');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636743', '10000', '1483791830373101569');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636744', '10000', '1483791998862487553');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636745', '10000', '1483791366801846274');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636746', '10000', '1483800980528295938');
INSERT INTO `sys_role_menu` VALUES ('1624626548427636747', '10000', '1483801174221254657');
INSERT INTO `sys_role_menu` VALUES ('1624626548494745602', '10000', '1483801333042769921');
INSERT INTO `sys_role_menu` VALUES ('1624626548494745603', '10000', '1483800746670682113');
INSERT INTO `sys_role_menu` VALUES ('1624626548494745604', '10000', '900000');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `login_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `login_pass` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birth` date NULL DEFAULT NULL,
  `head_img_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `province` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `city` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `county` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `job` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `of_area` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_date` datetime(0) NULL DEFAULT NULL,
  `user_state` int(11) NULL DEFAULT NULL COMMENT '0:新增 -1:禁用',
  `salt_value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_sex` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `org_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `login_name`(`login_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$shiro1$SHA-256$500000$Se0ZWB2YiEYrxIbphAq8sw==$ZGW8AkVmKCEeT+zJhVNd/u4leNMy4TMMI10B7f4aKPM=', 'IT教程资源', '2021-07-21', 'upload/2023-02/55d5a16e-42ca-4a59-8774-afec4fefb8af.jpg', '18912345888', '湖北省', '荆州市', '沙市区', '测试地址2', '湖北科技大学', NULL, '2021-07-21 15:32:11', 0, '', '男', 14400, 14400, '1');
INSERT INTO `sys_user` VALUES ('1436213564023988225', 'guest', '$shiro1$SHA-256$500000$Ylh0pdj6BsSaFQvDTjiXSg==$PD0r0d0iEj5QmQybpkAmjjMV5LPcQQ2xBM0MVTBUMDk=', 'guest', '2021-09-10', 'upload/2021-09/d66a96dc-0c56-400c-9cf4-20c52219f17b.jpg', '18912345678', '', '', '', NULL, NULL, NULL, '2021-09-10 14:22:52', -1, NULL, '女', 1440, 1440, '1');
INSERT INTO `sys_user` VALUES ('1436238803529338881', 'test', '$shiro1$SHA-256$500000$G5CA7pFkLsFI71/gC47mig==$XPh39gkedLG0pI17bYCYxvIFUyMOjclnmWQXUww4F9c=', 'test', NULL, NULL, '15812345678', NULL, NULL, NULL, NULL, NULL, NULL, '2021-09-10 16:03:09', -1, NULL, '男', 1440, 1440, '1');
INSERT INTO `sys_user` VALUES ('1482886664551538689', 'ddd', '$shiro1$SHA-256$500000$Ib8t2yRa4kxl3UYpSLPzcw==$Jzvp+s0MDPXRTK///ElejwjvyqeZ5OApbkjKp499+sg=', 'ddd', NULL, NULL, '13234567675', NULL, NULL, NULL, NULL, NULL, NULL, '2022-01-17 09:25:06', -1, NULL, '男', 1440, 1440, '1436963765940629506');
INSERT INTO `sys_user` VALUES ('1482887340090335234', 'asd', '$shiro1$SHA-256$500000$RbzvIEJybmuWE7leWglgQQ==$DLFqsORu5mR31wOMyhD/7FiZCkulyOfZ/RSbs5IhVoU=', 'sdf', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-01-17 09:27:47', -1, NULL, '男', 1440, 1440, NULL);
INSERT INTO `sys_user` VALUES ('1482887497108344834', 'asdr', '$shiro1$SHA-256$500000$WHj/ox+dcfemPw4hpioecg==$Bp4ZvsuBaz1qtc7ogrqudSgYuKlmH+rUJQtUMYxyDNw=', 'sdf', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-01-17 09:28:25', -1, NULL, '男', 1440, 1440, NULL);
INSERT INTO `sys_user` VALUES ('1482887684195274753', 'ancen', '$shiro1$SHA-256$500000$INOS6pLqEQtIjYdkYnAaVw==$/mqT0qxyy96zy9dBRXiWfDcPIx0VAN5JAwe2mWVMhKY=', '123456', NULL, NULL, '135987533656', NULL, NULL, NULL, NULL, NULL, NULL, '2022-01-17 09:29:09', -1, NULL, '男', 1440, 1440, NULL);
INSERT INTO `sys_user` VALUES ('1622952853330075650', 'ping', '$shiro1$SHA-256$500000$OhRfFX5xqcDLLvWe3QfGmw==$GK+qjDtdDBpb3dZdgyf1N+Zd+IExQSNBooErAMPSGbs=', '平少', NULL, NULL, '13622963458', NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-07 21:38:08', 0, NULL, '男', 1440, 1440, NULL);
INSERT INTO `sys_user` VALUES ('1624619865030729730', 'zhangsan', '$shiro1$SHA-256$500000$A3+7SftOocSRcSjRGprJVg==$8wdhtQnFEK4k5UTLkrkde4fiB/3Y4Jb/MJHNmzPnDzs=', '张三', '2023-02-12', 'upload/2023-02/19c81170-6a66-4321-927e-87768a3286bc.png', '13622963999', '广东省', '广州市', '荔湾区', '测试街道99号', '搬砖', NULL, '2023-02-12 12:02:14', 0, NULL, '男', 1440, 1440, '1');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1436530114627076098', '1436238803529338881', '1435874729092218881');
INSERT INTO `sys_user_role` VALUES ('1436530157388005377', '1436213564023988225', '1435874729092218881');
INSERT INTO `sys_user_role` VALUES ('1437677621277052929', '1437668689343119362', '1437677468801519617');
INSERT INTO `sys_user_role` VALUES ('1624618536786927618', '1', '10000');

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log`  (
  `system_log_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `controller_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `method_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `paras` blob NULL,
  `result` longblob NULL,
  `log_type` int(1) NULL DEFAULT NULL,
  `status` int(1) NULL DEFAULT NULL,
  `response_time` int(11) NULL DEFAULT NULL,
  `client_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_system` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `server_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_gmt` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `created_by` int(20) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`system_log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of system_log
-- ----------------------------
INSERT INTO `system_log` VALUES ('1436959449850802178', 'com.yiyue.controller.system.SysOrganController', 'list', 0x5B426173655061676544544F2873697A653D31302C2063757272656E743D312C20736F72744669656C643D2C20736F72744F726465723D295D, 0x7B22636F6465223A22323030222C2264617461223A7B2263757272656E74223A312C22686974436F756E74223A66616C73652C226F7264657273223A5B5D2C227061676573223A312C227265636F726473223A5B7B2263726561746544617465223A313633313433323133303030302C226F72674964223A2231343336393536363139353437303439393835222C226F72674E616D65223A22617364617364222C226F7267506572736F6E223A22617364617364222C226F726750686F6E65223A22617364617364222C226F72675374617465223A302C226F726754797065223A317D2C7B2263726561746544617465223A313633313433323131353030302C226F72674964223A2231222C226F72674E616D65223A22E5BF86E8B783E7BD91E7BB9CE7A791E68A80222C226F7267506572736F6E223A22E9BB84E883BD222C226F726750686F6E65223A223135383132333435363738222C226F72675374617465223A302C226F726754797065223A307D5D2C22736561726368436F756E74223A747275652C2273697A65223A31302C22746F74616C223A327D2C226D7367223A2253756363657373222C2274696D65223A22323032312D30392D31325430373A34363A34342E3930305A227D, 1, 200, 100, '127.0.0.1', 'Windows NT 10.0; Win64; x64', 'Chrome-92.0.4515.107', 'http://127.0.0.1:8080/system/organ/list', '2021-09-12 15:46:45', 1);
INSERT INTO `system_log` VALUES ('1436960754174504962', 'com.yiyue.controller.system.SysOrganController', 'list', 0x5B426173655061676544544F2873697A653D31302C2063757272656E743D312C20736F72744669656C643D2C20736F72744F726465723D295D, 0x7B22636F6465223A22323030222C2264617461223A7B2263757272656E74223A312C22686974436F756E74223A66616C73652C226F7264657273223A5B5D2C227061676573223A312C227265636F726473223A5B7B2263726561746544617465223A313633313433323133303030302C226F72674964223A2231343336393536363139353437303439393835222C226F72674E616D65223A22617364617364222C226F7267506572736F6E223A22617364617364222C226F726750686F6E65223A22617364617364222C226F72675374617465223A302C226F726754797065223A317D2C7B2263726561746544617465223A313633313433323131353030302C226F72674964223A2231222C226F72674E616D65223A22E5BF86E8B783E7BD91E7BB9CE7A791E68A80222C226F7267506572736F6E223A22E9BB84E883BD222C226F726750686F6E65223A223135383132333435363738222C226F72675374617465223A302C226F726754797065223A307D5D2C22736561726368436F756E74223A747275652C2273697A65223A31302C22746F74616C223A327D2C226D7367223A2253756363657373222C2274696D65223A22323032312D30392D31325430373A35313A35352E3837355A227D, 1, 200, 97, '127.0.0.1', 'Windows NT 10.0; Win64; x64', 'Chrome-92.0.4515.107', 'http://127.0.0.1:8080/system/organ/list', '2021-09-12 15:51:56', 1);

SET FOREIGN_KEY_CHECKS = 1;
