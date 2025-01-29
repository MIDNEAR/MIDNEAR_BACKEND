package com.midnear.midnearshopping.mapper.storeManagement;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface StatisticsMapper {
    Long getDailySales(Date date);
    Long getRefundedTotalPrice(Date date);
    Long getMonthlySales(String month);
    Long getMonthlyRefundedTotalPrice(String month);
}
