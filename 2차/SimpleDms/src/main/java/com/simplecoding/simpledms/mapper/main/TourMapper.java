package com.simplecoding.simpledms.mapper.main;

import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.main.Tour;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : TourMapper
 * @since : 24. 11. 27.
 * description :
 */
@Mapper
public interface TourMapper {
    public List<?> selectTour(Criteria searchVO);  //전체조회
    public int selectTourTotCnt(Criteria searchVO);  //총건수

    //추가, 업로드 -> 시퀀스 정수형일때
    public int insert(Tour tour);
    public int updateTourUrl(Tour tour);  // URL 업데이트 추가(tourId + tourUrl)
    public int selectGenerateTourId();  // tourId 생성 후 가져오는 매서드

    public Optional<Tour> select(int tourId);  //상세조회

    public int update(Tour tour);  //수정
    public int delete(int tourId);  //삭제

    public String getTourName(int tourId);  //리뷰게시판

    public List<String> getRoomMinPrice(int tourId); // 방 최저값 갖고오기
}