package com.simplecoding.simpledms.controller.coupon;

import com.simplecoding.simpledms.service.coupon.CouponService;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.coupon.Coupon;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author : KTE-149
 * @fileName : CouponController
 * @since : 24. 11. 26.
 * description :
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;


    // 쿠폰발급
    @PostMapping("/api/add/coupon")
    public ResponseEntity<?> insert(
            @RequestBody Coupon coupon,
            Authentication authentication // Spring Security의 Authentication 객체 주입
    ) {


        // 현재 인증된 사용자의 이메일 추출
        String email = authentication.getName();


        // 쿠폰을 이미 받은 사용자라면 발급하지 않음
        if (couponService.hasReceivedCoupon(email)) {
            return new ResponseEntity<>("이미 쿠폰을 받으셨습니다.", HttpStatus.BAD_REQUEST);
        }


        // Coupon 객체에 이메일 설정
        coupon.setMemberEmail(email);

        couponService.insert(coupon);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //쿠폰 회원마다 필터링

    @GetMapping("/api/use/coupon")
    public ResponseEntity<?> getCouponsByUser(Authentication authentication) {
        // 현재 인증된 사용자의 이메일 추출
        String email = authentication.getName();

        // 서비스 호출을 통해 사용자의 쿠폰 조회
        List<Coupon> userCoupons = couponService.selectCouponsByEmail(email);

        if (userCoupons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(userCoupons, HttpStatus.OK);

    }


    //    전체 조회

    @GetMapping("/api/list/coupon")
    public ResponseEntity<?> selectCouponList(Criteria searchVO) {
        List<?> coupons = couponService.selectCouponList(searchVO);
        ResultDto resultDto
                = new ResultDto(coupons, searchVO.getTotalItems());
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }


    // 이메일마다 쿠폰 조회
    @GetMapping("/api/coupon/email/{memberEmail}")
    public ResponseEntity<?> couponByEmail(@PathVariable String memberEmail) {
        Coupon coupon = couponService.couponByEmail(memberEmail);

        if (coupon == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 쿠폰이 없는 경우
        }

        return new ResponseEntity<>(coupon, HttpStatus.OK); // 정상 데이터 반환
    }


}






