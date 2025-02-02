package com.midnear.midnearshopping.service;

import com.midnear.midnearshopping.domain.dto.coupon_point.*;
import com.midnear.midnearshopping.domain.vo.coupon_point.CouponVo;
import com.midnear.midnearshopping.domain.vo.coupon_point.PointVo;
import com.midnear.midnearshopping.domain.vo.coupon_point.ReviewPointVo;
import com.midnear.midnearshopping.domain.vo.review.ReviewImagesVO;
import com.midnear.midnearshopping.mapper.coupon_point.CouponMapper;
import com.midnear.midnearshopping.mapper.coupon_point.PointMapper;
import com.midnear.midnearshopping.mapper.coupon_point.ReviewPointMapper;
import com.midnear.midnearshopping.mapper.coupon_point.UserCouponMapper;
import com.midnear.midnearshopping.mapper.review.ReviewImagesMapper;
import com.midnear.midnearshopping.mapper.review.ReviewsMapper;
import com.midnear.midnearshopping.mapper.users.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hpsf.Decimal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private final ReviewImagesMapper reviewImagesMapper;
    private final ReviewPointMapper reviewPointMapper;
    private final ReviewsMapper reviewsMapper;

    @Transactional
    public void grantPointsToAll(PointDto pointDto) {
        // 포인트 내역 저장
        PointVo pointVo = PointVo.builder()
                .amount(pointDto.getAmount())
                .reason(pointDto.getReason())
                .reviewId(null)
                .userId(pointDto.getUserId())
                .build();
        pointMapper.grantPoints(pointVo);

        // 모든 사용자에게 포인트 지급
        List<String> userIds = usersMapper.getAllId();
        for (String id : userIds) {
            usersMapper.addPointsToUser(id, pointDto.getAmount());
        }
    }

    @Transactional
    public void grantPointsToSelectedUsers(PointToSelectedUserDto pointToSelectedUserDto) {
        List<String> userIdList = pointToSelectedUserDto.getUserIdList();
        for (String id : userIdList) {
            Long userId = Long.valueOf(usersMapper.getUserIdById(id));
            // 포인트 내역 저장
            PointVo pointVo = PointVo.builder()
                    .amount(pointToSelectedUserDto.getAmount())
                    .reason(pointToSelectedUserDto.getReason())
                    .reviewId(null)
                    .userId(userId)
                    .build();
            pointMapper.grantPoints(pointVo);
        }

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
        reviewPointMapper.deletePreviousData();
        // 다시 등록
        reviewPointMapper.setReviewPointAmount(reviewPointVo);
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

    @Transactional
    public void grantReviewPoint(Long reviewId) {
        // 포토인지 텍스트인지 확인 후 설정된 지급 amount를 테이블에서 가져오고
        Boolean isPhotoReview = reviewImagesMapper.isPhotoReview(reviewId);
        Long amount = 0L;
        String reason = pointMapper.getProductInfoByReviewId(reviewId); // 상품명 _ 컬러로 지급 사유
        Long userId = reviewsMapper.getUserIdByReviewId(reviewId);
        if (isPhotoReview)
            amount = reviewPointMapper.getPhotoReview();
         else
            amount = reviewPointMapper.getTextReview();

        // 리뷰 승인 시 포인트 내역 저장
        PointVo pointVo = PointVo.builder()
                .amount(amount)
                .reason(reason)
                .reviewId(reviewId)
                .userId(userId)
                .build();
        pointMapper.grantPoints(pointVo);

        // 작성한 사용자에게 포인트 지급
        String id = usersMapper.getIdByUserId(userId);
        usersMapper.addPointsToUser(id, amount);

        // 승인 상태 업데이트
        reviewsMapper.setApproveStatusTrue(reviewId);
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
    public BigDecimal getPoints(String id) {
        Integer userId = usersMapper.getUserIdById(id);
        if (userId == null){
            throw new UsernameNotFoundException("User not found");
        }
        return usersMapper.getPointAmount(userId);
    }
}
