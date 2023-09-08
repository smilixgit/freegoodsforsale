package com.example.yecaoshi;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.mapper.HGoodsMapper;
import com.example.yecaoshi.mapper.HOrderMapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HGoods;
import com.example.yecaoshi.pojo.HOrder;
import com.example.yecaoshi.pojo.HUser;
import com.example.yecaoshi.pojo.Resp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    void cscs() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date1 = new Date();
        Date date2=new Date(date1.getTime()-1000*30);
        System.out.println(date1.getTime());
        System.out.println(date1);
        String date = df.format(date1);// new Date()为获取当前系统时间，也可使用当前时间戳
        String  ff=df.format(date2);
        System.out.println(date);
        System.out.println(ff);
    }
}

