package com.midnear.midnearshopping.service.order;

import java.util.Arrays;
import java.util.List;

public class PostalCodeChecker {
    public static String checkRegion(int postalCode) {
        // 제주도 범위
        if (postalCode >= 63000 && postalCode <= 63644) {
            return "jeju";
        }

        // 산간지역 우편번호 범위 (리스트 내 범위 포함 여부 확인)
        List<int[]> mountainRegions = Arrays.asList(
            new int[]{22386, 22388}, new int[]{23004, 23010}, new int[]{23100, 23116}, new int[]{23124, 23136}, 
            new int[]{31708, 31708}, new int[]{32133, 32133}, new int[]{33411, 33411}, new int[]{40200, 40240}, 
            new int[]{46768, 46771}, new int[]{52570, 52571}, new int[]{53031, 53033}, new int[]{53089, 53104}, 
            new int[]{54000, 54000}, new int[]{56347, 56349}, new int[]{57068, 57069}, new int[]{58760, 58762}, 
            new int[]{58800, 58810}, new int[]{58816, 58818}, new int[]{58826, 58826}, new int[]{58828, 58866}, 
            new int[]{58953, 58958}, new int[]{59102, 59103}, new int[]{59106, 59106}, new int[]{59127, 59127}, 
            new int[]{59129, 59129}, new int[]{59137, 59166}, new int[]{59650, 59650}, new int[]{59766, 59766}, 
            new int[]{59781, 59790}
        );

        for (int[] range : mountainRegions) {
            if (postalCode >= range[0] && postalCode <= range[1]) {
                return "mountain";
            }
        }
        // 위 조건에 해당하지 않으면 일반 지역
        return "basic";
    }

}
