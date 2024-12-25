package com.simplecoding.simpledms.controller.auth;

import com.simplecoding.simpledms.service.auth.MemberService;
import com.simplecoding.simpledms.vo.auth.Member;
import com.simplecoding.simpledms.vo.dto.UserReqDto;
import com.simplecoding.simpledms.vo.dto.UserResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author : KTE
 * @fileName : MemberController
 * @since : 24. 11. 11.
 * description :
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/api/auth/register")
    public ResponseEntity<?> insert(@RequestBody Member member) {

        memberService.insert(member);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody UserReqDto userReqDto) {

        UserResDto userResDto = memberService.login(userReqDto);

        return new ResponseEntity<>(userResDto, HttpStatus.OK);
    }

    // 이메일 찾기
    @PostMapping("/api/auth/find-email")
    public ResponseEntity<List<String>> findEmail(@RequestBody Member member) {
        // 이메일 찾기 서비스 호출
        List<String> emails = memberService.findEmail(member);

        if (!emails.isEmpty()) {
            return ResponseEntity.ok(emails); // 여러 이메일을 반환
        }

        // 이메일이 없으면 404 상태 코드와 함께 응답
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("이메일을 찾을 수 없습니다.")); // 실패 응답
    }

    // 비밀번호 변경
    @PutMapping("/api/auth/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody Member member) {
        try {
            // 비밀번호 변경 서비스 호출
            memberService.updatePassword(member);
            return ResponseEntity.ok("비밀번호가 변경되었습니다.");
        } catch (RuntimeException e) {
            // 해당 사용자가 없을 때 예외 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호를 변경할 수 없습니다. 잠시 후 다시 시도해 주세요.");
        }
    }

}
