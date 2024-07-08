import { createRouter,createWebHashHistory } from "vue-router";

const routes = [

    {
        path:"/login",
        name:"login",
        component:()=>import("@/views/Login/login.vue")
    },{
        path:"/",
        name:"index",
        component:()=>import("@/components/index.vue"),
        meta:{requiresAuth:true},
        children:[
            {
                path:'/',
                name:'main',
                component:()=>import("@/views/Main/Main.vue")
            },{
                path:"/manage",
                component:()=>import("@/views/manage/index.vue"),
                meta:{requiresAuth:true},
                children:[
                    {
                        path:'',
                        component:()=>import("@/views/manage/main.vue")
                    }
                ]
            },{
                path:"/class",
                component:()=>import("@/views/class/class.vue"),
                meta:{requiresAuth:true},
                children:[
                    {
                        path:'',
                        component:()=>import("@/views/class/main.vue")
                    }
                ]
            }
        ]
    },{
        path:"/face",
        name:"face",
        component:()=>import("@/views/Login/FaceLogin.vue")
    },{
        path:"/Enroll",
        component:()=>import("@/views/Login/Registered.vue")
    },{
        path:"/entering",
        component:()=>import("@/components/FaceEntering/FaceEntering.vue")
    },{
        path:"/course",
        component:()=>import("@/views/Main/Course.vue")
    },{
        path:"/indiv",
        component:()=>import("@/components/individual.vue")
    },{
        path:"/courseGroup",
        component:()=>import("@/views/Main/CourseGroup.vue")
    },{
        path:"/search",
        component:()=>import("@/views/Main/search.vue")
    }
    ,{
        path:"/collection",
        component:()=>import("@/components/Collection.vue")
    }
    ,{
        path:"/:path(.*)",
        name:"404",
        component:()=>import("@/components/404.vue")
    }

]

const router = createRouter({
    history:createWebHashHistory(),
    routes
})

router.beforeEach(async(to,from,next)=>{
    const token = JSON.parse(localStorage.getItem("Token"));
    if(to.meta.requiresAuth && !(await !(token===null|token===undefined|token===''))){
        return next({name:"login"})
    }
    next();
});

export default router;