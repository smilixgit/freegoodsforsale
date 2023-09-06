package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HUser;
import com.example.yecaoshi.pojo.Resp;
import com.example.yecaoshi.util.CommonUtil;

import com.example.yecaoshi.util.HttpUtils;
import com.example.yecaoshi.util.JWTUtils;
import com.example.yecaoshi.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("login")
@Slf4j

public class login {
    @Autowired
    private HUserMapper hUserMapper;
    @Autowired
    private Resp resp;
    @Autowired
    private JWTUtils jwtUtil;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HttpUtils httpUtil;
    @PostMapping ("postVercode")
    @ResponseBody
    public String postVercodebyMobile(HttpServletRequest request) throws IOException {


        Map Req_params = commonUtil.handleHttpServletRequestReq(request);
        int Random_value=commonUtil.getRandomValue(0,9999);

        //存入redis，有效期为3分钟
        redisUtil.set(Req_params.get("mobile").toString(),String.valueOf(Random_value),180);
        httpUtil.postVerCode(Req_params.get("mobile").toString(),String.valueOf(Random_value));
        log.info(Req_params.get("mobile").toString()+"发送了验证码");

        return JSON.toJSONString(resp.returnSuccess("发送验证码成功"));

    }
    /*
       检查用户登录
     */
    @PostMapping("checklogin")
    @ResponseBody
    public String checllogin(HttpServletRequest request) throws IOException {
        Map Req_param=commonUtil.handleHttpServletRequestReq(request);
        //检查用户的电话号码和验证码是否正确
        String mobileNumber=Req_param.get("mobile").toString();
        String verCode=Req_param.get("Vercode").toString();
        Object VerObject = redisUtil.get(mobileNumber);
        if(VerObject == null)
        {
            return  JSON.toJSONString(resp.returnFail("验证码已过期,请重新发送"));
        }
        if(!verCode.equals(VerObject.toString()))
        {
            return  JSON.toJSONString(resp.returnFail("验证码错误"));
        }
        //到这里，用户的电话号码和验证码都是对的，直接往数据表内插入这条数据，mobile
        QueryWrapper<HUser> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .eq("phone",mobileNumber);
        HUser hUser=hUserMapper.selectOne(queryWrapper);
        if(hUser==null)
        {
           //不存在这个用户直接插入
           hUser.setPhone(mobileNumber);
           hUser.setGroups(1);
           int insertFlag = hUserMapper.insert(hUser);

           if (insertFlag == 1)
           {
               resp.setCode(0);
               resp.setMsg("欢迎您，新用户");
               resp.setData(hUser);
               //返回token
               Map usermsg=new HashMap<>();
               usermsg.put("mobile",mobileNumber);
               usermsg.put("groups",1);
               resp.setToken(jwtUtil.getToken(usermsg));

               return JSON.toJSONString(resp);
           }
           else
           {
               return JSON.toJSONString(resp.returnFail("注册失败,请联系客服"));
           }

        }
        else
        {
            //返回token设置为7天内免登录
            resp.setCode(0);
            resp.setMsg("登录成功，正在跳转");
            resp.setData(hUser);
            //返回token
            Map usermsg=new HashMap<>();
            usermsg.put("mobile",hUser.getPhone().toString());
            usermsg.put("groups",String.valueOf(hUser.getGroups()));
            resp.setToken(jwtUtil.getToken(usermsg));

            return JSON.toJSONString(resp);
        }
    }
}
