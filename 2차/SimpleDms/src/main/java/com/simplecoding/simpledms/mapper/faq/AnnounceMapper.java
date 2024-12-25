package com.simplecoding.simpledms.mapper.faq;

import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.faq.Announce;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : AnnounceMapper
 * @since : 24. 12. 3.
 * description :
 */
@Mapper
public interface AnnounceMapper {
    List<?> selectAno(Criteria searchVO);
    int selectAnoCount(Criteria searchVO);
    int insertAnnounce(Announce announce);
    Optional<Announce> selectAnnounceById(int ano);
    int updateAnnounce(Announce announce);
    int deleteAnnounceById(int ano);
}
