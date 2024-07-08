<template>
  <header>
    <div class="header-container">
      <div class="header-item" style="text-align: left;">
        <h1><span style="color: #007bff;">智慧教育先锋</span></h1>
      </div>
      <div class="search-container">
        <div class="input-container">
          <input type="text" name="text" class="input" placeholder="请输入搜索关键词" @keydown.enter="onSearch" v-model="searchKeyword">
          <span class="icon"> 
            <svg width="19px" height="19px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path opacity="1" d="M14 5H20" stroke="#000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path opacity="1" d="M14 8H17" stroke="#000" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"></path> <path d="M21 11.5C21 16.75 16.75 21 11.5 21C6.25 21 2 16.75 2 11.5C2 6.25 6.25 2 11.5 2" stroke="#000" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"></path> <path opacity="1" d="M22 22L20 20" stroke="#000" stroke-width="3.5" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
          </span>
        </div>
        <el-button style="height: 40px;" @click="onSearch">搜索</el-button>
      </div>
      <div class="header-item">
        <nav>
          <ul style="margin-bottom: 0px;">
            <li><a class="nav-link" href="#">首页</a></li>
            <li><a class="nav-link" @click="toAdmin">班级</a></li>
            <li><a class="nav-link" @click="toSZDP">管理</a></li>
          </ul>
        </nav>
      </div>
      <div>
      <div v-if="!isLogin">
        <a class="nav-link" href="#login"  >登录/注册 <span class="sr-only">(current)</span></a>
      </div>
        <div class="header-avater" v-else >               
            <el-dropdown>
                <span class="el-dropdown-link">
                    <el-avatar shape="square" :src="user.avatar"/>
                <el-icon class="el-icon--right">
                    <arrow-down />
                </el-icon>
                </span>
                <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item @click="toIndiv">个人中心</el-dropdown-item>
                    <el-dropdown-item @click="toLogin">切换用户</el-dropdown-item>
                    <el-dropdown-item @click="toLogout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { ref, onMounted ,defineEmits} from 'vue';
import instance from '../../request/request.js'
import Events from '../../config/Events.js'
export default{
  data(){
    return{
      code:true,
      user:{
        avatar:null,
      }
    }
  },
  created(){
    this.userInit()
  },
  methods:{
    toLogin(){
      this.$router.push("/login")
    },
    toSZDP(){
      location.href = "#/manage"
    }
    ,toAdmin(){
      location.href = "#/class"
    },
    toLogout(){
      // localStorage.removeItem("Token")
      localStorage.clear();
      this.$router.push("/login")
    },
    userInit(){
      const user = JSON.parse(JSON.parse(localStorage.getItem("UserInformation")).user_name)
      this.user.avatar = "http://117.72.35.172:9000/"+user.salt
    },toIndiv(){
      this.$router.push("/indiv")
    }
  },
  setup(){
    let isLogin = ref(false);
    const checkLoginStatus = async ()=>{
      const result = JSON.parse(localStorage.getItem("Token"))
      const token = result.access_token;
      isLogin.value = !!token;
    }
    onMounted(()=>{
      checkLoginStatus();
    });
    const searchKeyword = ref('');
    const someArray = ref([])
    // 定义搜索处理函数
    const onSearch = () => {
      instance({
        method:"post",
        url:"/api/course/course/search",
        params:{
          "search":searchKeyword.value
        }
      }).then(res=>{
        someArray.value = res.data
        Events.emit("course",someArray.value)
        localStorage.setItem("search_course",JSON.stringify(res.data))
        location.href = "#/search"
      }).catch(err=>{
        console.log(err)
      })
      
    };
    return {isLogin,onSearch,searchKeyword};
  }
}
</script>

<style scoped>
.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px;
  background-color: #f0f0f0;
}

.header-item {
  flex: 1;
  text-align: center;
  padding-left: 5px;
}

.header-item h1 {
  font-size: 24px;
  font-weight: bold;
}

.header-item nav ul {
  list-style-type: none;
  padding: 0;
}

.header-item nav ul li {
  display: inline-block;
  margin: 0 10px;
}

.header-item input[type="text"] {
  margin-right: 10px;
}

.header-item img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

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
  /* text-transform: uppercase; */
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
</style>