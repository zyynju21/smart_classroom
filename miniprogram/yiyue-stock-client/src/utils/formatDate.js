import moment from 'moment'

/**
 * 获取当前时间
 * @returns {moment.Moment}
 */
export const getNowDate = () => {
  return moment(new Date(), 'YYYY-MM-DD HH:mm:ss')
}

/**
 * 当前时间之前的几天
 * @param number
 * @returns {moment.Moment}
 */
export const getPreDayDate = (number) => {
  const curTime = new Date().getTime()
  const date2 = curTime - number * 3600 * 24 * 1000
  const nDate = new Date(date2)
  return moment(nDate)
}

/**
 * 当前时间之前的一个月
 * @returns {moment.Moment}
 */
export const getBeforeDate = () => {
  const date = new Date()
  let time = date.getTime()
  time -= 30 * 86400000
  const nDate = new Date(time)
  return moment(nDate.toLocaleDateString())
}

/**
 * 当前时间之前的几周
 * @param number
 * @returns {*|moment.Moment}
 */
export const getPreWeekDate = (number) => {
  const nowDate = new Date().getTime()
  const date2 = nowDate - number * 7 * 24 * 60 * 60 * 1000
  const nDate = new Date(date2)
  return moment(nDate)
}

/**
 * 当前时间之前的几年
 * @param number
 * @returns {*|moment.Moment}
 */
export const getBeforeYearDate = (number) => {
  const date = new Date()
  date.setFullYear(date.getFullYear() - number)
  return moment(new Date(date))
}

/**
 * 获取指定时间那天的 00:00:00开始的时间
 * @param data
 * @returns {string}
 */
export const getStartDate = (data) => {
  if (!data) {
    return ''
  }
  return data.format('YYYY-MM-DD') + ' 00:00:00'
}

/**
 * 获取指定时间的 23:59:59结束的时间
 * @param data
 * @returns {string}
 */
export const getEndDate = (data) => {
  if (!data) {
    return ''
  }
  return data.format('YYYY-MM-DD') + ' 23:59:59'
}

/**
 * 格式化时间
 * @param data
 * @param format
 */
export const formatDate = (data, format) => {
  if (!data) {
    return ''
  }
  return data.format(format)
}
