package com.simplecoding.simpledms.controller.recommend;

import com.simplecoding.simpledms.service.recommend.RecommendService;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import com.simplecoding.simpledms.vo.main.Tour;
import com.simplecoding.simpledms.vo.recommend.Recommend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * @author : KTE-149
 * @fileName : RecommendController
 * @since : 24. 11. 29.
 * description :
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    //    전체 조회

    @GetMapping("/api/recommend")
    public ResponseEntity<?> selectRecommendList(Criteria searchVO) {
        List<?> recommends = recommendService.selectRecommendList(searchVO);
        ResultDto resultDto
                = new ResultDto(recommends, searchVO.getTotalItems());
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }


    //인서트

    @PostMapping("/api/recommend/add")
    public ResponseEntity<?> insert(@RequestParam(defaultValue = "")String tdName,
                                    @RequestParam(defaultValue = "")String loc,
                                    @RequestParam(defaultValue = "")String description,
                                    @RequestParam(required = false) MultipartFile image) throws Exception {




        Recommend recommend = new Recommend(tdName, loc, description, image.getBytes());
        recommendService.insert(recommend);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    //  이미지 다운로드 함수
    @GetMapping("/api/recommend/{tdId}")
    public ResponseEntity<byte[]> findDownload(@PathVariable int tdId) throws Exception {
//      상세조회 : 객체받기(첨부파일)
        Recommend recommend = recommendService.select(tdId).orElseThrow(() -> new FileNotFoundException("데이터 없음"));
//      첨부파일 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", String.valueOf(recommend.getTdId()));  //int형 변환
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//      첨부파일 headers에 담아서 전송
        return new ResponseEntity<byte[]>(recommend.getImageData(), headers, HttpStatus.OK);
    }



    //  상세조회
    @GetMapping("/api/recommend/get/{tdId}")
    public ResponseEntity<?> select(@PathVariable int tdId) {
        Optional<Recommend> recommend = recommendService.select(tdId);
//      에러처리
        if (recommend.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(recommend.get(), HttpStatus.OK);
    }




    //    삭제
    @DeleteMapping("/api/recommend/deletion/{tdId}")
    public ResponseEntity<?> delete(@PathVariable int tdId) {
        recommendService.delete(tdId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    //수정
    @PutMapping("/api/recommend/update/{tdId}")
    public ResponseEntity<?> update(@PathVariable int tdId,
                                    @RequestParam(defaultValue = "")String tdName,
                                    @RequestParam(defaultValue = "")String loc,
                                    @RequestParam(defaultValue = "")String description,
                                    @RequestParam MultipartFile image) throws Exception {
        Recommend recommend = new Recommend(tdId, tdName, loc, description, image.getBytes());
        recommendService.update(recommend);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
