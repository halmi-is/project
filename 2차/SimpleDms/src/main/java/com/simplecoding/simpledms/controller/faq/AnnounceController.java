package com.simplecoding.simpledms.controller.faq;

import com.simplecoding.simpledms.service.faq.AnnounceService;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import com.simplecoding.simpledms.vo.faq.Announce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : AnnounceController
 * @since : 24. 12. 3.
 * description :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/announcement")
public class AnnounceController {

    private final AnnounceService announceService;

    @GetMapping
    public ResponseEntity<?> selectAnouncement(Criteria searchVO) {
        List<?> announces = announceService.selectAnoList(searchVO);
        ResultDto resultDto = new ResultDto(announces, searchVO.getTotalItems());
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> insertAnouncement(@RequestBody Announce announce) {
        announceService.insertAno(announce);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/{ano}")
    public ResponseEntity<?> selectAno(@PathVariable int ano) {
        Optional<Announce> announce = announceService.selectAnoById(ano);
        return announce.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(announce.get(), HttpStatus.OK);
    }

    @PutMapping("/fix/{ano}")
    public ResponseEntity<?> updateAnouncement(@PathVariable int ano, @RequestBody Announce announce) {
        announceService.updateAno(announce);
        return new ResponseEntity<>(announce, HttpStatus.OK);
    }

    @DeleteMapping("/fix/{ano}")
    public ResponseEntity<?> deleteAno(@PathVariable int ano) {
        announceService.deleteAno(ano);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}