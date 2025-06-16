/**
 * Vue.js应用程序的主入口文件。
 * 这个文件的作用是：
 * 1. 引入Vue的核心库和根组件（App.vue）。
 * 2. 引入路由配置，用于管理页面导航。
 * 3. 引入Ant Design Vue UI组件库及其样式，用于构建美观的用户界面。
 * 4. 创建Vue应用实例。
 * 5. 将路由和UI组件库挂载到Vue实例上。
 * 6. 将Vue实例渲染到HTML页面中的#app元素上，完成应用的启动。
 */

// 从 'vue' 包中导入 createApp 函数，这是创建Vue应用实例所必需的。
import { createApp } from 'vue'

// 导入应用的根组件 App.vue。根组件是所有其他组件的容器。
import App from './App.vue'

// 导入路由配置文件。router 文件夹下的 index.js 定义了应用的URL路径和对应的组件。
import router from './router'

// 导入 Ant Design Vue UI 框架，这是一个流行的Vue UI组件库。
import Antd from 'ant-design-vue';

// 导入 Ant Design Vue 的默认样式表，以确保组件显示正常。
// 在 ant-design-vue v4+ 中，推荐使用 reset.css 来重置样式
// 但为了兼容旧版本，我们这里保留使用 antd.css
import 'ant-design-vue/dist/antd.css';

// 创建Vue应用实例，并将App组件作为根组件传入。
const app = createApp(App);

// 使用 .use() 方法将 Ant Design Vue 插件注册到Vue应用实例中。
app.use(Antd);

// 使用 .use() 方法将路由插件注册到Vue应用实例中。
app.use(router);

// 调用 .mount() 方法将Vue应用实例挂载到 public/index.html 文件中id为'app'的DOM元素上。
app.mount('#app');
