// src/mock/dashboard.js
export default {
  getSummaryData() {
    return {
      registerToday: {
        count: 128,
        amount: 56480
      },
      paymentToday: {
        count: 96,
        amount: 238500
      },
      // 新增7天趋势数据
      registerCountTrend: [120, 132, 101, 134, 90, 110, 85],  // 挂号人数趋势
      registerAmountTrend: [45000, 52000, 38000, 55000, 42000, 48000, 35000],  // 挂号金额趋势
      paymentCountTrend: [85, 92, 78, 105, 88, 95, 70],  // 缴费人数趋势
      paymentAmountTrend: [220000, 182000, 195000, 210000, 175000, 165000, 145000]  // 缴费金额趋势
    }
  },
  getChartsData() {
    return {
      registerTrend: [120, 132, 101, 134, 90, 230, 210],  // 挂号人数趋势(图表用)
      paymentTrend: [220, 182, 191, 234, 290, 330, 310],  // 缴费人数趋势(图表用)
      dates: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']  // 日期数据
    }
  },
  // 新增获取完整趋势数据的方法
  getFullTrendData() {
    const dates = ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    return {
      registerCountData: dates.map((date, index) => ({
        date,
        count: this.getSummaryData().registerCountTrend[index],
        percentage: Math.round(this.getSummaryData().registerCountTrend[index] / 2) // 计算百分比
      })),
      registerAmountData: dates.map((date, index) => ({
        date,
        amount: this.getSummaryData().registerAmountTrend[index],
        percentage: Math.round(this.getSummaryData().registerAmountTrend[index] / 1000) // 计算百分比
      })),
      paymentCountData: dates.map((date, index) => ({
        date,
        count: this.getSummaryData().paymentCountTrend[index],
        percentage: Math.round(this.getSummaryData().paymentCountTrend[index] / 1.5) // 计算百分比
      })),
      paymentAmountData: dates.map((date, index) => ({
        date,
        amount: this.getSummaryData().paymentAmountTrend[index],
        percentage: Math.round(this.getSummaryData().paymentAmountTrend[index] / 5000) // 计算百分比
      }))
    }
  }
}