package com.ibeetl.bbs.common;

/**
 * Created with IntelliJ IDEA.
 */
public class Const {

    // page size
    public static int TOPIC_PAGE_SIZE = 16;     // 首页帖子分页大小
    public static int POST_PAGE_SIZE = 8;       // 跟帖分页大小
    public static int REPLY_PAGE_SIZE = 5;      // 帖子回复分页大小
    public static int PAGE_SIZE_FOR_ADMIN = 30; // 管理员后台（查看帖子，回帖，跟帖）的分页大小

    // others
    public static String ADMIN_EMAIL = "iveryang@sina.cn";  // 注册一个该email，登陆，即可成为管理员
    public static String BBS_ID_SEPARATOR = "###";
    public static String TIMESTAMP = System.currentTimeMillis() + "";

    // cookie认证相关
    public static String USER_COOKIE_KEY    = "uid";
    public static String USER_COOKIE_SECRET = "ibeetl-bbs&#%!&*";

}
