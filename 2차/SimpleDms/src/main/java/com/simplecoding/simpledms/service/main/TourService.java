package com.simplecoding.simpledms.service.main;

import com.simplecoding.simpledms.mapper.main.TourMapper;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.main.Tour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : TourService
 * @since : 24. 11. 27.
 * description :
 */
@Service
@RequiredArgsConstructor
public class TourService {
    private final TourMapper tourMapper;

//  전체조회
    public List<?> selectTour(Criteria searchVO){
        List<?> page = tourMapper.selectTour(searchVO);
//      총건수
        int count = tourMapper.selectTourTotCnt(searchVO);
        searchVO.setTotalItems(count);
        return page;
    }

//  추가, 업로드
    public void insert(Tour tour){
        tourMapper.insert(tour);
//      생성된 tourId를 DB에서 가져오기(SQL문 CURRVAL)
        int tourId = tourMapper.selectGenerateTourId();
//      생성된 tourId를 tour객체에 저장
        tour.setTourId(tourId);
//      URL생성
        String url = generateTourUrl(tour.getTourId());
        tour.setTourFileUrl(url);
//      URL 업데이트
        tourMapper.updateTourUrl(tour);
    }
//  generateTourUrl 매서드 정의
    public String generateTourUrl(int tourId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()        // spring 기본주소 : http://localhost:8000
                .path("/api/tour/")              // 추가 경로 넣기 : /api/tour/
                .path(String.valueOf(tourId))
                .toUriString();                  // 합치기 : http://localhost:8000/api/tour/xxxxxx
    }

//  상세조회
    public Optional<Tour> select(int tourId){
        return tourMapper.select(tourId);
    }

//  수정
    public void update(Tour tour){
        int tourId = tour.getTourId();
        String url = generateTourUrl(tourId);
        tour.setTourId(tourId);
        tour.setTourFileUrl(url);
        tourMapper.update(tour);
    }

//  삭제
    public void delete(int tourId){
        tourMapper.delete(tourId);
    }

//  리뷰게시판
    public String ListName(int tourId){
        return tourMapper.getTourName(tourId);
    }


    // 최저값 갖고오기
    public List<String>getRoomMinPrice(int tourId){
        return tourMapper.getRoomMinPrice(tourId);
    }

}