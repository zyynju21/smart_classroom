/**
 * 保存查询条件
 * @param path
 * @param pars
 */
export const saveCondition = (path, pars) => {
  if (pars.records) {
    pars.records = []
  }
  sessionStorage.setItem(path, JSON.stringify({ path, pars }))
}

/**
 * 获取查询条件
 * @param path
 * @returns {Map<any, any>}
 */
export const getCondition = (path, query) => {
  const queryCondition = new Map()
  if (sessionStorage.getItem(path)) {
    const obj = JSON.parse(sessionStorage.getItem(path))
    queryCondition.set(obj.path, obj.pars)
  }
  if (queryCondition.has(path)) {
    query = queryCondition.get(path)
  }
  return query
}

/**
 * 清除查询条件
 * @param path
 */
export const removeCondition = (path) => {
  if (sessionStorage.getItem(path)) {
    sessionStorage.removeItem(path)
  }
}

/**
 *判断字符是否为空的方法
 */
export const isEmpty = (obj) => {
  if (typeof obj === 'undefined' || obj === null || obj === '') {
    return true
  } else {
    return false
  }
}

/**
 * 去除前后空格
 */
export const strTrim = (values) => {
  if (!isEmpty(values)) {
    for (const key in values) {
      values[key] = values[key].trim()
    }
  }
  return values
}
