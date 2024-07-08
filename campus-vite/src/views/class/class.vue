<template>
    <div class="common-layout">
        <el-container>
            <el-aside width="200px" class="glass-effect">
                <div>
                    班级(别看了还没做)
                </div>
            </el-aside>
            <el-main>
                <div>
                    <router-view></router-view>
                </div>
            </el-main>
        </el-container>
    </div>
   
</template>
<script setup>
</script>
<style scoped>
.glass-effect {
  backdrop-filter: blur(10px); /* 毛玻璃效果 */
  -webkit-backdrop-filter: blur(10px); /* 对于Safari的支持 */
  background-color: rgba(255, 255, 255, 0.1); /* 背景透明度调整以适应毛玻璃效果 */
}
</style>
