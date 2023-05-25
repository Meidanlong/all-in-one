package com.mdl.springboot.aigc.constant;

/**
 * midjourney常量
 *
 * @author meidanlong
 * @date 2023-05-12T19:10:19
 **/
public class MidjourneyConstants {

    public static final String HOST = "discord.com";

    public static final String API_URL = "https://" + HOST + "/api/v9/interactions";

    /*参数配置（抓取请求获得）*/
    /*账号切换（或配置多账号）需要更改（或添加）YOUR_ACCOUNT参数*/
    // 账号权限
    public static final String AUTHORIZATION = "YOUR_ACCOUNT_AUTHORIZATION";
    // 会话id
    public static final String SESSION_ID = "YOUR_ACCOUNT_SESSION_ID";

    /*下面参数一次配置不需要更改*/
    // 机器人token
    public static final String ROBOT_TOKEN = "YOUR_ROBOT_TOKEN";
    // 应用程序(机器人)id
    public static final String APPLICATION_ID = "THE_APPLICATION_ID";
    // 公会id(服务器id)
    public static final String GUILD_ID = "THE_GUILD_ID";
    // 频道id
    public static final String CHANNEL_ID = "THE_CHANNEL_ID";
    // data配置
    public static final String DATA_ID = "THE_DATA_ID";
    public static final String DATA_VERSION = "THE_DATA_VERSION";






    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
}
