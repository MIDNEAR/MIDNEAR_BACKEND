package com.midnear.midnearshopping.service.order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNumberGenerator {
    /**
     * 14자리 주문번호 생성
     * 형식: yyyyMMddHHmmss (14자리) + 3자리 난수
     */
    public static String generateOrderNumber() {
        // 현재 시간을 yyyyMMddHHmmss 형식으로 변환
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());

        // 3자리 난수 생성 (0 ~ 999)
        int randomSuffix = new Random().nextInt(1000);

        // 3자리 숫자로 포맷 (예: 007, 042, 999)
        String formattedRandom = String.format("%03d", randomSuffix);

        // 최종 주문번호 반환 (17자리)
        return timestamp + formattedRandom;
    }

    public static void main(String[] args) {
        // 테스트 실행
        System.out.println("Generated Order Number: " + generateOrderNumber());
    }
}
