// utils/db.js
// 云数据库操作封装

const db = wx.cloud.database();
const _ = db.command;

module.exports = {
  // 用户相关
  users: {
    // 查找或创建用户
    async findOrCreate(nickname, birthday) {
      try {
        // 先查找
        const { data } = await db.collection('users')
          .where({ nickname, birthday })
          .get();
        
        if (data.length > 0) {
          return { success: true, user: data[0] };
        }
        
        // 创建新用户
        const result = await db.collection('users').add({
          data: {
            nickname,
            birthday,
            createTime: new Date()
          }
        });
        
        return {
          success: true,
          user: {
            _id: result._id,
            nickname,
            birthday
          }
        };
      } catch (err) {
        console.error('用户操作失败:', err);
        return { success: false, error: err.message };
      }
    },

    // 获取用户信息
    async getById(userId) {
      try {
        const { data } = await db.collection('users').doc(userId).get();
        return { success: true, user: data };
      } catch (err) {
        return { success: false, error: err.message };
      }
    }
  },

  // 打卡记录相关
  checkins: {
    // 打卡
    async create(userId, date, fortune) {
      try {
        // 检查是否已打卡
        const { data } = await db.collection('checkins')
          .where({ userId, date })
          .get();
        
        if (data.length > 0) {
          return { success: false, error: '今日已打卡' };
        }

        await db.collection('checkins').add({
          data: {
            userId,
            date,
            fortune,
            createTime: new Date()
          }
        });
        
        return { success: true };
      } catch (err) {
        console.error('打卡失败:', err);
        return { success: false, error: err.message };
      }
    },

    // 检查今日是否已打卡
    async hasCheckedIn(userId, date) {
      try {
        const { data } = await db.collection('checkins')
          .where({ userId, date })
          .get();
        return data.length > 0;
      } catch (err) {
        return false;
      }
    },

    // 获取用户所有打卡记录
    async getByUserId(userId) {
      try {
        const { data } = await db.collection('checkins')
          .where({ userId })
          .orderBy('date', 'desc')
          .get();
        return { success: true, checkins: data };
      } catch (err) {
        return { success: false, error: err.message };
      }
    },

    // 获取指定月份打卡记录
    async getByMonth(userId, month) {
      try {
        const { data } = await db.collection('checkins')
          .where({
            userId,
            date: db.RegExp({
              regexp: `^${month}`,
              options: 'i'
            })
          })
          .orderBy('date', 'asc')
          .get();
        return { success: true, checkins: data };
      } catch (err) {
        return { success: false, error: err.message };
      }
    }
  },

  // 统计数据
  async getStats(userId) {
    try {
      const { data: checkins } = await db.collection('checkins')
        .where({ userId })
        .orderBy('date', 'desc')
        .get();

      const totalCheckins = checkins.length;
      
      if (totalCheckins === 0) {
        return {
          success: true,
          stats: {
            totalCheckins: 0,
            streakDays: 0,
            maxStreak: 0,
            avgScore: 0
          }
        };
      }

      // 计算平均运势
      const avgScore = Math.round(
        checkins.reduce((sum, c) => sum + c.fortune.overall, 0) / totalCheckins
      );

      // 计算连续打卡天数
      let streakDays = 0;
      let maxStreak = 0;
      let currentStreak = 0;
      
      const today = new Date().toISOString().split('T')[0];
      const yesterday = new Date(Date.now() - 86400000).toISOString().split('T')[0];
      
      const dateList = checkins.map(c => c.date);
      let checkDate = dateList.includes(today) ? today : yesterday;
      
      while (dateList.includes(checkDate)) {
        streakDays++;
        const prev = new Date(new Date(checkDate) - 86400000).toISOString().split('T')[0];
        checkDate = prev;
      }

      // 计算最高连续
      const sortedDates = [...dateList].sort();
      for (let i = 0; i < sortedDates.length; i++) {
        if (i === 0) {
          currentStreak = 1;
        } else {
          const diff = (new Date(sortedDates[i]) - new Date(sortedDates[i-1])) / 86400000;
          if (diff === 1) {
            currentStreak++;
          } else {
            maxStreak = Math.max(maxStreak, currentStreak);
            currentStreak = 1;
          }
        }
      }
      maxStreak = Math.max(maxStreak, currentStreak);

      return {
        success: true,
        stats: {
          totalCheckins,
          streakDays,
          maxStreak,
          avgScore
        }
      };
    } catch (err) {
      return { success: false, error: err.message };
    }
  }
};
