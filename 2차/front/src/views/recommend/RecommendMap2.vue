<template>
  <div>
    <!-- 지도 표시 영역 -->
    <div id="map" style="width: 100%; height: 400px"></div>

    <!-- 장소 검색 입력창 -->
    <div style="margin-top: 20px; text-align: center">
      <input
        id="search-input"
        type="text"
        placeholder="장소를 검색하세요"
        style="width: 300px; padding: 10px; font-size: 15px"
      />
    </div>
  </div>
</template>

<script>
/* global google */
export default {
  name: "GoogleMapExample",
  mounted() {
    this.initMap();
  },
  methods: {
    initMap() {
      const location = { lat: 43.6971, lng: 7.2753 };

      // 맵을 렌더링할 div 가져오기
      const mapElement = document.getElementById("map");

      // eslint-disable-next-line no-undef
      const map = new google.maps.Map(mapElement, {
        center: location,
        zoom: 14,
      });

      // 마커 설정
      const marker = new google.maps.Marker({
        position: location,
        map: map,
        title: "성 니콜라스 정교회 성당",
      });

      // 검색 기능 설정
      const input = document.getElementById("search-input");

      // 장소 검색을 위한 자동완성 기능
      const autocomplete = new google.maps.places.Autocomplete(input);
      autocomplete.bindTo("bounds", map);

      // 자동완성된 장소를 클릭했을 때
      autocomplete.addListener("place_changed", () => {
        const place = autocomplete.getPlace();

        if (!place.geometry) {
          return;
        }

        // 지도 중심 변경
        map.setCenter(place.geometry.location);
        map.setZoom(14);

        // 기존 마커 제거
        marker.setMap(null);

        // 새로운 마커 추가
        const newMarker = new google.maps.Marker({
          position: place.geometry.location,
          map: map,
          title: place.name,
        });

        // 새로운 마커 클릭 시 해당 장소 정보 로그
        newMarker.addListener("click", () => {
          console.log("Clicked place: ", place);
        });
      });
    },
  },
};
</script>

<style>
/* 구글 맵의 스타일을 개선 */
#map {
  width: 100%;
  height: 400px;
  border-radius: 10px; /* 둥근 모서리 */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 */
  transition: all 0.3s ease; /* 마우스 오버 시 부드러운 변화 */
}

#map:hover {
  transform: scale(1.1); /* 마우스 오버 시 약간 확대 */
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.15); /* 확대되면 그림자 더 강조 */
}
</style>
