package com.simplecoding.simpledms.vo.faq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;


/**
 * @author : KTE
 * @fileName : Talk
 * @since : 24. 12. 6.
 * description :
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Talk {
    //    TNO	NUMBER(38,0)
    //    TITLE	VARCHAR2(255 BYTE)
    //    CONTENT	VARCHAR2(255 BYTE)
    //    CATEGORY	VARCHAR2(255 BYTE)
    //    IMAGE	BLOB
    //    CREATE_DATE	DATE
    //    REPLY	VARCHAR2(255 BYTE)
    private int tno;                // PK
    private String title;           // 제목
    private String content;         // 내용
    private String category;        // 카테고리
    private String reply;           // 댓글
    private byte[] image;           // 이미지
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createDate;       // 작성 날짜
}
