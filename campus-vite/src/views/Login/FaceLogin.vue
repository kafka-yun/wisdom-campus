<template>
  <div class="camera_outer">
    
    <div class="video-camera-container">
      <video
        id="videoCamera"
        class="video-camera"
        :width="videoWidth"
        :height="videoHeight"
        autoplay
      ></video>
    </div>
    <canvas
      style="display: none"
      id="canvasCamera"
      :width="videoWidth"
      :height="videoHeight"
    ></canvas>
    <div v-if="imgSrc" class="img_bg_camera">
      <img :src="imgSrc" alt="" class="tx_img" />
    </div>
    <div class="container">
      <p>邮箱:</p><input type="text" v-model="email" placeholder="请输入邮箱" />
      <el-button @click="getCompetence()">打开摄像头</el-button>
      <el-button @click="stopNavigator()">关闭摄像头</el-button>
      <el-button @click="setImage()">登录</el-button>
    </div>
  </div>
</template>
  <script>
import axios from 'axios';
import {faceAuthenticate} from '@/request/auth.js'
import { reqs } from '../../request/public.js'
export default {
  data() {
    return {
      email:null,
      videoWidth: 300,
      videoHeight: 300,
      imgSrc: "",
      thisCancas: null,
      thisContext: null,
      thisVideo: null,
      T:{
          params:null,
          data:null,
          url:null,
        },
    };
  },
  mounted() {
    this.getCompetence();
  },
  methods: {
    // 调用权限（打开摄像头功能）
    getCompetence() {
      var _this = this;
      this.thisCancas = document.getElementById("canvasCamera");
      this.thisContext = this.thisCancas.getContext("2d");
      this.thisVideo = document.getElementById("videoCamera");
      // 旧版本浏览器可能根本不支持mediaDevices，我们首先设置一个空对象
      if (navigator.mediaDevices === undefined) {
        navigator.mediaDevices = {};
      }
      // 一些浏览器实现了部分mediaDevices，我们不能只分配一个对象
      // 使用getUserMedia，因为它会覆盖现有的属性。
      // 这里，如果缺少getUserMedia属性，就添加它。
      if (navigator.mediaDevices.getUserMedia === undefined) {
        navigator.mediaDevices.getUserMedia = function (constraints) {
          // 首先获取现存的getUserMedia(如果存在)
          var getUserMedia =
            navigator.webkitGetUserMedia ||
            navigator.mozGetUserMedia ||
            navigator.getUserMedia;
          // 有些浏览器不支持，会返回错误信息
          // 保持接口一致
          if (!getUserMedia) {
            return Promise.reject(
              new Error("getUserMedia is not implemented in this browser")
            );
          }
          // 否则，使用Promise将调用包装到旧的navigator.getUserMedia
          return new Promise(function (resolve, reject) {
            getUserMedia.call(navigator, constraints, resolve, reject);
          });
        };
      }
      var constraints = {
        audio: false,
        video: {
          width: this.videoWidth,
          height: this.videoHeight,
          transform: "scaleX(-1)",
        },
      };
      navigator.mediaDevices
        .getUserMedia(constraints)
        .then(function (stream) {
          // 旧的浏览器可能没有srcObject
          if ("srcObject" in _this.thisVideo) {
            _this.thisVideo.srcObject = stream;
          } else {
            // 避免在新的浏览器中使用它，因为它正在被弃用。
            _this.thisVideo.src = window.URL.createObjectURL(stream);
          }
          _this.thisVideo.onloadedmetadata = function (e) {
            _this.thisVideo.play();
          };
        })
        .catch((err) => {
          console.log(err);
        });
    },
    //  绘制图片（拍照功能）
    setImage() {
      var _this = this;
      // 点击，canvas画图
      _this.thisContext.drawImage(
        _this.thisVideo,
        0,
        0,
        _this.videoWidth,
        _this.videoHeight
      );
      // 获取图片base64链接
      var image = this.thisCancas.toDataURL("image/png");
      _this.imgSrc = image;
      console.log(this.imgSrc)
      this.$emit("refreshDataList", this.imgSrc);
      this.login(this.email,this.imgSrc)
    },
    // base64转文件
    dataURLtoFile(dataurl, filename) {
      var arr = dataurl.split(",");
      var mime = arr[0].match(/:(.*?);/)[1];
      var bstr = atob(arr[1]);
      var n = bstr.length;
      var u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File([u8arr], filename, { type: mime });
    },
    // 关闭摄像头
    stopNavigator() {
      this.thisVideo.srcObject.getTracks()[0].stop();
    },
    login(email,image){
      axios({
        method:"post",
        url:"/api/auth/face/contrast",
        data:{
          "image":image,
          "email":email
        }
      }).then(res=>{
        const result = res.data.data
        this.applyForToken(result)
      }).catch(err=>{
        const errMessage = err.response.data
        ElMessage.error(errMessage.errMessage)
      })
    },
    applyForToken(result){
      const authType = "face"
      faceAuthenticate(result,authType)
      .then(res=>{
        const resp_data = res.data
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
          console.log(err)
        })
      })
      .catch(err=>{
        console.log(err)
        const errMessage = err.response.data
        ElMessage.error(errMessage.error_description)
      })
    }
  },
};
</script>
  <style lang="scss" scoped>
  .container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 30vh; /* 设置容器高度为视口高度，以实现垂直居中 */
  }
  /* 为了让视频元素居中，我们可以使用flex布局 */
.video-camera-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 30em; /* 假设您希望在全屏高度内居中 */
}

/* 设置视频为圆形 */
.video-camera {
  max-width: 100%; /* 确保视频宽度不超过其容器 */
  max-height: 100%; /* 同样限制高度 */
  border-radius: 50%; /* 关键行：这将边角变为圆形，创建圆形效果 */
  overflow: hidden; /* 确保超出部分被隐藏，保持圆形 */
}
.camera_outer {
  position: relative;
  overflow: hidden;
  background-size: 100%;
  video,
  canvas,
  .tx_img {
    -moz-transform: scaleX(-1);
    -webkit-transform: scaleX(-1);
    -o-transform: scaleX(-1);
    transform: scaleX(-1);
  }
  .close-canvas{
    width: 0;
    height: 0;
  }
  .btn_camera {
    position: absolute;
    bottom: 4px;
    left: 0;
    right: 0;
    height: 50px;
    background-color: rgba(0, 0, 0, 0.3);
    line-height: 50px;
    text-align: center;
    color: #ffffff;
  }
  .bg_r_img {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    top: 0;
  }
  .img_bg_camera {
    position: absolute;
    right: 0;
    top: 0;
    img {
      width: 300px;
      height: 300px;
    }
    .img_btn_camera {
      position: absolute;
      bottom: 0;
      left: 0;
      right: 0;
      height: 50px;
      line-height: 50px;
      text-align: center;
      background-color: rgba(0, 0, 0, 0.3);
      color: #ffffff;
      .loding_img {
        width: 50px;
        height: 50px;
      }
    }
  }
}
</style>