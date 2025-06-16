/**
 * Vue Router 配置中心
 * 这个文件负责定义整个应用的导航规则，也就是URL路径与Vue组件之间的映射关系。
 * 当用户在浏览器中访问一个URL时，Vue Router会根据这里的配置，决定渲染哪个组件到<router-view/>中。
 */

// 导入Vue Router的核心函数
import { createRouter, createWebHashHistory } from 'vue-router'

// 导入所有需要通过路由访问的页面（视图）组件
import AdminLayout from '../views/AdminLayout.vue'
import Dashboard from '../views/DashboardView.vue'
import StudentInfo from '../views/StudentInfo.vue'
import GradeInquiry from '../views/GradeInquiry.vue'
import CourseSchedule from '../views/CourseSchedule.vue'
import ClubActivities from '../views/ClubActivities.vue'
import SystemSettings from '../views/SystemSettings.vue'
import LoginPage from '../views/LoginPage.vue'
import HomePage from '../HomePage.vue'
import RegisterPage from '../views/RegisterPage.vue'

/**
 * 定义路由规则数组。
 * 每个路由规则都是一个对象，包含以下关键属性：
 * - path: URL路径，例如 '/login'。
 * - name: 路由的唯一名称，方便在代码中通过名字进行导航，例如 router.push({ name: 'Login' })。
 * - component: 当路径匹配时，需要渲染的Vue组件。
 * - redirect: 重定向规则。例如，访问根路径'/'时，自动跳转到'/dashboard'。
 * - children: 嵌套路由，用于在一个父组件中渲染子组件，常用于布局组件。
 */
const routes = [
  // 首页的路由规则
  {
    path: '/',
    name: 'HomePage',
    component: HomePage
  },
  // 注册页面的路由规则
  {
    path: '/register',
    name: 'RegisterPage',
    component: RegisterPage
  },
  // 登录页面的路由规则
  {
    path: '/login',
    name: 'LoginPage',
    component: LoginPage
  },
  // 主管理界面的布局路由
  {
    // 所有以'/admin'开头的路径都会使用AdminLayout作为父组件
    path: '/admin', 
    name: 'AdminLayout',
    component: AdminLayout,
    // 当访问'/admin'时，默认重定向到'/admin/dashboard'
    redirect: '/admin/dashboard', 
    // 定义嵌套在AdminLayout中的子页面
    children: [
      {
        path: 'dashboard', // 路径会拼接在父路径后，完整路径是 /admin/dashboard
        name: 'Dashboard',
        component: Dashboard
      },
      {
        path: 'students', // 完整路径是 /admin/students
        name: 'StudentInfo',
        component: StudentInfo
      },
      {
        path: 'grades', // 完整路径是 /admin/grades
        name: 'GradeInquiry',
        component: GradeInquiry
      },
      {
        path: 'courses', // 完整路径是 /admin/courses
        name: 'CourseSchedule',
        component: CourseSchedule
      },
      {
        path: 'activities', // 完整路径是 /admin/activities
        name: 'ClubActivities',
        component: ClubActivities
      },
      {
        path: 'settings', // 完整路径是 /admin/settings
        name: 'SystemSettings',
        component: SystemSettings
      }
    ]
  },
  // 根路径重定向规则 (现在由首页处理，此条可以移除或注释)
  /*
  { 
    path: '/', 
    redirect: '/admin' 
  }
  */
]

/**
 * 创建Router实例
 * 这里是Vue Router的核心配置。
 */
const router = createRouter({
  /**
   * history: 配置路由模式。
   * - createWebHashHistory(): 使用URL哈希模式（例如 http://localhost:8080/#/login）。
   *   这种模式的好处是简单，不需要后端服务器做特殊配置，对SEO不友好。
   * - createWebHistory(): 使用HTML5 History API模式（例如 http://localhost:8080/login）。
   *   URL更美观，对SEO友好，但需要后端服务器配置支持，否则刷新页面会出现404错误。
   *   本项目中使用哈希模式，因为它更适合内部管理系统，且部署简单。
   */
  history: createWebHashHistory(),
  // 将上面定义的路由规则数组传递给Router实例
  routes
})

// 导出Router实例，以便在main.js中引入并挂载到Vue应用中
export default router 