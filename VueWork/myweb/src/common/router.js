
import Login from '../pages/Login.vue';

export default [
    {
        path: '/login',
        component: Login
    },
    // path：访问地址，      component：.vue页面
    //{ path:'/home', component: ()=>import('../pages/Home.vue') }
    {
        path: '/home',
        component: resolve => require(['../pages/Home.vue'], resolve),
        children:[  // 子视图路径，放置于 children 中
            {
                path: '/survey',
                component: resolve => require(['../pages/Survey.vue'], resolve)
            },
            {
                path: '/user',
                component: resolve => require(['../pages/Survey.vue'], resolve)
            },
            {
                path: '/account',
                component: resolve => require(['../pages/Survey.vue'], resolve)
            },
            {
                path: '/security',
                component: resolve => require(['../pages/Survey.vue'], resolve)
            }
        ]
    },


]