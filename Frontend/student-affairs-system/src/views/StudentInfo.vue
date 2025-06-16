<template>
  <div class="student-info-container">
    <div class="header">
      <h2>学生管理</h2>
      <div class="actions">
        <a-input-search
          v-model:value="searchValue"
          placeholder="搜索学生..."
          style="width: 200px"
          @search="onSearch"
        />
        <a-button type="primary" @click="showAddModal" :icon="h(UserAddOutlined)">添加学生</a-button>
      </div>
    </div>
    <a-table :columns="columns" :data-source="filteredData" :loading="loading" row-key="id" bordered>
      <template #action="{ record }">
        <a-space size="small">
          <a-button type="link" @click="handleEdit(record)">编辑</a-button>
          <a-popconfirm
            title="确定要删除这名学生吗?"
            ok-text="确定"
            cancel-text="取消"
            @confirm="handleDelete(record.id)"
          >
            <a-button type="link" danger>删除</a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </a-table>

    <!-- Add/Edit Modal -->
    <a-modal
      v-model:visible="isModalVisible"
      :title="modalTitle"
      @ok="handleModalOk"
      :confirm-loading="modalConfirmLoading"
      :destroyOnClose="true"
    >
      <a-form ref="formRef" :model="formState" :rules="rules" layout="vertical">
        <a-form-item label="学号" name="studentId">
          <a-input v-model:value="formState.studentId" />
        </a-form-item>
        <a-form-item label="姓名" name="name">
          <a-input v-model:value="formState.name" />
        </a-form-item>
        <a-form-item label="性别" name="gender">
          <a-radio-group v-model:value="formState.gender">
            <a-radio value="男">男</a-radio>
            <a-radio value="女">女</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="学院" name="collegeName">
          <a-input v-model:value="formState.collegeName" />
        </a-form-item>
        <a-form-item label="专业" name="majorName">
          <a-input v-model:value="formState.majorName" />
        </a-form-item>
        <a-form-item label="班级" name="className">
          <a-input v-model:value="formState.className" />
        </a-form-item>
        <a-form-item label="入学时间" name="enrollmentDate">
          <a-date-picker v-model:value="formState.enrollmentDate" style="width: 100%;" value-format="YYYY-MM-DD" />
        </a-form-item>
        <a-form-item label="毕业时间" name="graduationDate">
          <a-date-picker v-model:value="formState.graduationDate" style="width: 100%;" value-format="YYYY-MM-DD" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, h } from 'vue';
import { message } from 'ant-design-vue';
import * as api from '../api';
import { UserAddOutlined } from '@ant-design/icons-vue';
import dayjs from 'dayjs';

const loading = ref(true);
const students = ref([]);
const searchValue = ref('');

const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', sorter: (a, b) => a.id - b.id },
  { title: '学号', dataIndex: 'studentId', key: 'studentId' },
  { title: '姓名', dataIndex: 'name', key: 'name' },
  { title: '性别', dataIndex: 'gender', key: 'gender' },
  { title: '学院', dataIndex: 'collegeName', key: 'collegeName' },
  { title: '专业', dataIndex: 'majorName', key: 'majorName' },
  { title: '班级', dataIndex: 'className', key: 'className' },
  { title: '入学时间', dataIndex: 'enrollmentDate', key: 'enrollmentDate' },
  { title: '毕业时间', dataIndex: 'graduationDate', key: 'graduationDate' },
  { title: '操作', key: 'action', align: 'center', width: 120, slots: { customRender: 'action' } },
];

const fetchStudents = async () => {
  loading.value = true;
  try {
    const response = await api.getAllStudents();
    if (response.data.code == 200) {
      students.value = response.data.data;
    } else {
      message.error('加载学生数据失败: ' + (response.data.message || '未知错误'));
    }
  } catch (error) {
    const errorMsg = error.response?.data?.message || error.message || '请检查网络或联系管理员。';
    message.error('加载学生数据失败，' + errorMsg);
    console.error(error);
  } finally {
    loading.value = false;
  }
};

onMounted(fetchStudents);

const filteredData = computed(() => {
  if (!searchValue.value) {
    return students.value;
  }
  return students.value.filter(student =>
    Object.values(student).some(val =>
      String(val).toLowerCase().includes(searchValue.value.toLowerCase())
    )
  );
});

const onSearch = () => {
  // The computed property already handles filtering, this can be used for explicit search actions if needed
};

// Modal state
const isModalVisible = ref(false);
const modalTitle = ref('添加学生');
const modalConfirmLoading = ref(false);
const formRef = ref();
const formState = ref({});
const isEditing = ref(false);

const rules = {
  studentId: [{ required: true, message: '请输入学号' }],
  name: [{ required: true, message: '请输入姓名' }],
  gender: [{ required: true, message: '请选择性别' }],
  collegeName: [{ required: true, message: '请输入学院名称' }],
  majorName: [{ required: true, message: '请输入专业名称' }],
  className: [{ required: true, message: '请输入班级名称' }],
  enrollmentDate: [{ required: true, message: '请选择入学时间' }],
  graduationDate: [{ required: true, message: '请选择毕业时间' }],
};

const showAddModal = () => {
  isEditing.value = false;
  formState.value = { gender: '男' };
  modalTitle.value = '添加学生';
  isModalVisible.value = true;
};

const handleEdit = (record) => {
  isEditing.value = true;
  formState.value = { ...record };
  modalTitle.value = '编辑学生信息';
  isModalVisible.value = true;
};

const handleDelete = async (id) => {
  try {
    await api.deleteStudentById(id);
    message.success('学生删除成功');
    await fetchStudents();
  } catch (error) {
    console.error('delete error', error);
    message.error('删除失败');
  }
};

const handleModalOk = async () => {
  try {
    await formRef.value.validate();
    modalConfirmLoading.value = true;
    if (isEditing.value) {
      await api.updateStudent(formState.value.id, formState.value);
      message.success('学生信息更新成功');
    } else {
      await api.addStudent(formState.value);
      message.success('学生添加成功');
    }
    isModalVisible.value = false;
    await fetchStudents();
  } catch (error) {
    console.error(error);
    message.error('操作失败');
  } finally {
    modalConfirmLoading.value = false;
  }
};

</script>

<style scoped>
.student-info-container {
  padding: 24px;
  background: #fff;
  border-radius: 8px;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.actions {
  display: flex;
  gap: 10px;
}
</style> 