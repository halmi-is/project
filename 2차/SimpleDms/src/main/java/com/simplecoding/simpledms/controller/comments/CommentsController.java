package com.simplecoding.simpledms.controller.comments;

import com.simplecoding.simpledms.service.comments.CommentsService;
import com.simplecoding.simpledms.vo.comments.Comments;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE-149
 * @fileName : CommentsController
 * @since : 24. 12. 3.
 * description :
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentsController {


    private final CommentsService commentsService;


    //    전체 조회

    @GetMapping("/api/comments")
    public ResponseEntity<?> selectCommentsList(Criteria searchVO) {
        log.info("테스트");
        List<?> commentss = commentsService.selectCommentsList(searchVO);
//      TODO: depts(배열) 1개 + 총건수 1개 -> 박스포장용 객체
//          객체생성(택배박스: DTO 객체) : 속성필드 (배열), 속성필드(총건수)
        ResultDto resultDto
                = new ResultDto(commentss, searchVO.getTotalItems());
//      TODO: 상태코드 : HttpStatus.OK, NO_CONTENT ...
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }


    //    부서 생성

    @PostMapping("/api/comments/add")
    public ResponseEntity<?> insert(
            @RequestBody Comments comments, Authentication authentication
    ) {
        String email = authentication.getName();
        comments.setEmail(email);

        commentsService.insert(comments);

        return new ResponseEntity<>(HttpStatus.OK);
    }







    //상세

    @GetMapping("/api/comments/get/{comId}")
    public ResponseEntity<?> selectComments(
            @PathVariable int comId
    ) {
        Optional<Comments> comments = commentsService.selectComments(comId);

        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(comments.get(), HttpStatus.OK);
    }


    //수정
    @PutMapping("/api/comments/update/{comId}")
    public ResponseEntity<?> update(
            @PathVariable int comId,
            @RequestBody Comments comments
    ) {
        commentsService.update(comments);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //    삭제
    @DeleteMapping("/api/comments/deletion/{comId}")
    public ResponseEntity<?> delete(@PathVariable int comId) {
        commentsService.delete(comId);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
