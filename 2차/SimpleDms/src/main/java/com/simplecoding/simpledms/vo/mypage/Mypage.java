package com.simplecoding.simpledms.vo.mypage;

import lombok.*;

/**
 * @author : KTE-149
 * @fileName : Mypage
 * @since : 24. 12. 6.
 * description :
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Mypage {

//    MYPAGE_ID	NUMBER
//    EMAIL	VARCHAR2(100 BYTE)
//    ROOM_ID	NUMBER
//    REVIEW_ID	NUMBER

    private int mypageId;
    private String email;
    private int roomId;
    private int reviewId;

}
