<template>
  <div class="activity-container">
    <div class="header">
      <h2>活动管理</h2>
      <a-button type="primary" :icon="h(PlusOutlined)" @click="showAddModal">发布新活动</a-button>
    </div>

    <a-table :columns="columns" :data-source="activities" :loading="loading" row-key="activeId" bordered>
      <template #publishDate="{ text }">
        <span>{{ formatDateTime(text) }}</span>
      </template>
      <template #endDate="{ text }">
        <span>{{ formatDateTime(text) }}</span>
      </template>
      <template #action="{ record }">
        <a-space>
          <a-button type="link" @click="handleEdit(record)">编辑</a-button>
          <a-popconfirm
            title="确定要删除这个活动吗?"
            ok-text="确定"
            cancel-text="取消"
            @confirm="handleDelete(record.activeId)"
          >
            <a-button type="link" danger>删除</a-button>
          </a-popconfirm>
        </a-space>
      </template>
    </a-table>

    <a-modal
      v-model:visible="isModalVisible"
      :title="isEditMode ? '编辑活动' : '添加活动'"
      @ok="handleOk"
      :confirm-loading="modalLoading"
      :destroyOnClose="true"
    >
      <a-form ref="formRef" :model="formState" :rules="rules" layout="vertical">
        <a-form-item label="活动名称" name="activityName">
          <a-input v-model:value="formState.activityName" />
        </a-form-item>
        <a-form-item label="发布日期" name="publishDate">
          <a-date-picker show-time v-model:value="formState.publishDate" style="width: 100%;" />
        </a-form-item>
        <a-form-item label="结束日期" name="endDate">
          <a-date-picker show-time v-model:value="formState.endDate" style="width: 100%;" />
        </a-form-item>
        <a-form-item label="学生评价" name="studentReview">
          <a-textarea v-model:value="formState.studentReview" :rows="4" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, onMounted, h } from 'vue';
import { getAllActivities, addActivity, updateActivity, deleteActivityById } from '@/api';
import { PlusOutlined } from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';
import dayjs from 'dayjs';

const loading = ref(true);
const activities = ref([]);

const columns = [
  { title: 'ID', dataIndex: 'activeId', key: 'activeId', width: 80 },
  { title: '活动名称', dataIndex: 'activityName', key: 'activityName' },
  { title: '发布日期', dataIndex: 'publishDate', key: 'publishDate', slots: { customRender: 'publishDate' } },
  { title: '结束日期', dataIndex: 'endDate', key: 'endDate', slots: { customRender: 'endDate' } },
  { title: '学生评价', dataIndex: 'studentReview', key: 'studentReview' },
  { title: '操作', key: 'action', align: 'center', width: 120, slots: { customRender: 'action' } },
];

const fetchActivities = async () => {
  loading.value = true;
  try {
    const response = await getAllActivities();
    if (response.data.code == 200) {
      activities.value = response.data.data;
    } else {
      message.error('加载活动数据失败: ' + response.data.message);
    }
  } catch (error) {
    message.error('加载活动数据失败，请检查网络或联系管理员。');
    console.error(error);
  } finally {
    loading.value = false;
  }
};

onMounted(fetchActivities);

const formatDateTime = (date) => {
  return date ? dayjs(date).format('YYYY-MM-DD HH:mm:ss') : '';
};

// Modal state and logic
const isModalVisible = ref(false);
const modalTitle = ref('发布新活动');
const modalLoading = ref(false);
const formRef = ref();
const formState = ref({});
const isEditing = ref(false);

const rules = {
  activityName: [{ required: true, message: '请输入活动名称' }],
  publishDate: [{ required: true, message: '请选择发布日期' }],
  endDate: [{ required: true, message: '请选择结束日期' }],
};

const showAddModal = () => {
  isEditing.value = false;
  formState.value = { studentReview: '' }; // Default empty review
  modalTitle.value = '发布新活动';
  isModalVisible.value = true;
};

const handleEdit = (record) => {
  isEditing.value = true;
  // Convert date strings to dayjs objects for the date picker
  formState.value = { 
    ...record,
    publishDate: record.publishDate ? dayjs(record.publishDate) : null,
    endDate: record.endDate ? dayjs(record.endDate) : null,
  };
  modalTitle.value = '编辑活动信息';
  isModalVisible.value = true;
};

const handleOk = async () => {
  try {
    await formRef.value.validate();
    modalLoading.value = true;
    
    // 将前端 formState 转换为后端期望的数据结构
    // 假设后端需要的字段是 activityName, description, location, startTime, endTime
    const activityDataToSend = { ...formState.value };
    
    let response;
    if (isEditing.value) {
      response = await updateActivity(activityDataToSend.activeId, activityDataToSend);
    } else {
      response = await addActivity(activityDataToSend);
    }

    if (response.status === 200 || response.status === 201) {
      message.success(isEditing.value ? '更新成功' : '添加成功');
      isModalVisible.value = false;
      fetchActivities();
    } else {
      message.error(response.data.message || '操作失败');
    }
  } catch (errorInfo) {
    if (errorInfo.response?.data?.message) {
      message.error('请求失败: ' + errorInfo.response.data.message);
    } else if (errorInfo.message) {
      message.error('请求失败: ' + errorInfo.message);
    } else {
      message.warn('请检查表单输入项！');
    }
  } finally {
    modalLoading.value = false;
  }
};

const handleDelete = async (id) => {
  try {
    const response = await deleteActivityById(id);
    if (response.status === 200 || response.status === 204) {
      message.success('删除成功');
      fetchActivities();
    } else {
      message.error(response.data.message || '删除失败');
    }
  } catch (error) {
    message.error('删除请求失败: ' + (error.response?.data?.message || error.message));
  }
};
</script>

<style scoped>
.activity-container {
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
</style> 