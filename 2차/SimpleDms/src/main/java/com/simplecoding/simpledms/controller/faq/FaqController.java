package com.simplecoding.simpledms.controller.faq;

import com.simplecoding.simpledms.service.faq.FaqService;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import com.simplecoding.simpledms.vo.faq.Faq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : FaqController
 * @since : 24. 12. 2.
 * description :
 */
@RestController
@RequiredArgsConstructor
public class FaqController {

    private final FaqService faqService;

    @GetMapping("/api/faq/list")
    public ResponseEntity<?> selectFaqList(Criteria searchVO) {
        List<?> faqs = faqService.selectFaqList(searchVO);
        ResultDto resultDto = new ResultDto(faqs, searchVO.getTotalItems());
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PostMapping("/api/faq/list")
    public ResponseEntity<?> insertFaq(@RequestBody Faq faq) {
        faqService.insertFaq(faq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/faq/list/{fno}")
    public ResponseEntity<?> selectFaq(@PathVariable int fno) {
        Optional<Faq> faq = faqService.selectFaq(fno);
        if (faq.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(faq.get(), HttpStatus.OK);
    }

    @PutMapping("/api/faq/list/update/{fno}")
    public ResponseEntity<?> updateFaq(@PathVariable int fno, @RequestBody Faq faq) {
        faqService.updateFaq(faq);
        return new ResponseEntity<>(faq, HttpStatus.OK);
    }

    @DeleteMapping("/api/faq/list/delete/{fno}")

    public ResponseEntity<?> deleteFaq(@PathVariable int fno) {
        faqService.deleteFaq(fno);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

