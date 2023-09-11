package com.example.yecaoshi.control;

import com.example.yecaoshi.mapper.HPointBillMapper;
import com.example.yecaoshi.pojo.HPointBill;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class PointBillControl {

    @Autowired
    HPointBillMapper hPointBillMapper;
    public boolean updataUserBill(Long uid,int type,double amount,double begin_balance,Long orderId)
    {
        HPointBill hPointBill=new HPointBill();
        switch (type)
        {
            //领取补贴入账
            case 1:
                hPointBill.setUid(uid);
                hPointBill.setBegin(begin_balance);
                hPointBill.setCreateTime(new Date().getTime());
                hPointBill.setGroups(1);
                hPointBill.setOid(orderId);
                hPointBill.setNumber(amount);
                hPointBill.setStatus(1);
                hPointBill.setEnd(begin_balance+amount);//领取补贴入账
            //违规操作扣取补贴
            case 0:
                hPointBill.setUid(uid);
                hPointBill.setBegin(begin_balance);
                hPointBill.setCreateTime(new Date().getTime());
                hPointBill.setGroups(0);
                hPointBill.setOid(orderId);
                hPointBill.setNumber(amount);
                hPointBill.setStatus(1);
                hPointBill.setEnd(begin_balance+amount);

        }
        if(hPointBillMapper.insert(hPointBill)==0)
        {
            return false;
        }
        return true;

    }
}
