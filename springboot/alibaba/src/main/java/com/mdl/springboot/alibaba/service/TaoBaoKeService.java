package com.mdl.springboot.alibaba.service;

import com.mdl.springboot.alibaba.domain.TaoBaoKeConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:淘宝客服务
 * @author: meidanlong
 * @date: 2023/6/18 2:10 PM
 *
 *
 * material_id说明：https://market.m.taobao.com/app/qn/toutiao-new/index-pc.html#/detail/10628875?_k=gpov9a
 * 综合	女装	食品	美妆个护	家居家装	母婴
 * 27446	27448	27451	27453	27798	27454
 */
@Service
public class TaoBaoKeService {

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
        return TaoBaoKeHttpUtil.callApi(TaoBaoKeConstant.SEARCH_SHOP_METHOD, params);
    }

    /**
     * 物料精选
     * @return
     */
    public String optionalMaterial(String searchWord){
        Map<String, String> params = new HashMap<>();
        params.put("adzone_id", TaoBaoKeConstant.ADZONE_ID);
        params.put("q", searchWord);
        // TODO: 2023/6/18 转换接收对象
        return TaoBaoKeHttpUtil.callApi(TaoBaoKeConstant.MATERIAL_OPTIONAL_METHOD, params);
    }

    /**
     * 淘宝客商品详情查询
     * @param numIids
     * @return
     */
    public String getItemInfo(List<String> numIids){
        Map<String, String> params = new HashMap<>();
        params.put("num_iids", String.join(",", numIids));
        // TODO: 2023/6/18 转换接收对象
        return TaoBaoKeHttpUtil.callApi(TaoBaoKeConstant.ITEM_INFO_METHOD, params);
    }

    /**
     * 回流数据查询
     * @param taoPassword
     * @return
     */
    public String getTpwdReport(String taoPassword){
        Map<String, String> params = new HashMap<>();
        params.put("tao_password", taoPassword);
        params.put("adzone_id", TaoBaoKeConstant.ADZONE_ID);
        // TODO: 2023/6/18 转换接收对象
        return TaoBaoKeHttpUtil.callApi(TaoBaoKeConstant.TPWD_REPORT_METHOD, params);
    }

    /**
     * 获取后台供卖家发布商品的标准商品类目
     *
     * 需用户授权
     * https://open.taobao.com/v2/doc?spm=a219a.15212433.0.0.7870669aTceHRL#/abilityToOpen?docId=121222&docType=1
     * @param parentCid
     * @param cids
     * @return
     */
    public String getItemCats(String parentCid, List<String> cids){
        Map<String, String> params = new HashMap<>();
        if(CollectionUtils.isEmpty(cids)){
            params.put("parent_cid", parentCid);
        }else{
            params.put("cids", String.join(",", cids));
        }
        // TODO: 2023/6/18 转换接收对象
        return TaoBaoKeHttpUtil.callApi(TaoBaoKeConstant.ITEM_CATS_METHOD, params);
    }

    /**
     * 淘宝客-推广者-物料精选
     *
     * 不需用户授权
     * https://open.taobao.com/api.htm?docId=33947&docType=2&scopeId=16518
     * @param materialId
     * @return
     */
    public String getOptimusMaterial(String materialId){
        Map<String, String> params = new HashMap<>();
        params.put("adzone_id", TaoBaoKeConstant.ADZONE_ID);
        params.put("material_id", materialId);
        // TODO: 2023/6/18 转换接收对象
        return TaoBaoKeHttpUtil.callApi(TaoBaoKeConstant.OPTIMUS_MATERIAL_METHOD, params);
    }

}
