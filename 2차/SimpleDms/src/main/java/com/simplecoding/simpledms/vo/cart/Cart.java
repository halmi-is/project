package com.simplecoding.simpledms.vo.cart;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Cart {

    private int cartId; // 자동생성 기본키
    private String userEmail;
    private int stayDuration;
    private String tourFileUrl;
    private String tourName;
    private String roomName;
    private int capacity;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String checkInTime;
    private String checkOutTime;
    private String totalPrice;

    // 생성자
    public Cart(String userEmail, int stayDuration, String tourFileUrl, String tourName, String roomName, int capacity,
                LocalDate checkInDate, LocalDate checkOutDate, String checkInTime, String checkOutTime, String totalPrice) {
        this.userEmail = userEmail;
        this.stayDuration = stayDuration;
        this.tourFileUrl = tourFileUrl;
        this.tourName = tourName;
        this.roomName = roomName;
        this.capacity = capacity;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.totalPrice = totalPrice;
    }

}
