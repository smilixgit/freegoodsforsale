package com.example.yecaoshi.util;


import com.doudian.open.api.buyin_doukeOrderAds.BuyinDoukeOrderAdsRequest;
import com.doudian.open.api.buyin_doukeOrderAds.BuyinDoukeOrderAdsResponse;
import com.doudian.open.api.buyin_doukeOrderAds.data.OrdersItem;
import com.doudian.open.api.buyin_doukeOrderAds.param.BuyinDoukeOrderAdsParam;
import com.doudian.open.api.buyin_kolProductShare.BuyinKolProductShareRequest;
import com.doudian.open.api.buyin_kolProductShare.BuyinKolProductShareResponse;
import com.doudian.open.api.buyin_kolProductShare.param.BuyinKolProductShareParam;
import com.doudian.open.api.buyin_kolProductsDetail.BuyinKolProductsDetailRequest;
import com.doudian.open.api.buyin_kolProductsDetail.BuyinKolProductsDetailResponse;
import com.doudian.open.api.buyin_kolProductsDetail.param.BuyinKolProductsDetailParam;
import com.doudian.open.api.token.AccessTokenData;
import com.doudian.open.core.AccessToken;
import com.doudian.open.core.AccessTokenBuilder;
import com.doudian.open.core.GlobalConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j

public class DouyinAPI {
    @Autowired
    RedisUtil redisUtil;
    public final String Pid="dy_107216596770402681125_28644_3390673480";

    static {
        GlobalConfig.initAppKey("7182174365499278907");
        GlobalConfig.initAppSecret("d9d3ba49-121c-4c27-9d48-714a8ffb67fb");

    }
    public String api_getProductDetail(List<Long> goodsIdList)
    {
        //到redis中拿code，
        AccessToken accessToken = api_getAccessByAnno();
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
    public String api_getDouKouLing(String pro_url, String ext_info)
    {

        AccessToken accessToken = api_getAccessByAnno();


        BuyinKolProductShareRequest request = new BuyinKolProductShareRequest();
        BuyinKolProductShareParam param = request.getParam();
        param.setProductUrl(pro_url);
        param.setPid(Pid);
        param.setExternalInfo(ext_info);

        BuyinKolProductShareResponse response = request.execute(accessToken);
        return response.getData().getDyPassword();

    }

    public List<OrdersItem> api_getOrderByTimeRange(String start_time, String end_time, String cursor)
    {

        AccessToken accessToken = api_getAccessByAnno();

        BuyinDoukeOrderAdsRequest request = new BuyinDoukeOrderAdsRequest();
        BuyinDoukeOrderAdsParam param = request.getParam();
        param.setSize(200L);
        if (Objects.equals(cursor, "no")) {
            param.setCursor("0");
        } else {
                    param.setCursor(cursor);
        }
        param.setStartTime(start_time);
        param.setEndTime(end_time);
        param.setPid(Pid);

        param.setTimeType("update");

        BuyinDoukeOrderAdsResponse response = request.execute(accessToken);
        List<OrdersItem> orders = response.getData().getOrders();
        return orders;
    }
    public AccessToken api_getAccessByAnno()
    {
        /*
        在 access_token 过期前1h之前，ISV使用 refresh_token 刷新时，会返回原来的 access_token 和 refresh_token，但是二者有效期不会变；
        在 access_token 过期前1h之内，ISV使用 refresh_token 刷新时，会返回新的 access_token 和 refresh_token，但是原来的 access_token 和 refresh_token 继续有效一个小时；
        在 access_token 过期后，ISV使用 refresh_token 刷新时，将获得新的 acces_token 和 refresh_token，同时原来的 acces_token 和 refresh_token 失效；
        */
        AccessToken accessToken=new AccessToken();
        AccessTokenData accessTokenData=new AccessTokenData();
        accessTokenData.setAccessToken("3ef31529-d74c-49ce-abf8-0bec3ed3c506");
        accessTokenData.setRefreshToken("3bd7cb7f-3477-4966-a8e8-c5ca224b4e59");
        accessToken.setData(accessTokenData);
        return AccessTokenBuilder.refresh(accessToken);
    }
    public BuyinDoukeOrderAdsResponse api_getOrderDetailById(String orderId)
    {
        AccessToken accessToken=api_getAccessByAnno();
        BuyinDoukeOrderAdsRequest request = new BuyinDoukeOrderAdsRequest();
        BuyinDoukeOrderAdsParam param = request.getParam();
        param.setSize(200L);
        param.setCursor("0");

        param.setOrderIds(orderId);
        param.setPid(Pid);
        param.setTimeType("update");

        BuyinDoukeOrderAdsResponse response = request.execute(accessToken);
        return response;
    }


}
