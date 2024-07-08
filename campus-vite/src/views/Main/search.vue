<template>
    <div class="common-layout">
        <el-container>
        <el-header>
            <!-- <div class="search-container">
                <div class="input-container">
                <input type="text" name="text" class="input" placeholder="请输入搜索关键词" @keydown.enter="onSearch" v-model="searchKeyword">
                <span class="icon"> 
                    <svg width="19px" height="19px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path opacity="1" d="M14 5H20" stroke="#000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path opacity="1" d="M14 8H17" stroke="#000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M21 11.5C21 16.75 16.75 21 11.5 21C6.25 21 2 16.75 2 11.5C2 6.25 6.25 2 11.5 2" stroke="#000" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"></path> <path opacity="1" d="M22 22L20 20" stroke="#000" stroke-width="3.5" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
                </span>
                <el-button style="height: 40px;" @click="onSearch">搜索</el-button>
                </div>
            </div> -->
            <Header @course="initSearch"></Header>
        </el-header>
        <el-main>
            <div style="margin-bottom: 20px; padding: 10px; background-color: #f0f0f0; border-radius: 10px; text-align: center; font-family: 'STCaiyun'; letter-spacing: 10px;">
                <span style="color: #007bff; font-size: 20px;"></span>
            </div>
            <div class='containers'> 
              <div class='cards' v-for="(t,index) in content" :key="index" @click="handle(t)"  >
                    <div class='cards-content'>
                        <div class='top-bar'>
                        <span>
                            {{ t.price }} ￥
                        </span>
                        <span class='float-right lnr lnr-heart'></span>
                        </div>
                        <div class='img'>
                        <img :src='t.previewImage'>
                        </div>
                    </div>
                    <div class='cards-description'>
                        <div class='title'>
                        {{ t.title }}
                        </div>
                        <div class='cart'>
                          |
                        <span class='lnr lnr-cart'>{{ t.courseName }}</span>
                        </div>
                    </div>
                    <div class='cards-footer'>
                        <div class='span'>
                        RED
                        </div>
                        <div class='span'>
                        BEATS
                        </div>
                        <div class='span'>
                        HEADPHONE
                        </div>
                    </div>
              </div>
            </div>
        </el-main>
        </el-container>
    </div>
</template>
<script>
import instance from '../../request/request.js'
import Header from '../../components/Menu/Header.vue'
import Events from '../../config/Events.js'
export default{
    components:{
      Header
    },
    data(){
        return{
            searchKeyword:null,
            content:[],
        }
    },
    methods:{
        onSearch(){
          instance({
          method:"post",
          url:"/api/course/course/search",
          params:{
            "search":this.searchKeyword
          }
        }).then(res=>{
          console.log(res)
          this.content = res.data
        }).catch(err=>{
          console.log(err)
        })
        },
        initSearchCourse(){
            const search =  JSON.parse(localStorage.getItem("search_course"))
            this.content = search
        },
        handle(t){
            localStorage.setItem("course_code",JSON.stringify(t))
            this.$router.push("/course")
        },
        initSearch(data){
          this.content = data
        }
    },
    created(){
        this.initSearchCourse();
        Events.on("course",this.initSearch)
    },unmounted(){
      Events.off("course",this.initSearch)
    }
}
</script>
<style scoped>
.search-container {
  display: flex;
  justify-content: center;
}
.input-container {
  width: 220px;
  position: relative;
}

.icon {
  position: absolute;
  right: 10px;
  top: calc(50% + 5px);
  transform: translateY(calc(-50% - 5px));
}

.input {
  width: 100%;
  height: 40px;
  padding: 10px;
  transition: .2s linear;
  border: 2.5px solid rgb(165, 164, 164);
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 2px;
}

.input:focus {
  outline: none;
  border: 0.5px solid rgb(172, 172, 172);
  box-shadow: -5px -5px 0px rgb(160, 160, 160);
}

.input-container:hover > .icon {
  animation: anim 1s linear infinite;
}

@keyframes anim {
  0%,
  100% {
    transform: translateY(calc(-50% - 5px)) scale(1);
  }

  50% {
    transform: translateY(calc(-50% - 5px)) scale(1.1);
  }
}







.containers {
  padding-top: 20px;
  min-height: 90vh;
  display: grid;
  grid-auto-rows: 400px;
  grid-gap: 30px;
  grid-template-columns: repeat(5, minmax(200px, 300px));
  justify-content: center;
  align-items: stretch;
}
@media screen and (max-width: 720px) {
  .containers {
    grid-template-columns: 1fr;
    margin: 50px 30px;
  }
}

.cards {
  position: relative;
  padding: 10px;
  background: white;
  display: grid;
  grid-template-rows: 8fr 1fr 1fr;
  box-shadow: 0px 15px 20px 0px rgba(0, 0, 0, 0.057);
  transition: 0.2s ease-in;
}
.cards .cards-content {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}
.cards .cards-content .top-bar {
  width: calc(100% - 40px);
  position: absolute;
  top: 0;
  left: 0;
  padding: 20px;
}
.cards .cards-content .top-bar .float-right {
  float: right;
}
.cards .cards-content .img {
  width: 100%;
  justify-content: bottom;
  align-content: bottom;
  text-align: center;
}
.cards .cards-content .img img {
  max-width: 100%;
  max-height: 220px;
}
.cards .cards-description {
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 300;
  font-size: 14px;
  letter-spacing: 0.1em;
  /* width: calc(100%-20px); */
}
.cards .cards-description .title {
  text-transform: uppercase;
  text-align: left;
}
.cards .cards-description .cart {
  float: right;
  cursor: pointer;
}
.cards .cards-footer {
  text-transform: uppercase;
  padding: 0.3em;
  border-top: 0.5px solid #ececec;
  letter-spacing: 0.1em;
  font-size: 11px;
  color: white;
  justify-items: left;
  align-items: center;
  display: flex;
}
.cards .cards-footer .span {
  margin: 5px;
  width: auto;
  background: #dedede;
  padding: 4px 7px;
  border-radius: 2px;
  font-weight: 100;
  cursor: pointer;
}
.cards .cards-footer .span:hover {
  background: #aaaaaa;
}
.cards:hover {
  transition: 0.2s ease-in;
  transform: translateY(-10px);
  box-shadow: 0px 45px 60px 0px rgba(0, 0, 0, 0.087);
}
</style>