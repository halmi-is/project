package com.simplecoding.simpledms.vo.auth;

import lombok.*;

/**
 * @author : KTE
 * @fileName : Member
 * @since : 24. 11. 11.
 * description :
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Member {

    private String email; // 이메일(기본키)
    private String password; // 비밀번호
    private String name; // 이름
    private String codeName; // 권한명
    private String ssn; // 주민등록번호

}
