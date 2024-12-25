package com.simplecoding.simpledms.vo.comments;

import lombok.*;

import java.sql.Timestamp;


/**
 * @author : KTE-149
 * @fileName : Comment
 * @since : 24. 12. 3.
 * description :
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comments {

    private int comId;            // COM_ID (NUMBER)
    private String commentText;       // COMMENT (VARCHAR2)
    private String email;         // EMAIL (VARCHAR2)
    private Timestamp createdAt;  // CREATED_AT (TIMESTAMP)
    private String commentLoc;
    private Integer rating;            // RATING (NUMBER) 추가

//    public void setMemberEmail(String email) {
//    }
}
