package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.coupon_point.*;
import com.midnear.midnearshopping.domain.vo.coupon_point.CouponVo;
import com.midnear.midnearshopping.domain.vo.coupon_point.PointVo;
import com.midnear.midnearshopping.domain.vo.coupon_point.ReviewPointVo;
import com.midnear.midnearshopping.mapper.coupon_point.CouponMapper;
import com.midnear.midnearshopping.mapper.coupon_point.PointMapper;
import com.midnear.midnearshopping.mapper.coupon_point.UserCouponMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hpsf.Decimal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponPointService {
    private final PointMapper pointMapper;
    private final CouponMapper couponMapper;
    private final UsersMapper usersMapper;
    private final UserCouponMapper userCouponMapper;

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
        // 전체 페이지 사이즈 계산
        Long pageSize = usersMapper.getPageSize(id) / 10 + 1;
        // 아이디로 검색 결과
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

    @Transactional
    public void grantCouponToAll(CouponDto couponDto) {
        // 유효성 검사
        if (couponDto.getDiscountRate() > 100 || couponDto.getDiscountRate() == 0)
            throw new IllegalArgumentException();

        // 쿠폰 등록
        CouponVo couponVo = CouponVo.builder()
                .couponId(null)
                .couponName(couponDto.getCouponName())
                .discountRate(couponDto.getDiscountRate())
                .build();
        couponMapper.registerCoupon(couponVo);
        // 전체 사용자에게 쿠폰 지급
        List<Long> userIds = usersMapper.getAllUserId(); // pk인 id 값
        for (Long id: userIds) {
            couponMapper.grantCoupon(id, couponVo.getCouponId());
        }
    }

    @Transactional
    public void grantCouponToSelectedUsers(CouponToSelectedUserDto couponDto) {
        // 유효성 검사
        if (couponDto.getDiscountRate() > 100 || couponDto.getDiscountRate() == 0)
            throw new IllegalArgumentException();

        // 쿠폰 등록
        CouponVo couponVo = CouponVo.builder()
                .couponId(null)
                .couponName(couponDto.getCouponName())
                .discountRate(couponDto.getDiscountRate())
                .build();
        couponMapper.registerCoupon(couponVo);
        // 특정 사용자에게 쿠폰 지급
        List<String> userIds = couponDto.getUserIdList(); // 사용자 아이디
        for (String id: userIds) {
            Integer userId = usersMapper.getUserIdById(id);
            couponMapper.grantCoupon(Long.valueOf(userId), couponVo.getCouponId());
        }
    }


    //여기서부터 사용자 시~작
    public List<CouponInfoDto> getCouponsByUserId(String id) {
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null){
            throw new UsernameNotFoundException("User not found");
        }
        return userCouponMapper.getCouponsByUserId(userId);
    }
    //쿠폰 총 개수
    public int getCouponsCountByUserId(String id) {
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null){
            throw new UsernameNotFoundException("User not found");
        }
        return userCouponMapper.getCouponsCountByUserId(userId);
    }

    //포인트 사용 내역
    public List<UserPointDto> getPointLists(String id) {
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null){
            throw new UsernameNotFoundException("User not found");
        }
        return pointMapper.getPointList(userId);
    }
    //포인트 총량
    public Decimal getPoints(String id) {
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null){
            throw new UsernameNotFoundException("User not found");
        }
        return usersMapper.getPointAmount(userId);
    }

}
