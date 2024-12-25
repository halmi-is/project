// views/basic/dept/AddDept.vue // vueInit
<template>
  <div>
    <!-- dname -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="question"
        placeholder="question"
        v-model="faq.question"
      />
      <label for="question">question</label>
    </div>
   

    <!-- loc -->
    <div class="form-floating mb-3">
      <input
        type="text"
        class="form-control"
        id="answer"
        placeholder="answer"
        v-model="faq.answer"
      />
      <label for="loc">answer</label>
    </div>

    <!-- 버튼 -->
    <button type="button" class="btn btn-primary" @click="save">저장</button>
  </div>
</template>
<script>
import FaqService from '@/services/faq/FaqService';


export default {
  data() {
    return {
      faq: {
        question: "",
        answer: "",
      
      },
    };
  },
  methods: {
    async save() {
      try {
        let response = await FaqService.insert(this.faq);
        console.log(response.data); // 디버깅
        // TODO: 성공하면 강제이동 (전체조회/dept)
        this.$router.push("/adminfaqquestionlist");
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>
<style></style>
