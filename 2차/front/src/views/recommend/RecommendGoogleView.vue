<template>
  <div>
    <!-- 지도 표시 영역 -->
    <div ref="mapRef" style="width: 100%; height: 400px"></div>

    <!-- 장소 검색 입력창 -->
    <div style="margin-top: 20px; text-align: center">
      <input
        ref="searchInputRef"
        type="text"
        placeholder="숙소 반경10KM 별점4개 이상의 관광지"
        style="width: 300px; padding: 10px; font-size: 15px"
      />
    </div>

    <!-- 주변 관광지 목록 -->
    <div class="container mt-4">
      <div class="row">
        <div
          v-for="(place, index) in places"
          :key="index"
          class="col-md-4 mb-4"
        >
          <div class="card">
            <img
              :src="place.photo"
              class="card-img-top"
              alt="Tourist Spot"
              style="height: 200px; object-fit: cover"
            />
            <div class="card-body">
              <h5 class="card-title">{{ place.name }}</h5>
              <p class="card-text">
                {{ place.address }}
              </p>
              <p class="card-text">
                <strong>⭐ {{ place.rating }}</strong>
              </p>
              <a
                :href="place.mapUrl"
                class="btn btn-warning custom-btn"
                target="_blank"
                rel="noopener noreferrer"
              >
                지도에서 보기
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
/* global google */
import { ref, reactive, onMounted, onBeforeUnmount } from "vue";

export default {
  name: "GoogleMapExample",
  setup() {
    const mapRef = ref(null); // 지도 요소 참조
    const searchInputRef = ref(null); // 검색 입력창 참조
    const places = reactive([]); // 주변 관광지 데이터
    let mapInstance = null; // 맵 객체를 저장
    let googleMapScript = null; // 스크립트 참조
    let service = null; // PlacesService 객체
    const DEFAULT_IMAGE =
      "https://via.placeholder.com/400x200?text=No+Image+Available";

    const addGoogleMapScript = () => {
      if (!document.querySelector('script[src*="maps.googleapis.com"]')) {
        googleMapScript = document.createElement("script");
        googleMapScript.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyA34R4w_p2x2HtPqJ19yfUrvy389vCUwBU&libraries=places`;
        googleMapScript.defer = true;
        googleMapScript.onload = initMap;
        document.head.appendChild(googleMapScript);
      } else {
        initMap();
      }
    };

    const initMap = () => {
      if (!mapRef.value || !searchInputRef.value) {
        console.error("Map or search input element not found!");
        return;
      }

      const location = { lat: 35.2925, lng: 128.4092 };

      // 지도 초기화
      mapInstance = new google.maps.Map(mapRef.value, {
        center: location,
        zoom: 14,
      });

      // PlacesService 초기화
      service = new google.maps.places.PlacesService(mapInstance);

      // 검색창 자동완성 설정
      const autocomplete = new google.maps.places.Autocomplete(
        searchInputRef.value
      );
      autocomplete.bindTo("bounds", mapInstance);

      // 장소 선택 시 동작
      autocomplete.addListener("place_changed", () => {
        const place = autocomplete.getPlace();

        if (!place.geometry) {
          console.error("No geometry data found for the selected place.");
          return;
        }

        // 지도 중심 이동
        mapInstance.setCenter(place.geometry.location);
        mapInstance.setZoom(14);

        // 주변 관광지 검색
        fetchNearbyPlaces(place.geometry.location);
      });
    };

    const fetchNearbyPlaces = (location) => {
      const request = {
        location,
        radius: 10000, // 반경 10km
        type: "tourist_attraction", // 관광지
      };

      service.nearbySearch(request, (results, status) => {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
          places.splice(0); // 기존 데이터 초기화
          results
            .filter((place) => place.rating >= 4.0) // 별점 4.5점 이상 필터
            .slice(0, 9) // 최대 9개로 제한
            .forEach((place) => {
              const photo =
                place.photos && place.photos.length
                  ? place.photos[0].getUrl({ maxWidth: 400 })
                  : DEFAULT_IMAGE;

              places.push({
                name: place.name,
                address: place.vicinity,
                photo,
                rating: place.rating.toFixed(1), // 별점 표시
                mapUrl: `https://www.google.com/maps/place/?q=place_id:${place.place_id}`,
              });
            });
        } else {
          console.error("Failed to fetch nearby places:", status);
        }
      });
    };

    const cleanupGoogleMapScript = () => {
      if (googleMapScript) {
        document.head.removeChild(googleMapScript);
        googleMapScript = null;
        console.log("Google Maps API script removed.");
      }
    };

    onMounted(() => addGoogleMapScript());
    onBeforeUnmount(() => cleanupGoogleMapScript());

    return {
      mapRef,
      searchInputRef,
      places,
    };
  },
};
</script>

<style>
.card {
  border-radius: 10px;
  transition: transform 0.3s ease;
}

.card:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.card-title {
  font-size: 1.25rem;
  font-weight: bold;
}

.custom-btn {
  font-weight: bold;
}
</style>
