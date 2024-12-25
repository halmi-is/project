
<template>
  <div>
    <!-- dname -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="title"
        placeholder="title"
        v-model="anno.title"
      />
      <label for="dname">title</label>
    </div>

    <!-- loc -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="content"
        placeholder="content"
        v-model="anno.content"
      />
      <label for="loc">content</label>
    </div>



    <!-- 버튼 -->
    <button type="button" class="btn btn-primary" @click="save">저장</button>
  </div>
</template>
<script>
import AnnouncementService from '@/services/faq/AnnouncementService';


export default {
  data() {
    return {
      anno: {
        content:"",
        title: "",
        createDate: ""
      },
    };
  },
  methods: {
    async save() {
      try {
        let response = await AnnouncementService.insert(this.anno);
        console.log(response.data); // 디버깅
        // TODO: 성공하면 강제이동 (전체조회/dept)
        this.$router.push("/adminfaqannouncement");
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>
<style>

 /* 관리자 강조 영역 */
 .admin-dashboard {
  padding-top: 30px;
  padding-bottom: 30px;
}

/* 박스 컨테이너 */
.box-container {
  margin-bottom: 20px;
}

.box {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  text-align: center;
  transition: transform 0.3s ease;
}

.box:hover {
  transform: scale(1.05);
}

/* 아이콘 스타일 */
.box-icon {
  font-size: 3rem;
  color: #ffeb33; /* 노란색 강조 */
}

/* 타이틀 스타일 */
.box-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #343a40;
  margin-top: 10px;
}

/* 설명 텍스트 스타일 */
.box-description {
  font-size: 1rem;
  color: #6c757d;
}

/* 링크 스타일 */
.custom-link {
  text-decoration: none;
  color: inherit;
}

.custom-link:visited,
.custom-link:active {
  text-decoration: none;
  color: inherit;
}

.custom-link:hover {
  transition: 0.3s;
}





</style>
