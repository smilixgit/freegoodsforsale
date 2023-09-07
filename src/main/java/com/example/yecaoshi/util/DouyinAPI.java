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
import com.doudian.open.api.token.AccessTokenData;
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
        AccessToken accessToken = getAccessByAnno();
        System.out.println("新token的信息如下");
        System.out.println("accessToken"+accessToken.getAccessToken());
        System.out.println("refreshToken"+accessToken.getRefreshToken());
        BuyinKolProductsDetailRequest request = new BuyinKolProductsDetailRequest();
        BuyinKolProductsDetailParam param = request.getParam();
        param.setProductIds(goodsIdList);
        param.setFields("base_info, promotion_info");
        BuyinKolProductsDetailResponse response = request.execute(accessToken);
        String detailUrl = response.getData().getProducts().get(0).getBaseInfo().getDetailUrl();
        return detailUrl;
    }
    public String getDouKouLing(String pro_url,String ext_info)
    {

        AccessToken accessToken = getAccessByAnno();


        BuyinKolProductShareRequest request = new BuyinKolProductShareRequest();
        BuyinKolProductShareParam param = request.getParam();
        param.setProductUrl(pro_url);
        param.setPid("dy_107216596770402681125_28644_3390673480");
        param.setExternalInfo(ext_info);

        BuyinKolProductShareResponse response = request.execute(accessToken);
        return response.getData().getDyPassword();

    }

    public AccessToken getAccessTokenMy() {
        String douYinApiCode = "1c0da424-54e2-4990-801d-abc0673fd954";

        AccessToken accessToken= AccessTokenBuilder.build(douYinApiCode);
        //打印出这两个值
        System.out.println(accessToken.getAccessToken());
        System.out.println(accessToken.getRefreshToken());

//        log.info("抖音Apicode已经过期，准备刷新");
//            accessToken=AccessTokenBuilder.refresh(accessToken);
//            log.info("抖音Apicode已经过期，刷新成功");

        return accessToken;
    }
    public AccessToken getAccessByAnno()
    {
        /*
        在 access_token 过期前1h之前，ISV使用 refresh_token 刷新时，会返回原来的 access_token 和 refresh_token，但是二者有效期不会变；
        在 access_token 过期前1h之内，ISV使用 refresh_token 刷新时，会返回新的 access_token 和 refresh_token，但是原来的 access_token 和 refresh_token 继续有效一个小时；
        在 access_token 过期后，ISV使用 refresh_token 刷新时，将获得新的 acces_token 和 refresh_token，同时原来的 acces_token 和 refresh_token 失效；
        */
        AccessToken accessToken=new AccessToken();
        AccessTokenData accessTokenData=new AccessTokenData();
        accessTokenData.setAccessToken("");
        accessTokenData.setRefreshToken("");
        accessToken.setData(accessTokenData);
        return AccessTokenBuilder.refresh(accessToken);
    }


}
