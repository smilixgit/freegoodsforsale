package com.example.yecaoshi.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;

/**
 * 公共方法类
 */
@Component
public class CommonUtil {
    /**
     * 生成随机数
     * @param begin_number 开始的数字
     * @param end_number 结束的数字
     * @return 返回所产生的随机数——整数类型
     */
    public int getRandomValue(int begin_number,int end_number)
    {
        Random random=new Random();
        return random.nextInt(end_number-begin_number+1)+begin_number;
    }

    /**
     * 处理web用户端传过来的参数
     * @param request HttpServletRequest类型
     * @return Map类型
     * @throws IOException
     */
    public Map handleHttpServletRequestReq(HttpServletRequest request) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuffer sb=new StringBuffer();
        String s=null;
        while((s=br.readLine())!=null){
            sb.append(s);
        }
        return JSONObject.parseObject(String.valueOf(sb),Map.class);
    }

}
