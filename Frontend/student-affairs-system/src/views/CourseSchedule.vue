<template>
  <div class="page-container">
    <div class="header-bar">
      <div class="page-title">课程安排</div>
      <div class="actions">
        <a-input-search
            v-model:value="searchValue"
            placeholder="搜索课程名、代码、教师"
            style="width: 250px"
            @search="fetchCourses"
        />
        <a-button @click="() => { searchValue = ''; fetchCourses(); }">刷新</a-button>
        <a-button type="primary" @click="handleAdd">添加课程</a-button>
      </div>
    </div>

    <!-- 课程表格 -->
    <a-table
      :columns="columns"
      :dataSource="courses"
      :loading="loading"
      rowKey="key"
      style="margin-top: 20px"
    >
      <template #action="{ record }">
          <a-space>
            <a @click="handleEdit(record)">编辑</a>
            <a-popconfirm
              title="确定要删除这门课程吗？"
              @confirm="handleDelete(record.id)"
            >
              <a style="color: red">删除</a>
            </a-popconfirm>
          </a-space>
      </template>
    </a-table>

    <!-- 添加/编辑课程弹窗 -->
    <a-modal
      v-model:visible="visible"
      :title="isEdit ? '编辑课程' : '添加课程'"
      @ok="handleModalOk"
      :confirm-loading="modalLoading"
      :destroyOnClose="true"
    >
      <a-form ref="formRef" :model="formState" :label-col="{ span: 5 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="课程名称" name="courseName" :rules="[{ required: true, message: '请输入课程名称!' }]">
          <a-input v-model:value="formState.courseName" />
        </a-form-item>
        <a-form-item label="课程代码" name="courseCode" :rules="[{ required: true, message: '请输入课程代码!' }]">
          <a-input v-model:value="formState.courseCode" />
        </a-form-item>
        <a-form-item label="授课教师" name="teacher" :rules="[{ required: true, message: '请输入授课教师!' }]">
          <a-input v-model:value="formState.teacher" />
        </a-form-item>
        <a-form-item label="学分" name="credit" :rules="[{ required: true, message: '请输入学分!' }, { type: 'number', message: '学分必须是数字' }]">
          <a-input-number v-model:value="formState.credit" style="width: 100%" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { message } from 'ant-design-vue';
import * as api from '../api'; // 引入API模块

// --- 响应式状态定义 ---

// 表格加载状态
const loading = ref(false);
// 课程数据列表
const courses = ref([]);
// 弹窗可见性
const visible = ref(false);
// 弹窗加载状态
const modalLoading = ref(false);
// 是否为编辑模式
const isEdit = ref(false);
// 搜索词
const searchValue = ref('');
// 表单引用
const formRef = ref(null);
// 表单状态
const formState = ref({
  id: null,
  courseName: '',
  courseCode: '',
  teacher: '',
  credit: null,
});

// 表格列定义
const columns = [
  { title: 'ID', dataIndex: 'id', key: 'id', sorter: (a, b) => a.id - b.id },
  { title: '课程名称', dataIndex: 'courseName', key: 'courseName' },
  { title: '课程代码', dataIndex: 'courseCode', key: 'courseCode' },
  { title: '授课教师', dataIndex: 'teacher', key: 'teacher' },
  { title: '学分', dataIndex: 'credit', key: 'credit' },
  { title: '操作', key: 'action', align: 'center', slots: { customRender: 'action' } },
];

// --- 数据获取 ---

/**
 * @description 获取所有课程数据并更新表格
 */
const fetchCourses = async () => {
  loading.value = true;
  try {
    console.log(`正在获取课程数据... 搜索词: ${searchValue.value}`);
    const response = await api.getAllCourses(searchValue.value);
    console.log('获取课程响应:', response);
    
    const responseData = response.data;
    console.log('响应数据:', responseData);
    
    // 直接检查是否有数据
    if (!responseData) {
      console.warn('响应中没有数据');
      message.warning('获取课程列表失败：响应中没有数据');
      courses.value = [];
      loading.value = false;
      return;
    }
    
    let courseList = [];

    // 健壮性检查：处理直接数组和对象包装的数组两种情况
    if (Array.isArray(responseData)) {
      console.log('响应是数组格式');
      courseList = responseData;
    } else if (responseData && typeof responseData === 'object') {
      console.log('响应是对象格式，尝试提取数据');
      
      // 尝试从对象中提取数组数据
      if (Array.isArray(responseData.data)) {
        console.log('从 responseData.data 提取数组');
        courseList = responseData.data;
      } else if (Array.isArray(responseData.result)) {
        console.log('从 responseData.result 提取数组');
        courseList = responseData.result;
      } else if (Array.isArray(responseData.courses)) {
        console.log('从 responseData.courses 提取数组');
        courseList = responseData.courses;
      } else if (Array.isArray(responseData.list)) {
        console.log('从 responseData.list 提取数组');
        courseList = responseData.list;
      } else if (Array.isArray(responseData.items)) {
        console.log('从 responseData.items 提取数组');
        courseList = responseData.items;
      } else {
        console.log('尝试从响应中提取任何可能的数组数据');
        // 尝试从响应中找到任何数组类型的属性
        for (const key in responseData) {
          if (Array.isArray(responseData[key])) {
            console.log(`找到数组数据在属性: ${key}`);
            courseList = responseData[key];
            break;
          }
        }
      }
    }

    if (courseList.length === 0) {
      console.warn('未能从响应中提取课程列表数据', responseData);
      message.warning('获取课程列表失败：未能从响应中提取数据');
      courses.value = [];
      loading.value = false;
      return;
    }

    console.log('提取的课程列表:', courseList);
    console.log('第一条课程数据样例:', courseList[0]);

    // 映射后端数据到前端需要的数据结构
    courses.value = courseList.map((course, index) => {
      // 确保每条记录都有一个唯一的key值
      const id = course.id || `temp_id_${index}`;
      const mappedCourse = {
        key: id.toString(), // 添加明确的key属性，确保是字符串类型
        id: id,
        courseName: course.courseName || '',
        courseCode: course.courseCode || '',
        teacher: course.teacherName || '',
        credit: course.credits || 0,
      };
      console.log('映射后的课程数据:', mappedCourse);
      return mappedCourse;
    });

    console.log('最终课程列表数据:', courses.value);

  } catch (error) {
    console.error('请求课程列表失败:', error);
    if (error.response) {
      console.error('错误响应:', error.response);
      message.error(`请求课程列表失败: 服务器返回 ${error.response.status} ${error.response.statusText}`);
    } else if (error.request) {
      console.error('未收到响应:', error.request);
      message.error('请求课程列表失败: 未收到服务器响应，请检查网络连接');
    } else {
      console.error('请求设置错误:', error.message);
      message.error('请求课程列表失败: ' + error.message);
    }
    courses.value = []; // 同样在捕获到错误时置为空数组
  } finally {
    loading.value = false;
  }
};

// --- CRUD 操作 ---

/**
 * @description 重置表单状态
 */
const resetForm = () => {
  formState.value = {
    id: null,
    courseName: '',
    courseCode: '',
    teacher: '',
    credit: null,
  };
  if (formRef.value) {
    formRef.value.clearValidate();
  }
};

/**
 * @description 处理添加课程按钮点击事件
 */
const handleAdd = () => {
  resetForm();
  isEdit.value = false;
  visible.value = true;
};

/**
 * @description 处理编辑按钮点击事件
 * @param {object} record - 当前行课程数据
 */
const handleEdit = (record) => {
  isEdit.value = true;
  // 使用深拷贝，防止表单修改直接影响表格数据
  formState.value = { ...record };
  visible.value = true;
};

/**
 * @description 处理删除按钮点击事件
 * @param {number} id - 要删除的课程ID
 */
const handleDelete = async (id) => {
  try {
    const response = await api.deleteCourse(id);
    // **检查后端返回的业务状态码**
    if (response.data && (response.data.code === 200 || response.data.code === 0 || response.data.success)) {
      message.success('删除成功');
      await fetchCourses(); // 重新加载数据
    } else {
      message.error(response.data.msg || '删除失败');
    }
  } catch (error) {
    const errorMsg = error.response?.data?.msg || error.message || '删除请求失败';
    message.error(errorMsg);
  }
};

/**
 * @description 处理弹窗的"确定"按钮点击事件
 */
const handleModalOk = async () => {
  try {
    await formRef.value.validate(); // 触发表单验证
    modalLoading.value = true;
    
    // 将前端 formState 转换为后端期望的数据结构
    let courseDataToSend;
    
    if (isEdit.value) {
      // 编辑模式 - 包含 id
      courseDataToSend = {
        id: formState.value.id, // 使用id字段
        courseName: formState.value.courseName,
        courseCode: formState.value.courseCode,
        teacherName: formState.value.teacher, // 前端用 teacher, 后端用 teacherName
        credits: Number(formState.value.credit),  // 确保学分是数字类型
      };
    } else {
      // 添加模式 - 不包含 id (由后端生成)
      courseDataToSend = {
        courseName: formState.value.courseName,
        courseCode: formState.value.courseCode,
        teacherName: formState.value.teacher,
        credits: Number(formState.value.credit), // 确保学分是数字类型
      };
    }

    console.log('准备提交课程数据:', courseDataToSend); // 调试日志

    let response;
    try {
      if (isEdit.value) {
        response = await api.updateCourse(courseDataToSend.id, courseDataToSend);
      } else {
        response = await api.addCourse(courseDataToSend);
      }
      console.log('API响应:', response); // 打印完整响应
      
      // **检查后端返回的业务状态码**
      if (response.data && (response.data.code === 200 || response.data.code === 0 || response.data.success)) {
        message.success(isEdit.value ? '更新成功' : '添加成功');
        visible.value = false;
        await fetchCourses(); // 强制刷新
      } else {
        // 后端返回了业务错误
        message.error(response.data.msg || '操作失败，请稍后重试');
      }

    } catch (apiError) {
      console.error('API调用错误:', apiError);
      if (apiError.response) {
        console.error('错误响应:', apiError.response);
        const errorMsg = apiError.response.data.msg || `操作失败: ${apiError.response.status}`;
        message.error(errorMsg);
      } else {
        message.error('操作失败: ' + apiError.message);
      }
      modalLoading.value = false;
      return;
    }

    modalLoading.value = false;
  } catch (errorInfo) {
    // 表单验证失败或其他错误
    console.error('表单验证错误:', errorInfo);
    message.warn('请检查表单输入项！');
  }
};

// --- 生命周期钩子 ---

// 组件挂载后加载数据
onMounted(() => {
  fetchCourses();
});
</script>

<style scoped>
.page-container {
  padding: 24px;
}
.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}
.page-title {
  font-size: 22px;
  font-weight: 500;
}
.actions {
  display: flex;
  gap: 12px;
}
a {
  color: #1890ff;
}
</style> 