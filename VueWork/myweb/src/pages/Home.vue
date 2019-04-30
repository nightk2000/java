<template>
    <div class="flex-down">
        <div class="box-banner">
            banner
        </div>
        <div class="box-body">
            <div class="view-menu">
                <el-menu v-for="(menu,mindex) in menus" default-active="1" class="el-menu-vertical" @select="menuSelect" :default-openeds="defaultOpeneds">
                    <template v-if="menu.items.length">
                        <el-submenu :index="mindex+''">
                            <template slot="title">
                                <i :class="menu.icon"></i><span>{{menu.name}}</span>
                            </template>
                            <el-menu-item-group v-for="(smenu,sindex) in menu.items">
                                <el-menu-item :index="mindex+'-'+sindex">
                                    <router-link :to="smenu.path"><i :class="smenu.icon"></i><span>{{smenu.name}}</span></router-link>
                                </el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                    </template>
                    <template v-else>
                        <el-menu-item :index="mindex+''">
                            <router-link :to="menu.path"><i :class="menu.icon"></i><span>{{menu.name}}</span></router-link>
                        </el-menu-item>
                    </template>
                </el-menu>
            </div>
            <router-view class="view-body"/>
        </div>
        <div class="box-footer">
            &copy; Copyright
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return {
            msg: "Welcome to Your Vue.js App",
            menus:[
                {
                    path:'/survey',
                    name:'概览',
                    icon:'el-icon-data-analysis',
                    items:[]
                },
                {
                    path:'',
                    name:'管理',
                    icon:'el-icon-menu',
                    items:[
                        { path:'/user', name:'账户', icon:'' }
                    ]
                },
                {
                    path:'',
                    name:'设置',
                    icon:'el-icon-setting',
                    items:[
                        { path:'/account', name:'账户信息', icon:'' },
                        { path:'/security', name:'修改密码', icon:'' },
                        { path:'/login', name:'退出登录', icon:'' },
                    ]
                }
            ],
            defaultOpeneds:['1','2','3']
        }
    },
    computed: {
        
    },
    methods: {
        menuSelect: function(index,path){
            let menu = this.menus[path[0]];
            if( menu.items.length || path.length>1 ){
                menu = menu.items[ path[1].split('-')[1] ];
            }
            console.log('menu',menu);
        },
        
    }
}
</script>

<style>
/* align-items：垂直对齐方式， justify-content水平对齐方式  */
.box-body{display:flex; flex-direction:row; flex-wrap:wrap; align-items:top; justify-content:space-between;}
.box-body .view-menu{width:200px;height:100%;}
.box-body .view-body{flex:1;overflow-y:auto;}

a { color: #42b983; }

.el-submenu,.el-menu-item{text-align:left;}
.el-menu-item-group{background-color:#f0f0f0;}
</style>
