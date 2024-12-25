package com.simplecoding.simpledms.mapper.recommend;

import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.main.Tour;
import com.simplecoding.simpledms.vo.recommend.Recommend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE-149
 * @fileName : RecommendMapper
 * @since : 24. 11. 29.
 * description :
 */
@Mapper
public interface RecommendMapper {



    public List<?> selectRecommendList(Criteria searchVO);    // 전체조회
    public int selectRecommendListTotCnt(Criteria searchVO); // 총건수





  //////////////////////////////////////////////////////////



    public int insert(Recommend recommend); // 생성

    ////////////////////////////////////////

    public int selectGenerateTdId();  // tourId 생성 후 가져오는 매서드
    public int updatetdIdUrl(Recommend recommend);  // URL 업데이트 추가(tourId + tourUrl)

//////////////////////////////

    public Optional<Recommend> select(int tdId);  //상세조회

    public int update(Recommend recommend);  //수정
    public int delete(int tdId);  //삭제


}
