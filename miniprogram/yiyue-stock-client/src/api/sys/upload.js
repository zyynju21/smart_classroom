import path from './index'
import * as API from '../api'

export function deletedFile (params) {
  return API.DELETE(path.deleteFile, params)
}
