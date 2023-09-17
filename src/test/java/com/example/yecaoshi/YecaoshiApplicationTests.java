package com.example.yecaoshi;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.doudian.open.api.buyin_doukeOrderAds.BuyinDoukeOrderAdsResponse;
import com.example.yecaoshi.control.OrderControl;
import com.example.yecaoshi.mapper.HGoodsMapper;
import com.example.yecaoshi.mapper.HOrderMapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HGoods;
import com.example.yecaoshi.pojo.HOrder;
import com.example.yecaoshi.pojo.HUser;
import com.example.yecaoshi.pojo.Resp;

import com.example.yecaoshi.util.DouyinAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class YecaoshiApplicationTests {
    @Autowired
    private HGoodsMapper hGoodsMapper;
    @Autowired
    private HUserMapper hUserMapper;
    @Autowired
    private HOrderMapper hOrderMapper;
    @Autowired
    private Resp resp;
    @Autowired
    OrderControl control;


    @Test
    void goo()
    {
        String s;
        HUser hUser=hUserMapper.selectById(2);
        if(hUser!=null)
        {

            resp.setCode(0);
            resp.setMsg("检测成功");
            resp.setData(hUser);
             s = JSON.toJSONString(resp);


        }
        else
        {
            resp.setCode(1);
            resp.setMsg("用户名或密码错误");
            resp.setData(null);
             s = JSON.toJSONString(resp);
        }
        System.out.println(s);
    }
    @Test
    void csd()
    {
        List<HGoods> AllGoods=null;

            QueryWrapper<HGoods> queryWrapper=new QueryWrapper<>();
            queryWrapper.
                    eq("status",2);
            AllGoods=hGoodsMapper.selectList(queryWrapper);
        System.out.println(AllGoods);
    }
    @Test
    void cscs() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long todayZeroTimestamp = calendar.getTimeInMillis();
        System.out.println("今天零点的时间戳：" + todayZeroTimestamp);



    }
}

