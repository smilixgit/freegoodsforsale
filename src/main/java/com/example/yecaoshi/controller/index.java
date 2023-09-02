package com.example.yecaoshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class index {
    @RequestMapping("/")
    @ResponseBody
    public String goindex()
    {
        return "<label style=\"color: rebeccapurple\">测试成功</label>\n";
    }
}
