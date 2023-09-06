package com.example.yecaoshi.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doudian.open.api.buyin_kolProductShare.BuyinKolProductShareRequest;
import com.doudian.open.api.buyin_kolProductShare.BuyinKolProductShareResponse;
import com.doudian.open.api.buyin_kolProductShare.param.BuyinKolProductShareParam;
import com.doudian.open.api.buyin_kolProductsDetail.BuyinKolProductsDetailRequest;
import com.doudian.open.api.buyin_kolProductsDetail.BuyinKolProductsDetailResponse;
import com.doudian.open.api.buyin_kolProductsDetail.data.BuyinKolProductsDetailData;
import com.doudian.open.api.buyin_kolProductsDetail.data.ProductsItem;
import com.doudian.open.api.buyin_kolProductsDetail.param.BuyinKolProductsDetailParam;
import com.doudian.open.core.AccessToken;
import com.doudian.open.core.AccessTokenBuilder;
import com.doudian.open.core.GlobalConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j

public class DouyinAPI {
    @Autowired
    RedisUtil redisUtil;
    static {
        GlobalConfig.initAppKey("7182174365499278907");
        GlobalConfig.initAppSecret("d9d3ba49-121c-4c27-9d48-714a8ffb67fb");
    }
    public String getProductDetail(List<Long> goodsIdList)
    {
        //到redis中拿code，
        AccessToken accessToken = getAccessTokenMy();
        BuyinKolProductsDetailRequest request=new BuyinKolProductsDetailRequest();
        BuyinKolProductsDetailParam param=request.getParam();


        param.setProductIds(goodsIdList);
        BuyinKolProductsDetailResponse response=new BuyinKolProductsDetailResponse();
        response=request.execute(accessToken);
        BuyinKolProductsDetailData data = response.getData();
        ProductsItem productsItem = data.getProducts().get(0);
        String detailUrl = productsItem.getBaseInfo().getDetailUrl();
        return detailUrl;
    }
    public String getDouKouLing(String pro_url,String ext_info)
    {
        //到redis中拿code，
        AccessToken accessToken = getAccessTokenMy();


        BuyinKolProductShareRequest request = new BuyinKolProductShareRequest();
        BuyinKolProductShareParam param = request.getParam();
        param.setProductUrl(pro_url);
        param.setPid("dy_107216596770402681125_28644_3390673480");
        param.setExternalInfo(ext_info);
        param.setNeedQrCode(false);
        param.setPlatform(0);
        param.setUseCoupon(false);
        param.setNeedShareLink(false);

        param.setNeedZlink(false);
        BuyinKolProductShareResponse response = request.execute(accessToken);
        return response.toString();
    }

    public AccessToken getAccessTokenMy() {
        String douYinApiCode = redisUtil.get("DouYinApiCode").toString();

        AccessToken accessToken= AccessTokenBuilder.build(douYinApiCode);

            log.info("抖音Apicode已经过期，准备刷新");
            accessToken=AccessTokenBuilder.refresh(accessToken);
            log.info("抖音Apicode已经过期，刷新成功");

        return accessToken;
    }


}
