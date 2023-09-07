package com.example.yecaoshi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("h_goods")
public class HGoods {
  @TableId(value = "id", type = IdType.AUTO)

  private long id;
  private long merId;
  private String goodsId;
  private long groups;
  private String goodsName;
  private String goodsTitle;
  private String goodsUrl;
  private double minGroupPrice;
  private double groupAfterQuan;
  private double freePrice;
  private double couponDiscount;
  private String salesTip;
  private String commissonRate;
  private String promotionNum;
  private String free;
  private String zsDuoId;
  private long status;
  private long payWay;
  private long showUser;
  private long isSettle;
  private long beginTime;
  private long endTime;
  private String goodsThumbnailUrl;
  private String goodsGalleryUrls;
  private String remark;
  private long createTime;
  private long xjTime;
  private long settleTime;
  private String info;
  private long isAutoDisplay;
  private long autoStartPeople;
  private long autoIncreasePeople;
  private long autoTime;
  private long commodityWeight;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getMerId() {
    return merId;
  }

  public void setMerId(long merId) {
    this.merId = merId;
  }


  public String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(String goodsId) {
    this.goodsId = goodsId;
  }


  public long getGroups() {
    return groups;
  }

  public void setGroups(long groups) {
    this.groups = groups;
  }


  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }


  public String getGoodsTitle() {
    return goodsTitle;
  }

  public void setGoodsTitle(String goodsTitle) {
    this.goodsTitle = goodsTitle;
  }


  public String getGoodsUrl() {
    return goodsUrl;
  }

  public void setGoodsUrl(String goodsUrl) {
    this.goodsUrl = goodsUrl;
  }


  public double getMinGroupPrice() {
    return minGroupPrice;
  }

  public void setMinGroupPrice(double minGroupPrice) {
    this.minGroupPrice = minGroupPrice;
  }


  public double getGroupAfterQuan() {
    return groupAfterQuan;
  }

  public void setGroupAfterQuan(double groupAfterQuan) {
    this.groupAfterQuan = groupAfterQuan;
  }


  public double getFreePrice() {
    return freePrice;
  }

  public void setFreePrice(double freePrice) {
    this.freePrice = freePrice;
  }


  public double getCouponDiscount() {
    return couponDiscount;
  }

  public void setCouponDiscount(double couponDiscount) {
    this.couponDiscount = couponDiscount;
  }


  public String getSalesTip() {
    return salesTip;
  }

  public void setSalesTip(String salesTip) {
    this.salesTip = salesTip;
  }


  public String getCommissonRate() {
    return commissonRate;
  }

  public void setCommissonRate(String commissonRate) {
    this.commissonRate = commissonRate;
  }


  public String getPromotionNum() {
    return promotionNum;
  }

  public void setPromotionNum(String promotionNum) {
    this.promotionNum = promotionNum;
  }


  public String getFree() {
    return free;
  }

  public void setFree(String free) {
    this.free = free;
  }


  public String getZsDuoId() {
    return zsDuoId;
  }

  public void setZsDuoId(String zsDuoId) {
    this.zsDuoId = zsDuoId;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public long getPayWay() {
    return payWay;
  }

  public void setPayWay(long payWay) {
    this.payWay = payWay;
  }


  public long getShowUser() {
    return showUser;
  }

  public void setShowUser(long showUser) {
    this.showUser = showUser;
  }


  public long getIsSettle() {
    return isSettle;
  }

  public void setIsSettle(long isSettle) {
    this.isSettle = isSettle;
  }


  public long getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(long beginTime) {
    this.beginTime = beginTime;
  }


  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }


  public String getGoodsThumbnailUrl() {
    return goodsThumbnailUrl;
  }

  public void setGoodsThumbnailUrl(String goodsThumbnailUrl) {
    this.goodsThumbnailUrl = goodsThumbnailUrl;
  }


  public String getGoodsGalleryUrls() {
    return goodsGalleryUrls;
  }

  public void setGoodsGalleryUrls(String goodsGalleryUrls) {
    this.goodsGalleryUrls = goodsGalleryUrls;
  }


  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }


  public long getXjTime() {
    return xjTime;
  }

  public void setXjTime(long xjTime) {
    this.xjTime = xjTime;
  }


  public long getSettleTime() {
    return settleTime;
  }

  public void setSettleTime(long settleTime) {
    this.settleTime = settleTime;
  }


  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }


  public long getIsAutoDisplay() {
    return isAutoDisplay;
  }

  public void setIsAutoDisplay(long isAutoDisplay) {
    this.isAutoDisplay = isAutoDisplay;
  }


  public long getAutoStartPeople() {
    return autoStartPeople;
  }

  public void setAutoStartPeople(long autoStartPeople) {
    this.autoStartPeople = autoStartPeople;
  }


  public long getAutoIncreasePeople() {
    return autoIncreasePeople;
  }

  public void setAutoIncreasePeople(long autoIncreasePeople) {
    this.autoIncreasePeople = autoIncreasePeople;
  }


  public long getAutoTime() {
    return autoTime;
  }

  public void setAutoTime(long autoTime) {
    this.autoTime = autoTime;
  }


  public long getCommodityWeight() {
    return commodityWeight;
  }

  public void setCommodityWeight(long commodityWeight) {
    this.commodityWeight = commodityWeight;
  }

}
