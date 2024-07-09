<template>
  <div>
    <a-input-search placeholder="请输入用户" @search="onSearch" readOnly v-model="value">
      <a-button slot="enterButton">
        选择用户
      </a-button>
    </a-input-search>
    <a-modal
      width="60%"
      title="选择用户"
      :visible="visible"
      :confirm-loading="confirmLoading"
      :footer="null"
      @cancel="handleCancel"
    >
      <a-form-model layout="inline" :model="sysUserDTO">
        <a-form-model-item>
          <a-input v-model="sysUserDTO.userName" placeholder="用户名">
            <a-icon slot="prefix" type="user" style="color:rgba(0,0,0,.25)"/>
          </a-input>
        </a-form-model-item>
        <a-form-model-item>
          <a-input v-model="sysUserDTO.phone" placeholder="手机">
            <a-icon slot="prefix" type="phone" style="color:rgba(0,0,0,.25)"/>
          </a-input>
        </a-form-model-item>
        <a-form-model-item>
          <a-button
            type="primary"
            html-type="submit"
            @click="searchData"
          >
            查询
          </a-button>
        </a-form-model-item>
      </a-form-model>
      <a-row>
        <a-col>
          <a-table
            :loading="loading"
            :data-source="sysUserList.records"
            :columns="userColumns"
            :rowKey="record=>record.userId"
            @change="handleTableChange"
          >
            <template slot="operation" slot-scope="text, record">
              <a-icon type="check" @click="handleOrganChoose(record.userId,record.userName)"/>
            </template>
          </a-table>
        </a-col>
      </a-row>
      <!--分页-->
      <a-row>
        <a-col>
          <footer-pagination
            @onChangePage="onChangePage"
            @onShowSizeChange="onShowSizeChange"
          ></footer-pagination>
        </a-col>
      </a-row>
    </a-modal>
  </div>
</template>

<script>
  import FooterPagination from '@/views/components/footer_pagination/FooterPagination'
  import TopSearchBar from '@/views/components/top_search_bar/TopSearchBar'
  import { getSystemAllUserList, getSystemUserList } from '@/api/sys/sys_user'

  export default {
    name: 'ChooseUser',
    components: {
      FooterPagination,
      TopSearchBar
    },
    props: {
      value: {
        type: String,
        default: ''
      }
    },
    data () {
      return {
        formInline: {
          user: '',
          password: '',
        },
        visible: false,
        confirmLoading: false,
        sysUserDTO: {},
        loading: false,
        select: {
          key: '',
          value: '',
        },
        sysUserList: {
          total: 0,
          size: 10,
          current: 0,
          records: []
        },
      }
    },

    computed: {
      userColumns () {
        const userColumn = [
          {
            title: this.$t('sysUser.user_name'),
            dataIndex: 'userName',
            sorter: true,
            columnKey: 'user_name',
            align: 'left'
          },
          /* {
             title: this.$t('sysUser.login_name'),
             dataIndex: 'loginName',
             sorter: true,
             columnKey: 'login_name',
             align: 'left'
           },*/
          /* {
             title: this.$t('sysUser.head_img_url'),
             dataIndex: 'headImgUrl',
             sorter: true,
             columnKey: 'head_img_url',
             scopedSlots: { customRender: 'headImgUrl' }
           },*/
          {
            title: this.$t('sysUser.user_sex'),
            dataIndex: 'userSex',
            sorter: true,
            columnKey: 'user_sex'
          },
          /* {
             title: this.$t('sysUser.birth'),
             dataIndex: 'birth',
             sorter: true,
             columnKey: 'birth'
           },*/
          {
            title: this.$t('sysUser.phone'),
            dataIndex: 'phone',
            sorter: true,
            columnKey: 'phone'
          }
          /* {
             title: this.$t('sysUser.address'),
             dataIndex: 'address',
             sorter: true,
             columnKey: 'address'
           }*/
        ]
        if (this.orgType === 0) {
          userColumn.push({
            title: this.$t('sysOrgan.org_name'),
            dataIndex: 'orgName',
            sorter: true,
            columnKey: 'org_name'
          })
        }
        userColumn.push({
          title: this.$t('button.operation'),
          dataIndex: 'operation',
          scopedSlots: { customRender: 'operation' }
        })
        return userColumn
      }
    },
    mounted () {
      this.loadingSysUserList(this.sysUserList.current)
    },
    methods: {
      searchData () {
        this.loadingSysUserList(1)
      },
      //加载用户数据
      loadingSysUserList (current) {
        this.loading = true
        getSystemAllUserList({
          current: current || this.sysUserList.current,
          size: this.sysUserList.size,
          /* sortField: this.orderBy.sortField,
           sortOrder: this.orderBy.sortOrder,*/
          userName: this.sysUserDTO.userName,
          phone: this.sysUserDTO.phone
        }).then(res => {
          this.sysUserList = res.data.sysAllUserList
        }).finally(() => {
          this.loading = false
        })
      },
      handleOrganChoose (id, userName) {
        this.select.key = id
        this.select.value = userName
        this.$emit('input', userName)
        this.$emit('selectUserName', this.select)
        this.visible = false
      },
      onSearch () {

        this.visible = true
      },
      onChangePage (current) {
        this.sysUserList.current = current
        this.loadingSysUserList(current)
      },
      onShowSizeChange (size) {
        this.sysUserList.size = size
        this.loadingSysUserList(current)
      },
      handleTableChange (pagination, filters, sorter) {

        console.log(sorter)
      },
      /*handleOk (e) {
        this.confirmLoading = true
        setTimeout(() => {
          this.visible = false
          this.confirmLoading = false
        }, 2000)
      },*/
      handleCancel (e) {
        console.log('Clicked cancel button')
        this.visible = false
      },
    }
  }
</script>

<style scoped>

</style>