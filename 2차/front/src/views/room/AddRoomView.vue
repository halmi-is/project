<template>
  <div>
    <!-- Tour ID (Readonly) -->
    <div class="form-floating mb-3">
      <input
        type="number"
        class="form-control"
        id="tourId"
        placeholder="Tour ID"
        v-model="room.tourId"
        readonly
      />
      <label for="tourId">Tour ID</label>
    </div>

    <!-- Room Name -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="roomName"
        placeholder="Room Name"
        v-model="room.roomName"
      />
      <label for="roomName">Room Name</label>
    </div>

    <!-- Room Location -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="roomLocation"
        placeholder="Room Location"
        v-model="room.location"
      />
      <label for="roomLocation">Room Location</label>
    </div>

    <!-- Room Description -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="roomDescription"
        placeholder="Room Description"
        v-model="room.description"
      />
      <label for="roomDescription">Room Description</label>
    </div>

    <!-- Room Price -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="roomPrice"
        placeholder="Room Price"
        v-model="room.price"
      />
      <label for="roomPrice">Room Price</label>
    </div>

    <!-- Room Capacity -->
    <div class="form-floating mb-3">
      <input
        type="number"
        class="form-control"
        id="roomCapacity"
        placeholder="Room Capacity"
        v-model="room.capacity"
      />
      <label for="roomCapacity">Room Capacity</label>
    </div>

    <!-- Room CheckIn -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="roomCheckIn"
        placeholder="Room CheckIn"
        v-model="room.checkIn"
      />
      <label for="roomCapacity">Room CheckIn</label>
    </div>

    <!-- Room CheckOut -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="roomCheckIn"
        placeholder="Room CheckOut"
        v-model="room.checkOut"
      />
      <label for="roomCapacity">Room CheckOut</label>
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
    <div class="input-group">
      <input type="file" class="form-control" ref="file" @change="select" />
      <button class="btn btn-primary" type="button" @click="saveRoom">
        저장
      </button>
    </div>
  </div>
</template>

<script>
import RoomService from "@/services/room/RoomService";

export default {
  data() {
    return {
      room: {
        roomName: "",
        location: "",
        description: "",
        price: "", // 숫자로 입력
        capacity: "",
        checkIn: "",
        checkOut: "",
        image: undefined,

        tourId: this.$route.params.tourId, // URL에서 전달된 tourId 값을 사용
      },
    };
  },
  methods: {
    select() {
    const file = this.$refs.file.files[0];
    
    if (file) {
      this.room.image = file;
      this.room.roomUrl = URL.createObjectURL(file); // 이미지 URL을 생성하여 roomUrl에 할당
    }
  },

    async saveRoom() {
      try {
        // tourId만 전송하여 방 추가
        const response = await RoomService.addRoom(this.room); // 방 추가 API 호출
        console.log(response.data);
        // 방 추가 후 페이지 이동
        this.$router.push(`/maindetail/${this.room.tourId}`);
      } catch (error) {
        console.error("Failed to add room:", error);
      }
    },
  },
};
</script>

<style scoped>
/* 필요한 스타일 추가 */
</style>
