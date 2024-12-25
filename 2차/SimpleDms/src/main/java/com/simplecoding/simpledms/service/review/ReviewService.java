package com.simplecoding.simpledms.service.review;

import com.simplecoding.simpledms.mapper.review.ReviewMapper;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.main.Tour;
import com.simplecoding.simpledms.vo.review.Review;
import jakarta.servlet.Servlet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : ReviewService
 * @since : 24. 11. 27.
 * description :
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;

    //    전체조회 + 총건수 추가
    public List<?> selectReviewList(Criteria searchVO) {
        List<?> page = reviewMapper.selectReviewList(searchVO);
        //        TODO: 총건수 저장 : Criteria 의 totalItems
        int count = reviewMapper.selectReviewListTotCnt(searchVO);
        searchVO.setTotalItems(count);
        return page;
    }

    //    리뷰추가
    public void insert(Review review) {
        log.debug("service : "+review);
        // 리뷰 데이터 삽입
        reviewMapper.insert(review);

        // 생성된 리뷰 ID 가져오기
        int reviewId = reviewMapper.selectGenerateReviewId();
        review.setReviewId(reviewId);


        String url = "";

        // URL 생성
        // 1) if: review.getImageData() != null
        if (review.getImageData() != null) {
            // 이미지 데이터가 있는 경우, reviewId를 기반으로 URL을 생성
            url = generateReviewUrl(review.getReviewId());
            review.setImageUrl(url);
        } else {
            // 2) else: 기본 이미지 URL 설정
            // 이미지 데이터가 없는 경우 기본 이미지를 사용
            review.setImageUrl("https://picsum.photos/600/400");
        }
//      URL 업데이트
        reviewMapper.updateReviewUrl(review);
    }

    public String generateReviewUrl(int reviewId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/review/")
                .path(String.valueOf(reviewId))
                .toUriString();
    }

    //    상세조회
    public Optional<Review> select(int reviewId ) {return reviewMapper.select(reviewId); }

    //    리뷰수정
    public void update(Review review) {
        int reviewId = review.getReviewId();
        String url = generateReviewUrl(reviewId);
        review.setReviewId(reviewId);
        review.setImageUrl(url);
        reviewMapper.update(review);
    }

    //    리뷰삭제
    public void delete(int reviewId) {
        reviewMapper.delete(reviewId);
    }

    public List<Integer> getTourId() {
        return reviewMapper.getTourId();
    }

    // 이메일을 받아 해당 사용자가 작성한 리뷰 목록을 반환
    public List<Review> getReviewsByEmail(String authorEmail) {
        return reviewMapper.getReviewsByEmail(authorEmail);
    }





}
