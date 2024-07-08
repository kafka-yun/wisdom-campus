<script setup>
// import login from './components/newLogin.vue';
</script>

<template>

    <router-view></router-view>
    <!-- <login></login> -->
    
</template>

<style scoped>
@charset "UTF-8";
/*初始化*/
blockquote, body, button, dd, dl, dt, fieldset, form, h1, h2, h3, h4, h5, h6, hr, input, legend, li, ol, p, pre, td, textarea, th, ul {
  margin: 0;
  padding: 0;
  border: 0;
  font-weight: normal;
  vertical-align: baseline;
}
​
html {
  width:100%;
  height: 100%;
}
​
body {
  width:100%;
  height: 100%;
  font-family: Microsoft YaHei, tahoma, arial, Hiragino Sans GB, \\5b8b\4f53, sans-serif;
}
​
/*清除列表的默认风格*/
ul, ol, dl {
  list-style: none;
}
​
/*表单相关的元素*/
fieldset, img, input, button, textarea{
  border: none;
  margin: 0;
  padding: 0;
  /*去除外边线*/
  outline: none;
}
/*超链接标签*/
a{
  color: #999;text-decoration: none;
}
a:hover{
  color: #f8b62b;
}
/*h家族*/
h1,h2,h3,h4,h5,h6{
  font-weight: normal;
  font-size: 100%;
}
/*浮动*/
.fl{
  float: left;
}
​
.fr{
  float: right;
}
/*清除浮动*/
.clearfix::before,
.clearfix::after{
  content: '';
  display: table;
  clear: both;
}
​
/*水平对齐方式*/
.tl{
  text-align: left;
}
​
.tr{
  text-align: right;
}
​
.tc{
  text-align: center;
}
​
/*页面通用颜色*/
.f60{
  color: #f8b62b;
}
​
/*清除语义化标签的默认样式*/
s, i{
  font-style: normal;
  text-decoration: none;
}
</style>
