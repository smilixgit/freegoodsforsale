package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HUser;
import com.example.yecaoshi.pojo.resp;
import com.example.yecaoshi.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping("checklogin")
    @ResponseBody
    public String checklogin(@RequestParam(value = "mobile",required = true)String mobile)
    {

        QueryWrapper<HUser> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .eq("phone",mobile);
        HUser hUser=hUserMapper.selectOne(queryWrapper);
        if(hUser==null)
        {
            resp.setCode(0);
            resp.setMsg("电话或验证码错误");
            resp.setData(null);
            resp.setToken(null);
            log.error("请求失败");
            return JSON.toJSONString(resp);


        }
        else
        {
            resp.setCode(1);
            resp.setMsg("检测成功");
            resp.setData(hUser);
            //返回token
            Map usermsg=new HashMap<>();
            usermsg.put("id",hUser.getId());
            usermsg.put("groups",hUser.getGroups());
            resp.setToken(JwtUtil.createToken(JSON.toJSONString(usermsg)));

            return JSON.toJSONString(resp);
        }
    }
}
