package com.example.yecaoshi.control;

import com.doudian.open.api.buyin_doukeOrderAds.BuyinDoukeOrderAdsResponse;
import com.doudian.open.api.buyin_doukeOrderAds.data.OrdersItem;
import com.example.yecaoshi.mapper.HGoodsMapper;
import com.example.yecaoshi.mapper.HOrderMapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HGoods;
import com.example.yecaoshi.pojo.HOrder;
import com.example.yecaoshi.pojo.HUser;
import com.example.yecaoshi.util.DouyinAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class OrderControl {
  @Autowired
  HOrderMapper hOrderMapper;
  @Autowired
  HGoodsMapper hGoodsMapper;
  @Autowired
  HUserMapper hUserMapper;
  @Autowired
  DouyinAPI douyinAPI;
  @Autowired
  PointBillControl pointBillControl;

  //抓取订单，每隔一秒访问
  public List<OrdersItem> con_getOrderByTimeRange(Date start, Date end) throws ParseException {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式


    String end_time = simpleDateFormat.format(end);// new Date()为获取当前系统时间，也可使用当前时间戳
    String start_time = simpleDateFormat.format(start);
    List<OrdersItem> api_ListOrder = douyinAPI.api_getOrderByTimeRange(start_time, end_time, "no");


    return api_ListOrder;
  }

  /**
   * 处理抖音返回来的订单详情对象，并插入数据库
   *
   * @param order
   * @return
   */
  public boolean con_handleDouOrder(OrdersItem order) throws ParseException {
    HOrder hOrder = new HOrder();

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    HOrder hOrder_local = hOrderMapper.selectByOrderNo(order.getOrderId());
    //查一下数据库中是否含有当前的这个订单
    //如果存在的话，不执行插入，执行修改操作
    if (hOrder_local != null) {

      switch (order.getFlowPoint()) {
        case "PAY_SUCC":
          hOrder.setStatus(1l);
          hOrder.setOrderStatus(1l);
        case "REFUND":
          hOrder.setStatus(4l);
          hOrder.setOrderStatus(4l);
          hOrder.setReasonFailure("审核失败:订单退款");
        case "CONFIRM":
          hOrder.setStatus(2l);
          hOrder.setOrderStatus(2l);
          //一个月不体现。默认体现过了
        case "SETTLE":
          hOrder.setStatus(2l);
          hOrder.setOrderStatus(3l);
      }
      if (order.getConfirmTime() != null) {
        hOrder.setOrderReceiveTime(Long.parseLong(order.getConfirmTime()));
      }
      //如果体现了之后退款了，扣除回来，用到账单表
      if (Objects.equals(order.getFlowPoint(), "REFUND")) {
        //如果该用户提取了返利，扣除该笔返利
        if (hOrder_local.getIsFanli() == 1L) {
          HUser hUser = hUserMapper.selectById(hOrder.getUid());
          if (!pointBillControl.updataUserBill(hOrder_local.getUid(), 0, -(hOrder.getFreePrice()), hUser.getBalance(), Long.parseLong(hOrder_local.getOrderNo()))) {
            log.error(hOrder_local.getOrderNo() + "-号订单扣除返利失败，原因：领取补贴后退款");

          } else {
            log.info(hOrder_local.getOrderNo() + "-号订单扣除返利成功，原因：领取补贴后退款");

          }

        }
      }

      return true;

    } else {
      Long uid = 0l;
      Long gid = 0l;
      String externalInfo = order.getPidInfo().getExternalInfo();
      String mid_ext = externalInfo.toString();

      String[] mid_extlist = mid_ext.split("_");
      try {
        uid = Long.parseLong(mid_extlist[1]);
        gid = Long.parseLong(mid_extlist[3]);
      } catch (Exception exception) {
        log.error(order.getOrderId() + "-号订单出错，订单额外参数-" + mid_ext + "-出错");
        return false;
      }
      ;


      hOrder.setUid(uid);
      hOrder.setGid(gid);

      hOrder.setMerId(5);
      hOrder.setGroups(6);
      hOrder.setOrderNo(order.getOrderId());
      hOrder.setGoodsId(order.getProductId());
      hOrder.setGoodsName(order.getProductName());
      hOrder.setGoodsThumbnailUrl(order.getProductImg());
      //最小成团价
      hOrder.setMinGroupPrice(order.getTotalPayAmount() / 100);
      hOrder.setGroupPrice(order.getTotalPayAmount() / 100);
      //获取返利价格
      Map searchGood = new HashMap();
      searchGood.put("id", gid);
      List<HGoods> list = hGoodsMapper.selectByMap(searchGood);
      if (list.isEmpty()) {
        log.error(order.getOrderId() + "-号订单出错，订单gid-" + gid + "-不存在");
        return false;
      }

      hOrder.setFreePrice(list.get(0).getFreePrice());
      hOrder.setGoodsQuantity(order.getItemNum());
      hOrder.setStatus(1l);
      switch (order.getFlowPoint()) {
        case "PAY_SUCC":
          hOrder.setStatus(1l);
          hOrder.setOrderStatus(1l);
        case "REFUND":
          hOrder.setStatus(4l);
          hOrder.setOrderStatus(4l);
          hOrder.setReasonFailure("审核失败:订单退款");
        case "CONFIRM":
          hOrder.setStatus(2l);
          hOrder.setOrderStatus(2l);
        case "SETTLE":
          hOrder.setStatus(2l);
          hOrder.setOrderStatus(3l);
      }
      if (list.get(0).getFreePrice() * 100 < order.getTotalPayAmount()) {
        hOrder.setStatus(4l);
        hOrder.setOrderStatus(4l);
        hOrder.setReasonFailure("审核失败:支付价和返利价不一致");
      }
      Date date1=simpleDateFormat.parse(order.getPaySuccessTime());
      hOrder.setOrderPayTime(date1.getTime());
      hOrder.setRemark(simpleDateFormat.format(new Date()) + "||同步订单");
      if (hOrderMapper.insert(hOrder) == 0) {
        log.error(order.getOrderId() + "-号订单，增加订单失败");
      } else {
        log.info(order.getOrderId() + "-号订单同步成功");
      }


    }


    return true;
  }

  public BuyinDoukeOrderAdsResponse con_getOrderDetailById(String orderid)
  {
    return douyinAPI.api_getOrderDetailById(orderid);
  }
}
