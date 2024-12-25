package com.simplecoding.simpledms.controller.mypage;

import com.simplecoding.simpledms.service.mypage.MypageService;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : KTE-149
 * @fileName : MypageController
 * @since : 24. 12. 6.
 * description :
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;

    //    전체 조회

    @GetMapping("/api/mypage")
    public ResponseEntity<?> selectMypageList(Criteria searchVO) {
        List<?> mypages = mypageService.selectMypageList(searchVO);
        ResultDto resultDto
                = new ResultDto(mypages, searchVO.getTotalItems());
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

}