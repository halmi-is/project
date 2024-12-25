<template>
<div>
    <!-- comment -->
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="comment" placeholder="comment" v-model="main.comment" />
        <label for="comment">comment</label>
    </div>

    <!-- name -->
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="name" placeholder="name" v-model="main.name" />
        <label for="name">name</label>
    </div>

    <!-- location -->
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="location" placeholder="location" v-model="main.location" />
        <label for="loc">location</label>
    </div>

    <!-- view -->
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="view" placeholder="view" v-model="main.view" />
        <label for="view">view</label>
    </div>

    <!-- category -->
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="category" placeholder="category" v-model="main.category" />
        <label for="category">category</label>
    </div>

    <!-- pop -->
    <div class="form-floating mb-3">
        <input type="text" class="form-control" id="pop" placeholder="pop" v-model="main.pop" />
        <label for="pop">pop</label>
    </div>

    <!-- image -->
    <div class="input-group">
      <input type="file" class="form-control" ref="file" @change="select"/>
      <button class="btn btn-outline-secondary" type="button" @click="save">저장</button>
    </div>
    <br/>
    <!-- 현재 이미지 미리보기 -->
    <div class="mb-3">
      <img :src="main.tourFileUrl" alt="이미지" class="img-fluid rounded" style="max-height: 400px; object-fit: cover"/>
    </div>
</div>
</template>
<script>
import MainService from '@/services/main/MainService';
export default {
    data() {
        return {
            main:{
                comment:"",
                name:"",
                location:"",
                tourFileUrl: "",
                view: "",
                category: "",
                pop:"",
                image: undefined
            }
        }
    },
    methods: {
        select(){
            const file = this.$refs.file.files[0];
            if(file){
                this.main.image = file;
                // 이미지 URL을 생성하여 tourFileUrl에 할당
                this.main.tourFileUrl = URL.createObjectURL(file);
            }
        },
        async save(){
            try {
            let response = await MainService.insert(this.main);
            console.log(response.data);
            // TODO : 저장후 강제이동 : 전체조회 페이지(/dept)
            this.$router.push("/")
            } catch (error) {
                this.main.image = undefined;
                console.log(error);
            }
        }
    },
}
</script>
<style></style>