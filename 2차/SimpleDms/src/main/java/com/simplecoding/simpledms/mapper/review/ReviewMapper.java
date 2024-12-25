package com.simplecoding.simpledms.mapper.review;

import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.review.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : ReviewMapper
 * @since : 24. 11. 27.
 * description :
 */
@Mapper
public interface ReviewMapper {
    public List<?> selectReviewList(Criteria searchVO);
    public int selectReviewListTotCnt(Criteria searchVO);
    public int insert(Review review);
    public int updateReviewUrl(Review review);
    public int selectGenerateReviewId();
    public Optional<Review> select(int reviewId);
    public int update(Review review);
    public int delete(int reviewId);
    public List<Integer> getTourId();
    List<Review> getReviewsByEmail(String authorEmail);


}
