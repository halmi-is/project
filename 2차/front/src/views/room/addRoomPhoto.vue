<template>
  <div>
    <!-- room ID (Readonly) -->
    <div class="form-floating mb-3">
      <input
        type="number"
        class="form-control"
        id="roomId"
        placeholder="Room ID"
        v-model="room.roomId"
        readonly
      />
      <label for="roomId">Room ID</label>
    </div>

    <!-- 현재 이미지 보기 -->
    <div class="mb-3">
      <img
        :src="room.roomImageUrl"
        alt="이미지"
        class="img-fluid rounded"
        style="max-height: 400px; object-fit: cover"
      />
    </div>

    <!-- 이미지 업로드 -->
    <div class="input-group">
      <input type="file" class="form-control" ref="file" @change="select" />
      <button class="btn btn-primary" type="button" @click="saveRoomImage">
        저장
      </button>

      <button class="btn btn-warning me-3" type="button" @click="returnBefore">
        뒤로가기
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
        roomId: this.$route.params.roomId,
        image: undefined,
        roomImageUrl: "",
      },
    };
  },
  methods: {
    select() {
      const file = this.$refs.file.files[0];

      if (file) {
        this.room.image = file;
        this.room.roomImageUrl = URL.createObjectURL(file);
      }
    },

    async getImageByRoomId(roomId) {
      try {
        const response = await RoomService.getImageByRoomId(roomId);
        this.room = response.data;
      } catch (error) {
        console.error("Error fetching rooms:", error);
      }
    },

    async saveRoomImage() {
      try {
        const response = await RoomService.addRoomPhoto(this.room);
        console.log(response.data);
        alert("이미지가 추가되었습니다!");
      } catch (error) {
        console.error("Failed to add room:", error);
      }
    },

    returnBefore() {
      this.$router.go(-1);
    },
  },
  mounted() {
    const roomId = this.$route.params.roomId;
    if (roomId) {
      this.getImageByRoomId(roomId);
    } else {
      console.error("tourId is missing in the route.");
    }
  },
};
</script>

<style scoped>
/* 필요한 스타일 추가 */
</style>
