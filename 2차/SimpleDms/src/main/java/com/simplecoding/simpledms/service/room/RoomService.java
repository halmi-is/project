package com.simplecoding.simpledms.service.room;

import com.simplecoding.simpledms.mapper.room.RoomMapper;
import com.simplecoding.simpledms.vo.room.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomMapper roomMapper;

    @Autowired
    public RoomService(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }
    // 특정 숙소의 모든 방 조회: tourId로 조회(외래키)
    public List<Room> selectRoomsByTourId(int tourId) {
        return roomMapper.selectRoomsByTourId(tourId);
    }

    // 방 추가 메서드
    public void addRoom(Room room) {
        // 방 추가 (roomId는 자동 생성되지만, roomId는 미리 알 수 없음)
        roomMapper.addRoom(room);

        // 생성된 roomId를 DB에서 가져옴
        int roomId = roomMapper.selectGeneratedRoomId(); // CURRVAL을 가져옴
        room.setRoomId(roomId);  // 생성된 roomId를 room 객체에 설정

        // URL 생성
        String url = generateRoomUrl(room.getRoomId());
        room.setRoomUrl(url);

        // URL 업데이트
        roomMapper.updateRoomUrl(room);
    }

    // URL 생성 메서드
    public String generateRoomUrl(int roomId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/room/")
                .path(String.valueOf(roomId))
                .toUriString();
    }

    // 방 상세 조회
    public Optional<Room> selectRoom(int roomId) {
        return roomMapper.selectRoom(roomId);
    }

    // 방 정보 수정
    public int updateRoom(Room room) {

        int roomId = room.getRoomId();

        String url = generateRoomUrl(roomId);

        room.setRoomId(roomId);
        room.setRoomUrl(url);

        return roomMapper.updateRoom(room);
    }

    // 방 삭제
    public int deleteRoom(int roomId) {
        return roomMapper.deleteRoom(roomId);
    }

    // ------------------------------------------------------------------------------------------

    // 방 이미지 추가
    public void addRoomImage(Room room) {
        // 방 이미지 추가
        roomMapper.addRoomImage(room);

        // 생성된 roomIamgeId를 DB에서 가져옴
        int roomImageId = roomMapper.selectGeneratedRoomImageId(); // CURRVAL을 가져옴
        room.setRoomImageId(roomImageId);

        // 이미지 URL 생성
        String imageUrl = generateRoomImageUrl(room.getRoomImageId());
        room.setRoomImageUrl(imageUrl);

        // 이미지 URL 업데이트
        roomMapper.updateRoomImageUrl(room);
    }

    // 이미지 URL 생성 메서드
    public String generateRoomImageUrl(int roomImageId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/room/image/")
                .path(String.valueOf(roomImageId))
                .toUriString();
    }

    // 이미지 상세 조회
    public Optional<Room> selectRoomImage(int roomImageId) {
        return roomMapper.selectRoomImage(roomImageId);
    }

    // 특정 방의 모든 경로(url) 조회: roomId로 조회(외래키)
    public List<String> getImageByRoomId(int roomId) {
        return roomMapper.getImageByRoomId(roomId);
    }

}