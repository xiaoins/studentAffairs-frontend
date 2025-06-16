<template>
  <!-- 
    <router-view/> 是一个特殊的Vue组件，由vue-router提供。
    它的作用是作为一个占位符，用来动态地渲染当前URL路径所匹配的组件。
    例如，当用户访问 '/login' 路径时，<router-view/> 就会渲染 LoginPage.vue 组件。
    当用户访问 '/dashboard' 路径时，它就会渲染 DashboardView.vue 组件。
    这样就实现了单页应用（SPA）的页面切换效果，整个应用只有一个HTML页面，内容根据URL动态更新。
  -->
  <a-config-provider :locale="zhCN">
  <router-view />
  </a-config-provider>
</template>

<script setup>
import { ConfigProvider } from 'ant-design-vue';
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';

dayjs.locale('zh-cn');
</script>

<style>
/* 
  这部分是<style>标签，用于定义组件的CSS样式。
  这里的样式是全局的，会影响到整个应用程序的所有组件。
  通常用于定义一些基础的、通用的样式。
  例如，设置body的边距、字体等。
*/
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
