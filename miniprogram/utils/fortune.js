// utils/fortune.js
// 运势计算算法（前端直接计算）

const colors = ['红色', '橙色', '黄色', '绿色', '青色', '蓝色', '紫色', '粉色', '金色', '银色'];
const suitableList = ['签约', '出行', '投资', '表白', '理发', '搬家', '开业', '旅游', '学习', '健身'];
const avoidList = ['冲动消费', '争吵', '熬夜', '高风险投资', '迟到', '暴饮暴食', '拖延', '借钱', '八卦'];
const times = ['卯时(5-7点)', '辰时(7-9点)', '巳时(9-11点)', '午时(11-13点)', '未时(13-15点)', '申时(15-17点)'];

// 简单哈希函数
function simpleHash(str) {
  let hash = 0;
  for (let i = 0; i < str.length; i++) {
    const char = str.charCodeAt(i);
    hash = ((hash << 5) - hash) + char;
    hash = hash & hash;
  }
  return Math.abs(hash);
}

// 计算运势
function calculateFortune(birthday, date) {
  const seed = birthday + date;
  const hash = simpleHash(seed);
  
  return {
    overall: 60 + (hash % 41),
    career: 50 + ((hash * 7) % 51),
    wealth: 50 + ((hash * 11) % 51),
    love: 50 + ((hash * 13) % 51),
    health: 50 + ((hash * 17) % 51),
    luckyColor: colors[hash % colors.length],
    luckyNumber: (hash % 99) + 1,
    luckyTime: times[hash % times.length],
    suitable: suitableList[hash % suitableList.length],
    avoid: avoidList[(hash * 3) % avoidList.length]
  };
}

// 获取今日运势
function getTodayFortune(birthday) {
  const today = new Date().toISOString().split('T')[0];
  return {
    date: today,
    fortune: calculateFortune(birthday, today)
  };
}

// 获取指定日期运势
function getFortuneByDate(birthday, date) {
  return {
    date,
    fortune: calculateFortune(birthday, date)
  };
}

module.exports = {
  calculateFortune,
  getTodayFortune,
  getFortuneByDate
};
