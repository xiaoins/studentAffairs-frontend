import axios from 'axios';

/**
 * 辅助函数：根据当前环境确定正确的API路径
 * 后端不支持/admin前缀，所以需要移除它
 */
const getApiPath = (path) => {
  // 后端API不支持/admin前缀，所以始终使用不带前缀的路径
  // 移除path中的/admin前缀（如果有）
  if (path.startsWith('/admin/')) {
    return path.substring(6); // 移除"/admin"
  }
  return path;
};

/**
 * 创建一个axios实例，用于向后端发送HTTP请求。
 * 这种方式的好处是，如果未来后端地址变更，我们只需要修改这里的 baseURL 即可。
 */
const apiClient = axios.create({
  // 后端API的基础URL地址，使用/api前缀通过代理访问后端
  baseURL: '/api',
  // 默认请求头，指定发送的数据格式为JSON。
  headers: {
    'Content-Type': 'application/json',
  },
  // 添加凭证，确保cookie可以被发送和接收
  withCredentials: true,
});

// --- 学生管理API ---

/**
 * 获取所有学生列表。
 * @returns {Promise} 一个包含学生数据的Promise对象。
 */
export const getAllStudents = () => {
  return apiClient.get('/students');
};

/**
 * 添加一个新学生。
 * @param {object} student - 包含学生信息的对象。
 * @returns {Promise}
 */
export const addStudent = (student) => {
  return apiClient.post('/students', student);
};

/**
 * 根据ID更新一个学生的信息。
 * @param {number} id - 学生的ID。
 * @param {object} student - 更新后的学生信息对象。
 * @returns {Promise}
 */
export const updateStudent = (id, student) => {
  return apiClient.put(`/students/${id}`, student);
};

/**
 * 根据ID删除一个学生。
 * @param {number} id - 学生的ID。
 * @returns {Promise}
 */
export const deleteStudentById = (id) => {
  return apiClient.delete(`/students/${id}`);
};


// --- 活动管理API ---

/**
 * 获取所有活动列表。
 * @returns {Promise}
 */
export const getAllActivities = () => {
  return apiClient.get('/activities');
};

/**
 * 添加一个新活动。
 * @param {object} activity - 活动对象。
 * @returns {Promise}
 */
export const addActivity = (activity) => {
  return apiClient.post('/activities', activity);
};

/**
 * 根据ID更新一个活动。
 * @param {number} id - 活动的ID。
 * @param {object} activity - 更新后的活动对象。
 * @returns {Promise}
 */
export const updateActivity = (id, activity) => {
  return apiClient.put(`/activities/${id}`, activity);
};

/**
 * 根据ID删除一个活动。
 * @param {number} id - 活动的ID。
 * @returns {Promise}
 */
export const deleteActivityById = (id) => {
  return apiClient.delete(`/activities/${id}`);
};

// --- 课程管理API ---

/**
 * 获取所有课程列表
 * @returns {Promise}
 */
export const getAllCourses = (query) => {
  return apiClient.get('/courses', { params: { query } });
};

/**
 * 添加一个新课程
 * @param {object} course - 课程对象
 * @returns {Promise}
 */
export const addCourse = (course) => {
  return apiClient.post('/courses', course);
};

/**
 * 更新课程信息
 * @param {number} id - 课程ID
 * @param {object} course - 更新后的课程对象
 * @returns {Promise}
 */
export const updateCourse = (id, course) => {
  return apiClient.put(`/courses/${id}`, course);
};

/**
 * 删除课程
 * @param {number} id - 课程ID
 * @returns {Promise}
 */
export const deleteCourse = (id) => {
  return apiClient.delete(`/courses/${id}`);
};

// --- 成绩管理API ---

/**
 * 获取所有成绩列表
 * @returns {Promise}
 */
export const getAllScores = () => {
  return apiClient.get('/scores');
};

/**
 * 添加一个新成绩
 * @param {object} score - 成绩对象
 * @returns {Promise}
 */
export const addScore = (score) => {
  return apiClient.post('/scores', score);
};

/**
 * 更新成绩信息
 * @param {number} id - 成绩ID
 * @param {object} score - 更新后的成绩对象
 * @returns {Promise}
 */
export const updateScore = (id, score) => {
  return apiClient.put(`/scores/${id}`, score);
};

/**
 * 删除成绩
 * @param {number} id - 成绩ID
 * @returns {Promise}
 */
export const deleteScore = (id) => {
  return apiClient.delete(`/scores/${id}`);
};

// --- 管理员认证API ---

/**
 * 注册一个新管理员
 * @param {object} admin - 包含管理员用户名和密码的对象
 * @returns {Promise}
 */
export const registerAdmin = (admin) => {
  return apiClient.post('/register', admin);
};

/**
 * 管理员登录
 * @param {object} credentials - 包含管理员用户名和密码的对象
 * @returns {Promise}
 */
export const loginAdmin = (credentials) => {
  return apiClient.post('/login', credentials);
}; 