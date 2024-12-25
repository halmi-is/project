package com.simplecoding.simpledms.service.faq;

import com.simplecoding.simpledms.mapper.faq.TalkMapper;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.faq.Talk;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : KTE
 * @fileName : TalkService
 * @since : 24. 12. 6.
 * description :
 */
@Service
@RequiredArgsConstructor
public class TalkService {
    private final TalkMapper talkMapper;

    public List<?> selectTalkList(Criteria searchVO) {
        List<?> page = talkMapper.selectTalkList(searchVO);
        int count = talkMapper.selectTalkListCount(searchVO);
        searchVO.setTotalItems(count);
        return page;
    }

    public void insertTalk(Talk talk) {
        talkMapper.insertTalk(talk);
    }




    public Optional<Talk> selectTalk(int tno) {
        return talkMapper.selectTalk(tno);
    }

    public void updateTalk(Talk talk) {
        talkMapper.updateTalk(talk);
    }

    public void deleteTalk(int tno) {
        talkMapper.deleteTalk(tno);
    }
}
