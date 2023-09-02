package com.example.yecaoshi.interC;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.yecaoshi.pojo.resp;
import com.example.yecaoshi.util.JwtUtil;
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
    private resp resp;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("login_check ----prehandle----");
        log.info("login_check 检测token");
        //检测token
        String utoken = request.getHeader("Authorization");
        System.out.println(utoken);
        String cc= JwtUtil.validateToken(utoken);
        System.out.println(cc);
        if (utoken == null || Objects.equals(cc, "")) {

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
            resp.setCode(0);
            resp.setMsg(msg);
            resp.setData(null);
            writer.print(JSON.toJSONString(resp));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
