<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <div class="glass-effect">
          <Header></Header>
        </div>
      </el-header>
      <el-main>
        <div>
            <router-view></router-view>
        </div>
        <Cart></Cart>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import Header from '@/components/Menu/Header.vue'
import Cart from './Cart.vue';
</script>

<style>
.glass-effect {
  backdrop-filter: blur(10px); /* 毛玻璃效果 */
  -webkit-backdrop-filter: blur(10px); /* 对于Safari的支持 */
  background-color: rgba(255, 255, 255, 0.1); /* 背景透明度调整以适应毛玻璃效果 */
}
</style>