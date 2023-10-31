package com.mdl.springboot.alibaba.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:淘宝客单测
 * @author: meidanlong
 * @date: 2023/6/18 2:44 PM
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TaoBaoKeServiceTest {

    private String HTTPS = "https:";

    @Resource
    private TaoBaoKeService taoBaoKeService;

    @Test
    public void getShop() {
        String result = taoBaoKeService.getShop("男装");
        log.info("getShop result={}", result);
    }

    @Test
    public void optionalMaterial() {
        String result = taoBaoKeService.optionalMaterial("男装");
        log.info("optionalMaterial result={}", result);
    }

    @Test
    public void getItemInfo() {
        List<String> ids = Arrays.asList("123", "456");
        String result = taoBaoKeService.getItemInfo(ids);
        log.info("getItemInfo result={}", result);
    }

    @Test
    public void getTpwdReport() {
        String result = taoBaoKeService.getTpwdReport("12345678");
        log.info("getTpwdReport result={}", result);
    }

    @Test
    public void getItemCats() {
        String result = taoBaoKeService.getItemCats("50011999", null);
        log.info("getItemCats result={}", result);
    }

    @Test
    public void getOptimusMaterial() {
        String result = taoBaoKeService.getOptimusMaterial("27446");
        JSONArray jsonArray = JSON.parseObject(result).getJSONObject("tbk_dg_optimus_material_response").getJSONObject("result_list").getJSONArray("map_data");
        List<MaterialItem> materialItemList = jsonArray.stream().map(jo -> {
            JSONObject jsonObject = (JSONObject) jo;
            MaterialItem materialItem = new MaterialItem();
            materialItem.setTitle(jsonObject.getString("title"));
            materialItem.setNick(jsonObject.getString("nick"));
            materialItem.setPictUrl(HTTPS + jsonObject.getString("pict_url"));
            // 原价
            String zkFinalPrice = jsonObject.getString("zk_final_price");
            materialItem.setZkFinalPrice(zkFinalPrice);
            // 优惠
            Integer couponAmount = jsonObject.getInteger("coupon_amount");
            materialItem.setCouponAmount(couponAmount);
            // 现价
//            BigDecimal currentPrice = new BigDecimal(zkFinalPrice).subtract(new BigDecimal(couponAmount));
//            materialItem.setCurrentPrice(currentPrice.toString());
            String commissionRate = jsonObject.getString("commission_rate");
            materialItem.setCommissionRate(commissionRate + "%");
            String couponShareUrl = jsonObject.getString("coupon_share_url");
            materialItem.setCouponShareUrl(HTTPS + couponShareUrl);
            String couponStartFee = jsonObject.getString("coupon_start_fee");
            materialItem.setCouponStartFee("满"+couponStartFee+"元可用");
            Integer volume = jsonObject.getInteger("volume");
            materialItem.setVolume(volume);
            return materialItem;
        }).sorted(Comparator.comparingInt(MaterialItem::getVolume).reversed()).collect(Collectors.toList());

        List<String> nickList = materialItemList.stream().map(MaterialItem::getNick).collect(Collectors.toList());
        log.info("getItemCats result={}", result);
        log.info("getItemCats shortTitleList={}", JSON.toJSONString(materialItemList));
        log.info("getItemCats nickList={}", JSON.toJSONString(nickList));
    }

}
class MaterialItem{

    /**
     * 商品信息-商品标题
     */
    private String title;
    /**
     * 商品信息-商品主图
     */
    private String pictUrl;
    /**
     * 店铺信息-卖家昵称
     */
    private String nick;
    /**
     * 折扣价（元） 若属于预售商品，付定金时间内，折扣价=预售价  -- 原价
     */
    private String zkFinalPrice;
    /**
     * 优惠券（元） 若属于预售商品，该优惠券付尾款可用，付定金不可用 -- 优惠券优惠价格
     */
    private Integer couponAmount;
    /**
     * 现价 -- 不一定准
     */
//    private String currentPrice;
    /**
     * 商品信息-佣金比率(%)
     */
    private String commissionRate;
    /**
     * 优惠券信息-优惠券起用门槛，满X元可用。如：满299元减20元
     */
    private String couponStartFee;
    /**
     * 链接-宝贝+券二合一页面链接
     */
    private String couponShareUrl;
    /**
     * 商品信息-30天销量
     */
    private Integer volume;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPictUrl() {
        return pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl;
    }

    public String getZkFinalPrice() {
        return zkFinalPrice;
    }

    public void setZkFinalPrice(String zkFinalPrice) {
        this.zkFinalPrice = zkFinalPrice;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getCouponShareUrl() {
        return couponShareUrl;
    }

    public void setCouponShareUrl(String couponShareUrl) {
        this.couponShareUrl = couponShareUrl;
    }

    public String getCouponStartFee() {
        return couponStartFee;
    }

    public void setCouponStartFee(String couponStartFee) {
        this.couponStartFee = couponStartFee;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

//    public String getCurrentPrice() {
//        return currentPrice;
//    }
//
//    public void setCurrentPrice(String currentPrice) {
//        this.currentPrice = currentPrice;
//    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }
}