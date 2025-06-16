<template>
  <div class="settings-container">
    <div class="settings-header">
      <h1>系统设置</h1>
      <p>管理系统全局配置和核心功能</p>
    </div>

    <div class="settings-grid">
      <!-- 基本信息设置 -->
      <a-card hoverable title="基本信息" class="setting-card">
        <a-form layout="vertical">
          <a-form-item label="系统名称">
            <a-input v-model:value="settings.general.systemName" placeholder="例如：学工管理平台" />
          </a-form-item>
          <a-form-item label="系统Logo URL">
            <a-input v-model:value="settings.general.logoUrl" placeholder="输入新的Logo图片地址" />
          </a-form-item>
          <a-form-item>
            <a-button type="primary" :icon="h(SaveOutlined)" @click="saveSettings('general')">保存基本信息</a-button>
          </a-form-item>
        </a-form>
      </a-card>

      <!-- 安全设置 -->
      <a-card hoverable title="安全设置" class="setting-card">
        <a-form layout="vertical">
          <a-form-item label="当前密码">
            <a-input-password v-model:value="settings.security.currentPassword" placeholder="输入当前使用的密码" />
          </a-form-item>
          <a-form-item label="新密码">
            <a-input-password v-model:value="settings.security.newPassword" placeholder="输入新密码" />
          </a-form-item>
          <a-form-item label="确认新密码">
            <a-input-password v-model:value="settings.security.confirmPassword" placeholder="再次输入新密码" />
          </a-form-item>
          <a-form-item>
            <a-button type="primary" :icon="h(KeyOutlined)" @click="changePassword">更改密码</a-button>
          </a-form-item>
        </a-form>
      </a-card>

      <!-- 通知设置 -->
      <a-card hoverable title="通知设置" class="setting-card">
        <a-list item-layout="horizontal">
          <a-list-item>
            <a-list-item-meta title="邮件通知" description="当有重要事件发生时，发送邮件提醒" />
            <template #actions>
              <a-switch v-model:checked="settings.notifications.emailEnabled" />
            </template>
          </a-list-item>
          <a-list-item>
            <a-list-item-meta title="短信通知" description="紧急事务通过短信发送" />
            <template #actions>
              <a-switch v-model:checked="settings.notifications.smsEnabled" />
            </template>
          </a-list-item>
           <a-list-item>
            <a-list-item-meta title="站内信通知" description="在系统内接收通知消息" />
            <template #actions>
              <a-switch v-model:checked="settings.notifications.siteEnabled" />
            </template>
          </a-list-item>
        </a-list>
         <a-button type="primary" :icon="h(SaveOutlined)" @click="saveSettings('notifications')" style="margin-top: 24px;">保存通知设置</a-button>
      </a-card>

      <!-- 数据管理 -->
       <a-card hoverable title="数据管理" class="setting-card">
        <p>对系统数据进行备份和清理。</p>
        <a-space direction="vertical" style="width: 100%;">
          <a-button block :icon="h(DownloadOutlined)" @click="backupData">一键备份数据</a-button>
          <a-popconfirm
            title="确定要清除所有系统缓存吗？此操作不可逆！"
            ok-text="确定清除"
            cancel-text="取消"
            @confirm="clearCache"
          >
            <a-button block danger :icon="h(DeleteOutlined)">清除系统缓存</a-button>
          </a-popconfirm>
        </a-space>
      </a-card>
    </div>
  </div>
</template>

<script setup>
import { ref, h } from 'vue';
import {
  SaveOutlined,
  KeyOutlined,
  DownloadOutlined,
  DeleteOutlined,
} from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';

// 模拟的设置数据，实际应用中应从后端获取
const settings = ref({
  general: {
    systemName: '学工系统',
    logoUrl: '/src/assets/adminlogo.svg',
  },
  security: {
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  },
  notifications: {
    emailEnabled: true,
    smsEnabled: false,
    siteEnabled: true,
  },
});

const saveSettings = (type) => {
  // 模拟保存操作
  console.log(`Saving ${type} settings:`, settings.value[type]);
  message.success(`${type === 'general' ? '基本信息' : '通知设置'} 保存成功！`);
};

const changePassword = () => {
  const { currentPassword, newPassword, confirmPassword } = settings.value.security;
  if (!currentPassword || !newPassword || !confirmPassword) {
    message.error('所有密码字段都不能为空！');
    return;
  }
  if (newPassword !== confirmPassword) {
    message.error('新密码和确认密码不一致！');
    return;
  }
  // 模拟API调用
  console.log('Changing password...');
  message.success('密码修改成功！');
  // 清空密码字段
  settings.value.security.currentPassword = '';
  settings.value.security.newPassword = '';
  settings.value.security.confirmPassword = '';
};

const backupData = () => {
  message.loading('正在准备数据备份...', 1.5, () => {
    message.success('数据备份任务已开始，完成后将通知您。');
  });
};

const clearCache = () => {
    message.loading('正在清除缓存...', 1, () => {
    message.success('系统缓存已成功清除！');
  });
};
</script>

<style scoped>
.settings-container {
  padding: 24px;
  background-color: #f0f2f5;
}

.settings-header {
  margin-bottom: 24px;
}

.settings-header h1 {
  font-size: 24px;
  font-weight: 600;
}

.settings-header p {
  color: #888;
}

.settings-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(400px, 1fr));
  gap: 24px;
}

.setting-card {
  border-radius: 8px;
  transition: all 0.3s ease;
}

.setting-card:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
</style>
