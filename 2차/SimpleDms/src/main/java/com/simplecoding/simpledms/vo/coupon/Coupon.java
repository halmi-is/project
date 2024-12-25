package com.simplecoding.simpledms.vo.coupon;

import lombok.*;

import java.sql.Timestamp;

/**
 * @author : KTE-149
 * @fileName : Coupon
 * @since : 24. 11. 26.
 * description : 쿠폰 정보를 나타내는 VO 클래스
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Coupon {



    private String id;           // 쿠폰번호
    private double value;        // 할인율
    private String name;         // 쿠폰 이벤트명
    private String memberEmail;  // 회원 이메일 (선택사항)





}
