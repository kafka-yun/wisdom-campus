import axios from 'axios';
// 创建一个 axios 实例
const instance = axios.create({
  timeout: 10000, // 设置请求超时时间
});

// 添加请求拦截器
instance.interceptors.request.use(
  config => {
    // 从本地存储或其他地方获取 token
    const token = JSON.parse(localStorage.getItem("Token"))
    // 如果有 token，则将其添加到请求头中
    if (token) {
      config.headers.Authorization = 'bearer '+token.access_token; // 修改这里
    }

    return config;
  },
  error => {
    // 处理请求错误
    return Promise.reject(error);
  }
);

// 添加响应拦截器
instance.interceptors.response.use(
  response => {
    // 处理响应数据
    return response;
  },
  error => {
    const status = error.response.status;
    if (status){
      switch (status){
        case 401:
          location.href = "#/login"
          ElMessage.error("请登录")
          break;
        case 403:
          ElMessage.error("权限不足,请联系管理员升级")
          break;
        case 404:
          ElMessage.error("系统错误")
          location.href = "#/"
          break;
        default:
          ElMessage.error(error.response.data.message)
      }

    }
    // 处理响应错误
    return Promise.reject(error);
  }
);

export default instance;