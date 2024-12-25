package com.simplecoding.simpledms.service.faq;

import com.simplecoding.simpledms.mapper.faq.AnnounceMapper;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.faq.Announce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : AnnounceService
 * @since : 24. 12. 3.
 * description :
 */
@Service
@RequiredArgsConstructor
public class AnnounceService {

    private final AnnounceMapper announceMapper;

    public List<?> selectAnoList(Criteria searchVO) {
        List<?> page = announceMapper.selectAno(searchVO);
        int count = announceMapper.selectAnoCount(searchVO);
        searchVO.setTotalItems(count);
        return page;
    }

    public void insertAno(Announce announce) {
        announceMapper.insertAnnounce(announce);
    }

    public Optional<Announce> selectAnoById(int ano) {
        return announceMapper.selectAnnounceById(ano);
    }

    public void updateAno(Announce announce) {
        announceMapper.updateAnnounce(announce);
    }

    public void deleteAno(int ano) {
        announceMapper.deleteAnnounceById(ano);
    }
}
