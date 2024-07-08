import axios from 'axios'
export async function authenticate(user,authType){
    const response = await axios({                                                                              
        url:'/auth/auth/oauth/token?client_id=campus-auth&client_secret=campus-auth&grant_type=password&username={"username":"'+user.username+'","password":"'+user.password+'","authType":"'+authType+'"}',
        method:"post",
        headers:{
            'Content-Type': 'application/json',
        },

    });

    if(!response.code === 200){
        throw new Error("登录失败")
    }
    return response

}

export async function faceAuthenticate(user,authType){
    const response = await axios({                                                                              
        url:'/auth/auth/oauth/token?client_id=campus-auth&client_secret=campus-auth&grant_type=password&username={"username":"'+user.username+'","code":"'+user.code+'","authType":"'+authType+'"}',
        method:"post",
        headers:{
            'Content-Type': 'application/json',
        },

    });

    if(!response.code === 200){
        throw new Error("登录失败")
    }
    return response

}
