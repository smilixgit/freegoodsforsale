package com.example.yecaoshi.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component

public class Resp {
    public int code;
    public String msg;
    public Object data;
    public String token;

    /**
     *
     * @return 返回加载成功的返回值
     */
    public Resp returnSuccess(String msg)
    {
        Resp sucResp=new Resp();
        sucResp.setCode(0);
        sucResp.setMsg(msg);
        sucResp.setData(null);
        sucResp.setToken(null);
        return sucResp;
    }
    /**
     *
     * @return 返回加载失败的返回值
     */
    public Resp returnFail(String msg)
    {
        Resp sucResp=new Resp();
        sucResp.setCode(10000);
        sucResp.setMsg(msg);
        sucResp.setData(null);
        sucResp.setToken(null);
        return sucResp;
    }

}
