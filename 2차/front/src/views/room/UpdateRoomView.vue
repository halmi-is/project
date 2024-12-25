<template>
  <div class="container mt-5">
    <h1 class="mb-4">방 상세 정보 수정</h1>

    <!-- 방 이름 수정 -->
    <div class="mb-3">
      <label for="roomName" class="form-label">방 이름</label>
      <input
        type="text"
        id="roomName"
        v-model="room.roomName"
        class="form-control"
      />
    </div>

    <!-- 최대 인원 수정 -->
    <div class="mb-3">
      <label for="capacity" class="form-label">최대 인원</label>
      <input
        type="number"
        id="capacity"
        v-model="room.capacity"
        class="form-control"
      />
    </div>

    <!-- 가격 수정 -->
    <div class="mb-3">
      <label for="price" class="form-label">가격</label>
      <input
        type="text"
        id="price"
        v-model="room.price"
        class="form-control"
      />
    </div>

    <!-- 설명 수정 -->
    <div class="mb-3">
      <label for="description" class="form-label">설명</label>
      <textarea
        id="description"
        v-model="room.description"
        class="form-control"
        rows="4"
      ></textarea>
    </div>

    <!-- 체크인 시간 수정 -->
    <div class="mb-3">
      <label for="checkIn" class="form-label">체크인 시간</label>
      <textarea
        id="checkIn"
        v-model="room.checkIn"
        class="form-control"
        rows="2"
      ></textarea>
    </div>

    <!-- 체크아웃 시간 수정 -->
    <div class="mb-3">
      <label for="checkOut" class="form-label">체크아웃 시간</label>
      <textarea
        id="checkOut"
        v-model="room.checkOut"
        class="form-control"
        rows="2"
      ></textarea>
    </div>

    <!-- 현재 이미지 보기 -->
    <div class="mb-3">
      <img
        :src="room.roomUrl"
        alt="이미지"
        class="img-fluid rounded"
        style="max-height: 400px; object-fit: cover"
      />
    </div>

    <!-- 이미지 업로드 -->
    <div class="mb-3 input-group">
      <input
        type="file"
        placeholder="이미지를 넣으세요."
        class="form-control"
        ref="file"
        @change="select"
      />
      <button class="btn btn-warning" type="button" @click="updateRoom">
        수정
      </button>
    </div>
  </div>
</template>

<script>
import RoomService from "@/services/room/RoomService"; // 방 정보 서비스

export default {
  data() {
    return {
      room: {
        roomId: "",

        roomName: "",
        capacity: "",
        price: "",
        description: "",
        checkIn: "",
        checkOut: "",

        image: undefined,
      },
    };
  },
  methods: {
    select() {
      const file = this.$refs.file.files[0];

      if (file) {
        this.room.image = file;

        const previewUrl = URL.createObjectURL(file);

        this.room.roomUrl = previewUrl;
      }
    },

    // 방 상세 정보 가져오기
    async getRoomDetails(roomId) {
      try {
        const response = await RoomService.DetailRoom(roomId); // 방 ID로 방 상세 정보 가져오기
        console.log(response); // 응답 로그로 확인
        if (response && response.data) {
          this.room = response.data; // 방 정보 저장
        }
      } catch (error) {
        console.error("Error fetching room details:", error);
        if (error.response) {
          console.error("Response error:", error.response.data);
        }
      }
    },

    // 방 정보 수정
    async updateRoom() {
      try {
        const response = await RoomService.UpdateRoom(
          this.room.roomId,
          this.room
        );
        this.$router.push(`/maindetail/${this.room.tourId}`);
        console.log(response.data);
      } catch (error) {
        console.error("Failed to add room:", error);
      }
    },
  },

  mounted() {
    const roomId = this.$route.params.roomId; // URL에서 roomId 가져오기
    this.getRoomDetails(roomId); // 방 상세 정보 가져오기
  },
};
</script>

<style scoped>
/* CSS 스타일 */
h1 {
  margin-bottom: 20px;
}

button {
  margin-top: 10px;
}
</style>
