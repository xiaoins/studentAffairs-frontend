<template>
  <div>
    <!-- 操作与搜索栏 -->
    <a-card>
      <a-space>
        <a-button type="primary" @click="handleAdd">录入成绩</a-button>
        <a-button @click="fetchData">刷新</a-button>
        <!-- 学生筛选 -->
        <a-select
          v-model:value="searchStudentId"
          show-search
          placeholder="按学生筛选"
          style="width: 200px"
          :options="studentOptions"
          :filter-option="filterOption"
          allow-clear
          @change="handleSearch"
        />
      </a-space>
    </a-card>

    <!-- 成绩表格 -->
    <a-table
      :columns="columns"
      :dataSource="filteredScores"
      :loading="loading"
      rowKey="id"
      style="margin-top: 20px"
    >
      <template #action="{ record }">
          <a-space>
            <a @click="handleEdit(record)">编辑</a>
            <a-popconfirm
              title="确定要删除这条成绩记录吗？"
              @confirm="handleDelete(record.id)"
            >
              <a style="color: red">删除</a>
            </a-popconfirm>
          </a-space>
      </template>
    </a-table>

    <!-- 添加/编辑成绩弹窗 -->
    <a-modal
      v-model:visible="visible"
      :title="isEdit ? '编辑成绩' : '录入成绩'"
      @ok="handleModalOk"
      :confirm-loading="modalLoading"
      :destroyOnClose="true"
    >
      <a-form ref="formRef" :model="formState" :label-col="{ span: 5 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="学生" name="studentId" :rules="[{ required: true, message: '请选择学生!' }]">
          <a-select
            v-model:value="formState.studentId"
            show-search
            placeholder="选择一个学生"
            :options="studentOptions"
            :filter-option="filterOption"
            :disabled="isEdit"
          />
        </a-form-item>
        <a-form-item label="课程" name="courseId" :rules="[{ required: true, message: '请选择课程!' }]">
          <a-select
            v-model:value="formState.courseId"
            show-search
            placeholder="选择一门课程"
            :options="courseOptions"
            :filter-option="filterOption"
             :disabled="isEdit"
          />
        </a-form-item>
        <a-form-item label="分数" name="score" :rules="[{ required: true, message: '请输入分数!' }, { type: 'number', min: 0, max: 150, message: '分数必须在0-150之间' }]">
          <a-input-number v-model:value="formState.score" style="width: 100%" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, reactive } from 'vue';
import { message } from 'ant-design-vue';
import * as api from '../api';

// --- 响应式状态定义 ---

// 页面和表格加载状态
const loading = ref(false);
// 原始成绩列表
const scores = ref([]);
// 学生列表
const students = ref([]);
// 课程列表
const courses = ref([]);
// 筛选用的学生ID
const searchStudentId = ref(null);

// 弹窗相关状态
const visible = ref(false);
const modalLoading = ref(false);
const isEdit = ref(false);
const formRef = ref(null);
// 将 formState 改为 reactive 对象
const formState = reactive({
  id: null,
  studentId: null,
  courseId: null,
  score: null
});

// --- 计算属性 ---

// 将学生列表转换为 a-select 的 options 格式
const studentOptions = computed(() => 
  students.value.map(s => ({ 
    value: s.id,
    label: s.name 
  }))
);

// 将课程列表转换为 a-select 的 options 格式
const courseOptions = computed(() => 
  courses.value.map(c => ({ 
    value: c.id,
    label: c.courseName
  }))
);

// 根据筛选条件过滤成绩列表
const filteredScores = computed(() => {
  if (searchStudentId.value) {
    // 假设成绩对象中直接包含 sid 字段用于筛选
    return scores.value.filter(score => score.sid === searchStudentId.value);
  }
  return scores.value;
});

// --- 表格和表单 ---

// 表格列定义
const columns = [
  { title: '成绩ID', dataIndex: 'id', key: 'id', sorter: (a, b) => a.id - b.id },
  // 直接从扁平化的数据结构中读取 name 和 courseName
  { title: '学生姓名', dataIndex: 'studentName', key: 'studentName' },
  { title: '课程名称', dataIndex: 'courseName', key: 'courseName' },
  { title: '分数', dataIndex: 'score', key: 'score', sorter: (a, b) => a.score - b.score },
  { title: '操作', key: 'action', align: 'center', slots: { customRender: 'action' } },
];

/**
 * @description a-select组件的搜索函数
 * @param {string} input - 用户输入的值
 * @param {object} option - 当前选项
 */
const filterOption = (input, option) => {
  return option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0;
};


// --- 数据获取与转换 ---

// 辅助函数，用于安全地提取列表数据
const safelyExtractData = (response) => {
    const responseData = response.data;
    if (Array.isArray(responseData)) {
      return responseData;
    }
    if (responseData && Array.isArray(responseData.data)) {
      return responseData.data;
    }
    return []; // 返回空数组作为安全默认值
};

const fetchData = async () => {
  loading.value = true;
  try {
    console.log('正在获取成绩数据...'); // 调试日志
    const [scoreResponse, studentResponse, courseResponse] = await Promise.all([
      api.getAllScores(),
      api.getAllStudents(),
      api.getAllCourses(),
    ]);

    scores.value = safelyExtractData(scoreResponse);
    students.value = safelyExtractData(studentResponse);
    courses.value = safelyExtractData(courseResponse);
    
    console.log('获取到的成绩数据:', scores.value); // 调试日志
    
  } catch (error) {
    console.error('获取数据失败:', error);
    message.error('请求数据失败: ' + error.message);
    scores.value = [];
    students.value = [];
    courses.value = [];
  } finally {
    loading.value = false;
  }
};

// --- CRUD 操作 ---

const handleSearch = () => {
  // 当筛选变化时，表格会通过计算属性自动更新
  // 此函数可以留空，或用于未来更复杂的搜索逻辑
  console.log('Filtered by student ID:', searchStudentId.value);
};

/**
 * @description 处理添加按钮点击事件
 */
const handleAdd = () => {
  isEdit.value = false;
  // 重置表单状态
  Object.assign(formState, {
    id: null,
    studentId: null,
    courseId: null,
    score: null,
  });
  visible.value = true;
};

/**
 * @description 处理编辑按钮点击事件
 * @param {object} record - 当前行成绩数据
 */
const handleEdit = (record) => {
  isEdit.value = true;
  // 更新表单状态
  Object.assign(formState, {
    id: record.id,
    studentId: record.studentId,
    courseId: record.courseId,
    score: record.score,
  });
  visible.value = true;
};

const handleDelete = async (id) => {
  try {
    const response = await api.deleteScore(id);
    if (response.data && (response.data.code === 200 || response.data.code === 0 || response.data.success)) {
      message.success('删除成功');
      await fetchData();
    } else {
      message.error(response.data.message || '删除失败');
    }
  } catch (error) {
    const errorMsg = error.response?.data?.message || error.message || '删除请求失败';
    message.error(errorMsg);
  }
};

const handleModalOk = async () => {
  try {
    await formRef.value.validate();
    modalLoading.value = true;
    
    const scoreData = { ...formState };
    let response;

    if (isEdit.value) {
      response = await api.updateScore(scoreData.id, scoreData);
    } else {
      response = await api.addScore(scoreData);
    }

    if (response.data && (response.data.code === 200 || response.data.code === 0 || response.data.success)) {
      message.success(isEdit.value ? '更新成功' : '录入成功');
      visible.value = false;
      await fetchData();
    } else {
      message.error(response.data.message || '操作失败');
    }
  } catch (error) {
    const errorMsg = error.response?.data?.message || error.message || '操作失败，请检查输入';
    message.error(errorMsg);
    console.error('Modal OK error:', error);
  } finally {
    modalLoading.value = false;
  }
};

// --- 生命周期钩子 ---
onMounted(() => {
  fetchData();
});
</script>

<style scoped>
a {
  color: #1890ff;
}
</style> 