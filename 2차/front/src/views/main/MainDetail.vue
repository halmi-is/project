<template>
  <div>
    <!-- 방 추가 버튼, tourId를 URL에 포함하여 전달 -->
    <b-nav-item
      v-if="userRole === 'ROLE_ADMIN'"
      :href="'/addRoom/' + tourId"
      class="btn btn-primary px-4 py-2 rounded-pill shadow-sm text-white mb-3"
      style="background-color: black"
    >
      방 추가
    </b-nav-item>

    <!-- 최상단 캐러셀 -->
    <div id="topCarousel" class="carousel slide" data-bs-ride="false">
      <div class="carousel-inner">
        <!-- 모든 방 이미지들을 최상단 캐러셀로 반복 -->
        <div
          v-for="(imageUrl, idx) in AllImages"
          :key="idx"
          :class="['carousel-item', { active: idx === 0 }]"
        >
          <img
            :src="imageUrl"
            class="d-block w-100"
            alt="방 이미지"
            style="height: 400px; object-fit: cover"
          />
        </div>
      </div>
      <!-- 이전 버튼 -->
      <button
        class="carousel-control-prev"
        type="button"
        data-bs-target="#topCarousel"
        data-bs-slide="prev"
      >
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
      </button>
      <!-- 다음 버튼 -->
      <button
        class="carousel-control-next"
        type="button"
        data-bs-target="#topCarousel"
        data-bs-slide="next"
      >
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
      </button>
    </div>

    <!-- 숙소 정보란 -->
    <div class="tour-container">
      <div class="tour-name">{{ tour.name }}</div>
      <div class="tour-location">{{ tour.location }}</div>
    </div>

    <!-- 예약 달력 -->
    <div class="calendar-container">
      <div class="calendar-item">
        <p class="calendar-label">체크인</p>
        <input
          type="date"
          v-model="reservation.checkInDate"
          :min="today"
          :max="oneMonthLater"
          class="calendar-input"
          @change="handleCheckInChangeAndSync"
          @keydown="handleKeyDown"
        />
      </div>
      <div class="calendar-item">
        <p class="calendar-label">체크아웃</p>
        <input
          type="date"
          v-model="reservation.checkOutDate"
          :min="checkOutMinDate"
          :max="oneMonthLater"
          :disabled="!reservation.checkInDate"
          class="calendar-input"
          @change="syncCheckOutDate"
          @keydown="handleKeyDown"
        />
      </div>
      <div class="stay-duration-container">
        <!-- 숙박 일수 표시 -->
        <div v-show="stayDuration >= 0" class="stay-duration">
          <p>•{{ stayDuration }}박</p>
        </div>
      </div>
    </div>

    <!-- 각 방의 정보와 캐러셀 -->
    <div v-if="rooms.length > 0" class="d-flex flex-column align-items-center">
      <div
        v-for="room in rooms"
        :key="room.roomId"
        class="room-container d-flex align-items-center"
      >
        <!-- 좌측 박스: 방 캐러셀 이미지 -->
        <div class="image-box">
          <div
            :id="'carouselExampleControls-' + room.roomId"
            class="carousel slide"
          >
            <div class="carousel-inner">
              <!-- 첫 번째 이미지 활성화 -->
              <div
                v-for="(imageUrl, idx) in [
                  room.roomUrl,
                  ...(EachRoomUrls[room.roomId] || []),
                ]"
                :key="idx"
                :class="['carousel-item', { active: idx === 0 }]"
              >
                <img
                  :src="imageUrl"
                  class="d-block w-100"
                  alt="방 이미지"
                  style="height: 300px; object-fit: cover"
                  @click="
                    userRole === 'ROLE_ADMIN' && addRoomPhoto(room.roomId)
                  "
                />
              </div>
            </div>
            <button
              class="carousel-control-prev"
              type="button"
              :data-bs-target="'#carouselExampleControls-' + room.roomId"
              data-bs-slide="prev"
            >
              <span
                class="carousel-control-prev-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button
              class="carousel-control-next"
              type="button"
              :data-bs-target="'#carouselExampleControls-' + room.roomId"
              data-bs-slide="next"
            >
              <span
                class="carousel-control-next-icon"
                aria-hidden="true"
              ></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
        </div>

        <!-- 우측 박스: 방 정보 -->
        <div class="info-box d-flex flex-column justify-content-between" style="border: 1px solid #d3d3d3; border-radius: 8px;">
          <div>
            <strong style="color: dark">숙박</strong>
            <h5 class="card-title mt-3">{{ room.roomName }}</h5>
            <h3
              style="
                font-size: 18px;
                margin-top: 10px;
                color: #007bff;
                font-weight: bold;
              "
            >
              {{ room.description }}
            </h3>
            <p
              class="card-text"
              style="font-size: 15px; line-height: 2; color: black"
            >
              <strong>인원(기준) :</strong> {{ room.capacity }}인<br />
              <strong>가격 : </strong>
              <span style="color: black; font-weight: bold"
                >{{
                  stayDuration >= 0
                    ? (
                        parseInt(room.price.replace(/,/g, ""), 10) *
                        stayDuration
                      ).toLocaleString()
                    : room.price
                }}원/{{ stayDuration >= 0 ? stayDuration : "" }}박 (1박당
                {{ room.price }}원)</span
              >
              <br />
              <strong>체크인 :</strong> {{ room.checkIn }} ~
              <strong>체크아웃 :</strong> {{ room.checkOut }}<br />
            </p>
          </div>
          <div class="button-group me-4 mb-10">
            <button
              v-if="userRole === 'ROLE_ADMIN'"
              @click="editRoom(room.roomId)"
              class="btn btn-outline-warning me-2"
            >
              업데이트
            </button>
            <button
              v-if="userRole === 'ROLE_ADMIN'"
              @click="deleteRoom(room.roomId)"
              class="btn btn-outline-danger"
            >
              삭제
            </button>
          </div>
          <div
            class="left-room-count"
            style="position: absolute; color: orange; margin-top: 250px"
          >
            <!-- <p>남은객실</p> -->
          </div>
          <div class="button-reservation-group me-20">
            <!-- 장바구니 버튼 -->
            <button
              class="btn btn-light me-2"
              style="border: 1px solid green; width: 40px; height: 40px"
              @click="addToCart(room)"
            >
              <i class="bi bi-cart"></i>
            </button>
            <!-- 예약하기 버튼 -->
            <button
              class="btn btn-danger"
              style="padding: 5px 10px; font-size: 15px; height: 40px"
              @click="makeReservation(room)"
            >
              예약하기
            </button>
          </div>
        </div>
      </div>
    </div>
    <div v-else class="text-center">
      <p>방 정보가 없습니다.</p>
    </div>
  </div>
</template>

<script>
import MainService from "@/services/main/MainService"; // 숙소 정보 서비스
import RoomService from "@/services/room/RoomService"; // 방 정보 서비스
import CartService from "@/services/cart/CartService"; // 장바구니 정보 서비스
import Swal from "sweetalert2";
export default {
  data() {
    return {
      tour: {
        name: "",
        location: "",
        description: "",
        price: "",
        tourFileUrl: "", // 숙소 이미지 경로
      },
      rooms: [],
      tourId: null, // tourId 변수 추가

      EachRoomUrls: {}, // roomId로 조회한 각 방의 url들을 담을 변수 (객체) => 각 roomId에 해당하는 배열들을 찾음

      AllImages: [],

      userRole: "", // 유저 권한
      userEmail: "", // 유저 이메일

      // **예약버튼 클릭시 예약 => 버튼에 정보담기(reservation 객체)**
      reservation: {
        totalPrice: "", // 총 금액(1박 금액 * 숙박 일수)
        userEmail: "", // 유저 이메일
        stayDuration: 0, // 숙박 일수
        tourFileUrl: "", // 숙소 이미지
        tourName: "", // 숙소 명
        roomName: "", // 방 이름
        capacity: 0, // 수용 기준 인원
        checkInDate: "", // 체크인 날짜
        checkOutDate: "", // 체크아웃 날짜
        checkInTime: "", // 체크인 시간
        checkOutTime: "", // 체크아웃 날짜
      },

      cartItem: {
        totalPrice: "", // 총 금액(1박 금액 * 숙박 일수)
        userEmail: "", // 유저 이메일
        stayDuration: 0, // 숙박 일수
        tourFileUrl: "", // 숙소 이미지
        tourName: "", // 숙소 명
        roomName: "", // 방 이름
        capacity: 0, // 수용 기준 인원
        checkInDate: "", // 체크인 날짜
        checkOutDate: "", // 체크아웃 날짜
        checkInTime: "", // 체크인 시간
        checkOutTime: "", // 체크아웃 날짜
      },

      today: this.formatDate(new Date()), // 오늘 날짜
      tomorrow: this.formatDate(this.addDay(new Date(), 1)), // 내일 날짜
      oneMonthLater: this.formatDate(this.addMonth(new Date(), 1)), // 오늘 날짜 기준, 한 달 뒤의 ㅣ날짜

      stayDuration: -1, // 숙박 일수 (기본 -1로 설정하여 계산 전에 표시 안되게 함)
    };
  },
  computed: {
    // 체크아웃 최소 날짜는 체크인 다음 날로 설정
    checkOutMinDate() {
      if (this.reservation.checkInDate) {
        const checkInDate = new Date(this.reservation.checkInDate);
        return this.formatDate(this.addDay(checkInDate, 1));
      }
      return this.tomorrow;
    },
  },
  methods: {
    // 숙소(Tour) 정보 가져오기
    async getTourDetail(tourId) {
      try {
        const response = await MainService.get(tourId);
        this.tour = response.data;
      } catch (error) {
        console.error("Error fetching tour details:", error);
      }
    },

    // 방 정보 가져오기
    async getRoomsByTourId(tourId) {
      try {
        const response = await RoomService.getRoomsByTourId(tourId);
        this.rooms = response.data;

        // 각 방의 이미지 URL들을 비동기적으로 가져오기
        let allImages = []; // 모든 방의 이미지 URL들을 담을 배열
        for (const room of this.rooms) {
          await this.getUrlsByRoomId(room.roomId); // 각 방의 이미지 URL을 가져옴
          allImages = [
            ...allImages, // 기존의 allImages 배열 (빈 배열일 수 있음)
            room.roomUrl, // 첫 번째 방의 기본 이미지 'room1.jpg'
            ...this.EachRoomUrls[room.roomId], // 첫 번째 방의 추가 이미지들 ['room1_add1.jpg', 'room1_add2.jpg']
          ]; // 이미지들을 배열에 추가
        }

        // 모든 이미지들을 AllImages 배열에 담기
        this.AllImages = allImages;
      } catch (error) {
        console.error("Error fetching rooms:", error);
      }
    },

    // 각 방의 RoomId에 대한 여러 이미지 경로 가져오기
    async getUrlsByRoomId(roomId) {
      try {
        const response = await RoomService.getUrlsByRoomId(roomId);

        // this.EachRoomUrls[roomId] = response.data; 방식으로 저장
        this.EachRoomUrls[roomId] = response.data; // 방 ID에 해당하는 URL 배열을 EachRoomUrls에 저장
        console.log("EachRoomUrls after update:", this.EachRoomUrls); // 각 방에 대한 URL이 잘 들어갔는지 확인
      } catch (error) {
        console.error("Error fetching rooms:", error);
      }
    },

    // 방 업데이트 페이지 이동
    editRoom(roomId) {
      this.$router.push(`/editRoom/${roomId}`); // 업데이트 페이지로 리다이렉트
    },

    // 각 방에 사진 추가 페이지
    addRoomPhoto(roomId) {
      this.$router.push(`/addRoomPhoto/${roomId}`); // 각 방에 사진 추가 페이지로 리다이렉트
    },

    // 방 삭제 처리
    async deleteRoom(roomId) {
      try {
        const response = await RoomService.RemoveRoom(roomId); // 방 삭제 API 호출
        console.log("Room deleted:", response.data);

        // 방 삭제 후, 목록에서 해당 방 제거
        this.rooms = this.rooms.filter((room) => room.roomId !== roomId);
      } catch (error) {
        console.error("Error deleting room:", error);
      }
    },

    // 달력 기능
    formatDate(date) {
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, "0");
      const day = date.getDate().toString().padStart(2, "0");
      return `${year}-${month}-${day}`;
    },
    addDay(date, days) {
      const newDate = new Date(date);
      newDate.setDate(newDate.getDate() + days);
      return newDate;
    },
    addMonth(date, months) {
      const newDate = new Date(date);
      newDate.setMonth(newDate.getMonth() + months);
      return newDate;
    },

    // 체크인 날짜 변경 시 처리
    handleCheckInChange() {
      // 체크아웃 초기화
      this.reservation.checkOutDate = "";
      // 숙박일수 초기화
      this.stayDuration = -1;
    },

    // 키보드 입력을 막는 함수
    handleKeyDown(event) {
      event.preventDefault(); // 키보드 입력을 막음 => 달력UI로만 날짜 선택가능
    },

    // 숙박 일수 계산
    calculateStayDuration() {
      const checkIn = new Date(this.reservation.checkInDate);
      const checkOut = new Date(this.reservation.checkOutDate);
      if (checkIn && checkOut && checkOut > checkIn) {
        const timeDiff = checkOut - checkIn;
        const dayDiff = timeDiff / (1000 * 3600 * 24); // 밀리초를 일 단위로 변환
        this.stayDuration = dayDiff;
      } else {
        this.stayDuration = -1; // 유효하지 않은 날짜 선택 시
      }
    },

    // 문자열로 된 가격을 숫자로 바꾸고, 숙박일수랑 곱한 후, 다시 ','로 포맷팅하는 함수
    formatPriceWithStayDuration(price, stayDuration) {
      // 가격을 문자열에서 숫자로 변환
      const priceNumber = parseInt(price.replace(/,/g, ""), 10);

      // 가격과 숙박일수를 곱함
      const totalPrice = priceNumber * stayDuration;

      // 총 금액을 ','로 구분된 문자열로 변환하여 반환
      return totalPrice.toLocaleString(); // 예: 1000000 -> "1,000,000"
    },

    // ** 예약 **
    async makeReservation(room) {
      // 사용자가 로그인했는지 확인
      if (!this.userEmail) {
        await Swal.fire({
          icon: "warning",
          title: "로그인 필요",
          text: "로그인이 필요합니다.",
        });
        return; // 로그인되지 않았다면 함수 종료
      }

      if (!this.reservation.checkOutDate) {
        await Swal.fire({
          icon: "warning",
          title: "체크아웃 날짜 미선택",
          text: "체크아웃 날짜를 선택하세요.",
        });
        return; // 체크아웃 날짜가 없으면 이후 코드 실행을 멈춤
      }

      // 예약 정보를 설정
      this.reservation.totalPrice = this.formatPriceWithStayDuration(
        room.price,
        this.stayDuration
      );
      this.reservation.userEmail = this.userEmail;
      this.reservation.stayDuration = this.stayDuration;
      this.reservation.tourFileUrl = this.tour.tourFileUrl;
      this.reservation.tourName = this.tour.name;
      this.reservation.roomName = room.roomName;
      this.reservation.capacity = room.capacity;
      this.reservation.checkInTime = room.checkIn;
      this.reservation.checkOutTime = room.checkOut;

      // 입실 및 퇴실 날짜 계산
      const checkInDate = this.reservation.checkInDate; // 입실 날짜 (YYYY-MM-DD)
      const checkOutDate = this.reservation.checkOutDate; // 퇴실 날짜 (YYYY-MM-DD)

      // SweetAlert2로 예약 확인 메시지 표시
      const result = await Swal.fire({
        icon: "info",
        title: "예약 확인",
        html: `
      <p>숙소: <strong>${this.tour.name}</strong></p>
      <p>객실: <strong>${room.roomName}</strong></p>
      <p>입실 날짜: <strong>${checkInDate}</strong></p>
      <p>퇴실 날짜: <strong>${checkOutDate}</strong></p>
      <p>총 ${this.stayDuration}박</p>
      <p>총 금액: <strong>${this.formatPriceWithStayDuration(
        room.price,
        this.stayDuration
      )}원</strong></p>
      <p>체크인 시간: <strong>${room.checkIn}</strong></p>
      <p>체크아웃 시간: <strong>${room.checkOut}</strong></p>
      <p>예약을 진행하시겠습니까?</p>
    `,
        showCancelButton: true,
        confirmButtonText: "예약 진행",
        cancelButtonText: "취소",
      });

      if (result.isConfirmed) {
        // 예약 정보 로컬스토리지에 저장
        localStorage.setItem(
          `reservation_${room.roomId}`,
          JSON.stringify(this.reservation)
        );

        // 예약 페이지로 이동
        this.$router.push(`/payment/${room.roomId}`);
      } else {
        console.log("예약이 취소되었습니다.");
      }
    },
    syncCheckInDate() {
      // reservation.checkInDate가 변경되면 cartItem.checkInDate에 값 동기화
      this.cartItem.checkInDate = this.reservation.checkInDate;
    },

    syncCheckOutDate() {
      // reservation.checkInDate가 변경되면 cartItem.checkInDate에 값 동기화
      this.cartItem.checkOutDate = this.reservation.checkOutDate;
    },

    handleCheckInChangeAndSync() {
      this.handleCheckInChange(); // 체크인 날짜 처리
      this.syncCheckInDate(); // 장바구니 동기화
    },

    async addToCart(room) {
      // 사용자가 로그인했는지 확인
      if (!this.userEmail) {
        await Swal.fire({
          icon: "warning",
          title: "로그인 필요",
          text: "로그인이 필요합니다.",
        });
        return; // 로그인되지 않았다면 함수 종료
      }

      if (!this.reservation.checkOutDate) {
        await Swal.fire({
          icon: "warning",
          title: "체크아웃 날짜 미선택",
          text: "체크아웃 날짜를 선택하세요.",
        });
        return; // 체크아웃 날짜가 없으면 이후 코드 실행을 멈춤
      }

      // cartItem에 필요한 값들 추가
      this.cartItem.totalPrice = this.formatPriceWithStayDuration(
        room.price,
        this.stayDuration
      );
      this.cartItem.userEmail = this.userEmail;
      this.cartItem.stayDuration = this.stayDuration;
      this.cartItem.tourFileUrl = this.tour.tourFileUrl;
      this.cartItem.tourName = this.tour.name;
      this.cartItem.roomName = room.roomName;
      this.cartItem.capacity = room.capacity;
      this.cartItem.checkInTime = room.checkIn;
      this.cartItem.checkOutTime = room.checkOut;

      try {
        // 서버에 장바구니 추가 요청
        const response = await CartService.addToCart(this.cartItem);
        console.log(response.data);

        // 로컬스토리지에서 기존 장바구니 개수 가져오기
        let cartCount = parseInt(localStorage.getItem("cartCount")) || 0;

        // 장바구니 개수 증가
        cartCount++;
        localStorage.setItem("cartCount", cartCount);

        // SweetAlert2 confirm 대화상자 사용
        const result = await Swal.fire({
          icon: "success",
          title: "장바구니 추가 성공",
          text: "장바구니에 추가되었습니다. 장바구니로 이동하시겠습니까?",
          showCancelButton: true,
          confirmButtonText: "이동",
          cancelButtonText: "머무르기",
        });

        if (result.isConfirmed) {
          // "이동" 버튼 클릭 시 /cart 페이지로 이동
          this.$router.push(`/cart`);
        } else {
          // "머무르기" 클릭 시
          console.log("장바구니 페이지로 이동하지 않았습니다.");
        }
      } catch (error) {
        console.error("Failed to add Cart:", error);

        // 에러 발생 시 SweetAlert2 알림
        await Swal.fire({
          icon: "error",
          title: "오류 발생",
          text: "장바구니 추가에 실패했습니다. 다시 시도해주세요.",
        });
      }
    },
  },
  mounted() {
    const tourId = this.$route.params.tourId; // URL에서 tourId 가져오기
    if (tourId) {
      this.tourId = tourId; // tourId 값을 데이터 속성에 저장
      this.getTourDetail(tourId); // 투어 상세 정보 가져오기
      this.getRoomsByTourId(tourId); // 방 정보 가져오기
    } else {
      console.error("tourId is missing in the route.");
    }

    // 로컬스토리지에서 토큰 정보 가져오기
    const user = localStorage.getItem("user"); // 저장된 사용자 정보 가져오기
    if (user) {
      const parsedUser = JSON.parse(user); // JSON 문자열을 객체로 파싱
      this.userRole = parsedUser.codeName; // 권한 정보 저장
      this.userEmail = parsedUser.email; // 이메일 정보 저장
    } else {
      console.error("No user data found in localStorage.");
    }
  },
  watch: {
    // 체크인 날짜 변경 시 체크아웃 날짜와 숙박 일수 다시 계산
    "reservation.checkInDate": function () {
      if (this.reservation.checkInDate && this.reservation.checkOutDate) {
        this.calculateStayDuration();
      }
      this.checkOutMinDate = this.reservation.checkInDate;
    },

    "reservation.checkOutDate": function () {
      if (this.reservation.checkInDate && this.reservation.checkOutDate) {
        this.calculateStayDuration();
      }
    },
  },
};
</script>

<style scoped>
/* 방 디자인 */
.room-container {
  display: flex;
  flex-direction: row; /* 가로 정렬 */
  width: 100%;
  max-width: 900px; /* 카드 최대 너비 */
  /* border: 1px solid #ddd; */
  /* border-radius: 8px; */
  overflow: hidden;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
  padding: 15px;
  margin: auto; /* 가운데 정렬 */
}

/* 숙소 디자인 */
.tour-container {
  display: flex;
  flex-direction: column; /* 위아래 정렬 */
  align-items: flex-start; /* 왼쪽 정렬 */
  width: 100%;
  max-width: 900px;
  background-color: #f9f9f9;
  /* border: 1px solid #ddd; 테두리 */
  /* border-radius: 8px; */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  font-weight: 700;
  padding-left: 25px;
  margin: 0 auto; /* 가운데 정렬 */
}

.calendar-container {
  display: flex; /* 가로로 배치 */
  flex-direction: row; /* 기본값은 row, 하지만 명시적으로 설정 */
  align-items: center; /* 세로로 중앙 정렬 */
  justify-content: center; /* 가로로 왼쪽 정렬 */
  width: 100%;
  max-width: 900px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
  font-weight: 700;
  padding-left: 25px;
  margin: 0 auto; /* 가운데 정렬 */
  text-align: center; /* 글씨 중앙 정렬 */
  gap: 10px; /* 항목 간 간격 */
}

.stay-duration-container {
  min-width: 50px; /* 공간 확보 */
  display: flex;
  align-items: center; /* 세로축 중앙 정렬 */
  justify-content: center; /* 가로 정렬 */
  white-space: nowrap; /* 텍스트 줄바꿈 방지 */
  position: relative; /* 위치 조정 가능하게 설정 */
}

.stay-duration-container p {
  position: absolute; /* 부모 기준으로 위치 조정 */
  top: 19px; /* 부모 높이의 50% 아래로 이동 */
  margin-left: -25px;
  transform: translateY(
    -50%
  ); /* 본래 높이의 절반만큼 위로 이동해 정확히 중앙 정렬 */
}

.calendar-input {
  font-size: 16px;
  border-radius: 4px;
  padding-left: 5px;
}

.tour-name {
  color: #000; /* 검정색 */
  font-size: 1.5em; /* 큰 글씨 */
  margin-top: 10px; /* 아래 간격 */
  margin-bottom: 3px; /* 아래 간격 */
}

.tour-location {
  color: #007bff; /* 파란색 */
  font-size: 1em;
}

.image-box {
  flex: 1; /* 이미지 박스 비율 조정 */
  width: 50%; /* 5:5 비율 */
  /* height: 200px; 이미지 높이 고정 */
  background-color: #f8f9fa;
}

.info-box {
  flex: 1; /* 정보 박스 비율 조정 */
  width: 50%; /* 5:5 비율 */
  height: 300px;
  padding: 15px;
  background-color: #fff;
}

.card-title {
  font-size: 1.2rem;
  font-weight: bold;
}

.card-text {
  font-size: 0.9rem;
  line-height: 1.5;
}

.button-group button {
  margin-bottom: -25px;
  width: 100px;
}

.button-reservation-group {
  margin-left: 280px;
}

/* 캐러셀 너비를 카드와 동일하게 설정 */
#carouselExampleControls,
#topCarousel {
  max-width: 900px; /* 카드와 동일한 최대 너비 */
  margin: auto; /* 가운데 정렬 */
  overflow: hidden; /* 넘친 요소를 잘라냄 */
}

.carousel-inner {
  max-width: 900px; /* 카드와 동일한 최대 너비 */
  margin: auto; /* 가운데 정렬 */
}

.carousel-inner img {
  height: 600px; /* 이미지 높이 유지 */
  width: 100%; /* 부모 요소 너비에 맞춤 */
  object-fit: cover; /* 비율을 유지하면서 잘림 */
}

/* 작은 화면에서 캐러셀 높이를 줄임 */
@media (max-width: 768px) {
  .carousel-inner img {
    height: 300px; /* 작은 화면에서는 높이를 줄임 */
  }
}

/* 캐러셀 컨트롤 버튼 위치 자동 조정 */
.carousel-control-prev,
.carousel-control-next {
  position: absolute;
  top: 50%; /* 이미지 높이의 50% */
  transform: translateY(-50%); /* 가운데 정렬 */
  z-index: 1; /* 버튼이 이미지 위로 올라오도록 설정 */
  width: 50px;
  height: 50px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.3); /* 반투명 배경 */
  border-radius: 50%;
}

/* 왼쪽 버튼 */
.carousel-control-prev {
  left: 20px; /* 좌측 이미지 끝에서 20px 거리 */
}

/* 오른쪽 버튼 */
.carousel-control-next {
  right: 20px; /* 우측 이미지 끝에서 20px 거리 */
}

/* 왼쪽 버튼 아이콘 커스터마이징 */
.carousel-control-prev-icon {
  background-image: url("https://img.icons8.com/material-rounded/50/ffffff/chevron-left.png"); /* 새로운 Chevron Left 아이콘 */
}

/* 오른쪽 버튼 아이콘 커스터마이징 */
.carousel-control-next-icon {
  background-image: url("https://img.icons8.com/material-rounded/50/ffffff/chevron-right.png"); /* 새로운 Chevron Right 아이콘 */
}
</style>
