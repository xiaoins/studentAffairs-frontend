<template>
  <div class="grade-inquiry-container">
    <div class="header-bar">
      <a-button type="primary" @click="handleAdd" class="action-button">录入成绩</a-button>
      <a-input-search
          v-model:value="searchQuery"
          placeholder="按学生姓名搜索"
          style="width: 250px"
          @search="onSearch"
          class="search-input"
      />
    </div>

    <a-table :columns="columns" :data-source="scores" :loading="loading" row-key="id" class="grade-table">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-button type="link" @click="handleEdit(record)">编辑</a-button>
          <a-popconfirm
              title="确定要删除这条成绩吗?"
              ok-text="确定"
              cancel-text="取消"
              @confirm="handleDelete(record.id)"
          >
            <a-button type="link" danger>删除</a-button>
          </a-popconfirm>
        </template>
      </template>
    </a-table>

    <a-modal
        v-model:visible="modalVisible"
        :title="modalTitle"
        @ok="handleModalOk"
        @cancel="handleModalCancel"
        :confirm-loading="modalConfirmLoading"
    >
      <a-form :model="formState" :label-col="{ span: 4 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="学生" name="studentId" :rules="[{ required: true, message: '请选择学生' }]">
          <a-select v-model:value="formState.studentId" :options="studentOptions" placeholder="请选择学生" :disabled="isEdit"></a-select>
        </a-form-item>
        <a-form-item label="课程" name="courseId" :rules="[{ required: true, message: '请选择课程' }]">
          <a-select v-model:value="formState.courseId" :options="courseOptions" placeholder="请选择课程" :disabled="isEdit"></a-select>
        </a-form-item>
        <a-form-item label="分数" name="score" :rules="[{ required: true, message: '请输入分数' }]">
          <a-input-number v-model:value="formState.score" :min="0" :max="100" style="width: 100%"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import {ref, onMounted, reactive} from 'vue';
import { useApi } from '@/composables/useApi';
import { message } from 'ant-design-vue';

const api = useApi();

const scores = ref([]);
const loading = ref(false);
const searchQuery = ref('');

const columns = [
  { title: '成绩ID', dataIndex: 'id', key: 'id', sorter: (a, b) => a.id - b.id, sortDirections: ['descend', 'ascend'] },
  { title: '学生', dataIndex: 'studentName', key: 'studentName' },
  { title: '班级', dataIndex: 'className', key: 'className' },
  { title: '课程', dataIndex: 'courseName', key: 'courseName' },
  { title: '教师', dataIndex: 'teacherName', key: 'teacherName' },
  { title: '分数', dataIndex: 'score', key: 'score' },
  { title: '操作', key: 'action' },
];

const fetchData = async (name = '') => {
  loading.value = true;
  try {
    const res = await api.getScoreList({ name });
    if (res.code === '200' && res.data) {
      scores.value = res.data;
    } else {
      scores.value = [];
      message.error(res.message || '获取成绩列表失败');
    }
  } catch (error) {
    message.error('请求成绩列表时发生错误');
  } finally {
    loading.value = false;
  }
};

const onSearch = () => {
  fetchData(searchQuery.value);
};

// --- Modal and Form State ---
const modalVisible = ref(false);
const modalTitle = ref('');
const modalConfirmLoading = ref(false);
const isEdit = ref(false);

const formState = reactive({
  id: null,
  studentId: '', // Correct: Use string for business ID
  courseId: null,
  score: null,
});

const studentOptions = ref([]);
const courseOptions = ref([]);

const fetchSelectOptions = async () => {
  try {
    const [studentRes, courseRes] = await Promise.all([
      api.getAllStudents(),
      api.getAllCourses()
    ]);

    if (studentRes.code === '200' && studentRes.data) {
      studentOptions.value = studentRes.data.map(s => ({
        label: s.name,
        value: s.studentId, // Correct: Use business ID (string) as value
      }));
    }

    if (courseRes.code === '200' && courseRes.data) {
      courseOptions.value = courseRes.data.map(c => ({
        label: c.courseName,
        value: c.id,
      }));
    }
  } catch (error) {
    message.error('加载下拉选项失败');
  }
};

const resetForm = () => {
  formState.id = null;
  formState.studentId = '';
  formState.courseId = null;
  formState.score = null;
};

const handleAdd = () => {
  resetForm();
  isEdit.value = false;
  modalTitle.value = '录入新成绩';
  modalVisible.value = true;
};

const handleEdit = (record) => {
  isEdit.value = true;
  modalTitle.value = '编辑成绩';
  // Directly assign, Vue's reactivity will handle it
  formState.id = record.id;
  formState.studentId = record.studentId; // Correct: Use business ID from record
  formState.courseId = record.courseId;
  formState.score = record.score;
  modalVisible.value = true;
};

const handleModalOk = async () => {
  modalConfirmLoading.value = true;
  try {
    let res;
    const payload = { ...formState };

    if (isEdit.value) {
      res = await api.updateScore(payload);
    } else {
      res = await api.addScore(payload);
    }

    if (res.code === '200') {
      message.success(isEdit.value ? '更新成功' : '添加成功');
      modalVisible.value = false;
      await fetchData(searchQuery.value);
    } else {
      message.error(res.message || '操作失败');
    }
  } catch (error) {
    message.error('操作时发生错误');
  } finally {
    modalConfirmLoading.value = false;
  }
};

const handleModalCancel = () => {
  modalVisible.value = false;
};

const handleDelete = async (id) => {
  try {
    const res = await api.deleteScore(id);
    if (res.code === '200') {
      message.success('删除成功');
      await fetchData(searchQuery.value);
    } else {
      message.error(res.message || '删除失败');
    }
  } catch (error) {
    message.error('删除时发生错误');
  }
};


onMounted(() => {
  fetchData();
  fetchSelectOptions();
});

</script>

<style scoped>
.grade-inquiry-container {
  padding: 24px;
}

.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-input {
  /* The antd component has its own styling, so we might not need much here */
}

.action-button {
  /* Style for buttons like '录入成绩' */
}

.grade-table {
  /* Custom styles for the table if needed */
}
</style> 