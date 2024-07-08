<template>
    
    <div>
        <div v-for="(item,index) in labelList" :key="index">
            <div v-for="(items,indexs) in item.categoryList" :key="indexs">
                <span class="label_style">{{ items.label }}</span>
                <span  class="item_style" v-for="(v,k) in items.categoryList" key="k" @click="queryCourse(v)">
                    {{ v.label }}  <span v-if="k + 1 !== items.categoryList.length">&ensp;&ensp;|&ensp;&ensp;</span>
                </span>
            </div>
        </div>
    </div>

</template>
<script>
import instance from '../../request/request.js'
export default{
    data(){
        return{
            labelList:[]
        }
    },
    methods:{
        initLabel(){
            instance({
                method:"get",
                url:"/api/course/category/label"
            }).then(res=>{
                this.labelList = res.data
            }).catch(err=>{
                console.log(err)
            })
        },
        queryCourse(item){
            localStorage.setItem("label_title",item.label)
            localStorage.setItem("label_course",JSON.stringify(item.categoryList))
            location.href = '#/courseGroup'
        }
    },
    created(){
        this.initLabel();
    }
}
</script>
<style scoped>
.label_style{
    margin-right: 8px;
    font-size: 16px;
    font-weight: 600;
    color: rgb(51, 51, 51);
}
.item_style{
    line-height: 17px;
    font-size: 12px;
    font-weight: 400;
    color: #7c7c83;

}
.item_style:hover{
    color:#79bbff
}
</style>