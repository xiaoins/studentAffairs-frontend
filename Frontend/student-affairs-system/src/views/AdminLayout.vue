<template>
  <!-- 
    Ant Design Vue的<a-layout>组件，用于构建整体页面布局。
    它通常包含Header, Sider, Content, Footer等部分。
    这里的:has-sider="true"是一个优化，告诉布局组件它内部有一个Sider，可以避免闪烁。
  -->
  <a-layout style="min-height: 100vh">
    <!-- 
      Sider是侧边栏组件。
      v-model:collapsed - 双向绑定侧边栏的折叠状态。
      :collapsible="true" - 使侧边栏可以被用户点击折叠。
    -->
    <a-layout-sider v-model:collapsed="collapsed" :collapsible="true">
      <!-- Logo区域 -->
      <div class="logo">
        <img src="../assets/logo.png" alt="Logo" />
        <!-- 当侧边栏未折叠时，显示系统名称 -->
        <h1 v-if="!collapsed">学工系统</h1>
      </div>
      <!-- 
        导航菜单。
        theme="dark" - 设置菜单为暗色主题。
        v-model:selectedKeys - 双向绑定当前选中的菜单项。
        mode="inline" - 设置菜单为内联模式。
      -->
      <a-menu theme="dark" v-model:selectedKeys="selectedKeys" mode="inline">
        <!-- 
          使用v-for指令遍历menuItems数组，动态生成菜单项。
          :key="item.key"是必须的，用于Vue的列表渲染优化。
        -->
        <a-menu-item v-for="item in menuItems" :key="item.key" @click="handleMenuClick(item)">
          <!-- 菜单项图标 -->
          <component :is="item.icon" />
          <!-- 菜单项文本 -->
          <span>{{ item.title }}</span>
        </a-menu-item>
      </a-menu>

      <!-- 占位符，将退出按钮推到底部 -->
      <div style="flex-grow: 1;"></div>

      <!-- 退出登录按钮 -->
      <a-menu theme="dark" mode="inline" :selectable="false">
        <a-menu-item key="logout" @click="handleLogout">
          <logout-outlined />
          <span>退出系统</span>
        </a-menu-item>
      </a-menu>

    </a-layout-sider>

    <!-- 右侧内容区域布局 -->
    <a-layout>
      <!-- 
        右侧头部区域。
        通常用于放置用户信息、通知、设置等。
        这里的padding: 0是为了移除默认内边距，background: '#fff'设置白色背景。
      -->
      <a-layout-header style="background: #fff; padding: 0">
        <!-- 这里可以添加头部的其他内容，例如用户头像、退出按钮等 -->
      </a-layout-header>
      <!-- 
        主要内容区域。
        这里的:style设置了内边距和最小高度。
      -->
      <a-layout-content :style="{ margin: '24px 16px', padding: '24px', background: '#fff', minHeight: '280px' }">
        <!-- 
          <router-view>是嵌套路由的出口。
          当用户点击侧边栏导航时，URL会改变，
          匹配到的子路由组件（如Dashboard, StudentInfo等）会在这里被渲染。
        -->
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup>
// 从 'vue' 和 'vue-router' 中导入所需的函数
import { ref, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
// 导入所有需要用到的Ant Design图标
import {
  DesktopOutlined,
  UserOutlined,
  ReadOutlined,
  ScheduleOutlined,
  TrophyOutlined,
  SettingOutlined,
  LogoutOutlined,
} from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';

// --- 状态定义 ---

// 定义侧边栏是否折叠的响应式状态，默认为false（不折叠）
const collapsed = ref(false);
// 定义当前选中的菜单项的key，默认为'1'（工作台）
const selectedKeys = ref(['1']);

// --- 路由和导航 ---

// 获取Vue Router的实例，用于编程式导航
const router = useRouter();
// 获取当前激活的路由对象，可以从中获取路径、参数等信息
const route = useRoute();

// --- 菜单定义 ---

// 定义侧边栏的菜单项数组
const menuItems = [
  { key: '1', title: '工作台', icon: DesktopOutlined, path: '/admin/dashboard' },
  { key: '2', title: '学生管理', icon: UserOutlined, path: '/admin/students' },
  { key: '3', title: '课程安排', icon: ScheduleOutlined, path: '/admin/courses' },
  { key: '4', title: '成绩查询', icon: ReadOutlined, path: '/admin/grades' },
  { key: '5', title: '活动管理', icon: TrophyOutlined, path: '/admin/activities' },
  { key: '6', title: '系统设置', icon: SettingOutlined, path: '/admin/settings' },
];

// --- 逻辑处理 ---

/**
 * 菜单项点击事件处理函数。
 * @param {object} item - 被点击的菜单项对象，包含key, path等信息。
 */
const handleMenuClick = (item) => {
  // 使用router.push()方法跳转到对应的路径
  router.push(item.path);
};

/**
 * 退出登录处理函数
 */
const handleLogout = () => {
  // 实际项目中，这里通常会清除token或session
  // localStorage.removeItem('token'); 
  message.success('您已成功退出，即将返回首页。', 2);
  // 跳转回首页
  router.push('/');
};

/**
 * 将路由路径映射到菜单key的函数。
 * @param {string} path - 当前的URL路径。
 * @returns {string|null} 匹配到的菜单key，如果没找到则返回null。
 */
const getKeyFromPath = (path) => {
  // 查找menuItems数组中路径匹配的项
  const menuItem = menuItems.find(item => item.path === path);
  // 如果找到了，返回它的key，否则返回null
  return menuItem ? menuItem.key : null;
};

// --- 生命周期和侦听器 ---

// 使用watch来侦听路由对象的变化
watch(
  route,
  (newRoute) => {
    // 当路由发生变化时（例如，用户点击浏览器前进后退按钮）
    // 从新的路径中获取对应的菜单key
    const key = getKeyFromPath(newRoute.path);
    if (key) {
      // 如果找到了key，就更新selectedKeys，让侧边栏菜单高亮显示正确的项
      selectedKeys.value = [key];
    }
  },
  { 
    // immediate: true 选项确保在组件加载后立即执行一次watch回调
    // 这样可以保证在刷新页面时，侧边栏能正确高亮当前页面的菜单项
    immediate: true 
  }
);
</script>

<style scoped>
/* 
  scoped CSS: 这里的样式只对当前组件（AdminLayout.vue）生效，不会泄露到子组件或其他组件。
*/

/* Logo区域的样式 */
.logo {
  height: 64px; /* 高度与头部保持一致 */
  margin: 0;
  display: flex;
  align-items: center; /* 垂直居中 */
  justify-content: center; /* 水平居中 */
  color: white;
  overflow: hidden; /* 防止内容溢出 */
}

/* Logo图片样式 */
.logo img {
  height: 32px;
  width: 32px;
  margin-right: 8px; /* 图片和文字之间的间距 */
}

/* 系统名称标题样式 */
.logo h1 {
  color: white;
  font-size: 18px;
  margin: 0;
  white-space: nowrap; /* 防止文字换行 */
}

/* Ant Design组件的样式覆盖（如果需要） */
.site-layout .site-layout-background {
  background: #fff;
}
</style> 