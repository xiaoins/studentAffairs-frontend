<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
         <div class="logo-wrapper">
           <img src="@/assets/adminlogo.svg" alt="Logo" class="login-logo" />
         </div>
         <h1 class="title">学工系统管理平台</h1>
         <p class="subtitle">现代化学生事务管理系统</p>
      </div>

      <a-form
        :model="formState"
        @finish="onFinish"
        layout="vertical"
        class="login-form-content"
      >
        <a-form-item
          label="用户名"
          name="username"
          :rules="[{ required: true, message: '请输入用户名!' }]"
        >
           <a-input
             v-model:value="formState.username"
             placeholder="请输入管理员账号"
             size="large"
           />
        </a-form-item>

        <a-form-item
          label="密码"
          name="password"
          :rules="[{ required: true, message: '请输入密码!' }]"
        >
           <a-input-password
             v-model:value="formState.password"
             placeholder="请输入密码"
             size="large"
           />
        </a-form-item>

        <a-form-item>
          <a-button type="primary" html-type="submit" class="login-form-button" block size="large" :loading="loading">
            登录系统
          </a-button>
        </a-form-item>
        <div class="back-to-home">
          <router-link to="/">返回首页</router-link>
        </div>
      </a-form>
      <div class="login-footer">
        © 2025 学工系统管理平台 - 现代化学生事务管理
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue';
import * as api from '@/api';

const router = useRouter();
const loading = ref(false);
const formState = reactive({
  username: '',
  password: '',
});

const onFinish = async (values) => {
  loading.value = true;
  try {
    const response = await api.loginAdmin(values);
    if (response.data && response.data.code === 200) {
      // 登录成功，实际项目中后端会返回token，前端需要保存
      // localStorage.setItem('token', response.data.data.token);
      message.success('登录成功！即将跳转至管理后台...', 2);
      router.push('/admin');
    } else {
      message.error(response.data.message || '登录失败！');
    }
  } catch (error) {
    message.error(error.response?.data?.message || '登录请求失败，请检查网络或后端服务。');
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #6DD5FA, #8A2BE2);
  padding: 20px;
}

.login-box {
  width: 100%;
  max-width: 420px;
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(15px);
  -webkit-backdrop-filter: blur(15px);
  border-radius: 20px;
  padding: 40px;
  box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
  border: 1px solid rgba(255, 255, 255, 0.18);
  color: #333;
  transition: all 0.3s ease-in-out;
}

.login-box:hover {
    transform: translateY(-10px);
    box-shadow: 0 16px 45px 0 rgba(31, 38, 135, 0.42);
}

.login-header {
  text-align: center;
  margin-bottom: 32px;
}

.logo-wrapper {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  background: linear-gradient(135deg, #89f7fe 0%, #66a6ff 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 0 15px rgba(102, 166, 255, 0.5);
  transition: all 0.3s ease-in-out;
}

.logo-wrapper:hover {
  transform: scale(1.1) rotate(10deg);
  box-shadow: 0 0 25px rgba(102, 166, 255, 0.75);
}

.login-logo {
  width: 45px;
}

.title {
  font-size: 28px;
  font-weight: 700;
  color: #333;
  margin: 0;
}

.subtitle {
  color: #555;
  margin-top: 8px;
}

.login-form-content :deep(.ant-form-item-label > label) {
  color: #333;
  font-weight: 600;
}

.login-form-content :deep(.ant-input-affix-wrapper),
.login-form-content :deep(.ant-input) {
  background: #f0f2f5 !important;
  border: 1px solid #d9d9d9 !important;
  border-radius: 50px !important;
  padding: 0 20px !important;
  height: 48px;
  line-height: 46px;
  transition: all 0.3s;
}

.login-form-content :deep(input.ant-input) {
    background: transparent !important;
}

.login-form-content :deep(.ant-input-affix-wrapper input.ant-input) {
  border: none !important;
  box-shadow: none !important;
  padding: 0 !important;
  background-color: transparent !important;
}

.login-form-content :deep(.ant-input-affix-wrapper:hover),
.login-form-content :deep(.ant-input:hover) {
  border-color: #8A2BE2 !important;
}

.login-form-content :deep(input.ant-input::placeholder) {
  color: #aaa !important;
}

.login-form-button {
  background: linear-gradient(90deg, #5b21b6, #9d34da);
  border: none;
  border-radius: 50px;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  transition: all 0.3s ease-in-out;
  box-shadow: 0 5px 15px rgba(0,0,0,0.2);
}

.login-form-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.3);
}

.back-to-home {
  text-align: center;
  margin-top: 16px;
}

.back-to-home a {
  color: #555;
  text-decoration: none;
  transition: color 0.3s;
}

.back-to-home a:hover {
  color: #333;
  text-decoration: underline;
}

.login-footer {
  text-align: center;
  color: #888;
  margin-top: 32px;
  font-size: 12px;
}
</style> 