package com.example.yecaoshi.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.yecaoshi.control.OrderControl;
import com.example.yecaoshi.mapper.HAdminMapper;
import com.example.yecaoshi.mapper.HGoodsMapper;
import com.example.yecaoshi.pojo.HAdmin;
import com.example.yecaoshi.pojo.HUser;
import com.example.yecaoshi.pojo.Resp;
import com.example.yecaoshi.util.CommonUtil;
import com.example.yecaoshi.util.DouyinAPI;
import com.example.yecaoshi.util.JWTUtils;
import com.example.yecaoshi.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin")
@Slf4j
@CrossOrigin
public class forAdmin {
    @Autowired
    private HGoodsMapper hGoodsMapper;
    @Autowired
    private JWTUtils jwtUtil;
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
    @Autowired
    private HAdminMapper hAdminMapper;
    @PostMapping("checklogin")
    @ResponseBody
    public String checllogin(HttpServletRequest request) throws Exception {

        Map Req_param=commonUtil.handleHttpServletRequestReq(request);
        //检查用户的电话号码和验证码是否正确
        String adminname=Req_param.get("username").toString();
        String adminpassword=Req_param.get("password").toString();
        System.out.println(adminname);
        System.out.println(adminpassword);
        //到这里，用户的电话号码和验证码都是对的，直接往数据表内插入这条数据，mobile
        QueryWrapper<HAdmin> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .eq("admin_name",adminname)
        ;
        HAdmin hAdmin=hAdminMapper.selectOne(queryWrapper);
        if(hAdmin==null)
        {
            resp.setCode(1);
            resp.setMsg("用户名或密码错误");
            resp.setData("ss");


            resp.setToken(null);

            return JSON.toJSONString(resp);
        }
        if(!hAdmin.getAdminPassword().equals(adminpassword))
        {

            resp.setCode(1);
            resp.setMsg("用户名或密码错误");
            resp.setData(null);


            resp.setToken(null);

            return JSON.toJSONString(resp);
        }
        else
        {
            //返回token设置为7天内免登录
            resp.setCode(0);
            resp.setMsg("登录成功，正在跳转");
            resp.setData(hAdmin);
            //返回token
            Map usermsg=new HashMap<>();
            usermsg.put("adminname",adminname);

            resp.setToken(jwtUtil.getToken(usermsg));

            return JSON.toJSONString(resp);
        }
    }
    @PostMapping("checkToken")
    @ResponseBody
    public String checkToken(HttpServletRequest request,HttpServletResponse response) throws Exception {
        this.preHandleAdminLogin(request,response);
        resp.setCode(0);
        return JSON.toJSONString(resp);
    }
    public boolean preHandleAdminLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("管理员登录 ----prehandle----");
        log.info("管理员登录 检测token");
        System.out.println("检测token开始");
        //检测token
        String utoken = request.getHeader("X-Hioshop-Token");
        System.out.println(utoken);

        if(utoken==null || utoken.equals("null"))
        {
            returnError(response,"请重新登录");
            return false;
        }
        System.out.println(utoken);
        DecodedJWT cc = jwtUtil.verify(utoken);
        System.out.println(cc);
        if (cc.getClaim("adminname") == null) {

            returnError(response,"请重新登录");
            return false;

        }
        return true;
    }
    private void returnError(HttpServletResponse response,String msg) {
        Resp resp=new Resp();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            resp.setCode(401);
            resp.setMsg(msg);
            resp.setData(null);
            writer.print(JSON.toJSONString(resp));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
