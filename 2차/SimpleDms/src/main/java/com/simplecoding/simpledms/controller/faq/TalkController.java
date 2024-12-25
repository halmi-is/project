package com.simplecoding.simpledms.controller.faq;

import com.simplecoding.simpledms.service.faq.TalkService;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import com.simplecoding.simpledms.vo.faq.Talk;
import lombok.RequiredArgsConstructor;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : TalkController
 * @since : 24. 12. 6.
 * description :
 */

@RestController
@RequiredArgsConstructor
public class TalkController {

    private final TalkService talkService;

    @GetMapping("/api/faq/talk")
    public ResponseEntity<?> selectTalkList(Criteria searchVO){
        List<?> talks = talkService.selectTalkList(searchVO);
        ResultDto resultDto = new ResultDto(talks, searchVO.getTotalItems());
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PostMapping("/api/faq/talk")
    public ResponseEntity<?> insertTalk(@RequestBody Talk talk) {
        talkService.insertTalk(talk);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/faq/talk/{tno}")
    public ResponseEntity<?> selectTalk(@PathVariable int tno){
        Optional<Talk> talk = talkService.selectTalk(tno);
        if (talk.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(talk.get(), HttpStatus.OK);
    }

    @PutMapping("/api/faq/talkanswer/{tno}")
    public ResponseEntity<?> updateTalk(@PathVariable int tno, @RequestBody Talk talk) {
        talkService.updateTalk(talk);
        return new ResponseEntity<>(talk, HttpStatus.OK);
    }

    @DeleteMapping("/api/faq/talkanswer/{tno}")
    public ResponseEntity<?> deleteTalk(@PathVariable int tno) {
        talkService.deleteTalk(tno);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
