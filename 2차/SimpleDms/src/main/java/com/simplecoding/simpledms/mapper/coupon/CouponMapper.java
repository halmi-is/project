package com.simplecoding.simpledms.mapper.coupon;

import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.coupon.Coupon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : KTE-149
 * @fileName : CouponMapper
 * @since : 24. 11. 26.
 * description :
 */
@Mapper
public interface CouponMapper {

   int insert(Coupon coupon);  // 쿠폰 insert 메서드
   List<Coupon> selectCouponsByEmail(String email);
   public List<?> selectCouponList(Criteria searchVO);   // 전체조회
   public int selectCouponListTotCnt(Criteria searchVO); // 총건수구하기
   
   public Coupon couponByEmail(String memberEmail); // 각 이메일마다 쿠폰 조회


}
