package com.example.yecaoshi.control;

import com.doudian.open.api.buyin_doukeOrderAds.BuyinDoukeOrderAdsResponse;
import com.doudian.open.api.buyin_doukeOrderAds.data.OrdersItem;
import com.doudian.open.api.buyin_doukeOrderAds.data.PidInfo;
import com.example.yecaoshi.mapper.HOrderMapper;
import com.example.yecaoshi.pojo.HGoods;
import com.example.yecaoshi.pojo.HOrder;
import com.example.yecaoshi.util.DouyinAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderControl {
  @Autowired
  HOrderMapper hOrderMapper;

  @Autowired
  DouyinAPI douyinAPI;
  //抓取订单，每隔一秒访问
  public String con_getOrderByTimeRange() throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    Date end = new Date();
    Date start=new Date(end.getTime()-1000*30);

    String end_time = simpleDateFormat.format(end);// new Date()为获取当前系统时间，也可使用当前时间戳
    String  start_time=simpleDateFormat.format(start);
    String cesho=douyinAPI.api_getOrderByTimeRange(start_time,end_time,"no");



    return cesho;
  }

  /**
   * 处理抖音返回来的订单详情对象，并插入数据库
   * @param order
   * @return
   */
  public int con_handleDouOrder(OrdersItem order)
  {
    HOrder hOrder=new HOrder();
    HGoods hGoods=new HGoods();
    //查一下数据库中是否含有当前的这个订单
    //如果存在的话，不执行插入，执行修改操作
    if(hOrderMapper.selectByOrderNo(order.getOrderId()))
    {

    }
    else
    {

      String externalInfo = order.getPidInfo().getExternalInfo();
      String mid_ext=externalInfo.toString();
      String []mid_extlist=mid_ext.split("_");
      hOrder.setUid(Long.parseLong(mid_extlist[1]));
      hOrder.setGid(Long.parseLong(mid_extlist[3]));

      hOrder.setMerId(5);
      hOrder.setGroups(6);
      hOrder.setOrderNo(order.getOrderId());
      hOrder.setGoodsId(order.getProductId());
      hOrder.setGoodsName(order.getProductName());
      hOrder.setGoodsThumbnailUrl(order.getProductImg());



    }


    return  1;
  }
}
