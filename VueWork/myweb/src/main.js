// @代表assets目录，.代表当前目录
import Vue from 'vue'
import App from './App.vue'

import routers from './common/router.js'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI);

import VueRouter from 'vue-router'
Vue.use(VueRouter)

const router = new VueRouter({
    routes: routers
});

router.beforeEach((to, from, next) => { // 菜单跳转前判断登录状态
    //console.log('to.path')
    let user = JSON.parse(sessionStorage.getItem('login.session'));
    if (user == null) {
        if (to.path == '/login' ) {
            sessionStorage.clear();
            next();
        } else {
            console.log("user", user);
            next('/login');  // 强制跳转到登录
        }
    } else {
        if( to.path=='/' ){
            next('/home');
        }else{
            next();
        }
    }
});

const app = new Vue({
    el: '#app',
    render: h => h(App),
    router: router
});


