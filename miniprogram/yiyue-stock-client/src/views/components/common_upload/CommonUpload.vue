<template>
  <div class="common-upload">
    <!--上传图片-->
    <div>
      <a-upload
        multiple
        list-type="picture"
        :fileList="fileList"
        :remove="removeUpload"
        :before-upload="beforeUpload"
        @preview="handlePreview"
        :customRequest="handleUploadFile"
        class="upload-list-inline"
      >
        <a-button v-if="fileList.length < maxUploadSize">
          <a-icon type="upload"/>
          {{ $t('button.upload') }}
        </a-button>
      </a-upload>
    </div>
    <!-- 查看图片模态框 -->
    <a-modal width="60%" :visible="previewVisible" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage"/>
    </a-modal>
  </div>
</template>

<script>
  import axios from 'axios'
  import api from '../../../api/sys/index'
  import fileRelate from '@/utils/fileRelate'
  import { deletedFile } from '../../../api/sys/upload'

  export default {
    name: 'CommonUpload',
    props: {
      // 上传文件最大个数
      maxUploadSize: {
        type: Number,
        default: 5
      },
      // 关联附件类型Code
      fileRelate: {
        type: String,
        default: undefined
      },
      // 回显文件数组
      files: {
        type: Array,
        default: () => {
          return []
        }
      }
    },
    data () {
      return {
        previewVisible: false,
        previewImage: '',
        fileList: []
      }
    },
    watch: {
      files: function () {
        if (this.files && this.files.length > 0) {
          this.formatToExistingFileList(this.files)
        }
      }
    },
    mounted () {
      if (this.files && this.files.length > 0) {
        this.formatToExistingFileList(this.files)
      }
    },
    methods: {
      // 文件上传
      handleUploadFile (data) {
        // 判断最大上传个数是否超过
        if (this.fileList.length >= this.maxUploadSize) {
          return false
        }
        const _this = this
        const file = data.file
        // 把文件拼接到上传处,显示进度条
        this.fileList.push(file)
        const formData = new FormData()
        // 添加文件
        formData.append('file', file)
        axios(
          {
            method: 'post',
            url: api.uploadFile,
            processData: false,
            data: formData
          })
          .then(res => {
            // 上传成功,删除上传中的状态
            _this.fileList = _this.fileList.filter(item => item.uid !== file.uid)
            _this.formatToFileList(res.data, file.uid, file.name, true)
            _this.$emit('receive-file-function', _this.fileList)
          })
          // eslint-disable-next-line handle-callback-err
          .catch(error => {
            // 上传失败,删除上传中的状态
            _this.fileList = _this.fileList.filter(item => item.uid !== file.uid)
            _this.$message.error('upload failed.')
          })
      },
      // 将后台返回的数组格式化成file放入fileList中
      formatToExistingFileList (fileList) {
        if (fileList.length <= 0) {
          return
        }
        fileList.forEach(file => {
          this.formatToFileList(file.filePath, file.filePath, file.originFileName, false)
        })
      },
      // 将后台返回的fileId数组格式化成file放入fileList中
      formatToFileList (fileId, uid, name, isNew) {
        const defaultUrl = process.env.VUE_APP_IMG_PREVIEW_URL + '/file/' + fileId
        const url = this.getFileThumbUrl(name, defaultUrl)
        const tempFile = {
          uid: uid,
          name: name,
          status: 'done',
          url: url,
          fileId: fileId,
          response: '{"status": "success"}',
          isNew: isNew
        }
        this.fileList.push(tempFile)
      },
      // 本页面方法获取 文件显示图标
      getFileThumbUrl (fileName, defaultUrl) {
        const suffixIndex = fileName.lastIndexOf('.')
        if (suffixIndex === -1) {
          // 没有扩展名 无法判断类型，直接返回
          return defaultUrl
        }
        const suffix = fileName.substring(suffixIndex + 1).toLowerCase().toString()
        if (suffix === 'jpg' || suffix === 'jpeg' || suffix === 'png' || suffix === 'bmp' || suffix === 'gif' ||
          suffix === 'tif' || suffix === 'pcx' || suffix === 'tga' || suffix === 'exif' || suffix === 'fpx' ||
          suffix === 'svg' || suffix === 'psd' || suffix === 'cdr' || suffix === 'pcd' || suffix === 'dxf' ||
          suffix === 'ufo' || suffix === 'eps' || suffix === 'ai' || suffix === 'raw' || suffix === 'wmf' || suffix === 'webp') {
          return defaultUrl
        }
        /// 后期处理其他类型
        /* if (suffix === 'xls' || suffix === 'xlsx') {
           return ''
         }
         if (suffix === 'doc' || suffix === 'docx') {
           return ''
         }
         if (suffix === 'ppt' || suffix === 'pptx') {
           return ''
         }
         if (suffix === 'pdf') {
           return ''
         }
         // 文件类型
         return '' */
      },
      // 文件上传之前进行效验 返回false即取消上传行为
      beforeUpload (file) {
        // 限制文件上传的类型
        /*  const isJPG = file.type === 'image/jpeg'
        if (!isJPG) {
          message.error('You can only upload JPG file!')
        } */
        // 限制文件上传的大小
        const isLt20M = file.size / 1024 / 1024 < 20
        if (!isLt20M) {
          this.$message.error('file must smaller than 20MB!')
        }
        // return isJPG && isLt2M
        return isLt20M
      },
      // 查看预览图片
      handlePreview (file) {
        // 获取文件的后缀名
        const fileName = file.name
        const suffixIndex = fileName.lastIndexOf('.')
        const suffix = fileName.substring(suffixIndex + 1).toLowerCase().toString()
        if (suffix === 'pdf') {
          /// 后期处理
          return false
        }
        if (suffix === 'xls' || suffix === 'xlsx' || suffix === 'doc' || suffix === 'docx' || suffix === 'ppt' || suffix === 'pptx') {
          /// 后期处理
          return false
        }
        this.previewImage = file.url || file.thumbUrl
        this.previewVisible = true
      },
      // 预览关闭图片
      handleCancel () {
        this.previewVisible = false
      },
      removeUpload (file) {
        this.handleDeleteFile(file)
        this.fileList = this.fileList.filter(item => item.uid !== file.uid)
        this.$emit('receive-file-function', this.fileList, file, true)
      },
      // 调用后台删除文件
      handleDeleteFile (file) {
        const _this = this
        deletedFile({
          fileId: file.fileId,
          fileRelateCode: file.isNew ? fileRelate.FileRelate.other.name : this.fileRelate
        }).then(() => {
          _this.$message.success(this.$t('message.delete_success'))
        })
      }
    }
  }
</script>

<style scoped>
  .upload-list-inline >>> .ant-upload-list-item {
    float: left;
    width: 200px;
    margin-right: 8px;
  }

  .upload-list-inline >>> .ant-upload-animate-enter {
    animation-name: uploadAnimateInlineIn;
  }

  .upload-list-inline >>> .ant-upload-animate-leave {
    animation-name: uploadAnimateInlineOut;
  }
</style>
