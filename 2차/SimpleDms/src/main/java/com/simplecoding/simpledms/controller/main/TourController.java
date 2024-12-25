package com.simplecoding.simpledms.controller.main;

import com.simplecoding.simpledms.service.main.TourService;
import com.simplecoding.simpledms.vo.common.Criteria;
import com.simplecoding.simpledms.vo.dto.ResultDto;
import com.simplecoding.simpledms.vo.main.Tour;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * @author : KTE
 * @fileName : TourController
 * @since : 24. 11. 27.
 * description :
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TourController {
    private final TourService tourService;

//  전체조회
    @GetMapping("/api/tour")
    public ResponseEntity<?> selectTour(Criteria searchVO) {
        List<?> tours = tourService.selectTour(searchVO);
//      총건수
        ResultDto resultDto = new ResultDto(tours, searchVO.getTotalItems());
        return new ResponseEntity<>(resultDto, HttpStatus.OK);
    }

//  추가, 업로드
    @PostMapping("/api/tour/add")
    public ResponseEntity<?> insert(@RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "") String location,
                                    @RequestParam(required = false) MultipartFile image,
                                    @RequestParam(defaultValue = "") String view,
                                    @RequestParam(defaultValue = "") String category,
                                    @RequestParam(defaultValue = "") String comment,
                                    @RequestParam(defaultValue = "") String pop) throws Exception {
        byte[] imageData = null;
        // 이미지가 있으면 byte 배열로 변환
        if (image != null && !image.isEmpty()) {
            imageData = image.getBytes();
        }

        // Tour 객체 생성
        Tour tour = new Tour(name, location, imageData, view, category, comment, pop);
        tourService.insert(tour);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//  이미지 다운로드 함수
    @GetMapping("/api/tour/{tourId}")
    public ResponseEntity<byte[]> findDownload(@PathVariable int tourId) throws Exception {
//      상세조회 : 객체받기(첨부파일)
        Tour tour = tourService.select(tourId).orElseThrow(() -> new FileNotFoundException("데이터 없음"));
//      첨부파일 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", String.valueOf(tour.getTourId()));  //int형 변환
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//      첨부파일 headers에 담아서 전송
        return new ResponseEntity<byte[]>(tour.getTourData(), headers, HttpStatus.OK);
    }

//  상세조회
    @GetMapping("/api/tour/get/{tourId}")
    public ResponseEntity<?> select(@PathVariable int tourId) {
        Optional<Tour> tour = tourService.select(tourId);
//      에러처리
        if (tour.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tour.get(), HttpStatus.OK);
    }

//  수정
    @PutMapping("/api/tour/update/{tourId}")
    public ResponseEntity<?> update(@PathVariable int tourId,
                                    @RequestParam(defaultValue = "") String name,
                                    @RequestParam(defaultValue = "") String location,
                                    @RequestParam(required = false) MultipartFile image,
                                    @RequestParam(defaultValue = "") String view,
                                    @RequestParam(defaultValue = "") String category,
                                    @RequestParam(defaultValue = "") String comment,
                                    @RequestParam(defaultValue = "") String pop) throws Exception {
        byte[] imageData = null;
        // 이미지가 있으면 byte 배열로 변환
        if (image != null && !image.isEmpty()) {
            imageData = image.getBytes();
        }

        // Tour 객체 생성 (이미지가 없으면 null 값 처리)
        Tour tour = new Tour(tourId, name, location, imageData, view, category, comment, pop);
        tourService.update(tour);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //  삭제
    @DeleteMapping("/api/tour/deletion/{tourId}")
    public ResponseEntity<?> delete(@PathVariable int tourId) {
        tourService.delete(tourId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//  리뷰게시판
    @GetMapping("api/tour/tourname/{tourId}")
    public ResponseEntity<?> tourname(@PathVariable int tourId) {
        String name= tourService.ListName(tourId);
        return new ResponseEntity<>(name, HttpStatus.OK);
    }

    // 최저값 list 받아오기
    @GetMapping("/api/tour/minprice/{tourId}")
    public ResponseEntity<List<String>> getRoomMinPrice(@PathVariable int tourId) {
        List<String> prices = tourService.getRoomMinPrice(tourId);

        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

}