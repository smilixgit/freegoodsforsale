package com.example.yecaoshi.interC;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.yecaoshi.pojo.Resp;

import com.example.yecaoshi.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

//设置一个拦截器
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private Resp resp;
    @Autowired
    private JWTUtils jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("login_check ----prehandle----");
        log.info("login_check 检测token");
        System.out.println("检测token开始");
        //检测token
        String utoken = request.getHeader("Authorization");
        if(utoken==null || utoken.equals("null"))
        {
            returnError(response,"请重新登录");
            return false;
        }
        System.out.println(utoken);
        DecodedJWT cc = jwtUtil.verify(utoken);
        System.out.println(cc);
        if (cc.getClaim("mobile") == null) {

            returnError(response,"请重新登录");
            return false;

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("login_check ----posthandle----");
        log.info("处理完了");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("login_check ----afterCompletion----");
    }

    private void returnError(HttpServletResponse response,String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            resp.setCode(10001);
            resp.setMsg(msg);
            resp.setData(null);
            writer.print(JSON.toJSONString(resp));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
