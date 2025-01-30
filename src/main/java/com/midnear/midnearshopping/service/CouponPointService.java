package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.coupon_point.PointDto;
import com.midnear.midnearshopping.domain.vo.coupon_point.PointVo;
import com.midnear.midnearshopping.domain.vo.users.UsersVO;
import com.midnear.midnearshopping.mapper.coupon_point.PointMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponPointService {
    private final PointMapper pointMapper;
    private final UsersMapper usersMapper;

    @Transactional
    public void grantPointsToAll(PointDto pointDto) {
        // 포인트 내역 저장
        PointVo pointVo = PointVo.toEntity(pointDto);
        pointMapper.grantPoints(pointVo);

        // 모든 사용자에게 포인트 지급
        List<String> userIds = usersMapper.getAllId();
        for (String id : userIds) {
            usersMapper.addPointsToUser(id, pointDto.getAmount());
        }
    }


}
