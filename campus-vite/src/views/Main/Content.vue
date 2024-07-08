<template>
    <div>
        <div>
          <div style="margin-bottom: 20px; padding: 10px; background-color: #f0f0f0; border-radius: 10px; text-align: center; font-family: 'STCaiyun'; letter-spacing: 10px;">
              <span style="color: #007bff; font-size: 20px;">其他课程</span>
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
        </div>

    </div>
</template>
<script>
import instance from '../../request/request.js'
export default{
  data(){
    return{
      content:[],
      course:{
        courseId:"1"
      }
    }
  },created(){
    this.getContent()
  },methods:{
    getContent(){
      instance({
        method:"get",
        url:'/api/course/course/content',
      }).then(res=>{
        this.content = res.data
      }).catch(err=>{
        console.log(err)
        ElMessage.error(err.response.data.error)
      })
    },
    handle(t){
      localStorage.setItem("course_code",JSON.stringify(t))
      this.$router.push("/course")
    }
  }
}
</script>
<style scoped>

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