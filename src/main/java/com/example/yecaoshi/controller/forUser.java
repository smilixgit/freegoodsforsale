package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.HUser;
import com.example.yecaoshi.pojo.Resp;
import com.example.yecaoshi.util.CommonUtil;
import com.example.yecaoshi.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("user")
@Slf4j

public class forUser {
    @Autowired
    private HUserMapper hUserMapper;
    @Autowired
    private Resp resp;
    @Autowired
    private CommonUtil commonUtil;
    @PostMapping("getUserInfo")
    @ResponseBody
    public String getUserInfo(HttpServletRequest request) throws IOException {
        Map requestReq = commonUtil.handleHttpServletRequestReq(request);
        String uid = requestReq.get("uid").toString();
        QueryWrapper<HUser> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .eq("id",uid);
        HUser hUser=hUserMapper.selectOne(queryWrapper);
        resp.setMsg("加载个人信息成功");
        resp.setData(hUser);
        resp.setCode(1);
        resp.setToken(null);
        return JSON.toJSONString(resp);
    }

}
