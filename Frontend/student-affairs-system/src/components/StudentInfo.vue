<template>
  <div class="student-info-container">
    <a-row justify="space-between" align="middle" style="margin-bottom: 20px;">
      <a-col>
        <a-input-search
          placeholder="输入学生姓名或学号查询"
          style="width: 300px;"
          @search="onSearch"
        />
      </a-col>
      <a-col>
        <a-button type="primary" style="margin-right: 10px;">新增信息</a-button>
        <a-button>批量操作</a-button>
      </a-col>
    </a-row>

    <a-table :columns="columns" :data-source="data" :pagination="pagination" @change="handleTableChange" bordered>
      <template #bodyCell="{ column, _record }"> <!-- eslint-disable-line vue/no-unused-vars -->
        <template v-if="column.key === 'action'">
          <a-button type="link">编辑</a-button>
          <a-button type="link" danger>删除</a-button>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script>
import { reactive } from 'vue';

export default {
  name: 'StudentInfo',
  setup() {
    const columns = [
      {
        title: '姓名',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '性别',
        dataIndex: 'gender',
        key: 'gender',
      },
      {
        title: '学号',
        dataIndex: 'studentId',
        key: 'studentId',
      },
      {
        title: '班级',
        dataIndex: 'class',
        key: 'class',
      },
      {
        title: '操作',
        key: 'action',
      },
    ];

    const data = reactive([
      {
        key: '1',
        name: '张三',
        gender: '男',
        studentId: '20210001',
        class: '计算机1班',
      },
      {
        key: '2',
        name: '李四',
        gender: '女',
        studentId: '20210002',
        class: '软件2班',
      },
      {
        key: '3',
        name: '王五',
        gender: '男',
        studentId: '20210003',
        class: '网络工程1班',
      },
      {
        key: '4',
        name: '赵六',
        gender: '女',
        studentId: '20210004',
        class: '计算机1班',
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

    return {
      columns,
      data,
      pagination,
      onSearch,
      handleTableChange,
    };
  },
};
</script>

<style scoped>
.student-info-container {
  padding: 20px;
  background-color: #f0f2f5; /* 稍微灰色的背景，与图片风格接近 */
  border-radius: 8px;
}
</style> 