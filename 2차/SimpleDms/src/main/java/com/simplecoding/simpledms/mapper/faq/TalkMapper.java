package com.simplecoding.simpledms.mapper.faq;

import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.faq.Talk;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : TalkMapper
 * @since : 24. 12. 6.
 * description :
 */
@Mapper
public interface TalkMapper {

    List<?> selectTalkList(Criteria searchVO);

    int selectTalkListCount(Criteria searchVO);

    int insertTalk(Talk talk);

    Optional<Talk> selectTalk(int tno);

    int updateTalk(Talk talk);

    int deleteTalk(int tno);
}
