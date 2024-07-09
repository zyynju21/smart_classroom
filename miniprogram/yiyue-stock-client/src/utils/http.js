import { axios } from '@/utils/request'

const http = {
  post (url, json) {
    return new Promise((resolve, reject) => {
      axios.post(url, json).then(response => {
        resolve(response)
      }).catch(err => {
        reject(err)
      })
    })
  },
  get (url, json) {
    return new Promise((resolve, reject) => {
      axios.get(url, json).then(response => {
        resolve(response.data)
      }).catch(err => {
        reject(err)
      })
    })
  }
}
export default http
