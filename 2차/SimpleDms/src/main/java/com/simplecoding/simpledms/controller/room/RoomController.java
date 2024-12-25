package com.simplecoding.simpledms.controller.room;

import com.simplecoding.simpledms.service.room.RoomService;
import com.simplecoding.simpledms.vo.room.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    // 특정 숙소의 모든 방 조회: tourId
    @GetMapping("/api/room/tour/{tourId}")
    public ResponseEntity<List<Room>> getRoomsByTourId(@PathVariable int tourId) {
        List<Room> rooms = roomService.selectRoomsByTourId(tourId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    // 방 추가
    @PostMapping("/api/room/add")
    public ResponseEntity<?> insert(@RequestParam int tourId, // 외래키
                                    @RequestParam(defaultValue = "") String roomName,
                                    @RequestParam(defaultValue = "") String price,
                                    @RequestParam(defaultValue = "") String description,
                                    @RequestParam(defaultValue = "") int capacity,
                                    @RequestParam(defaultValue = "") String checkIn,
                                    @RequestParam(defaultValue = "") String checkOut,
                                    @RequestParam(required = false) MultipartFile image) throws Exception {

        Room room = new Room(tourId, roomName, price, description, capacity, checkIn, checkOut, image.getBytes());

        roomService.addRoom(room);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: 이미지 다운로드 함수
    @GetMapping("/api/room/{roomId}")
    public ResponseEntity<byte[]> fileDownload(@PathVariable int roomId) throws Exception {

        // 상세조회 서비스
        Room room = roomService.selectRoom(roomId)
                .orElseThrow(() -> new FileNotFoundException("데이터가 없습니다."));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", String.valueOf(room.getRoomId()));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return new ResponseEntity<byte[]>(room.getRoomData(), headers, HttpStatus.OK);
    }

    // 방 상세 조회
    @GetMapping("/api/room/detail/{roomId}")
    public ResponseEntity<Room> getRoom(@PathVariable int roomId) {
        Optional<Room> room = roomService.selectRoom(roomId);
        if (room.isPresent()) {
            return new ResponseEntity<>(room.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 방 정보 수정
    @PutMapping("/api/room/update/{roomId}")
    public ResponseEntity<String> updateRoom(
            @RequestParam(defaultValue = "") String roomName,
            @RequestParam(defaultValue = "") String price,
            @RequestParam(defaultValue = "") String description,
            @RequestParam(defaultValue = "") int capacity,
            @RequestParam(defaultValue = "") String checkIn,
            @RequestParam(defaultValue = "") String checkOut,
            @RequestParam(required = false) MultipartFile image,
            @PathVariable int roomId) throws Exception {

        Room room = new Room(roomName, price, description, capacity, checkIn, checkOut, image.getBytes(), roomId);

        roomService.updateRoom(room);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 방 삭제
    @DeleteMapping("/api/room/delete/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable int roomId) {
        int deleted = roomService.deleteRoom(roomId);

        if (deleted > 0) {
            return new ResponseEntity<>("방이 삭제되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("방 삭제에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 방 이미지 추가
    @PostMapping("/api/room/image/add")
    public ResponseEntity<?> insert(@RequestParam int roomId,
                                    @RequestParam(required = false) MultipartFile image) throws Exception {

        Room room = new Room(image.getBytes(), roomId);

        roomService.addRoomImage(room);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // TODO: 방 이미지 다운로드 함수
    @GetMapping("/api/room/image/{roomImageId}")
    public ResponseEntity<byte[]> roomImageDownload(@PathVariable int roomImageId) throws Exception {
        Room roomImage = roomService.selectRoomImage(roomImageId)
                .orElseThrow(() -> new FileNotFoundException("데이터가 없음."));

        byte[] imageData = roomImage.getRoomImageData();
        if (imageData == null || imageData.length == 0) {
            throw new FileNotFoundException("이미지 데이터가 없습니다.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", String.valueOf(roomImage.getRoomImageId()));
        headers.setContentType(MediaType.IMAGE_JPEG);  // 이미지 MIME 타입에 맞게 설정

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    // 방 이미지 상세 조회
    @GetMapping("/api/room/image/detail/{roomImageId}")
    public ResponseEntity<Room> getRoomImage(@PathVariable int roomImageId) {
        Optional<Room> room = roomService.selectRoomImage(roomImageId);
        if (room.isPresent()) {
            return new ResponseEntity<>(room.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 특정 방의 전체 url을 조회: roomId
    @GetMapping("/api/room/image/url/{roomId}")
    public ResponseEntity<List<String>> getImageByRoomId(@PathVariable int roomId) {
        List<String> rooms = roomService.getImageByRoomId(roomId);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

}
