package com.example.yecaoshi.controller;

import com.example.yecaoshi.mapper.HUserMapper;
import com.example.yecaoshi.pojo.resp;
import com.example.yecaoshi.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
@Slf4j

public class forAdmin {
    @Autowired
    private HUserMapper hUserMapper;
    @Autowired
    private com.example.yecaoshi.pojo.resp resp;
    @Autowired
    private JwtUtil jwtUtil;
    
}
