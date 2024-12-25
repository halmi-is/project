package com.simplecoding.simpledms.mapper.auth;

import com.simplecoding.simpledms.vo.auth.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : MemberMapper
 * @since : 24. 11. 11.
 * description :
 */
@Mapper
public interface MemberMapper {

    // 회원가입
    public int insert(Member member);

    // 우리 회원인지 확인
    public long existsById(String email);

    // 회원 상세조회
    public Optional<Member> selectMember(String email);

    // 이메일 찾기
    public List<String> findEmail(Member member);

    // 비밀번호 변경
    public int updatePassword(Member member);

    // <!-- 이메일과 주민등록번호로 상세조회 --> => 비밀번호 변경 시, 사용
    public Optional<Member> selectMemberByEmailAndSSN(Member member);

}
