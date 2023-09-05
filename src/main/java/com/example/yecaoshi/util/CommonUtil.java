package com.example.yecaoshi.util;

import org.springframework.stereotype.Component;

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

}
