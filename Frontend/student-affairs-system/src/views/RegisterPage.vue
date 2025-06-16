<template>
  <div class="register-container">
    <a-card class="register-card" :style="{ width: '400px' }">
      <template #title>
        <div class="card-header">
          <img src="@/assets/logo.png" alt="logo" class="logo-img">
          <h2>管理员注册</h2>
        </div>
      </template>

      <a-form
        :model="formState"
        @finish="onFinish"
      >
        <a-form-item
          name="username"
          :rules="[{ required: true, message: '请输入您的用户名!' }]"
        >
          <a-input v-model:value="formState.username" placeholder="用户名">
            <template #prefix><UserOutlined /></template>
          </a-input>
        </a-form-item>

        <a-form-item
          name="password"
          :rules="[{ required: true, message: '请输入您的密码!' }]"
        >
          <a-input-password v-model:value="formState.password" placeholder="密码">
            <template #prefix><LockOutlined /></template>
          </a-input-password>
        </a-form-item>

        <a-form-item
          name="captcha"
          :rules="[{ required: true, message: '请输入验证码!' }]"
        >
          <div class="captcha-wrapper">
            <a-input v-model:value="formState.captcha" placeholder="验证码" class="captcha-input">
              <template #prefix><SafetyOutlined /></template>
            </a-input>
            <div class="captcha-code" @click="generateCaptcha">{{ captchaCode }}</div>
          </div>
        </a-form-item>

        <a-form-item>
          <a-button
            type="primary"
            html-type="submit"
            :loading="loading"
            block
          >
            注册
          </a-button>
        </a-form-item>
        <div class="back-to-login">
          <router-link to="/login">已有账户？直接登录</router-link>
        </div>
      </a-form>
    </a-card>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue';
import { UserOutlined, LockOutlined, SafetyOutlined } from '@ant-design/icons-vue';
import * as api from '@/api';

const router = useRouter();
const loading = ref(false);
const captchaCode = ref('');

const formState = reactive({
  username: '',
  password: '',
  captcha: '',
});

/**
 * 生成一个4位的随机验证码
 */
const generateCaptcha = () => {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let result = '';
  for (let i = 0; i < 4; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length));
  }
  captchaCode.value = result;
};

const onFinish = async (values) => {
  // 验证码校验
  if (formState.captcha.toLowerCase() !== captchaCode.value.toLowerCase()) {
    message.error('验证码不正确！');
    generateCaptcha(); // 刷新验证码
    return;
  }

  loading.value = true;
  try {
    const response = await api.registerAdmin(values);
    if (response.data && response.data.code === 200) {
      message.success('注册成功！现在您可以登录了。', 2);
      router.push('/login');
    } else {
      message.error(response.data.message || '注册失败，请稍后再试。');
    }
  } catch (error) {
    message.error(error.response?.data?.message || '注册请求失败，请检查网络。');
  } finally {
    loading.value = false;
  }
};

// 组件挂载时生成初始验证码
onMounted(() => {
  generateCaptcha();
});
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f2f5;
  background-image: url('@/assets/homepage-banner-v3.svg');
  background-size: cover;
}

.register-card {
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.card-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
}

.logo-img {
  width: 64px;
  height: 64px;
}

.captcha-wrapper {
  display: flex;
  align-items: center;
  gap: 8px;
}

.captcha-input {
  flex-grow: 1;
}

.captcha-code {
  width: 100px;
  height: 40px; /* 与 antd input 高度保持一致 */
  line-height: 40px;
  text-align: center;
  background-color: #eee;
  border: 1px solid #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  user-select: none;
  font-family: 'Courier New', Courier, monospace;
  font-size: 20px;
  font-weight: bold;
  letter-spacing: 2px;
  transition: background-color 0.3s;
}

.captcha-code:hover {
  background-color: #e0e0e0;
}

.back-to-login {
  text-align: center;
}

.back-to-login a {
  color: #1890ff;
  text-decoration: none;
}
</style> 