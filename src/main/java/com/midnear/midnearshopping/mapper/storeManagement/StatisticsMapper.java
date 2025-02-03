package com.midnear.midnearshopping.mapper.storeManagement;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

@Mapper
public interface StatisticsMapper {
    BigDecimal getDailySales(Date date);
    BigDecimal getRefundedTotalPrice(Date date);
    BigDecimal getWeeklySales(@Param("start") String start, @Param("end") String end);
    BigDecimal getWeeklyRefundedTotalPrice(@Param("start") String start, @Param("end") String end);
    BigDecimal getMonthlySales(String month);
    BigDecimal getMonthlyRefundedTotalPrice(String month);
}
