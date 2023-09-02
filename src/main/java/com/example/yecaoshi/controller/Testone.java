package com.example.yecaoshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Testone {
  @RequestMapping("/ceshi")
  @ResponseBody
  public String go()
      {

       return  "cs";
      }
}
