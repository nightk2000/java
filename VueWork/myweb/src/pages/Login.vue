<template>
    <div class="flex-down">
        <div class="box-banner">
            Loginaaa<br/>Loginaaa<br/>Loginaaa<br/>Loginaaa<br/>Loginaaa<br/>Loginaaa<br/>
        </div>
        <div class="box-body">
            <div class="login">
                <div class="title">登录</div>
                <div>
                    <el-input placeholder="请输入内容" v-model="user.username" clearable></el-input>
                    <el-input placeholder="请输入密码" v-model="user.password" show-password></el-input>
                </div>
                <el-button type="primary" @click="onSubmit">立即创建</el-button>
            </div>
        </div>
        <div class="box-footer">
            &copy; Copyright
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            user:{
                username:'',
                password:''
            }
        }
    },
    computed: {
        
    },
    methods: {
        login: function(){
            sessionStorage.setItem('login.session',new Date().getTime());
            this.$router.replace({path:'/home'});
        },
        onSubmit: function(){
            this.login();
        }
    }
}

//string sessionStorage.key(int index) ：返回当前 sessionStorage 对象的第index序号的key名称。若没有返回null。
//string sessionStorage.getItem(string key) ：返回键名(key)对应的值(value)。若没有返回null。
//void   sessionStorage.setItem(string key, string value) ：该方法接受一个键名(key)和值(value)作为参数，将键值对添加到存储中；如果键名存在，则更新其对应的值。
//void   sessionStorage.removeItem(string key) ：将指定的键名(key)从 sessionStorage 对象中移除。
//void   sessionStorage.clear() ：清除 sessionStorage 对象所有的项。
</script>

<style>
.box-body{display:flex;flex-direction:column;flex-wrap:wrap; align-items:center; justify-content:space-between;}

.login{width:400px;height:260px;box-shadow:0px 0px 3px #cccccc;margin-top:10vh;}
.login .title{text-align:center;padding:40px 0px 30px;}
.login .el-input{width:80%;margin:0px auto;display:block;}
</style>
