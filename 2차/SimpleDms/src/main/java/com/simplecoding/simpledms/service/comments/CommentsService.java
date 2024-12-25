package com.simplecoding.simpledms.service.comments;

import com.simplecoding.simpledms.mapper.comments.CommentsMapper;
import com.simplecoding.simpledms.vo.comments.Comments;
import com.simplecoding.simpledms.vo.common.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE-149
 * @fileName : CommentsService
 * @since : 24. 12. 3.
 * description :
 */
@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsMapper commentsMapper;


    //    전체조회 + 총건수 추가
    public List<?> selectCommentsList(Criteria searchVO) {
        List<?> page = commentsMapper.selectCommentsList(searchVO);
        //        TODO: 총건수 저장 : Criteria 의 totalItems
        int count = commentsMapper.selectCommentsListTotCnt(searchVO);
        searchVO.setTotalItems(count);
        return page;
    }

    public void insert(Comments comments) {
        commentsMapper.insert(comments);
    }

    //    TODO: 부서 상세조회
    public Optional<Comments> selectComments(int comId) {
        return commentsMapper.selectComments(comId);
    }

    //    TODO: 부서 수정
//
    public void update(Comments comments) {
        commentsMapper.update(comments);
    }



    //    TODO: 부서 삭제 (기본키:dno)
    public void delete(int comId) {
        commentsMapper.delete(comId);
    }


    //실험용(안되면 지워도됨)
//    public void CommentsEmail(String eamil, String commentText) {
//
//
//        Comments comments = new Comments();
//        comments.setEmail(eamil);
//        comments.setCommentText(commentText);
//        commentsMapper.insert(comments);
//



    }












