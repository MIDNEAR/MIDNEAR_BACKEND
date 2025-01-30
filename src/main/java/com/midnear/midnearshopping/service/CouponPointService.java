package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.coupon_point.PointDto;
import com.midnear.midnearshopping.domain.dto.coupon_point.PointToSelectedUserDto;
import com.midnear.midnearshopping.domain.vo.coupon_point.PointVo;
import com.midnear.midnearshopping.domain.vo.coupon_point.ReviewPointVo;
import com.midnear.midnearshopping.mapper.coupon_point.PointMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Transactional
    public void grantPointsToSelectedUsers(PointToSelectedUserDto pointToSelectedUserDto) {
        // 포인트 내역 저장
        PointVo pointVo = PointVo.builder()
                .pointId(null)
                .amount(pointToSelectedUserDto.getAmount())
                .reason(pointToSelectedUserDto.getReason())
                .build();
        pointMapper.grantPoints(pointVo);

        List<String> userIdList = pointToSelectedUserDto.getUserIdList();
        // 특정 사용자에게 포인트 지급
        for (String id : userIdList) {
            usersMapper.addPointsToUser(id, pointToSelectedUserDto.getAmount());
        }
    }

    public Map<String, Object> searchUser(String id, int pageNumber) {
        Map<String, Object> response = new HashMap<>();
        int offset = (pageNumber - 1) * 10;
        Long pageSize = usersMapper.getPageSize(id) / 10 + 1;
        List<String> result = usersMapper.findUserByIdPaging(id, offset);

        response.put("pageSize", pageSize);
        response.put("searchResult", result);
        return response;

    }

    @Transactional
    public void setReviewPointAmount(ReviewPointVo reviewPointVo) {
        // 이전 데이터 지우고
        pointMapper.deletePreviousData();
        // 다시 등록
        pointMapper.setReviewPointAmount(reviewPointVo);
    }
}
