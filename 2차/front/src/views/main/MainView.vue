<template>
  <div class="container my-5">
    <!-- 배너(캐러셀) -->
    <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel" data-bs-interval="4000">
    <div class="carousel-indicators">
      <button type="button" data-bs-target="#carouselExampleControls" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
      <button type="button" data-bs-target="#carouselExampleControls" data-bs-slide-to="1" aria-label="Slide 2"></button>
      <button type="button" data-bs-target="#carouselExampleControls" data-bs-slide-to="2" aria-label="Slide 3"></button>
      <button type="button" data-bs-target="#carouselExampleControls" data-bs-slide-to="3" aria-label="Slide 4"></button>
    </div>
    <div class="carousel-inner">
      <div class="carousel-item active">
        <a href="/maindetail/227">
          <img :src="require(`@/assets/images/main/배너제주.png`)" class="d-block w-100" width="800" height="600" />
        </a>
      </div>
      <div class="carousel-item">
        <a href="/maindetail/228">
          <img :src="require(`@/assets/images/main/배너광안.png`)" class="d-block w-100" width="800" height="600" />
        </a>
      </div>
      <div class="carousel-item">
        <a href="/coupon">
          <img :src="require(`@/assets/images/main/배너쿠폰.png`)" class="d-block w-100" width="800" height="600" />
        </a>
      </div>
      <div class="carousel-item">
        <a href="/recommend">
          <img :src="require(`@/assets/images/main/배너추천.png`)" class="d-block w-100" width="800" height="600" />
        </a>
      </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="visually-hidden">Next</span>
    </button>
  </div>  
  <br/>

    <!-- 메뉴 1 : 연말특가 -->
    <div>
      <button class="btn btn-warning" style="border-radius: 10px; color: white; font-size: 21px;
      font-family: Jua, sans-serif;">2024 연말, 마지막 특가</button>
      <div style="display: flex; align-items: center; gap: 10px; margin-top: 10px; margin-bottom: 10px;">
        <span class="bi bi-chat-right-heart" style="font-size: 18px; cursor: pointer;"></span>
        <p style="margin: 0; font-size: 16px; color: #555; font-size: 30px; font-family: Dongle, sans-serif;">
          아직 늦지 않았어요! 연말엔 예쁜 숙소에서 낭만 가득 휴식 어때요?
        </p>
      </div>
    </div>

    <!-- 추천 카드 1 -->
    <div class="row row-cols-1 row-cols-md-4 g-3">
      <div class="col">
        <div class="card" style="border: 1px solid lightgray; border-radius: 20px; overflow: hidden; height: 400px;">
          <a href="/maindetail/228" style="text-decoration: none;">
            <img :src="require(`@/assets/images/main/추천광안.jpg`)" class="card-img-top" style="width: 100%; height: 400px;
            object-fit: cover;"/>
            <h5 class="card-title mt-2" style="font-size: 22px; font-family: Jua, sans-serif; color: white; 
            top: 340px; left: 14px; position: absolute;">광안대교 야경맛집</h5>
            <p style="font-size: 18px; font-weight: bolder; color: white; top: 345px; left: 195px; position: absolute;">363,000원~</p>
          </a>
        </div>
      </div>
      <!-- 추천 카드 2 -->
      <div class="col">
        <div class="card" style="border: 1px solid lightgray; border-radius: 20px; overflow: hidden; height: 400px;">
          <a href="/maindetail/227" style="text-decoration: none;">
            <img :src="require(`@/assets/images/main/추천제주.jpg`)" class="card-img-top" style="width: 100%; height: 400px;
            object-fit: cover;"/>
            <h5 class="card-title mt-2" style="font-size: 22px; font-family: Jua, sans-serif; color: white; 
            top: 340px; left: 14px; position: absolute;">휴양지는 제주도로</h5>
            <p style="font-size: 18px; font-weight: bolder; color: white; top: 345px; left: 195px; position: absolute;">191,000원~</p>
          </a>
        </div>
      </div>
      <!-- 추천 카드 3 -->
      <div class="col">
        <div class="card" style="border: 1px solid lightgray; border-radius: 20px; overflow: hidden; height: 400px;">
          <a href="/maindetail/226" style="text-decoration: none;">
            <img :src="require(`@/assets/images/main/추천프랑스.png`)" class="card-img-top" style="width: 100%; height: 400px;
            object-fit: cover;"/>
            <h5 class="card-title mt-2" style="font-size: 22px; font-family: Jua, sans-serif; color: white; 
            top: 340px; left: 14px; position: absolute;">디즈니랜드</h5>
            <p style="font-size: 18px; font-weight: bolder; color: white; top: 345px; left: 180px; position: absolute;">1,267,208원~</p>
          </a>
        </div>
      </div>
      <!-- 추천 카드 4 -->
      <div class="col">
        <div class="card" style="border: 1px solid lightgray; border-radius: 20px; overflow: hidden; height: 400px;">
          <a href="/maindetail/169" style="text-decoration: none;">
            <img :src="require(`@/assets/images/main/추천일본.png`)" class="card-img-top" style="width: 100%; height: 400px;
            object-fit: cover;"/>
            <h5 class="card-title mt-2" style="font-size: 22px; font-family: Jua, sans-serif; color: white; 
            top: 340px; left: 14px; position: absolute;">료칸에서 온천까지</h5>
            <p style="font-size: 18px; font-weight: bolder; color: white; top: 345px; left: 195px; position: absolute;">163,273원~</p>
          </a>
        </div>
      </div>
    </div>  <!-- 연말특가 닫는태그 -->
    <br/>

    <!-- 메뉴 2 : 숙소 전체 -->
    <div>
      <button class="btn btn-warning" style="border-radius: 10px; color: white; font-size: 21px;
      font-family: Jua, sans-serif;">최다 숙소 보유</button>
      <div style="display: flex; align-items: center; gap: 10px; margin-top: 10px; margin-bottom: 10px;">
        <span style="font-size: 18px; cursor: pointer;">👍</span>
        <p style="margin: 0; font-size: 16px; color: #555; font-size: 30px; font-family: Dongle, sans-serif;">
          여행 전에도, 여행 중에도 언제 어디서든 예약OK
        </p>
      </div>
    </div>

    <!-- 필터 -->
    <div class="flex justify-content-between align-items-center mb-4">
      <div class="flex  mb-4">
        <!-- 상위 카테고리 -->
          <button class="btn btn-outline-secondary mx-2" @click="getreset()">전체보기</button>
          <!-- 인기 급상승 ON/OFF 버튼 -->
          <button class="btn btn-outline-secondary mx-2" @click="togglePopular('인기급상승')">인기급상승</button>
          <button class="btn btn-outline-secondary mx-2" @click="selectKeyword('국내')">국내</button>
          <button class="btn btn-outline-secondary mx-2" @click="selectKeyword('해외')">해외</button>
        <!-- 하위 카테고리 -->
          <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
            필터
          </button>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" @click="getAll('교통편의')">교통편의</a></li>
            <li><a class="dropdown-item" @click="getAll('바다')">해변가근처</a></li>
            <li><a class="dropdown-item" @click="getAll('산')">분위기있는</a></li>
          </ul>
      </div>
    </div>


    <!-- 상품 카드 2 -->
    <div class="row row-cols-1 row-cols-md-4 g-4">
      <div class="col" v-for="(data, index) in mains" :key="index">
        <div class="card" style="border: 1px solid lightgray; border-radius: 20px; overflow: hidden; height: 400px;">
          <router-link :to="'/maindetail/' + data.tourId">
            <img :src="data.tourFileUrl" class="card-img-top" style="width: 100%; height: 200px; object-fit: cover;"/>
          </router-link>
          <div class="card-body" style="display: flex; flex-direction: column; justify-content: space-between; width: 300px;">
            <h5 class="card-title mt-2 fw-bold">{{ data.comment }}</h5>
            <p class="card-text">{{ data.location }}</p>
            <div style="display: flex; justify-content: space-between;">
              <p class="text-primary fw-bold" style="margin-top:10px;">{{ data.minPrice }}원~</p>
              <router-link :to="'/mainupdate/' + data.tourId" style="text-decoration: none;">
                <button v-if="userRole === 'ROLE_ADMIN'" class="btn btn-outline-dark" style="display: flex; align-items: center; gap: 5px;">
                  <span class="bi bi-box-arrow-in-right" style="font-size: 18px; vertical-align: middle;"></span>
                수정/삭제</button>
              </router-link>
            </div>
          </div>
        </div> 
      </div> <!-- 반복문 닫는태그 -->
    </div> <!-- 카드 닫는태그 -->
    <br/>

    <!-- 메뉴 3 : 추천/쿠폰/리뷰/faq 이동 카드 -->
    <div>
      <button class="btn btn-warning" style="border-radius: 10px; color: white; font-size: 21px;
      font-family: Jua, sans-serif;">페이지별 모음집</button>
      <div style="display: flex; align-items: center; gap: 10px; margin-top: 10px; margin-bottom: 10px;">
        <span class="bi bi-clipboard-check" style="font-size: 18px; cursor: pointer;"></span>
        <p style="margin: 0; font-size: 16px; color: #555; font-size: 30px; font-family: Dongle, sans-serif;">
          각종 리뷰, 근처 핫플, 쿠폰혜택까지 놓치지 마세요~!
        </p>
      </div>
    </div>

    <!-- 각 페이지 이동카드(링크) -->
    <div class="row row-cols-1 row-cols-md-4 g-4">
      <div class="col">
        <div class="card h-100">
          <a href="/recommend">
            <img :src="require(`@/assets/images/main/추천.png`)" class="card-img-top" style="width: 100%; height: 200px; object-fit: cover;"/>
          </a>
          <div class="card-body" style="display: flex; width: 300px;">
            <a href="/recommend" style="text-decoration: none;">
            <button class="btn btn-outline-dark" style="display: flex; align-items: center; gap: 5px;">
              <span class="bi bi-box-arrow-in-right" style="font-size: 18px; vertical-align: middle;"></span>
            추천여행지</button></a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100">
          <a href="/coupon">
            <img :src="require(`@/assets/images/main/쿠폰.png`)" class="card-img-top" style="width: 100%; height: 200px; object-fit: cover;"/>
          </a>
          <div class="card-body" style="display: flex; width: 300px;">
            <a href="/coupon" style="text-decoration: none;">
            <button class="btn btn-outline-dark" style="display: flex; align-items: center; gap: 5px;">
              <span class="bi bi-box-arrow-in-right" style="font-size: 18px; vertical-align: middle;"></span>
            쿠폰/이벤트</button></a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100">
          <a href="/review">
            <img :src="require(`@/assets/images/main/리뷰.png`)" class="card-img-top" style="width: 100%; height: 200px; object-fit: cover;"/>
          </a>
          <div class="card-body" style="display: flex; width: 300px;">
            <a href="/review" style="text-decoration: none;">
            <button class="btn btn-outline-dark" style="display: flex; align-items: center; gap: 5px;">
              <span class="bi bi-box-arrow-in-right" style="font-size: 18px; vertical-align: middle;"></span>
            리뷰게시판</button></a>
          </div>
        </div>
      </div>
      <div class="col">
        <div class="card h-100">
          <a href="/faq">
            <img :src="require(`@/assets/images/main/FAQ.png`)" class="card-img-top" style="width: 100%; height: 200px; object-fit: cover;"/>
          </a>
          <div class="card-body" style="display: flex; width: 300px;">
            <a href="/faq" style="text-decoration: none;">
            <button class="btn btn-outline-dark" style="display: flex; align-items: center; gap: 5px;">
              <span class="bi bi-box-arrow-in-right" style="font-size: 18px; vertical-align: middle;"></span>
            고객센터</button></a>
          </div>
        </div>
      </div>
    </div>  <!-- 페이지 이동링크 닫는태그 -->
  
  </div>  <!-- 전체 닫는태그 -->
</template>
<script>
import MainService from "@/services/main/MainService";
export default {
  data() {
    return {
      mains: [], //빈배열(json)
      searchKeyword:"", // 검색어
      pop:"", // 인기급상승 확인
      keywords: ["국내","해외"], //드롭다운 항목
      userRole: "", // 유저 권한
    };
  },
  methods: {
    async getAll(view = ""){
      this.view = view;
      // 디버깅용
      console.log(this.pop);
      console.log(this.view);
      console.log(this.searchKeyword);
      try {
        let response = await MainService.getALLnp(this.searchKeyword,this.view,this.pop);
        const { results, totalCount } = response.data;
        console.log(response.data);

        // 모든 tourId의 최솟값 가져오기
        const updatedResults = await Promise.all(
          // results 배열을 반복 처리
          results.map(async (item) => {
            // 각 item에 대해 비동기 함수 getMinPrice 호출
            // 해당 item의 tourId를 이용해 최솟값(minPrice)을 가져옴
            const minPrice = await this.getMinPrice(item.tourId);

            // 기존 item 객체에 minPrice 속성을 추가하여 새 객체를 반환
            return {
              ...item, // 기존 item 속성을 모두 유지
              minPrice, // 새로 계산된 minPrice 속성을 추가
            };
          })
        );
        // Promise.all: 모든 비동기 작업(map 내부의 async 함수)이 완료될 때까지 기다리고
        // 완료된 결과 배열을 반환 (updatedResults는 새로운 배열)
        this.mains = updatedResults;
        this.totalCount = totalCount;
      } catch (error) {
        console.log(error);
      }
    },
    // 국내/해외 키워드 값 보내고 해당 키워드값 전체조회 실행
    selectKeyword(keyword){
      this.searchKeyword = keyword;
      this.pop = "";
      this.getAll();
    },
    // 전체보기 버튼(전체조회)
    getreset(){
      this.searchKeyword = "";
      this.pop ="";
      this.getAll();
    },
    //인기급상승 키워드 값 보내고 해당 키워드값 전체조회 실행
    togglePopular(pop) {
      this.pop = pop;
      this.searchKeyword = "";
      this.getAll();
    },
    // 최저가 불러오기
    async getMinPrice(tourId) {
      try {
        // Java에서 반환된 데이터의 price 배열 추출
        const response = await MainService.getRoomMinPrice(tourId);
        const prices = response.data; // price 데이터 가져오기

        // 응답 데이터가 비어 있는 경우
        if (!prices || prices.length === 0) {
          return "가격 없음";
        }

        // 문자열 배열을 숫자 배열로 변환
        const numericPrices = prices.map((price) =>
          parseInt(price.replace(/,/g, ""), 10)
        );
        const minPrice = Math.min(...numericPrices);

        return minPrice.toLocaleString(); // 최솟값 반환
      } catch (error) {
        console.error(`Failed to fetch prices for tourId ${tourId}:`, error);
        return "데이터 없음"; // 에러 발생 시 기본 메시지
      }
    },
  },
  mounted() {
    this.getAll();
    // 로컬스토리지에서 토큰 정보 가져오기
    const user = localStorage.getItem("user"); // 저장된 사용자 정보 가져오기
    if (user) {
      const parsedUser = JSON.parse(user); // JSON 문자열을 객체로 파싱
      this.userRole = parsedUser.codeName; // 권한 정보 저장
    } else {
      console.error("No user data found in localStorage.");
    }
  },
};
</script>
<style></style>