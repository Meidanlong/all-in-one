package com.meidl.springboot.wechat.service.workweixin.crop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mdl.common.utils.HttpUtil;
import com.meidl.springboot.wechat.domain.dto.CreateDocDTO;
import com.meidl.springboot.wechat.domain.dto.DocDTO;
import com.meidl.springboot.wechat.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:企业微信文档操作
 * @author: meidanlong
 * @date: 2023/2/16 18:34
 */
@Slf4j
@Service
public class WedocService {

    private final static String wedocUrl = "https://qyapi.weixin.qq.com/cgi-bin/wedoc/create_doc?access_token=%s";

    @Resource
    private AccessTokenService accessTokenService;

    /**
     * 创建文档
     *
     * @see
     *  {@link <a href="https://developer.work.weixin.qq.com/document/path/97460"/a>}
     * @author meidanlong
     * @date 2023/2/16
     * @version 1.0.0
     * @param createDocDTO fieldDesc @required
     * @return DocDTO
     */
    public DocDTO createDoc(CreateDocDTO createDocDTO){
        String accessToken = accessTokenService.getAccessToken();
        String url = String.format(wedocUrl, accessToken);
        Map<String, String> param = new HashMap<>();
//        param.put("spaceid", createDocDTO.getSpaceId());
//        param.put("fatherid", createDocDTO.getFatherId());
        param.put("doc_type", createDocDTO.getDocType().getCode().toString());
        param.put("doc_name", createDocDTO.getDocName());
        List<String> adminUsers = createDocDTO.getAdminUsers();
        if(!CollectionUtils.isEmpty(adminUsers)){
            param.put("admin_users", JSON.toJSONString(adminUsers));
        }
        String jsonParam = JSON.toJSONString(param);
        log.info("[WedocService#createDoc] - url={}, param={}", url, jsonParam);
        String result = HttpUtil.doPostJson(url, null, jsonParam);
        JSONObject jsonObject = ResultUtil.getResult(result, "企业微信创建文档");
        DocDTO docDTO = new DocDTO();
        docDTO.setUrl(jsonObject.getString("url"));
        docDTO.setDocId(jsonObject.getString("docid"));
        return docDTO;
    }
}
