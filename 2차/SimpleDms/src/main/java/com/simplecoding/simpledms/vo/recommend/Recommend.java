package com.simplecoding.simpledms.vo.recommend;

import lombok.*;

/**
 * @author : KTE-149
 * @fileName : Recommend
 * @since : 24. 11. 29.
 * description :
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recommend {


    private int tdId;
    private String tdName;
    private String loc;
    private String description;
    private byte[] imageData;
    private String imageUrl;


    public Recommend(int tdId, String tdName, String loc, String description, byte[] imageData) {
        this.tdId = tdId;
        this.tdName = tdName;
        this.loc = loc;
        this.description = description;
        this.imageData = imageData;
    }


    public Recommend(String tdName, String loc, String description, byte[] imageData) {
        this.tdName = tdName;
        this.loc = loc;
        this.description = description;
        this.imageData = imageData;
    }
}

