// pages/index/index.js
const fortuneUtil = require('../../utils/fortune.js');
const db = require('../../utils/db.js');
const app = getApp();

Page({
  data: {
    userInfo: null,
    nickname: '',
    birthday: '',
    today: '',
    loading: false,
    fortune: {},
    hasCheckedIn: false,
    checkingIn: false,
    weekdays: ['日', '一', '二', '三', '四', '五', '六'],
    calendarDays: [],
    stats: {}
  },

  onLoad() {
    // 设置今天日期作为选择器上限
    const today = new Date().toISOString().split('T')[0];
    this.setData({ today });
    
    // 检查是否已登录
    const userInfo = app.globalData.userInfo;
    if (userInfo) {
      this.setData({ userInfo });
      this.loadFortuneData();
    }
  },

  onShow() {
    if (this.data.userInfo) {
      this.loadFortuneData();
    }
  },

  // 输入昵称
  onNicknameInput(e) {
    this.setData({ nickname: e.detail.value });
  },

  // 选择生日
  onBirthdayChange(e) {
    this.setData({ birthday: e.detail.value });
  },

  // 登录
  async login() {
    const { nickname, birthday } = this.data;
    
    if (!nickname || !birthday) {
      wx.showToast({ title: '请填写完整信息', icon: 'none' });
      return;
    }

    this.setData({ loading: true });

    try {
      const result = await db.users.findOrCreate(nickname, birthday);
      
      if (!result.success) {
        throw new Error(result.error);
      }

      const userInfo = {
        _id: result.user._id,
        nickname: result.user.nickname,
        birthday: result.user.birthday
      };
      
      app.globalData.userInfo = userInfo;
      wx.setStorageSync('userInfo', userInfo);
      
      this.setData({ userInfo, loading: false });
      this.loadFortuneData();
    } catch (error) {
      this.setData({ loading: false });
      wx.showToast({ title: error.message || '登录失败', icon: 'none' });
    }
  },

  // 切换用户
  switchUser() {
    wx.showModal({
      title: '提示',
      content: '确定要切换用户吗？',
      success: (res) => {
        if (res.confirm) {
          app.globalData.userInfo = null;
          wx.removeStorageSync('userInfo');
          this.setData({
            userInfo: null,
            nickname: '',
            birthday: '',
            fortune: {},
            hasCheckedIn: false,
            calendarDays: []
          });
        }
      }
    });
  },

  // 加载运势数据
  async loadFortuneData() {
    const { userInfo } = this.data;
    
    this.setData({ loading: true });

    try {
      // 1. 计算今日运势（前端直接算）
      const { fortune } = fortuneUtil.getTodayFortune(userInfo.birthday);

      // 2. 检查今日是否已打卡
      const today = new Date().toISOString().split('T')[0];
      const hasCheckedIn = await db.checkins.hasCheckedIn(userInfo._id, today);

      // 3. 获取打卡历史（用于日历）
      const month = this.getCurrentMonth();
      const checkinsResult = await db.checkins.getByMonth(userInfo._id, month);

      // 4. 获取统计数据
      const statsResult = await db.getStats(userInfo._id);

      this.setData({
        fortune,
        hasCheckedIn,
        stats: statsResult.success ? statsResult.stats : {},
        loading: false
      });

      // 渲染日历
      this.renderCalendar(checkinsResult.success ? checkinsResult.checkins : []);
    } catch (error) {
      this.setData({ loading: false });
      wx.showToast({ title: error.message || '加载失败', icon: 'none' });
    }
  },

  // 打卡
  async checkIn() {
    if (this.data.hasCheckedIn || this.data.checkingIn) return;

    this.setData({ checkingIn: true });

    try {
      const today = new Date().toISOString().split('T')[0];
      const result = await db.checkins.create(
        this.data.userInfo._id,
        today,
        this.data.fortune
      );

      if (!result.success) {
        throw new Error(result.error);
      }

      wx.showToast({
        title: '打卡成功！',
        icon: 'success'
      });

      // 刷新数据
      this.loadFortuneData();
    } catch (error) {
      wx.showToast({ title: error.message || '打卡失败', icon: 'none' });
    } finally {
      this.setData({ checkingIn: false });
    }
  },

  // 获取当前月份
  getCurrentMonth() {
    const now = new Date();
    return `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}`;
  },

  // 渲染日历
  renderCalendar(checkins) {
    const now = new Date();
    const year = now.getFullYear();
    const month = now.getMonth();
    const firstDay = new Date(year, month, 1).getDay();
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const today = now.getDate();

    const checkedDates = new Set(
      checkins.map(c => parseInt(c.date.split('-')[2]))
    );

    const calendarDays = [];

    // 空白填充
    for (let i = 0; i < firstDay; i++) {
      calendarDays.push({ day: '', date: '' });
    }

    // 日期
    for (let day = 1; day <= daysInMonth; day++) {
      calendarDays.push({
        day,
        date: `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`,
        checked: checkedDates.has(day),
        isToday: day === today
      });
    }

    this.setData({ calendarDays });
  }
});
