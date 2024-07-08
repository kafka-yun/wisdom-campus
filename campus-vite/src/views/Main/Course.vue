<template>
    <div class="common-layout">
        <el-container>
        
        <el-container>
            <el-header>
                <div>
                    <h3>{{ course.title }}</h3>
                </div>
                <div>
                    <svg t="1717556583012" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="902" width="16" height="16"><path d="M783.74 401.86L372.23 155.28c-85.88-51.46-195.08 10.41-195.08 110.53v493.16c0 100.12 109.2 161.99 195.08 110.53l411.51-246.58c83.5-50.04 83.5-171.03 0-221.06z" p-id="903" fill="#8a8a8a"></path></svg>
                    <span>{{ course.count }}</span>
                </div>
            </el-header>
            <el-main class="video-main">
                <div class="video-container">
                    <video ref="videoPlayer" class="video-js vjs-default-skin" controls>
                        <source :src="course.media.filePath" type="video/mp4">
                    </video>
                </div>
                <div>
                    收藏
                    <button 
                    @click="toggleFavorite"
                    class="heart-button">
                        <svg  v-if="isFavorited" class="favorite-red heart-icon" version="1.0" viewBox="0 0 24 24" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><path d="M16.4,4C14.6,4,13,4.9,12,6.3C11,4.9,9.4,4,7.6,4C4.5,4,2,6.5,2,9.6C2,14,12,22,12,22s10-8,10-12.4C22,6.5,19.5,4,16.4,4z"></path></svg>
                        <svg  v-else class="favorite-gray heart-icon" version="1.0" viewBox="0 0 24 24" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><path d="M16.4,4C14.6,4,13,4.9,12,6.3C11,4.9,9.4,4,7.6,4C4.5,4,2,6.5,2,9.6C2,14,12,22,12,22s10-8,10-12.4C22,6.5,19.5,4,16.4,4z"></path></svg>
                    </button>

                </div>
            </el-main>
        </el-container>
        <el-aside width="450px">
            <div>
                <div>
                    <TeacherCard></TeacherCard>
                </div>
                <div style="margin-top: 20px; border: 2px solid #d0d0d0; border-radius: 5px; ">
                    <div style="text-align: center; font-family: 'STCaiyun'; letter-spacing: 10px;">
                        <span style="color: #007bff; font-size: 20px; text-align: center;">课程列表</span>
                    </div>
                    <ul class="list-group">
                        <li 
                        v-for="(item, index) in course.mediaList" 
                        :key="index" 
                        class="list-group-item list-group-item-class" 
                        @click="handleItemClick(item)"
                        >
                        {{ item.title }}
                        </li>
                    </ul>
                </div>
            </div>
        </el-aside>
        </el-container>
    </div>
</template>
<script>
import videojs from 'video.js'
import instance from '../../request/request.js'
import TeacherCard from './TeacherCard.vue'
import {APP_VUE_MINIO_PATH} from '../../config/minio.js'
import operate from './operate.vue'
import Operate from './operate.vue'
export default{
    components:{
        operate
    },
    data() {
        return {
            contents: null,
            course: {
                courseId: null,
                courseName: null,
                previewImage: null,
                title: null,
                introduce: null,
                price: null,
                count: null,
                media: {
                    filePath: null,
                    fileType: null,
                },
                mediaList: [],
            },
            isFavorited: false,

        };
    }, created() {
        this.queryCourseById();
        this.collectionCode();
    },
     methods: {
        collectionCode(){
            instance({
                method:"get",
                url:"/api/user/collection/query/"+this.contents.id,
            }).then(res=>{
                const result = res.data.data
                console.log(result)
                if(result.length!=0){
                    this.isFavorited = true
                }
            }).catch(err=>{
                console.log(err)
            })
        },
        toggleFavorite() {
            this.isFavorited = !this.isFavorited; // 点击时切换收藏状态
            console.log(this.isFavorited)
            instance({
                method:"get",
                url:"/api/user/collection/course/"+this.contents.id,
                params:{
                    "code":this.isFavorited
                }
            }).then(res=>{
                console.log(res)
                if(res.data.code === 200||res.data.code ==='200'){
                    ElMessage({
                        message: res.data.data.message,
                        type: res.data.data.type,
                    })
                }
            }).catch(err=>{
                console.log(err)
                ElMessage.error(err.response.data.errMessage)
            })
        },
        queryCourseById() {
            this.contents = JSON.parse(localStorage.getItem("course_code"));
            instance({
                method: "get",
                url: "/api/course/course/query/" + this.contents.id
            }).then(res => {
                const result = res.data.data;
                this.course.media.filePath = APP_VUE_MINIO_PATH + result.media[0].fileName;
                this.course.mediaList = result.media;
                this.course.previewImage = result.previewImage;
                this.course.count = result.count;
                this.course.title = result.course.title;
                this.$nextTick(() => {
                    const player = videojs(this.$refs.videoPlayer);
                    player.src(this.course.media.filePath);
                    player.load();
                });
            }).catch(err => {
                console.log(err);
            });
        },
        handleItemClick(item) {
            this.course.media.filePath = APP_VUE_MINIO_PATH + item.fileName;
            this.$nextTick(() => {
                const player = videojs(this.$refs.videoPlayer);
                player.src(this.course.media.filePath);
                player.load();
            });
        }
    },
    components: { TeacherCard, Operate }
}
</script>
<style scoped>
.heart-button {
  padding: 0;
  border: none;
  background: transparent;
  cursor: pointer;
  outline: none;
}

.heart-icon {
  width: 24px;
  height: 24px;
  transition: 0.3s;
}

.favorite-red{
  fill: red;
}

.favorite-gray{
  fill: gray;
}
.video-container {
    position: relative;
    width: 100%; /* 或具体宽度，比如 800px */
    padding-bottom: 56.25%; /* 16:9 的纵横比，可根据需要调整 */
    height: 0; /* 让容器的高度由 padding-bottom 控制 */
    overflow: hidden;
}

.video-js {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 80%;
    object-fit: cover; /* 保持视频纵横比并填充整个容器 */
}
.list-group-item-class:hover{
    color:#30acff;
}
.video-main{
    padding-right: 120px;
}
.container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

.container {
  display: block;
  position: relative;
  cursor: pointer;
  user-select: none;
}

.container svg {
  position: relative;
  top: 0;
  left: -20px;
  height: 30px;
  width: 40px;
  transition: all 0.3s;
  fill: #666;
}

.container svg:hover {
  transform: scale(1.1);
}

.container input:checked ~ svg {
  fill: #E3474F;
}
</style>