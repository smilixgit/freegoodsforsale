package com.example.yecaoshi.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("h_Order")
public class HOrder {
  @TableId(value = "id", type = IdType.AUTO)
  private long id;
  private long uid;
  private String pId;
  private long merId;
  private long gid;
  private long groups;
  private String orderNo;
  private String goodsId;
  private String goodsName;
  private String goodsThumbnailUrl;
  private double minGroupPrice;
  private double groupPrice;
  private double freePrice;
  private double frPrice;
  private long goodsQuantity;
  private long status;
  private long orderStatus;
  private String pid;
  private String zsDuoId;
  private long isFanli;
  private long isMerTh;
  private long isImport;
  private String image;
  private String remark;
  private long createTime;
  private long successTime;
  private long uploadTime;
  private long orderCreateTime;
  private long orderPayTime;
  private long orderReceiveTime;
  private long orderSettleTime;
  private long orderModifyAt;
  private String reasonFailure;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public String getPId() {
    return pId;
  }

  public void setPId(String pId) {
    this.pId = pId;
  }


  public long getMerId() {
    return merId;
  }

  public void setMerId(long merId) {
    this.merId = merId;
  }


  public long getGid() {
    return gid;
  }

  public void setGid(long gid) {
    this.gid = gid;
  }


  public long getGroups() {
    return groups;
  }

  public void setGroups(long groups) {
    this.groups = groups;
  }


  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }


  public String getGoodsId() {
    return goodsId;
  }

  public void setGoodsId(String goodsId) {
    this.goodsId = goodsId;
  }


  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }


  public String getGoodsThumbnailUrl() {
    return goodsThumbnailUrl;
  }

  public void setGoodsThumbnailUrl(String goodsThumbnailUrl) {
    this.goodsThumbnailUrl = goodsThumbnailUrl;
  }


  public double getMinGroupPrice() {
    return minGroupPrice;
  }

  public void setMinGroupPrice(double minGroupPrice) {
    this.minGroupPrice = minGroupPrice;
  }


  public double getGroupPrice() {
    return groupPrice;
  }

  public void setGroupPrice(double groupPrice) {
    this.groupPrice = groupPrice;
  }


  public double getFreePrice() {
    return freePrice;
  }

  public void setFreePrice(double freePrice) {
    this.freePrice = freePrice;
  }


  public double getFrPrice() {
    return frPrice;
  }

  public void setFrPrice(double frPrice) {
    this.frPrice = frPrice;
  }


  public long getGoodsQuantity() {
    return goodsQuantity;
  }

  public void setGoodsQuantity(long goodsQuantity) {
    this.goodsQuantity = goodsQuantity;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public long getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(long orderStatus) {
    this.orderStatus = orderStatus;
  }


  public String getPid() {
    return pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }


  public String getZsDuoId() {
    return zsDuoId;
  }

  public void setZsDuoId(String zsDuoId) {
    this.zsDuoId = zsDuoId;
  }


  public long getIsFanli() {
    return isFanli;
  }

  public void setIsFanli(long isFanli) {
    this.isFanli = isFanli;
  }


  public long getIsMerTh() {
    return isMerTh;
  }

  public void setIsMerTh(long isMerTh) {
    this.isMerTh = isMerTh;
  }


  public long getIsImport() {
    return isImport;
  }

  public void setIsImport(long isImport) {
    this.isImport = isImport;
  }


  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
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


  public long getSuccessTime() {
    return successTime;
  }

  public void setSuccessTime(long successTime) {
    this.successTime = successTime;
  }


  public long getUploadTime() {
    return uploadTime;
  }

  public void setUploadTime(long uploadTime) {
    this.uploadTime = uploadTime;
  }


  public long getOrderCreateTime() {
    return orderCreateTime;
  }

  public void setOrderCreateTime(long orderCreateTime) {
    this.orderCreateTime = orderCreateTime;
  }


  public long getOrderPayTime() {
    return orderPayTime;
  }

  public void setOrderPayTime(long orderPayTime) {
    this.orderPayTime = orderPayTime;
  }


  public long getOrderReceiveTime() {
    return orderReceiveTime;
  }

  public void setOrderReceiveTime(long orderReceiveTime) {
    this.orderReceiveTime = orderReceiveTime;
  }


  public long getOrderSettleTime() {
    return orderSettleTime;
  }

  public void setOrderSettleTime(long orderSettleTime) {
    this.orderSettleTime = orderSettleTime;
  }


  public long getOrderModifyAt() {
    return orderModifyAt;
  }

  public void setOrderModifyAt(long orderModifyAt) {
    this.orderModifyAt = orderModifyAt;
  }


  public String getReasonFailure() {
    return reasonFailure;
  }

  public void setReasonFailure(String reasonFailure) {
    this.reasonFailure = reasonFailure;
  }

}
