package com.simplecoding.simpledms.vo.faq;

import lombok.*;

/**
 * @author : KTE
 * @fileName : faq
 * @since : 24. 12. 2.
 * description :
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Faq {
    //    FNO	NUMBER
//    QUESTION	VARCHAR2(255 BYTE)
//    ANSWER	CLOB
    private int fno;
    private String question;
    private String answer;
    private String hashtag;

}
