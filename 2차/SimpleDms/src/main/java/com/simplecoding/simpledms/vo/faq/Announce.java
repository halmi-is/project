package com.simplecoding.simpledms.vo.faq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * @author : KTE
 * @fileName : Announce
 * @since : 24. 12. 3.
 * description :
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Announce {
    private int ano;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date createDate;
}
