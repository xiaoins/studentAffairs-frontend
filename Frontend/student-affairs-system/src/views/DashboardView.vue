<template>
  <!-- 仪表盘主容器 -->
  <div class="dashboard">
    <!-- 
      第一行：数据卡片统计
      <a-row> 和 <a-col> 是 Ant Design Vue 的栅格系统，用于创建响应式布局。
      :gutter="[16, 16]" 设置了列之间的水平和垂直间距。
    -->
    <a-row :gutter="[16, 16]" class="summary-cards">
      <!-- 
        使用v-for遍历stats数组，动态生成每个统计卡片。
        :xs, :sm, :md, :lg, :xl 是响应式断点，定义了在不同屏幕尺寸下，每列占据的栅格数（总共24栅格）。
        这使得布局在手机、平板和桌面上都能良好显示。
      -->
      <a-col v-for="stat in stats" :key="stat.title" :xs="24" :sm="12" :md="12" :lg="6" :xl="6">
        <!-- 
          <a-card> 组件用于显示每个统计项。
          :hoverable="true" 使卡片在鼠标悬停时有浮起效果。
        -->
        <a-card :hoverable="true" class="stat-card">
          <!-- 
            <a-statistic> 是一个专门用于展示统计数值的组件。
            :title - 统计项的标题。
            :value - 统计项的数值。
          -->
          <a-statistic :title="stat.title" :value="stat.value">
            <!-- #prefix 插槽用于在数值前添加图标 -->
            <template #prefix>
              <component :is="stat.icon" :style="{ color: stat.color }" />
            </template>
            <!-- #suffix 插槽用于在数值后添加单位或文本 -->
            <template #suffix>
              <span>{{ stat.suffix }}</span>
            </template>
          </a-statistic>
        </a-card>
      </a-col>
    </a-row>

    <!-- 第二行：图表和快速入口 -->
    <a-row :gutter="[16, 16]" style="margin-top: 16px">
      <!-- 左侧：学生专业分布图 -->
      <a-col :span="16">
        <a-card title="学生专业分布" :hoverable="true">
          <!-- 这里将来用于渲染图表，例如使用 ECharts 或 Chart.js -->
          <div ref="majorChart" style="height: 400px;"></div>
        </a-card>
      </a-col>
      <!-- 右侧：快速操作入口 -->
      <a-col :span="8">
        <a-card title="快速操作" :hoverable="true">
          <a-space direction="vertical" style="width: 100%">
            <a-button type="primary" block @click="goTo('/admin/students')">学生管理</a-button>
            <a-button block @click="goTo('/admin/grades')">成绩查询</a-button>
            <a-button block @click="goTo('/admin/courses')">课程安排</a-button>
          </a-space>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script setup>
// 从 'vue' 中导入所需的函数和API
import { ref, onMounted, onUnmounted } from 'vue';
// 从 'vue-router' 中导入 useRouter 以进行编程式导航
import { useRouter } from 'vue-router';
// 导入Ant Design的图标
import { UserOutlined, BookOutlined, BarChartOutlined, TeamOutlined } from '@ant-design/icons-vue';
// 导入 ECharts
import * as echarts from 'echarts';

// --- 响应式状态定义 ---

// 统计数据数组，用于生成顶部的卡片
const stats = ref([
  { title: '总学生数', value: 1250, icon: UserOutlined, suffix: '人', color: '#1890ff' },
  { title: '课程总数', value: 88, icon: BookOutlined, suffix: '门', color: '#52c41a' },
  { title: '平均成绩', value: 85.6, icon: BarChartOutlined, suffix: '分', color: '#faad14' },
  { title: '社团活动', value: 23, icon: TeamOutlined, suffix: '个', color: '#f5222d' },
]);

// ECharts图表实例的引用
const majorChart = ref(null);
let chartInstance = null;

// 获取路由实例
const router = useRouter();

// --- 函数定义 ---

/**
 * 跳转到指定路径的函数
 * @param {string} path - 目标路由路径
 */
const goTo = (path) => {
  router.push(path);
};

// ECharts 图表的配置项
const chartOption = {
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left',
  },
  series: [
    {
      name: '专业分布',
      type: 'pie',
      radius: '50%',
      data: [
        { value: 335, name: '计算机科学与技术' },
        { value: 310, name: '软件工程' },
        { value: 234, name: '网络工程' },
        { value: 135, name: '信息安全' },
        { value: 254, name: '物联网工程' }
      ],
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
};

// --- 生命周期钩子 ---

// onMounted 是Vue的生命周期钩子，在组件被挂载到DOM后执行
onMounted(() => {
  // 初始化ECharts图表
  chartInstance = echarts.init(majorChart.value);
  // 设置图表配置
  chartInstance.setOption(chartOption);
  // 添加窗口大小变化的监听，以实现图表的响应式调整
  window.addEventListener('resize', chartInstance.resize);
});

// onUnmounted 是在组件被卸载前执行的钩子
onUnmounted(() => {
  // 移除窗口大小变化的监听，防止内存泄漏
  window.removeEventListener('resize', chartInstance.resize);
  // 销毁ECharts实例
  chartInstance.dispose();
});
</script>

<style scoped>
/* 组件的局部样式 */
.dashboard {
  padding: 1px; /* 防止外边距折叠 */
}

.stat-card .ant-statistic-title {
  font-size: 14px;
  color: rgba(0, 0, 0, 0.45);
}

.stat-card .ant-statistic-content {
  font-size: 24px;
}
</style> 