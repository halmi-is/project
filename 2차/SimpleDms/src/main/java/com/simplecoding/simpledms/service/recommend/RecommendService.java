package com.simplecoding.simpledms.service.recommend;

import com.simplecoding.simpledms.mapper.recommend.RecommendMapper;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.recommend.Recommend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;


/**
 * @author : KTE-149
 * @fileName : RecommendService
 * @since : 24. 11. 29.
 * description :
 */
@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendMapper recommendMapper;


    //    전체조회 + 총건수 추가
    public List<?> selectRecommendList(Criteria searchVO) {
        List<?> page = recommendMapper.selectRecommendList(searchVO);
        //        TODO: 총건수 저장 : Criteria 의 totalItems
        int count = recommendMapper.selectRecommendListTotCnt(searchVO);
        searchVO.setTotalItems(count);
        return page;
    }


    public void insert(Recommend recommend) {
        recommendMapper.insert(recommend);
//      생성된 tourId를 DB에서 가져오기(SQL문 CURRVAL)
        int tdId = recommendMapper.selectGenerateTdId();
//      생성된 tourId를 tour객체에 저장\
        recommend.setTdId(tdId);
//      URL생성
        String url = generateRecommendUrl(recommend.getTdId());
        recommend.setImageUrl(url);
//      URL 업데이트
        recommendMapper.updatetdIdUrl(recommend);
    }


    //  generateTourUrl 매서드 정의
    public String generateRecommendUrl(int tdId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()        // spring 기본주소 : http://localhost:8000
                .path("/api/recommend/")              // 추가 경로 넣기 : /api/tour/
                .path(String.valueOf(tdId))
                .toUriString();                  // 합치기 : http://localhost:8000/api/tour/xxxxxx
    }



    //  상세조회
    public Optional<Recommend> select(int tdId){
        return recommendMapper.select(tdId);
    }


    //  수정
    public void update(Recommend recommend){
        int tdId = recommend.getTdId();
        String url = generateRecommendUrl(tdId);
        recommend.setTdId(tdId);
        recommend.setImageUrl(url);
        recommendMapper.update(recommend);
    }

    //  삭제
    public void delete(int tdId){
        recommendMapper.delete(tdId);
    }











}








