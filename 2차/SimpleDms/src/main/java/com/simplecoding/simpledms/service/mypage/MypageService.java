package com.simplecoding.simpledms.service.mypage;

import com.simplecoding.simpledms.mapper.mypage.MypageMapper;
import com.simplecoding.simpledms.vo.common.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : KTE-149
 * @fileName : MypageService
 * @since : 24. 12. 6.
 * description :
 */
@Service
@RequiredArgsConstructor
public class MypageService {

    private final MypageMapper mypageMapper;


    //    전체조회 + 총건수 추가
    public List<?> selectMypageList(Criteria searchVO) {
        List<?> page = mypageMapper.selectMypageList(searchVO);
        //        TODO: 총건수 저장 : Criteria 의 totalItems
        int count = mypageMapper.selectMypageListTotCnt(searchVO);
        searchVO.setTotalItems(count);
        return page;
    }

}
