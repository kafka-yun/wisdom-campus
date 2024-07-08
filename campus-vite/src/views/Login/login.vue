<template>
    <section class="w3l-workinghny-form">
      <div class="workinghny-form-grid">
        <div class="wrapper">
          <div class="logo">
            <h1><a class="brand-logo" href="/">登录</a></h1>
          </div>
          <div class="workinghny-block-grid">
            <div class="workinghny-left-img align-end">
              <img src="@/assets/2.png" alt="img" class="img-responsive" />
            </div>
            <div class="form-right-inf">
              <div class="login-form-content">
                <h2>Where to?</h2>
                <form action="#" @submit.prevent="onSubmit" :rules="rules" ref="loginFormRef" class="signin-form">
                  <div class="one-frm">
                    <label>用户名</label>
                    <input type="text" v-model="user.username" placeholder="" required />
                  </div>
                  <div class="one-frm">
                    <label>密码</label>
                    <input type="password" v-model="user.password" placeholder="" required />
                  </div>
                  <label class="check-remaind">
                    <input type="checkbox" v-model="rememberMe"/>
                    
                    <span class="checkmark"></span>
                    <p class="remember">已阅读并同意尚学平台 <a href="https://identity.tencent.com/agreement">《用户协议》</a><a href="https://www.wechat.com/zh_CN/privacy_policy.html">《隐私政策》</a>，允许尚学统一管理本人账号信息</p>
                  </label>
                  <button class="btn btn-style mt-3" @click="toLogin">登 录</button>
                  <p>其他方式登录:</p>
                  <div class="login-container">
                      <el-button @click="toFaceLogin">人脸登录</el-button>
                      <el-button>微信</el-button>
                  </div>
                  <p class="already">没有该平台用户 <router-link to="/Enroll">立即注册</router-link></p>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </template>
  
<script>
import {authenticate} from '@/request/auth.js'
import { reqs } from '../../request/public.js'
import { ElMessage } from 'element-plus'
export default{
    data(){
        return{
            user:{
                username:"",
                password:""
            },
            T:{
              params:null,
              data:null,
              url:null,
            },
            rememberMe:false,
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
            }
        }
    },methods:{
        toLogin(){
            if(this.rememberMe){
                const authType = "password"
                authenticate(this.user,authType)
                .then(res=>{
                    const resp_data = res.data
                    // init.initToken(resp_data)
                    // console.log('string')
                    // console.log(JSON.stringify(resp_data))
                    localStorage.setItem("Token",JSON.stringify(resp_data))
                    ElMessage({
                        message: '登录成功',
                        type: 'success',
                    })
                    const result = JSON.parse(localStorage.getItem("Token"))
                    this.T.params = {
                      token:result.access_token
                    }
                    this.T.url = "/auth/auth/oauth/check_token"
                    this.T.data = "get"
                    reqs(this.T).then(res=>{
                      localStorage.setItem("UserInformation",JSON.stringify(res.data))
                      this.$router.push("/")
                    }).catch(err=>{
                      ElMessage.error("初始化失败");
                    })
                })
                .catch(err=>{
                    const errMessage = err.response.data
                    ElMessage.error(errMessage.error_description)
                })
            }else{
                ElMessage.warning("请勾选同意条款")
            }
      },
      toFaceLogin(){
        this.$router.push("/face")
      }
    }
}
</script>
  
<style scoped>
@import '@/css/style.css';
</style>