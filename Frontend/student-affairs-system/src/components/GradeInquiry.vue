<template>
  <div class="grade-inquiry-container">
    <a-row justify="space-between" align="middle" style="margin-bottom: 20px;">
      <a-col>
        <a-input-search
          placeholder="输入学生姓名、学号或课程名称查询"
          style="width: 350px;"
          @search="onSearch"
        />
      </a-col>
      <a-col>
        <!-- 成绩查询模块可能不需要新增或批量操作按钮，根据图片模板暂时不添加 -->
      </a-col>
    </a-row>

    <a-row :gutter="[24, 24]" style="margin-bottom: 20px;">
      <a-col :span="16">
        <a-card title="成绩趋势图" :bordered="false">
          <v-chart class="chart" :option="lineChartOption" autoresize />
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card title="成绩分布" :bordered="false">
          <v-chart class="chart" :option="pieChartOption" autoresize />
        </a-card>
      </a-col>
    </a-row>

    <a-table :columns="columns" :data-source="data" :pagination="pagination" @change="handleTableChange" bordered>
      <template #bodyCell="{ column, text }">
        <template v-if="column.dataIndex === 'score'">
          <a-tag :color="text >= 60 ? 'green' : 'red'">{{ text }}</a-tag>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script>
import { reactive, computed } from 'vue';
import VChart from 'vue-echarts';
import { use } from 'echarts/core';
import {
  CanvasRenderer
} from 'echarts/renderers';
import {
  LineChart,
  PieChart
} from 'echarts/charts';
import {
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
} from 'echarts/components';

use([
  CanvasRenderer,
  LineChart,
  PieChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent
]);

export default {
  name: 'GradeInquiry',
  components: {
    VChart,
  },
  setup() {
    const columns = [
      {
        title: '学生姓名',
        dataIndex: 'studentName',
        key: 'studentName',
      },
      {
        title: '学号',
        dataIndex: 'studentId',
        key: 'studentId',
      },
      {
        title: '课程名称',
        dataIndex: 'courseName',
        key: 'courseName',
      },
      {
        title: '成绩',
        dataIndex: 'score',
        key: 'score',
        sorter: (a, b) => a.score - b.score,
      },
      {
        title: '学分',
        dataIndex: 'credit',
        key: 'credit',
      },
      {
        title: '考试日期',
        dataIndex: 'examDate',
        key: 'examDate',
      },
    ];

    const data = reactive([
      {
        key: '1',
        studentName: '张三',
        studentId: '20210001',
        courseName: '高等数学A',
        score: 85,
        credit: 4,
        examDate: '2023-01-15',
      },
      {
        key: '2',
        studentName: '李四',
        studentId: '20210002',
        courseName: '大学物理',
        score: 72,
        credit: 3,
        examDate: '2023-01-16',
      },
      {
        key: '3',
        studentName: '王五',
        studentId: '20210003',
        courseName: 'C++程序设计',
        score: 58,
        credit: 3,
        examDate: '2023-01-18',
      },
      {
        key: '4',
        studentName: '赵六',
        studentId: '20210004',
        courseName: '英语听力',
        score: 91,
        credit: 2,
        examDate: '2023-01-19',
      },
      {
        key: '5',
        studentName: '张三',
        studentId: '20210001',
        courseName: '数据结构',
        score: 65,
        credit: 3,
        examDate: '2023-01-20',
      },
    ]);

    const pagination = reactive({
      current: 1,
      pageSize: 10,
      total: data.length,
      showTotal: (total) => `共 ${total} 条数据`,
    });

    const onSearch = (value) => {
      console.log('搜索内容:', value);
      // 这里可以添加搜索逻辑，例如过滤数据
    };

    const handleTableChange = (pag, filters, sorter) => {
      pagination.current = pag.current;
      pagination.pageSize = pag.pageSize;
      console.log('分页信息:', pag);
      console.log('过滤信息:', filters);
      console.log('排序信息:', sorter);
      // 这里可以添加数据加载和排序过滤逻辑
    };

    // 成绩趋势图配置
    const lineChartOption = computed(() => ({
      xAxis: {
        type: 'category',
        data: data.map(item => item.examDate),
      },
      yAxis: {
        type: 'value',
      },
      tooltip: {},
      series: [{
        data: data.map(item => item.score),
        type: 'line',
        smooth: true,
        areaStyle: {},
      }],
    }));

    // 成绩分布饼图配置
    const pieChartOption = computed(() => {
      const scoreRanges = {
        '及格 (>=60)': 0,
        '不及格 (<60)': 0,
      };
      data.forEach(item => {
        if (item.score >= 60) {
          scoreRanges['及格 (>=60)']++;
        } else {
          scoreRanges['不及格 (<60)']++;
        }
      });

      return {
        tooltip: {
          trigger: 'item',
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '成绩分布',
            type: 'pie',
            radius: '70%',
            data: Object.entries(scoreRanges).map(([name, value]) => ({ name, value })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      };
    });

    return {
      columns,
      data,
      pagination,
      onSearch,
      handleTableChange,
      lineChartOption,
      pieChartOption,
    };
  },
};
</script>

<style scoped>
.grade-inquiry-container {
  padding: 20px;
  background-color: #f0f2f5;
  border-radius: 8px;
}
.chart {
  height: 300px;
}
</style> 