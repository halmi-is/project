package com.simplecoding.simpledms.config;

import com.simplecoding.simpledms.security.jwt.AuthTokenFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : KTE
 * @fileName : WebSecurityConfig
 * @since : 24. 11. 12.
 * description : TODO: 스프링 시큐리티 보안설정 파일
 * TODO: @Configuration: 자바 클래스 위에 붙이면 설정파일이 됨
 */
@Configuration
public class WebSecurityConfig {

    // TODO: 암호화 객체 생성 함수 : UserDetailsServiceImpl에서 사용함
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // TODO: 필터 클래스
    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    // TODO: 스프링 시큐리티 설치(보안모드) : 이미지, css, js 등도 보안모드 발동 => 비활성화(무시) 해줘야함
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 사용법: (web) -> web.ignoring().requestMatchers("무시할 경로1", "무시할 경로2", ...)
        return (web) -> web.ignoring().requestMatchers(
                "/img/**",
                "/css/**",
                "/js/**",
                "/api/review/{reviewId}" // 이미지 다운로드 함수 주소, 리뷰게시판 접근권한 설정에 따른 이미지 개별설정(보안무시)
//                "/api/advanced/fileDb/{uuid}", // 이미지 다운로드 함수 주소 => 보안 무시 => 이미지 보임
//                "/api/advanced/gallery/{uuid}" // 이미지 다운로드 함수 주소 => 보안 무시 => 이미지 보임
                // /api/advanced/gallery/{uuid}이나{uuid123}이나 Spring Security에서 이 두 경로를 다르게 인식하지 않습니다.
                // **왜냐면 {}는 단지 변수로 처리되기 때문에 실제로는 "두 경로가 동일하다"**고 간주됩니다.
        );
    }

    // TODO: 함수: 보안 설정 ex) 부서/사원메뉴 실행할 수 있게 허용
    //      => 화면의 CRUD 기능을 허용/불가 설정을 하는것
    //      => cors 보안 : 1개의 컴퓨터에서 포트 번호 및 IP를 여러개 사용할 수 있게 하는 설정
    //           ex) vue (8080) <-> springboot(8000) : x(기본) -> o(허용)
    // @Bean ==  @Service, @Mapper 등 => 스프링 시작 시 객체를 생성함(IOC)
    //  => 설정 자바파일에 사용함(@Bean)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults()); // 1) cors 보안설정 사용
        http.csrf((csrf) -> csrf.disable());  // 2) csrf 해킹공격(쿠키/세션 인증) 방어 비활성화
        // 3) 쿠키/세션 인증 비활성화(다른 인증 사용: jwt)
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(req -> req.disable()); // 4) jsp 방식 비활성화 -> axios 통신사용

        // TODO: 화면별 보안설정: ex) 부서 화면 모두(CRUD) 허용, 사원은 허용안함
        // 용어: 인증(authentication) , 권한(authority)
        // 1) 인증: 로그인하는것 (ID/PASSWORD 확인)
        // 2) 권한: 화면별로 (관리자/유저) 관리자가 볼 수 있는것,
        //                               유저가 볼 수 있는것을 나눈것
        http.authorizeHttpRequests(
                // 옵션(페이지이동) redirect기능, permitAll() url은 모두 접근 허용(로그인 관계없이)
                req -> req.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/api/auth/**").permitAll() // 로그인/회원가입 페이지
                        .requestMatchers("/api/tour/**").permitAll() // 메인 페이지
                        .requestMatchers("/api/faq/**").permitAll() // FAQ 페이지
                        .requestMatchers("/api/room/**").permitAll() // 예약, 상세 페이지
                        .requestMatchers("/api/cart/**").permitAll() // 장바구니 페이지
                        .requestMatchers("/api/coupon/**").permitAll() // 쿠폰 페이지
                        .requestMatchers("/api/recommend/**").permitAll() // 추천여행지 페이지
//                        .requestMatchers("/api/comments/**").permitAll() // 추천여행지 댓글 게시판
                        .requestMatchers("/api/review/review").permitAll() // 리뷰 게시판(메인), 글등록 등 나머지 안됨
                        .requestMatchers("/api/review/get/{reviewId}").permitAll() // 리뷰 게시판(상세), 글등록 등 나머지 안됨
//                        .requestMatchers("/api/payment/complete").permitAll() // 결제 페이지
//                        .requestMatchers("/api/user/hasCoupons").authenticated() // 유저 쿠폰내역 확인?
//                        .requestMatchers("/**").permitAll() // 모두 접근 허용(로그인 관계없이), 작업편의상 설정해놓음
                        .anyRequest().authenticated() // => 나머지 url들은 인증(로그인) 해야 볼 수 있음
        );
        
        // 스프링 시큐리티에 적용 : 토큰 필터
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build(); // 권한/인증 규칙 설정완료(반영)
    }
}