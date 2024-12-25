<template>
    <div>
        <!-- tdName -->
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="tdName" placeholder="tdName" v-model="recommend.tdName" />
            <label for="tdName">tdName</label>
        </div>
    
        <!-- loc -->
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="loc" placeholder="loc" v-model="recommend.loc" />
            <label for="loc">loc</label>
        </div>
    
        <!-- description -->
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="description" placeholder="description" v-model="recommend.description" />
            <label for="loc">description</label>
        </div>
   
    
        <!-- image 추가상자 -->
        <div class="input-group">
          <input type="file" class="form-control" ref="file" @change="select"/>
          <button class="btn btn-outline-secondary" type="button" @click="save">저장</button>
        </div>
        <br/>
        <!-- 현재 이미지 미리보기 -->
        <div class="mb-3">
          <img :src="recommend.imageUrl" alt="이미지" class="img-fluid rounded" style="max-height: 400px; object-fit: cover"/>
        </div>


         

    </div>
    </template>
    <script>
 
import RecommendService from '@/services/recommend/RecommendService';
    export default {
        data() {
            return {
                recommend:{
                    tdName:"",
                    location:"",
                    description:"",
                    imageUrl: "",
                    image: undefined
                }
            }
        },
        methods: {
            select(){
                const file = this.$refs.file.files[0];
                if(file){
                    this.recommend.image = file;
                    // 이미지 URL을 생성하여 tourFileUrl에 할당
                    this.recommend.imageUrl = URL.createObjectURL(file);
                }
            },
            async save(){
                try {
                let response = await RecommendService.insert(this.recommend);
                console.log(response.data);
                // TODO : 저장후 강제이동 : 전체조회 페이지(/dept)
                alert("이미지 데이터 추가완료")
                this.$router.push("/recommend")
                } catch (error) {
                    this.recommend.image = undefined;
                    console.log(error);
                }
            }
        },
    }
    </script>
    <style></style>