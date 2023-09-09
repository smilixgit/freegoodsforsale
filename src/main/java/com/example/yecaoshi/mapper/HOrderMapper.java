package com.example.yecaoshi.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yecaoshi.pojo.HOrder;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface HOrderMapper extends BaseMapper<HOrder> {

  default boolean selectByOrderNo(String orderNo)
  {
      QueryWrapper<HOrder> queryWrapper=new QueryWrapper<>();
      queryWrapper
              .eq("order_no",orderNo);
      HOrder hOrder=selectOne(queryWrapper);
      return hOrder != null;
  }

}
