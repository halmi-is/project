package com.simplecoding.simpledms.vo.room;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Room {

    private int roomId;          // 방 번호(기본키)
    private int tourId;          // 숙소 번호(외래키)

    private String roomName;     // 방 이름
    private String price;        // 가격 ( ex) 30,000 )
    private String description;  // 설명
    private int capacity;        // 최대 인원

    private byte[] roomData;     // 이미지
    private String roomUrl;      // 다운로드 url

    private String checkIn;      // 체크인
    private String checkOut;     // 체크아웃

    private int roomImageId;       // 방 이미지 번호(기본키)
    private byte[] roomImageData;  // 이미지
    private String roomImageUrl;   // 다운로드 url

    // 생성자
    public Room(int tourId, String roomName, String price, String description, int capacity, String checkIn, String checkOut, byte[] roomData) {
        this.tourId = tourId;
        this.roomName = roomName;
        this.price = price;
        this.description = description;
        this.capacity = capacity;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomData = roomData;
    }

    // 생성자: tourId 빼고 , roomId 추가
    public Room(String roomName, String price, String description, int capacity, String checkIn, String checkOut, byte[] roomData, int roomId) {
        this.roomName = roomName;
        this.price = price;
        this.description = description;
        this.capacity = capacity;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomData = roomData;
        this.roomId = roomId;
    }

    // 방 이미지 추가 생성자
    public Room(byte[] roomImageData, int roomId) {
        this.roomImageData = roomImageData;
        this.roomId = roomId;
    }

}
