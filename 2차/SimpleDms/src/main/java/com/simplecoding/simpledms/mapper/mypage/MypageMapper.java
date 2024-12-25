package com.simplecoding.simpledms.mapper.mypage;

import com.simplecoding.simpledms.vo.common.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : KTE-149
 * @fileName : MypageMapper
 * @since : 24. 12. 6.
 * description :
 */
@Mapper
public interface MypageMapper {

    public List<?> selectMypageList(Criteria searchVO);    // 전체조회
    public int selectMypageListTotCnt(Criteria searchVO); // 총건수

}
