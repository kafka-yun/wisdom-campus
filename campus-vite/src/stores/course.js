import { defineStore } from "pinia";
import {reactive,computed} from 'vue'
export const ContentStore=defineStore('course',()=>{

    const course = reactive({
        id:'',
        courseName:'',
        companyId:'',
    })
    const getContent = computed(()=>{
        return course;
    })
    function initContent (res){
        course.id = res.id,
        course.courseName = res.courseName,
        course.companyId = res.companyId
    }

    return {initContent,getContent}

},{
    persist:true
})