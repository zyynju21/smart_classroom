<template>
  <a-upload
    name="file"
    list-type="picture-card"
    class="avatar-uploader"
    :show-upload-list="false"
    :action="axios.defaults.baseURL+'/file/upload'"
    :before-upload="beforeUpload"
    @change="handleChangePhoto"
  >
    <img v-if="imageUrl" :src="imageUrl?axios.defaults.baseURL+'/'+imageUrl:''" alt="avatar" width="128" height="128"/>
    <div v-else>
      <a-icon :type="loading ? 'loading' : 'plus'"/>
      <div class="ant-upload-text">
        Upload
      </div>
    </div>
  </a-upload>
</template>

<script>
  import {axios} from '@/utils/request'
  export default {
    name: 'SinglePhotoUpload',
    data () {
      return {
        loading: false,
        imageUrl: '',
      }
    },
    props: {
      defaultImg:{
        type:String,
        default: ''
      },
      defaultSize: {
        type: Number,
        default: 500
      }
    },
    watch:{
      defaultImg(newVal,oldVal){
        this.imageUrl=newVal
      }
    },
    methods: {
      /*上传图片检查*/
      beforeUpload (file) {
        const isImg = file.type === 'image/jpeg' || file.type === 'image/png'
        if (!isImg) {
          this.$message.error('请上传图片文件')
        }
        const isSize = file.size / 1024 < this.defaultSize
        if (!isSize) {
          this.$message.error('上传文件不能超过' + this.defaultSize + 'k')
        }
        return isImg && isSize
      },
      //
      handleChangePhoto (info) {
        if (info.file.status === 'uploading') {
          this.loading = true;
          return;
        }
        if (info.file.status === 'done') {
          this.imageUrl = info.file.response.data;
          this.loading = false;
        }
        this.$emit('fileUpload',this.imageUrl)
      },
    }
  }
</script>

<style>
  .avatar-uploader > .ant-upload {
    width: 128px;
    height: 128px;
  }

  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>