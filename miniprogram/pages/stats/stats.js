// pages/stats/stats.js
const db = require('../../utils/db.js');
const app = getApp();

Page({
  data: {
    userInfo: null,
    stats: {},
    checkins: []
  },

  onLoad() {
    const userInfo = app.globalData.userInfo;
    if (userInfo) {
      this.setData({ userInfo });
      this.loadStats();
    }
  },

  onShow() {
    const userInfo = app.globalData.userInfo;
    if (userInfo && !this.data.userInfo) {
      this.setData({ userInfo });
      this.loadStats();
    } else if (userInfo) {
      // 每次显示都刷新数据
      this.loadStats();
    }
  },

  async loadStats() {
    const userId = this.data.userInfo._id;
    
    wx.showLoading({ title: '加载中' });

    try {
      // 并行获取统计数据和打卡记录
      const [statsRes, checkinsRes] = await Promise.all([
        db.getStats(userId),
        db.checkins.getByUserId(userId)
      ]);

      this.setData({
        stats: statsRes.success ? statsRes.stats : {},
        checkins: checkinsRes.success ? checkinsRes.checkins : []
      });
    } catch (error) {
      wx.showToast({ title: error.message || '加载失败', icon: 'none' });
    } finally {
      wx.hideLoading();
    }
  },

  goToIndex() {
    wx.switchTab({
      url: '/pages/index/index'
    });
  }
});
