<template>
  <div class="club-activities-container">
    <a-row justify="space-between" align="middle" style="margin-bottom: 20px;">
      <a-col>
        <a-input-search
          placeholder="输入活动名称或社团查询"
          style="width: 300px;"
          @search="onSearch"
        />
      </a-col>
      <a-col>
        <a-button type="primary" style="margin-right: 10px;">发布活动</a-button>
        <a-button>我的活动</a-button>
      </a-col>
    </a-row>

    <a-row :gutter="[24, 24]">
      <a-col v-for="activity in activities" :key="activity.id" :xs="24" :sm="12" :md="8" :lg="6">
        <a-card hoverable :title="activity.name">
          <template #extra>
            <a-tag :color="activity.status === '进行中' ? 'processing' : 'default'">{{ activity.status }}</a-tag>
          </template>
          <p>社团：{{ activity.club }}</p>
          <p>时间：{{ activity.date }}</p>
          <p>地点：{{ activity.location }}</p>
          <p>报名人数：{{ activity.participants }}/{{ activity.capacity }}</p>
          <a-button type="link" style="margin-top: 10px;" @click="viewDetails(activity.id)">查看详情</a-button>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import { reactive } from 'vue';

export default {
  name: 'ClubActivities',
  setup() {
    const activities = reactive([
      {
        id: '1',
        name: '新生迎新晚会',
        club: '学生会',
        date: '2024-09-10',
        location: '学校礼堂',
        participants: 150,
        capacity: 200,
        status: '进行中',
      },
      {
        id: '2',
        name: '编程马拉松',
        club: '计算机协会',
        date: '2024-09-25',
        location: '实验楼B-201',
        participants: 30,
        capacity: 50,
        status: '进行中',
      },
      {
        id: '3',
        name: '校园歌手大赛',
        club: '音乐社团',
        date: '2024-10-05',
        location: '操场舞台',
        participants: 80,
        capacity: 100,
        status: '已结束',
      },
      {
        id: '4',
        name: '英语角活动',
        club: '英语协会',
        date: '2024-09-18',
        location: '图书馆咖啡厅',
        participants: 25,
        capacity: 30,
        status: '进行中',
      },
      {
        id: '5',
        name: '志愿服务：敬老院探访',
        club: '青年志愿者协会',
        date: '2024-10-12',
        location: '市敬老院',
        participants: 40,
        capacity: 40,
        status: '已满员',
      },
    ]);

    const onSearch = (value) => {
      console.log('搜索内容:', value);
      // 这里可以添加搜索逻辑，例如过滤数据
    };

    const viewDetails = (id) => {
      console.log('查看活动详情:', id);
      // 这里可以添加跳转到活动详情页的逻辑
    };

    return {
      activities,
      onSearch,
      viewDetails,
    };
  },
};
</script>

<style scoped>
.club-activities-container {
  padding: 20px;
  background-color: #f0f2f5;
  border-radius: 8px;
}
.ant-card {
  margin-bottom: 24px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.09);
}
.ant-card-head {
  border-bottom: 1px solid #f0f0f0;
}
</style> 