<template>
    <div id="app">
        <router-view/>
    </div>
</template>

<script>
export default {
    name: "app",
    data() {
        return {
        };
    }
};
</script>

<style>
html,body{margin:0px; min-width:800px;min-height:600px;}
#app {width:100vw; height:100vh;}
*{font-family:'Microsoft Yahei','Consolas';box-sizing:border-box;}

.flex-down{ display:flex; flex-direction:column; height:100vh;}

.box-banner{height:60px;line-height:60px;border-bottom:1px solid #e0e0e0;}
.box-body{flex-grow:1;}
.box-footer{height:30px;line-height:30px;}
</style>
