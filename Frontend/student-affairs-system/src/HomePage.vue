<template>
  <div class="page-wrapper">
    <!-- Header -->
    <header class="header">
      <div class="container">
        <div class="logo">
          <img src="@/assets/adminlogo.svg" alt="Logo" />
          <span>学工系统</span>
        </div>
        <nav class="nav">
          <a href="#features">功能</a>
          <a href="#efficiency">效率</a>
          <a href="#testimonials">评价</a>
          <a href="#faq">FAQ</a>
        </nav>
        <div class="auth-buttons">
          <button class="btn btn-secondary" @click="goToLogin">登录</button>
          <button class="btn btn-primary" @click="goToRegister">免费注册</button>
        </div>
      </div>
    </header>

    <main>
      <!-- Hero Section -->
      <section class="hero">
        <div class="hero-video-background">
          <video autoplay muted loop playsinline>
            <source src="@/assets/index.mp4" type="video/mp4">
            Your browser does not support the video tag.
          </video>
          <div class="hero-overlay"></div>
        </div>
        <div class="container hero-container">
          <div class="hero-content">
            <p class="eyebrow">学习体验的革新者</p>
            <h1>终极定制化<br>在线学习平台</h1>
            <p class="hero-subtext">我们致力于通过前沿技术，将管理与学习过程变得像游戏一样有趣和高效。</p>
          </div>
        </div>
      </section>

       <!-- Partners Section -->
      <section class="partners">
        <div class="container">
          <h2 class="section-title">我们的合作伙伴</h2>
          <div class="partner-logos">
            <span>Doubao</span>
            <span>Xiaoins</span>
            <span>Gemini2.5pro</span>
            <span>Deepseek</span>
            <span>IntelliJ IDEA</span>
            <span>Vscode</span>
        </div>
        </div>
      </section>

      <!-- Features Section -->
      <section id="features" class="features-section">
        <div class="container">
          <h2 class="section-title">顶级的系统功能</h2>
          <div class="feature-card-row">
            <div class="feature-card">
              <div class="feature-content">
                <h3>个性化学习路径</h3>
                <p>基于学生的学习数据和兴趣，智能推荐课程和活动，打造专属成长路线。</p>
              </div>
              <div class="feature-image">
                <img src="@/assets/Personalized learning path.png" alt="Feature 1"/>
              </div>
            </div>
             <div class="feature-card reverse">
              <div class="feature-content">
                <h3>高级分析与报告</h3>
                <p>为教师和管理员提供深入的数据洞察，实时跟踪学生进度，优化教学策略。</p>
              </div>
              <div class="feature-image">
                <img src="@/assets/Advanced Analysis and Reporting.png" alt="Feature 2"/>
              </div>
            </div>
             <div class="feature-card">
              <div class="feature-content">
                <h3>移动端友好访问</h3>
                <p>随时随地通过手机或平板访问，确保学习和管理不间断。</p>
              </div>
              <div class="feature-image">
                <img src="@/assets/Mobile - friendly access.png" alt="Feature 3"/>
              </div>
            </div>
          </div>
        </div>
      </section>
      
      <!-- Efficiency Section -->
      <section id="efficiency" class="efficiency-section">
        <div class="container efficiency-container">
          <div class="efficiency-content">
            <h2 class="section-title">不言而喻的高效率</h2>
            <p>我们专注于每一个细节，以确保为您提供最流畅、最高效的管理与学习体验。</p>
          </div>
          <div class="efficiency-grid">
            <div class="efficiency-card"><h4>直观的用户界面</h4><p>精心设计的界面，无需培训即可轻松上手。</p></div>
            <div class="efficiency-card"><h4>自主学习支持</h4><p>激励学生自主探索，培养终身学习能力。</p></div>
            <div class="efficiency-card"><h4>高性价比方案</h4><p>以合理的价格，享受顶级的教育技术服务。</p></div>
            <div class="efficiency-card"><h4>学生与教师联动</h4><p>促进师生间的有效沟通，共同进步。</p></div>
          </div>
        </div>
      </section>

      <!-- Testimonials Section -->
      <section id="testimonials" class="testimonials-section">
        <div class="container">
          <h2 class="section-title">来自学生的声音</h2>
          <div class="testimonials-grid">
            <div class="testimonial-card">
              <p>"这个平台彻底改变了我的学习方式！游戏化的任务让我充满动力。"</p>
              <div class="author">
                <img src="https://placehold.co/40x40" alt="Avatar"/>
                <span>张伟, 大学生</span>
              </div>
            </div>
             <div class="testimonial-card">
              <p>"数据分析功能非常强大，作为班干部，我能更好地了解同学们的学习状况。""</p>
              <div class="author">
                <img src="https://placehold.co/40x40" alt="Avatar"/>
                <span>李静, 学习委员</span>
              </div>
            </div>
             <div class="testimonial-card">
              <p>"移动端访问太方便了，随时随地都能处理事务和学习，再也不会错过重要通知。""</p>
              <div class="author">
                <img src="https://placehold.co/40x40" alt="Avatar"/>
                <span>王磊, 社团主席</span>
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- FAQ Section -->
      <section id="faq" class="faq-section">
        <div class="container">
          <h2 class="section-title">常见问题解答</h2>
          <div class="faq-accordion">
            <div class="faq-item" v-for="(item, index) in faqs" :key="index" @click="toggleFaq(index)">
              <div class="faq-question">
                {{ item.question }}
                <span class="faq-icon">{{ item.open ? '-' : '+' }}</span>
              </div>
              <div class="faq-answer" v-show="item.open">
                <p>{{ item.answer }}</p>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>

    <!-- Footer -->
    <footer class="footer">
      <div class="container">
        <div class="footer-content">
          <div class="footer-about">
            <div class="logo">
              <img src="@/assets/adminlogo.svg" alt="Logo" />
              <span>学工系统</span>
            </div>
            <p>致力于提供下一代学生事务管理与在线学习解决方案。</p>
          </div>
          <div class="footer-links">
            <h4>快速链接</h4>
            <ul>
              <li><a href="#features">功能</a></li>
              <li><a href="#testimonials">评价</a></li>
              <li><a href="/login">登录</a></li>
            </ul>
          </div>
          <div class="footer-subscribe">
            <h4>订阅我们的动态</h4>
            <div class="subscribe-form">
              <input type="email" placeholder="输入您的邮箱">
              <button class="btn btn-primary">订阅</button>
            </div>
          </div>
        </div>
        <div class="footer-bottom">
          <p>&copy; 2025 学工系统. All Rights Reserved.</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      faqs: [
        {
          question: '如何开始使用学工系统？',
          answer: '只需点击页面右上角的"免费注册"按钮，创建一个账户，即可立即体验我们为您量身打造的学习管理新方式。',
          open: true
        },
        {
          question: '系统是否支持移动设备？',
          answer: '是的，我们的平台完全响应式设计，您可以在任何设备上获得无缝的体验，包括桌面电脑、平板和智能手机。',
          open: false
        },
        {
          question: '我的数据安全吗？',
          answer: '我们采用行业最高标准的数据加密和隐私保护措施，确保您的个人信息和学习数据安全无忧。',
          open: false
        }
      ]
    }
  },
  methods: {
    goToLogin() {
      this.$router.push('/login');
    },
    goToRegister() {
      this.$router.push('/register');
    },
    toggleFaq(index) {
      this.faqs = this.faqs.map((item, i) => ({
        ...item,
        open: i === index ? !item.open : false
      }));
    }
  }
}
</script>

<style scoped>
:root {
  --primary-color: #4F46E5; /* A more vibrant indigo */
  --primary-light: #C7D2FE;
  --dark-blue: #1E3A8A; /* A richer dark blue */
  --text-color: #4B5563; /* Softer text color */
  --heading-color: #111827; /* Near-black for headings */
  --white-color: #FFFFFF;
  --light-gray: #F9FAFB;
  --border-color: #E5E7EB;
}

.page-wrapper {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
  background-color: var(--white-color);
  color: var(--text-color);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* Header */
.header {
  background: linear-gradient(to right, #ffffff, #f0f5ff);
  padding: 15px 0;
  border-bottom: 1px solid var(--border-color);
  position: sticky;
  top: 0;
  z-index: 1000;
  backdrop-filter: blur(10px);
  background-color: rgba(255,255,255,0.8);
}
.header .container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.logo {
  display: flex;
  align-items: center;
  font-weight: 600;
  font-size: 20px;
  color: var(--heading-color);
}
.logo img {
  height: 35px;
  margin-right: 10px;
}
.nav {
  display: flex;
  gap: 30px;
}
.nav a {
  text-decoration: none;
  color: var(--text-color);
  font-weight: 500;
  transition: color 0.3s ease;
}
.nav a:hover {
  color: var(--primary-color);
}
.auth-buttons {
  display: flex;
  gap: 15px;
}
.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 500;
  transition: all 0.3s ease;
}
.btn-primary {
  background: linear-gradient(to right, var(--primary-color), #6366F1);
  color: var(--white-color);
  box-shadow: 0 4px 15px 0 rgba(79, 70, 229, 0.4);
}
.btn-primary:hover {
  opacity: 0.95;
  box-shadow: 0 6px 20px 0 rgba(79, 70, 229, 0.5);
  transform: translateY(-1px);
}
.btn-secondary {
  background-color: var(--primary-light);
  color: var(--primary-color);
  font-weight: 600;
}
.btn-secondary:hover {
  background-color: #A5B4FC;
}

/* Hero Section */
.hero {
  position: relative;
  padding: 120px 0;
  color: var(--white-color);
  text-align: center;
  overflow: hidden;
}
.hero-video-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -2;
}
.hero-video-background video {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.hero-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(45deg, rgba(30, 58, 138, 0.7), rgba(79, 70, 229, 0.6));
  z-index: -1;
}
.hero-container {
  display: flex;
  align-items: center;
  gap: 50px;
}
.hero-content {
  flex: 1;
}
.hero-content .eyebrow {
  color: var(--white-color) !important;
  opacity: 0.9;
  font-weight: 600;
  margin-bottom: 10px;
}
.hero-content h1 {
  font-size: 52px;
  color: var(--white-color) !important;
  line-height: 1.2;
  margin-bottom: 20px;
  text-shadow: 0 2px 10px rgba(0,0,0,0.5);
}
.hero-subtext {
  font-size: 18px;
  line-height: 1.6;
  margin: 0 auto;
  max-width: 600px;
  opacity: 0.9;
  color: var(--white-color) !important;
}

/* Partners Section */
.partners {
  padding: 60px 0;
  background: var(--light-gray);
}
.partners .section-title {
    margin-bottom: 40px;
}
.partner-logos {
  display: flex;
  justify-content: space-around;
  align-items: center;
  gap: 20px;
  filter: grayscale(100%);
  opacity: 0.6;
}
.partner-logos span {
    font-size: 1.5rem;
    font-weight: 500;
}

/* General Section Styling */
.section-title {
  text-align: center;
  font-size: 36px;
  color: var(--heading-color);
  margin-bottom: 50px;
}
.features-section, .efficiency-section, .testimonials-section, .faq-section {
  padding: 80px 0;
}

/* Features Section */
.feature-card-row {
  display: flex;
  flex-direction: column;
  gap: 100px;
}
.feature-card {
  display: flex;
  align-items: center;
  gap: 50px;
}
.feature-card.reverse {
  flex-direction: row-reverse;
}
.feature-content {
  flex: 1;
}
.feature-content h3 {
  font-size: 28px;
  color: var(--heading-color);
  margin-bottom: 15px;
}
.feature-image {
  flex: 1;
}
.feature-image img {
  width: 100%;
  border-radius: 15px;
  transition: transform 0.4s ease, box-shadow 0.4s ease;
}

.feature-card:hover .feature-image img {
  transform: scale(1.03);
  box-shadow: 0 10px 30px rgba(30, 41, 59, 0.1);
}

/* Efficiency Section */
.efficiency-section {
    background: linear-gradient(to bottom, #F0F5FF, var(--white-color));
}
.efficiency-container {
    display: flex;
    align-items: center;
    gap: 50px;
}
.efficiency-content {
    flex: 1;
}
.efficiency-content .section-title {
    text-align: left;
}
.efficiency-grid {
    flex: 1;
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
}
.efficiency-card {
    background: linear-gradient(135deg, #f5f7fa, #c3cfe2);
    padding: 25px;
    border-radius: 15px;
}
.efficiency-card h4 {
    font-size: 18px;
    color: var(--heading-color);
    margin-bottom: 10px;
}

/* Testimonials Section */
.testimonials-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 30px;
}
.testimonial-card {
  background: var(--light-gray);
  padding: 30px;
  border-radius: 15px;
  border: 1px solid var(--border-color);
}
.testimonial-card p {
  font-size: 16px;
  line-height: 1.6;
  margin-bottom: 20px;
}
.author {
  display: flex;
  align-items: center;
  gap: 10px;
  font-weight: 600;
}
.author img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

/* FAQ Section */
.faq-accordion {
  max-width: 800px;
  margin: 0 auto;
}
.faq-item {
  border-bottom: 1px solid var(--border-color);
  padding: 20px 0;
  cursor: pointer;
}
.faq-question {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: 600;
  color: var(--heading-color);
}
.faq-icon {
  font-size: 24px;
  color: var(--primary-color);
  transition: transform 0.3s ease;
}
.faq-item.open .faq-icon {
    transform: rotate(45deg);
}
.faq-answer {
  padding-top: 15px;
  line-height: 1.7;
}

/* Footer */
.footer {
  background: linear-gradient(135deg, var(--dark-blue), #1E2950);
  color: #a9b3c9;
  padding: 60px 0 20px;
}
.footer-content {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr;
  gap: 50px;
  margin-bottom: 40px;
}
.footer-about .logo {
  color: var(--white-color);
  margin-bottom: 20px;
}
.footer h4 {
  color: var(--white-color);
  font-size: 18px;
  margin-bottom: 20px;
}
.footer ul {
  list-style: none;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.footer a {
  text-decoration: none;
  color: #a9b3c9;
}
.subscribe-form {
  display: flex;
}
.subscribe-form input {
  flex-grow: 1;
  border: none;
  padding: 12px;
  border-radius: 8px 0 0 8px;
}
.subscribe-form button {
  border-radius: 0 8px 8px 0;
}
.footer-bottom {
  border-top: 1px solid #2c3a57;
  padding-top: 20px;
  text-align: center;
  font-size: 14px;
}
</style> 