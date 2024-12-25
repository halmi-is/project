<template>
    <div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="adminTitle" placeholder="adminTitle"
                v-model="admin.adminTitle" />
            <label for="adminTitle">adminTitle</label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="adminContent" placeholder="adminContent"
                v-model="admin.adminContent" />
            <label for="adminContent">adminContent</label>
        </div>

        <!-- TODO: 현재 이미지 보기 -->
        <div class="mb-3 col-12">
            <img :src="admin.adminUrl" width="200px" alt="이미지" />
        </div>

        <!-- 파일선택상자 -->
        <div class="input-group">
            <input type="admin" class="form-control" ref="admin" @change="select" />
            <button class="btn btn-warning" type="button" @click="update">
                수정
            </button>
        </div>
        <div class="mt-3">
            <button class="btn btn-danger" type="button" @click="remove">삭제</button>
        </div>

        <!-- 데이터 테이블 -->
        <div class="mt-4">
            <button class="btn btn-primary" type="button" @click="getAdminData">데이터 로드</button>
            <table class="table mt-3">
                <thead>
                    <tr>
                        <th scope="col">fno</th>
                        <th scope="col">question</th>
                        <th scope="col">answer</th>
                        <th scope="col">hashtag</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="(data, index) in adminData" :key="index">
                        <td>{{ data.fno }}</td>
                        <td>{{ data.question }}</td>
                        <td>{{ data.answer }}</td>
                        <td>{{ data.hashtag }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import AdminService from '@/services/admin/AdminService';
export default {
    data() {
        return {
            admin: {
                uuid: this.$route.params.uuid, // 기본키
                adminTitle: "",
                adminContent: "",
                adminUrl: "", // 다운로드 url
                image: undefined, // 선택이미지
            },
            adminData: [], // 데이터 테이블에 사용할 데이터
        };
    },
    methods: {
        async getDetail(uuid) {
            try {
                let response = await AdminService.get(uuid);
                console.log(response.data); // 디버깅
                this.admin = response.data;
            } catch (error) {
                console.log(error);
            }
        },
        select() {
            this.admin.image = this.$refs.admin.files[0];
        },
        async update() {
            try {
                let response = await AdminService.update(this.admin.uuid, this.admin);
                console.log(response.data); // 디버깅
                alert("수정되었습니다.");
            } catch (error) {
                this.admin.image = undefined;
                console.log(error);
            }
        },
        async remove() {
            try {
                let response = await AdminService.remove(this.admin.uuid);
                console.log(response.data); // 디버깅
                // 전체조회 화면 강제 이동
                this.$router.push("/admin");
            } catch (error) {
                console.log(error);
            }
        },
        // 데이터베이스에서 데이터 가져오기
        async getAdminData() {
            try {
                let response = await AdminService.getAll();
                console.log(response.data); // 디버깅
                this.adminData = response.data; // 테이블에 표시할 데이터 저장
            } catch (error) {
                console.log(error);
            }
        },
    },
    mounted() {
        this.getDetail(this.$route.params.uuid);
    },
};
</script>

<style scoped>
/* 필요한 스타일 추가 */
</style>
