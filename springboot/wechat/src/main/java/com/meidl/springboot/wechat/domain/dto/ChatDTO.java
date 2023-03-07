package com.meidl.springboot.wechat.domain.dto;

import com.mdl.common.domain.BaseObject;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/3/7 09:33
 */
@Data
public class ChatDTO extends BaseObject {

    /**
     * 群聊名，最多50个utf8字符，超过将截断
     */
    private String name;
    /**
     * 指定群主的id。如果不指定，系统会随机从userlist中选一人作为群主
     */
    private String owner;
    /**
     * 群成员id列表。至少2人，至多2000人 (必填)
     */
    private List<String> userlist;
    /**
     * 群聊的唯一标志，不能与已有的群重复；字符串类型，最长32个字符。只允许字符0-9及字母a-zA-Z。如果不填，系统会随机生成群id
     */
    private String chatid;
}
