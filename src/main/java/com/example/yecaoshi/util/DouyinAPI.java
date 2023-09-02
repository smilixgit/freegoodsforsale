package com.example.yecaoshi.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.doudian.open.api.buyin_kolProductsDetail.BuyinKolProductsDetailRequest;
import com.doudian.open.api.buyin_kolProductsDetail.BuyinKolProductsDetailResponse;
import com.doudian.open.api.buyin_kolProductsDetail.param.BuyinKolProductsDetailParam;
import com.doudian.open.core.AccessToken;
import com.doudian.open.core.AccessTokenBuilder;
import com.doudian.open.core.GlobalConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component

public class DouyinAPI {
    static {
        GlobalConfig.initAppKey("7182174365499278907");
        GlobalConfig.initAppSecret("d9d3ba49-121c-4c27-9d48-714a8ffb67fb");
    }
    public String getProductDetail(List<Long> goodsIdList)
    {
        AccessToken accessToken=AccessTokenBuilder.refresh("1246ea0f-4194-48ef-b4e6-cb79bd19d45f");
        BuyinKolProductsDetailRequest request=new BuyinKolProductsDetailRequest();
        BuyinKolProductsDetailParam param=request.getParam();


        param.setProductIds(goodsIdList);
        BuyinKolProductsDetailResponse response=new BuyinKolProductsDetailResponse();
        response=request.execute(accessToken);
        return response.toString();
    }


}
