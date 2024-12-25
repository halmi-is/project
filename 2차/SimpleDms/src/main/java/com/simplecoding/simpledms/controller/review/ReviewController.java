package com.simplecoding.simpledms.controller.review;

import com.simplecoding.simpledms.service.review.ReviewService;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import com.simplecoding.simpledms.vo.main.Tour;
import com.simplecoding.simpledms.vo.recommend.Recommend;
import com.simplecoding.simpledms.vo.review.Review;
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

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 전체 조회
    @GetMapping("/api/review/review")
    public ResponseEntity<?> selectReviewListTotCnt(Criteria searchVO) {
        List<?> reviews = reviewService.selectReviewList(searchVO);
        ResultDto resultDto
                = new ResultDto(reviews, searchVO.getTotalItems());
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    // 리뷰 추가
    @PostMapping("/api/review/add")
    public ResponseEntity<?> insert(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "") String content,
            @RequestParam(defaultValue = "0.0") double rating,
            @RequestParam(defaultValue = "") String authorEmail,
            @RequestParam(defaultValue = "0") int targetId,
            @RequestParam(required = false) MultipartFile image) throws Exception {


        log.debug("title" + title);
        log.debug("content" + content);
        // 이미지가 없을 경우 대비
        byte[] imageData = null;
        if (image != null && !image.isEmpty()) {
            imageData = image.getBytes();
        }

        // 리뷰 객체 생성
        Review review = new Review(title, content, rating, authorEmail, targetId, imageData);


      
        // 서비스 호출
        reviewService.insert(review); // 서비스 클래스에서 리뷰 등록 로직 구현

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //  이미지 다운로드 함수
    @GetMapping("/api/review/{reviewId}")
    public ResponseEntity<byte[]> findDownload(@PathVariable int reviewId) throws Exception {
//      상세조회 : 객체받기(첨부파일)
        Review review = reviewService.select(reviewId).orElseThrow(() -> new FileNotFoundException("데이터 없음"));
//      첨부파일 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", String.valueOf(review.getReviewId()));  //int형 변환
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//      첨부파일 headers에 담아서 전송
        return new ResponseEntity<byte[]>(review.getImageData(), headers, HttpStatus.OK);
    }






    // 상세조회
    @GetMapping("/api/review/get/{reviewId}")
    public ResponseEntity<?> selectReview(@PathVariable int reviewId) {
        Optional<Review> review = reviewService.select(reviewId);
        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(review.get(), HttpStatus.OK);
    }

    // 리뷰 수정
    @PutMapping("/api/review/update/{reviewId}")
    public ResponseEntity<?> update(@PathVariable int reviewId,
                                    @RequestParam String title,
                                    @RequestParam String content,
                                    @RequestParam double rating,
                                    @RequestParam String authorEmail,
                                    @RequestParam int targetId,
                                    @RequestParam(required = false) MultipartFile image) throws Exception {

        byte[] imageData = null;
        // 이미지가 있으면 byte 배열로 변환
        if (image != null && !image.isEmpty()) {
            imageData = image.getBytes();
        }

        Review review = new Review(title, content, rating, authorEmail, targetId, imageData);
        review.setReviewId(reviewId); // 요청된 ID로 review 객체 업데이트
        reviewService.update(review);
        return new ResponseEntity<>(HttpStatus.OK); // 200 OK
    }

    // 리뷰 삭제
    @DeleteMapping("/api/review/deletion/{reviewId}")
    public ResponseEntity<?> delete(@PathVariable int reviewId) {
        reviewService.delete(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }

    @GetMapping("/api/review/tourId")
    public ResponseEntity<?> selectTourList() {
        List<Integer> list = reviewService.getTourId();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }






    // 이메일로 리뷰 목록을 조회하는 API
    @GetMapping("/api/reviews")
    public List<Review> getReviewsByEmail(@RequestParam String email) {
        return reviewService.getReviewsByEmail(email);
    }








}
