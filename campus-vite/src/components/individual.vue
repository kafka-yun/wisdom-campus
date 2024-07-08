<template>
    <section class="heading">
        <div class="profile">
            <!-- 头像 -->
            <div class="block">
                <el-form :model="user" label-position="left" label-width="80px" ref="user">
                    <el-upload
                        class="upload-demo"
                        ref="upload"
                        :on-change="uploadFile"
                        :auto-upload="false"
                        list-type="picture"
                        :show-file-list="true">
                        <el-avatar shape="square" :size="300" :src="salt" style="border-radius: 80px;" />
                        <template #tip>
                        <div class="el-upload__tip">
                            jpg/png files with a size less than 500KB.
                        </div>
                        </template>
                    </el-upload>
                </el-form>
            </div>
        </div>
        </section>
        <div class="wrapper">
        <section class="block info">
            <h2>个人信息</h2>
            <ul>
                <li>昵 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称: <span><el-input v-model="user.nickname" style="width: 240px" placeholder="昵称"/></span></li>
                <li>账 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号: <span><el-input v-model="user.username" style="width: 240px" disabled placeholder="Please input"/></span></li>
                <li>性 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别: 
                    <span>
                        <el-select
                        v-model="value"
                        :placeholder="user.sex === 0?'女':'男'"
                        size="large"
                        style="width: 240px"
                        >
                        <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        />
                        </el-select>
                    </span>
                </li>
                <li>电子邮箱: <span><el-input v-model="user.email" style="width: 190px" placeholder="邮箱"/></span><el-button type="primary" @click="sendEmailCode">发送</el-button></li>
                <li>验&nbsp;证&nbsp;码&nbsp;: <span><el-input v-model="code" style="width: 240px" placeholder="请输入验证码"/></span></li>
            </ul> 
            <el-button @click="submitForms('user')">修改</el-button>           
        </section>
        <section class="block info">
            <h2>更多操作</h2>
            <ul>
            <!-- <li class="pri-tag">密码修改</li> -->
            <li class="pri-tag" @click="collection">收藏列表</li>
            <li class="pri-tag" @click="entering">人脸录入</li>
            </ul>
        </section>
        </div>
</template>
<script>
import axios from 'axios'
import instance from '../request/request.js'
import { v4 as uuidv4 } from 'uuid';
// import axios from 'axios'
export default {
    data(){
        return{
            user:{},
            options:[
                {
                    value:1,
                    label:"男"
                },
                {
                    value:0,
                    label:"女"
                }
            ],
            code:null,
            salt:null,
            value:null,
            FileStatus:false,
            Xheader:{
              key:null,
              BusniessId:null,
              token:null
            }
        }
    },methods:{
        collection(){
          location.href = "#/collection"
        },
        entering(){
          instance({
            method:"get",
            url:"/api/user/face/code"
          }).then(res=>{
            const result = res.data.data
            if (result==='true'||result===true) {
              const uuid = uuidv4();
              const key = this.user.username+":entering";
              instance({
                method:"post",
                url:"/api/course/course/addLock",
                params:{
                  "key":key,
                  "BusniessId":uuid,
                }

              }).then(res=>{
                if(res.data.code===200||res.data.code ==='200'){
                  this.Xheader.key = key,
                  this.Xheader.BusniessId = uuid,
                  this.Xheader.token = res.data.data,
                  localStorage.setItem("X-Header",JSON.stringify(this.Xheader));
                }
              }).catch(err=>{
                ElMessage.error(err.response.data.errMessage)
              })
              location.href = '#/entering'
            }else{
              ElMessage({
                message: '人脸信息已存在无需重复录入',
                type: 'success',
              })
            }
          }).catch(err=>{
            console.log(err)
          })
          
        },
        initInfomatio(){  
            const resutl = JSON.parse(localStorage.getItem("UserInformation"))
            this.user = JSON.parse(resutl.user_name)
            this.salt = "http://117.72.35.172:9000/"+ this.user.salt
        },
        uploadFile(file,fileList) {
            console.log(file.raw)
            this.user.salt = file.raw
            this.FileStatus = true
        },
        submitForms(formName){
            this.$refs[formName].validate((valid)=>{
                const formdata = new window.FormData();
                formdata.append("image",this.user.salt)
                instance({
                    method:"post",
                    url:"/api/user/info/update/info/"+this.code,
                    params:{
                        "nickname":this.user.nickname,
                        "email":this.user.email,
                        "sex":this.value
                    },
                    data:formdata
                }).then(res=>{
                    console.log(res.data)
                    const data = res.data.data
                    this.user.email = data.email
                    this.user.nickname = data.nickname
                    var info = JSON.parse(localStorage.getItem("UserInformation"))
                    console.log("1")
                    console.log(info)
                    var userInfo = JSON.parse(info.user_name)
                    userInfo.nickname = data.nickname
                    userInfo.email = data.email
                    if(data.salt != null){
                      this.user.salt = data.salt
                      userInfo.salt = data.salt
                    }
                    console.log("2")
                    info.user_name = JSON.stringify(userInfo)
                    localStorage.setItem("UserInformation",JSON.stringify(info))
                    ElMessage({
                            message: '修改成功',
                            type: 'success',
                        })
                    this.$router.go(0)

                }).catch(err=>{
                    console.log(err)
                })
            })
        },
        sendEmailCode(){
            if (this.user.email){
                axios({
                    method:"get",
                    url:"/api/auth/registered/send/"+this.user.email
                }).then(res=>{
                    if (res.data.code === 200){
                        ElMessage({
                            message: '邮箱发送成功',
                            type: 'success',
                        })
                    }
                }).catch(err=>{
                    console.log(err)
                })
            }else{
                ElMessage({
                    message: '请输入邮箱号!',
                    type: 'warning',
                })
            }
        },
    },
    created(){
        this.initInfomatio()
    }
}

</script>

<style scoped>
@charset "UTF-8";
@import url(https://fonts.googleapis.com/earlyaccess/cwtexyen.css);
@import url(https://fonts.googleapis.com/css?family=Josefin+Sans);
@import url(https://fonts.googleapis.com/css?family=Source+Code+Pro:400,500);
@keyframes change-text {
  0%, 55%, 100% {
    transform: translateY(-45%);
  }
  5%, 50% {
    transform: translateY(-95%);
  }
}
@keyframes typing {
  0% {
    width: 0;
  }
}
@keyframes text-blink {
  50% {
    border-color: transparent;
  }
}
body {
  box-sizing: border-box;
  color: #13293D;
  font-family: "Josefin Sans", cwtexyen, sans-serif;
}

a {
  text-decoration: none;
  color: #1B98E0;
}
a:hover {
  color: #13293D;
}

.wrapper {
  margin: 5% 10%;
}

.heading {
  background-color: #E8F1F2;
  padding: 5%;
  text-align: center;
}
.heading h1 {
  margin: 0 auto;
  font-family: "Source Code Pro", monospace;
  font-size: 50px;
  font-weight: 500;
  animation: 8s typing steps(16, end), 0.5s text-blink step-end infinite;
  width: 9.6em;
  white-space: nowrap;
  overflow: hidden;
  border-right: 3px solid;
}
@media screen and (max-width: 550px) {
  .heading h1 {
    font-size: 30px;
  }
}

.sm-name {
  display: none;
  color: #006494;
}
@media screen and (max-width: 550px) {
  .sm-name {
    display: block;
  }
}

.name {
  margin-top: 10px;
  height: 52px;
  overflow: hidden;
}
@media screen and (max-width: 550px) {
  .name {
    display: none;
  }
}
.name ul {
  list-style: none;
  padding: 0;
  color: #006494;
  font-size: 50px;
  animation: 8s change-text linear infinite;
}
.name ul li {
  padding-top: 5px;
  line-height: 52px;
}

.social ul {
  list-style: none;
  padding-left: 0;
}
.social ul a {
  color: #13293D;
}
.social ul li {
  display: inline-block;
  margin: 0 5px;
}

.block h2::first-letter {
  font-size: 30px;
  color: #006494;
}
.block h2 {
  font-family: "Source Code Pro", monospace;
  border-bottom: 2px solid;
}
.block ul {
  list-style: none;
  padding-left: 0;
}


.research {
  color: #AD0000;
}

.pri-tag {
  display: inline-block;
  padding: 8px;
  background: #13293D;
  color: #E8F1F2;
  margin: 5px;
  border-radius: 5px;
}

.sec-tag {
  display: inline-block;
  padding: 8px;
  background: #E8F1F2;
  color: #13293D;
  margin: 5px;
  border-radius: 5px;
}

.about p::before {
  content: "    ";
}

.degree h3, .exp-item h3 {
  margin: 10px 0 5px 0;
  padding: 0;
}
.degree p, .exp-item p {
  margin: 0;
  padding: 0;
}

.exp-item ul {
  list-style: square;
  padding-left: 15px;
}
.exp-item ul li {
  padding-top: 5px;
}

.profile {
  background-size: 100%;
  border-radius: 50%;
  margin: 20px auto;
  width: 240px;
  height: 120px;
  display: inline-block;
}
</style>