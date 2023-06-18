package com.mdl.springboot.alibaba.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: meidanlong
 * @date: 2023/6/18 2:10 PM
 */
@Service
public class TaoBaoKeService {

    private static final String SEARCH_SHOP_METHOD = "taobao.tbk.shop.get";
    private static final String MATERIAL_OPTIONAL_METHOD = "taobao.tbk.dg.material.optional";

    /**
     * 店铺搜索
     * @param searchWord
     * @return 
     */
    public String getShop(String searchWord){
        Map<String, String> params = new HashMap<>();
        params.put("fields", "user_id,shop_title,shop_type,seller_nick,pict_url,shop_url");
        params.put("q", searchWord);
        // TODO: 2023/6/18 转换接收对象
        return TaoBaoKeHttpUtil.callApi(SEARCH_SHOP_METHOD, params);
    }

    /**
     * 物料精选
     * @return
     */
    public String optionalMaterial(String adzone_id){
        Map<String, String> params = new HashMap<>();
        params.put("adzone_id", adzone_id);
        // TODO: 2023/6/18 转换接收对象
        return TaoBaoKeHttpUtil.callApi(MATERIAL_OPTIONAL_METHOD, params);
    }

}
