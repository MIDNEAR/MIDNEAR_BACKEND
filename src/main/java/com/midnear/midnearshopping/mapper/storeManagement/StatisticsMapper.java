package com.midnear.midnearshopping.mapper.storeManagement;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface StatisticsMapper {
    Long getDailySales(Date date);
    Long getRefundedTotalPrice(Date date);
    Long getWeeklySales(@Param("start") String start, @Param("end") String end);
    Long getWeeklyRefundedTotalPrice(@Param("start") String start, @Param("end") String end);
    Long getMonthlySales(String month);
    Long getMonthlyRefundedTotalPrice(String month);
}
