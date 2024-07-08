<template>
    <el-button type="primary" style="margin-left: 16px" @click="drawer = true">
      小助手
    </el-button>
  
    <el-drawer v-model="drawer" title="I am the title" :with-header="false">
      <span>智慧学堂</span>
      <div>
          <CartIndex></CartIndex>
      </div>
    </el-drawer>
</template>

<script  setup>
import { ref } from 'vue'
import CartIndex from './CartIndex.vue'
const drawer = ref(false)
</script>
<style scoped>

</style>