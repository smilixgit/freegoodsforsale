package com.example.yecaoshi.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Data
@Component

public class resp {
    public int code;
    public String msg;
    public Object data;
    public String token;
}
