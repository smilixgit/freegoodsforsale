package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HUser;
import com.example.yecaoshi.pojo.Resp;
import com.example.yecaoshi.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("privateB")
@Slf4j
public class privateB {
    @Autowired
    private HUserMapper hUserMapper;
    @Autowired
    private Resp resp;
    @Autowired
    private JWTUtils jwtUtil;

//    @RequestMapping("getAccount")
//    @ResponseBody
//    public String getUserinfo(HttpServletRequest request) throws Exception {
//
//        Map user=this.decryptJwt(request);
//        QueryWrapper<HUser> queryWrapper=new QueryWrapper<>();
//        queryWrapper
//                .eq("id",user.get("id"));
//        HUser hUser=hUserMapper.selectOne(queryWrapper);
//        resp.setCode(1);
//        resp.setMsg("加载用户信息成功");
//        resp.setData(hUser);
//        resp.setToken(null);
//        return JSON.toJSONString(resp);
//
//    }
   // public Map decryptJwt(HttpServletRequest request) throws Exception {
//        String utoken = request.getHeader("Authorization");
//
////        String usermsg= jwtUtil.verify(utoken);
//
//        Map user=new HashMap();
//        user=JSON.parseObject(usermsg);
//        return user;
   // }
}
