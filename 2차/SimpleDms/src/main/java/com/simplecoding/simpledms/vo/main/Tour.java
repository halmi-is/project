package com.simplecoding.simpledms.vo.main;

import lombok.*;

/**
 * @author : KTE
 * @fileName : Tour
 * @since : 24. 11. 27.
 * description :
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tour {
//    TOUR_ID	NUMBER(38,0)
//    NAME	VARCHAR2(100 BYTE)
//    LOCATION	VARCHAR2(255 BYTE)
//    TOUR_DATA	BLOB
//    TOUR_FILE_URL	VARCHAR2(1000 BYTE)
//    VIEW	VARCHAR2(100 BYTE)
//    CATEGORY	VARCHAR2(100 BYTE)
//    COMMENT	VARCHAR2(100 BYTE)
//    POP	VARCHAR2(20 BYTE)
    private int tourId;  //기본키
    private String name;  //숙소명
    private String location;  //숙소위치
    private byte[] tourData;
    private String tourFileUrl;
    private String view;  //메인필터_숙소 카테고리
    private String category;  //메인필터_숙소 국내외 구분
    private String comment;  //메인페이지_숙소 제목
    private String pop;  //메인필터_인기급상승 테그

//  생성자
    public Tour(int tourId, String name, String location, byte[] tourData, String view,
                String category, String comment, String pop) {
        this.tourId = tourId;
        this.name = name;
        this.location = location;
        this.tourData = tourData;
        this.view = view;
        this.category = category;
        this.comment = comment;
        this.pop = pop;
    }

    public Tour(String name, String location, byte[] tourData, String view, String category,
                String comment, String pop) {
        this.name = name;
        this.location = location;
        this.tourData = tourData;
        this.view = view;
        this.category = category;
        this.comment = comment;
        this.pop = pop;
    }
}