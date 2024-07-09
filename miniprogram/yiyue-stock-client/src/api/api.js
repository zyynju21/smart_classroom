import { axios } from '@/utils/request'

/* 封装get方法 */
export function GET (url, params = {}) {
  return new Promise((resolve, reject) => {
    axios.get(url, {
      params: params
    }).then(response => {
      resolve(response.data)
    }).catch(err => {
      reject(err)
    })
  })
}

/* 封装post方法 */
export function POST (url, params) {
  return new Promise((resolve, reject) => {
    axios.post(url, params).then(response => {
      resolve(response)
    }).catch(err => {
      reject(err)
    })
  })
}

/* 封装put方法 */
export function PUT (url, params = {}) {
  return new Promise((resolve, reject) => {
    axios.put(url, params).then(response => {
      resolve(response.data)
    }).catch(err => {
      reject(err)
    })
  })
}

/* 封装delete方法 */
export function DELETE (url, params) {
  return new Promise((resolve, reject) => {
    axios.delete(url, {
      params: params
    }).then(response => {
      resolve(response.data)
    }).catch(err => {
      reject(err)
    })
  })
}

/* 序列化JsonTOFormData  */
export function PARSE_JSON_TO_FORM_DATA (data = {}) {
  if (data) {
    const params = new FormData()
    for (const key in data) {
      params.append(key, data[key])
    }
    return params
  }
  return null
}

/*
 * @param url 请求地址 也可以 + 参数
 * @param theFileName 下载文件名称
 * @param params 请求参数
 * @returns {Promise<unknown>}
 */
export function DOWNLOAD_FILE (url, params, theFileName) {
  return new Promise((resolve, reject) => {
    axios({
      method: 'post',
      url: url,
      responseType: 'blob',
      data: params
    }).then(res => {
      const fileName = theFileName // 下载文件名称
      const blob = new Blob([res], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'
      }) // new Blob([res])中不加data就会返回下图中[objece objece]内容（少取一层）
      if (!!window.ActiveXObject || 'ActiveXObject' in window) {
        // 判断是不是ie的浏览器
        window.navigator.msSaveOrOpenBlob(blob, fileName + '.xls')
        resolve(res)
      } else {
        const elink = document.createElement('a')
        elink.download = fileName
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        // 释放URL 对象
        URL.revokeObjectURL(elink.href)
        document.body.removeChild(elink)
        resolve(res)
      }
    }).catch(err => reject(err))
  })
}
