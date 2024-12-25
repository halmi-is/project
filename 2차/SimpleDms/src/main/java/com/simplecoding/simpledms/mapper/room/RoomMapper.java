package com.simplecoding.simpledms.mapper.room;

import com.simplecoding.simpledms.vo.room.Room;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RoomMapper {

    // 특정 숙소의 모든 방 조회 (TOUR_ID로)
    List<Room> selectRoomsByTourId(int tourId);

    // 방 추가
    int addRoom(Room room);

    // 방 URL 업데이트 추가 (ROOM_ID와 ROOM_URL로)
    int updateRoomUrl(Room room);

    // roomId를 생성 후 가져오는 메서드
    int selectGeneratedRoomId();

    // 방 상세 조회 (ROOM_ID로)
    Optional<Room> selectRoom(int roomId);

    // 방 정보 수정
    int updateRoom(Room room);

    // 방 삭제
    int deleteRoom(int roomId);

    // ------------------------------------------------------------------------------------------

    // 방 이미지 추가
    int addRoomImage(Room room);
    
    // 방 이미지 url 업데이트
    int updateRoomImageUrl(Room room);

    // roomId를 생성 후 가져오는 메서드
    int selectGeneratedRoomImageId();

    // 이미지 상세 조회
    Optional<Room> selectRoomImage(int roomImageId);

    // 특정 방의 모든 url 조회
    List<String> getImageByRoomId(int roomId);

}
