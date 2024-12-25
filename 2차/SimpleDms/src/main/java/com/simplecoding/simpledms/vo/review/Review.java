package com.simplecoding.simpledms.vo.review;

import lombok.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Review {
    private int reviewId;        // 기본키
    private String title;        // 후기 제목
    private String content;      // 후기 내용
    private double rating;       // 평점
    private String imageUrl;     // 첨부 이미지 URL
    private String authorEmail;  // 작성자 이메일
    private int targetId;     // 대상 ID
//    private String targetName; // 숙소 이름
    private Timestamp createdAt; // 생성일
    private Timestamp updatedAt; // 수정일
    private byte[] imageData;


//    Review(title, content, rating, authorEmail, targetId, imageData);

    public Review(String title, String content, double rating, String authorEmail, int targetId, byte[] imageData) {
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.authorEmail = authorEmail;
        this.targetId = targetId;
        this.imageData = imageData;
    }
//    (title, content, rating, authorEmail, targetId, image.getBytes())

}
