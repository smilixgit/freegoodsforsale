package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.control.OrderControl;
import com.example.yecaoshi.mapper.HGoodsMapper;
import com.example.yecaoshi.pojo.HGoods;
import com.example.yecaoshi.pojo.HOrder;
import com.example.yecaoshi.pojo.Resp;
import com.example.yecaoshi.util.CommonUtil;
import com.example.yecaoshi.util.DouyinAPI;
import com.example.yecaoshi.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("goods")
@Slf4j
@CrossOrigin
public class forGoods {
    @Autowired
    private HGoodsMapper hGoodsMapper;
    @Autowired
    private Resp resp;
    @Autowired
    private DouyinAPI douyinAPI;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private OrderControl orderControl;
    @RequestMapping("getDouGoods")
    @ResponseBody
    public String web_getAllGoods(HttpServletRequest request) throws IOException {
        Map Req_param= (Map) commonUtil.handleHttpServletRequestReq(request).get("params");
        Long sea_status;
        List<HGoods> AllGoods=null;
        String status="";

          try {
               status = Req_param.get("status").toString();
          }
          catch (Exception exception)
          {
              System.out.println(exception.getMessage());
              QueryWrapper<HGoods> queryWrapper=new QueryWrapper<>();
              queryWrapper.
                      eq("status", 1l);
              AllGoods=hGoodsMapper.selectList(queryWrapper);
              resp.setCode(0);
              resp.setMsg("获取商品成功");
              resp.setToken(null);
              resp.setData(AllGoods);
              return JSON.toJSONString(resp);
          }
           sea_status=Long.parseLong(status);
            System.out.println(status);
            if(sea_status==3l)
           {
               QueryWrapper<HGoods> queryWrapper=new QueryWrapper<>();
               queryWrapper.
                       gt("id", 0L);
               AllGoods=hGoodsMapper.selectList(queryWrapper);
               resp.setCode(0);
               resp.setMsg("获取商品成功");
               resp.setToken(null);
               resp.setData(AllGoods);
               return JSON.toJSONString(resp);

           }
            else
            {
                QueryWrapper<HGoods> queryWrapper=new QueryWrapper<>();
                queryWrapper.
                        eq("status", sea_status);
                AllGoods=hGoodsMapper.selectList(queryWrapper);
            }

        resp.setCode(0);
        resp.setMsg("获取商品成功");
        resp.setToken(null);
        resp.setData(AllGoods);
        return JSON.toJSONString(resp);

    }
    @RequestMapping("getGoodsDetail")
    @ResponseBody
    public String web_getGoodsDetail(HttpServletRequest request) throws IOException {
        Map Req_params = commonUtil.handleHttpServletRequestReq(request);
        String goodsid = Req_params.get("goodsid").toString();


        Map rdata;
        try {
            List<Long> longList = new ArrayList<>();
            longList.add(Long.valueOf(goodsid));
            rdata = douyinAPI.api_getProductDetail(longList);
        } catch (Exception exception) {
            resp.setCode(401);
            resp.setMsg("获取商品信息出错");
            resp.setToken(null);
            resp.setData(null);
            return JSON.toJSONString(resp);
        }
        resp.setCode(0);
        resp.setMsg("返回成功");
        resp.setToken(null);
        resp.setData(rdata);
        return JSON.toJSONString(resp);

    }

    @PostMapping("getDycode")
    @ResponseBody
    public String web_getDoukouling(HttpServletRequest request) throws IOException {
        Map requestReq = commonUtil.handleHttpServletRequestReq(request);
        String ext_info="uid_"+requestReq.get("uid").toString()+"_gid_"+requestReq.get("id").toString();
        resp.setCode(1);

        resp.setToken(null);
        resp.setData(douyinAPI.api_getDouKouLing(requestReq.get("goods_url").toString(),ext_info));
        resp.setMsg("转链成功");
        return JSON.toJSONString(resp);
    }
    @PostMapping("ceshiyong")
    @ResponseBody
    public String ceshiyong() throws ParseException {

        return "ccccc";
    }
    @PostMapping("insertGoods")
    @ResponseBody
    public String insertgoods(HttpServletRequest request) throws  IOException {
        Map Req_param= (Map) commonUtil.handleHttpServletRequestReq(request).get("info");
        HGoods hGoods=new HGoods();
        hGoods.setGoodsName(Req_param.get("goodsname").toString());
        hGoods.setGoodsId(Req_param.get("goodsid").toString());
        hGoods.setGoodsTitle(Req_param.get("goodstitle").toString());
        hGoods.setGoodsUrl(Req_param.get("goodsdetailurl").toString());
        hGoods.setFreePrice(Double.parseDouble(Req_param.get("goodsfreeprice").toString()));
        hGoods.setPromotionNum(Req_param.get("goodspromotionnum").toString());
        hGoods.setMinGroupPrice(Double.parseDouble(Req_param.get("goodsprice").toString()));
        hGoods.setGroupAfterQuan(Double.parseDouble(Req_param.get("goodspricearterquan").toString()));
        hGoods.setGoodsThumbnailUrl(Req_param.get("goodsthumbnailurl").toString());
        if(hGoodsMapper.insert(hGoods)==0)
        {
            return JSON.toJSONString(resp.returnFail("保存入库失败"));
        }

        return JSON.toJSONString(resp.returnSuccess("保存成功"));
    }
    @PostMapping("outgoods")
    @ResponseBody
    public String outgoods(HttpServletRequest request) throws  IOException {
        Map Req_param= commonUtil.handleHttpServletRequestReq(request);
        String id = Req_param.get("id").toString();
        HGoods hGoods = hGoodsMapper.selectById(id);
        hGoods.setStatus(2l);

        if(hGoodsMapper.updateById(hGoods)==0)
        {
            return JSON.toJSONString(resp.returnFail("更新入库失败"));
        }

        return JSON.toJSONString(resp.returnSuccess("更新成功"));
    }
    @PostMapping("ingoods")
    @ResponseBody
    public String ingoods(HttpServletRequest request) throws  IOException {
        Map Req_param= commonUtil.handleHttpServletRequestReq(request);
        String id = Req_param.get("id").toString();
        HGoods hGoods = hGoodsMapper.selectById(id);
        hGoods.setStatus(1l);

        if(hGoodsMapper.updateById(hGoods)==0)
        {
            return JSON.toJSONString(resp.returnFail("更新入库失败"));
        }

        return JSON.toJSONString(resp.returnSuccess("更新成功"));
    }
    @PostMapping("filterGood")
    @ResponseBody
    public String filterGood(HttpServletRequest request) throws IOException {
        Map Req_param= (Map) commonUtil.handleHttpServletRequestReq(request).get("filterForm");

        QueryWrapper<HGoods> queryWrapper=new QueryWrapper<>();


        if(Req_param.get("goodsname")!=null && !Objects.equals(Req_param.get("goodsname").toString(), ""))
        {
            queryWrapper
                    .like("goods_name",Req_param.get("goodsname").toString());
        }
        if(Req_param.get("goodsid")!=null && !Objects.equals(Req_param.get("goodsid").toString(), ""))
        {
           queryWrapper
                   .eq("goods_id",Req_param.get("goodsid").toString());
        }

        List<HGoods> list = hGoodsMapper.selectList(queryWrapper);
        return JSON.toJSONString(resp.returnSuccessWithData(list));
    }
    @PostMapping("destory")
    @ResponseBody
    public String destory(HttpServletRequest request) throws IOException {
        Map Req_param= (Map) commonUtil.handleHttpServletRequestReq(request).get("params");
        QueryWrapper<HGoods> queryWrapper=new QueryWrapper<>();
        String id=Req_param.get("id").toString();
        System.out.println(id.toString());
        queryWrapper
                .eq("id",Long.parseLong(id));



        hGoodsMapper.delete(queryWrapper);
        return JSON.toJSONString(resp.returnSuccess("操作成功"));



    }
}
