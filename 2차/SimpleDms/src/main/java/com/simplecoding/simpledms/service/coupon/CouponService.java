package com.simplecoding.simpledms.service.coupon;

import com.simplecoding.simpledms.mapper.coupon.CouponMapper;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.coupon.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : KTE-149
 * @fileName : CouponService
 * @since : 24. 11. 26.
 * description :
 */
@Service
@RequiredArgsConstructor

public class CouponService {
    private final CouponMapper couponMapper;


    public void insert(Coupon coupon) {

        couponMapper.insert(coupon);
    }            // 쿠폰인서트


    //    TODO: 전체조회 + 총건수(Criteria 의 totalItems 변수 저장)
    public List<?> selectCouponList(Criteria searchVO) {
        List<?> page = couponMapper.selectCouponList(searchVO);

//        TODO: 총건수 저장 : Criteria 의 totalItems
        int count = couponMapper.selectCouponListTotCnt(searchVO);
        searchVO.setTotalItems(count);

        return page;
    }


    public List<Coupon> selectCouponsByEmail(String email) {
        // 이메일을 기준으로 쿠폰 조회
        return couponMapper.selectCouponsByEmail(email);
    }


    public boolean hasReceivedCoupon(String email) {
        // 사용자가 이미 받은 쿠폰을 조회
        List<Coupon> userCoupons = couponMapper.selectCouponsByEmail(email);
        return !userCoupons.isEmpty(); // 이미 쿠폰이 있으면 true 반환
    }

    public  Coupon couponByEmail(String memberEmail) {
        return couponMapper.couponByEmail(memberEmail);
    }


}
