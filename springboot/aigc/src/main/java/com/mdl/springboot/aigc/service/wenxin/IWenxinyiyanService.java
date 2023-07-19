package com.mdl.springboot.aigc.service.wenxin;

import com.mdl.springboot.aigc.domain.wenxin.WenXinChatRequestDTO;

/**
 * @description: 文心一言服务
 * @author meidanlong
 * @date 2023年07月19日
 * @version: 1.0
 */
public interface IWenxinyiyanService {

    void chatStream(WenXinChatRequestDTO requestDTO);

    String chat(WenXinChatRequestDTO requestDTO);
}
