package com.example.yecaoshi.util;


import com.doudian.open.api.alliance_materialsProductsSearch.AllianceMaterialsProductsSearchRequest;
import com.doudian.open.api.alliance_materialsProductsSearch.AllianceMaterialsProductsSearchResponse;
import com.doudian.open.api.alliance_materialsProductsSearch.param.AllianceMaterialsProductsSearchParam;
import com.doudian.open.api.buyin_doukeOrderAds.BuyinDoukeOrderAdsRequest;
import com.doudian.open.api.buyin_doukeOrderAds.BuyinDoukeOrderAdsResponse;
import com.doudian.open.api.buyin_doukeOrderAds.data.OrdersItem;
import com.doudian.open.api.buyin_doukeOrderAds.param.BuyinDoukeOrderAdsParam;
import com.doudian.open.api.buyin_kolProductShare.BuyinKolProductShareRequest;
import com.doudian.open.api.buyin_kolProductShare.BuyinKolProductShareResponse;
import com.doudian.open.api.buyin_kolProductShare.param.BuyinKolProductShareParam;
import com.doudian.open.api.buyin_kolProductsDetail.BuyinKolProductsDetailRequest;
import com.doudian.open.api.buyin_kolProductsDetail.BuyinKolProductsDetailResponse;
import com.doudian.open.api.buyin_kolProductsDetail.data.BaseInfo;
import com.doudian.open.api.buyin_kolProductsDetail.data.CouponInfo;
import com.doudian.open.api.buyin_kolProductsDetail.param.BuyinKolProductsDetailParam;
import com.doudian.open.api.token.AccessTokenData;
import com.doudian.open.core.AccessToken;
import com.doudian.open.core.AccessTokenBuilder;
import com.doudian.open.core.GlobalConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Map api_getProductDetail(List<Long> goodsIdList)
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
        BaseInfo baseInfo = response.getData().getProducts().get(0).getBaseInfo();
        CouponInfo couponInfo = response.getData().getProducts().get(0).getCouponInfo();
        Map Rmap=new HashMap();
        Rmap.put("baseinfo",baseInfo);
        Rmap.put("couponinfo",couponInfo);
        return Rmap;
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
        accessTokenData.setAccessToken("b1761541-b507-4415-99be-318b312c050d");
        accessTokenData.setRefreshToken("5de94660-2c5e-4369-a211-729b965d34f8");
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
    //检索精选联盟商品
    public AllianceMaterialsProductsSearchResponse getJingXuanGoods(String title)
    {
        AccessToken accessToken=api_getAccessByAnno();
        AllianceMaterialsProductsSearchRequest request = new AllianceMaterialsProductsSearchRequest();
        AllianceMaterialsProductsSearchParam param = request.getParam();
        param.setTitle(title);

        param.setPriceMin(1);
        param.setPriceMax(1000);
        param.setSellNumMin(0);
        param.setSellNumMax(1000);
        param.setSearchType(3);
        param.setSortType(1);
        param.setCosFeeMin(1);
        param.setCosFeeMax(50);
        param.setCosRatioMin(1);
        param.setCosRatioMax(15);
        param.setPage(1L);
        param.setPageSize(10L);
        param.setShareStatus(1);


        AllianceMaterialsProductsSearchResponse response = request.execute(accessToken);
        return  response;
    }
    public void getAcByCode()
    {
        AccessToken accessToken= AccessTokenBuilder.build("a183851b-f871-4eaa-aeb3-ce6c984ee17b");  //16dde1a3-2d6f-4946-aef2-afbd29d2eb92是code
        System.out.println(accessToken.getRefreshToken());
        System.out.println(accessToken.getData().getAccessToken());
        System.out.println("测试");
    }

}
