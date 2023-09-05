package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HUser;
import com.example.yecaoshi.pojo.resp;
import com.example.yecaoshi.util.CommonUtil;

import com.example.yecaoshi.util.HttpUtils;
import com.example.yecaoshi.util.JwtUtil;
import com.example.yecaoshi.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private resp resp;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private HttpUtils httpUtil;
    @PostMapping ("checklogin")
    @ResponseBody
    public String checklogin(HttpServletRequest request) throws IOException {
        //新想法，新用户如果验证码正确的话直接用户信息入库，不用注册,所以不用卡数据库中有没有这个用户，
        //只要检查电话号码和验证码一致即可，验证码可用时间为三分钟，用过即刻摧毁，超过三分钟也即刻摧毁

//        QueryWrapper<HUser> queryWrapper=new QueryWrapper<>();
//        queryWrapper
//                .eq("phone",mobile);
//        HUser hUser=hUserMapper.selectOne(queryWrapper);
//        if(hUser==null)
//        {
//            resp.setCode(0);
//            resp.setMsg("电话或验证码错误");
//            resp.setData(null);
//            resp.setToken(null);
//            log.error("请求失败");
//            return JSON.toJSONString(resp);
//
//
//        }
//        else
//        {
//            //返回token设置为15天内免登录
//            resp.setCode(1);
//            resp.setMsg("检测成功");
//            resp.setData(hUser);
//            //返回token
//            Map usermsg=new HashMap<>();
//            usermsg.put("id",hUser.getId());
//            usermsg.put("groups",hUser.getGroups());
//            resp.setToken(JwtUtil.createToken(JSON.toJSONString(usermsg)));
//
//            return JSON.toJSONString(resp);
//        }
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer sb=new StringBuffer();
        String s=null;
        while((s=br.readLine())!=null){
            sb.append(s);
        }
        Map Req_params= JSONObject.parseObject(String.valueOf(sb),Map.class);
        int Random_value=commonUtil.getRandomValue(0,9999);
        System.out.println("发送来的数据为"+Req_params.get("mobile").toString());
        System.out.println("生成的随机数为"+Random_value);
        //存入redis，有效期为3分钟
        redisUtil.set(Req_params.get("mobile").toString(),String.valueOf(Random_value),180);
        httpUtil.postVerCode(Req_params.get("mobile").toString(),String.valueOf(Random_value));

        return "测试成功";

    }
}
