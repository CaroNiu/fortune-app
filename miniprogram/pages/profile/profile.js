// pages/profile/profile.js
const app = getApp();

Page({
  data: {
    userInfo: null
  },

  onLoad() {
    const userInfo = app.globalData.userInfo;
    this.setData({ userInfo });
  },

  onShow() {
    const userInfo = app.globalData.userInfo;
    this.setData({ userInfo });
  },

  showAbout() {
    wx.showModal({
      title: '关于每日运势',
      content: '每日运势小程序 v1.0\n\n基于您的生日生成每日运势，记录打卡生活。\n\n使用微信云开发，数据安全可靠。\n\n运势仅供娱乐，请理性看待。',
      showCancel: false
    });
  },

  clearData() {
    wx.showModal({
      title: '清除本地数据',
      content: '确定要清除所有本地数据吗？\n（云端数据不受影响）',
      success: (res) => {
        if (res.confirm) {
          wx.clearStorage();
          wx.showToast({ title: '已清除', icon: 'success' });
        }
      }
    });
  },

  logout() {
    wx.showModal({
      title: '退出登录',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          app.globalData.userInfo = null;
          wx.removeStorageSync('userInfo');
          this.setData({ userInfo: null });
          wx.showToast({ title: '已退出', icon: 'success' });
        }
      }
    });
  },

  goToIndex() {
    wx.switchTab({
      url: '/pages/index/index'
    });
  }
});
