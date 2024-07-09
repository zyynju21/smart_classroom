<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingSysUserList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="sysUserDTO" :model="sysUserDTO" :wrapper-col="{span:24}">
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input v-model="sysUserDTO.userName" :placeholder="$t('sysUser.user_name')" allow-clear />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input v-model="sysUserDTO.loginName" :placeholder="$t('sysUser.login_name')" allow-clear />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-select v-model="sysUserDTO.userSex" :placeholder="$t('sysUser.user_sex')" allow-clear>
              <a-select-option value="男">男</a-select-option>
              <a-select-option value="女">女</a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input v-model="sysUserDTO.phone" :placeholder="$t('sysUser.phone')" allow-clear />
          </a-form-model-item>
        </a-col>
        <a-col
          v-show="orgType === 0"
          :xxl="3"
          :xl="3"
          :lg="3"
          :md="3"
          :sm="24">
          <a-form-model-item>
            <a-input v-model="sysUserDTO.orgName" :placeholder="$t('sysOrgan.org_name')" allow-clear />
          </a-form-model-item>
        </a-col>
      </a-form-model>
    </search-bar>
    <!-- 表格 -->
    <a-row>
      <a-col
        :xxl="leftWidth"
        :xl="leftWidth"
        :lg="leftWidth"
        :md="leftWidth"
        :sm="24"
        v-if="orgType === 0">
        <a-card style="width:100%;">
          <div style="max-height: 300px;overflow-y:auto;">
            <a-tree
              :tree-data="sysOrganTree"
              :defaultExpandAll="true"
              :defaultSelectedKeys="defaultSelectedKeys"
              @select="onOrganSelect"
              v-if="!loading"
            >
            </a-tree>
          </div>
        </a-card>
      </a-col>
      <a-col :xxl="rightWidth" :xl="rightWidth" :lg="rightWidth" :md="rightWidth" :sm="24">
        <div style="margin-left: 16px;">
          <a-table
            :loading="loading"
            :data-source="sysUserList.records"
            :columns="userColumns"
            :rowKey="record => record.userId"
            :pagination="false"
            @change="handleTableChange"
          >
            <!-- <span slot="userState" slot-scope="userState">
              <a-tag v-if="userState === 0" color="green">{{ $t('sysuser.user_state_enable') }}</a-tag>
              <a-tag v-if="userState === -1" color="gray">{{ $t('sysuser.user_state_disable') }}</a-tag>
            </span> -->
            <template slot="headImgUrl" slot-scope="headImgUrl">
              <div v-if="headImgUrl !== null && headImgUrl !== ''" class="head_img">
                <img :src="axios.defaults.baseURL+'/'+headImgUrl" alt="" width="40" height="40">
              </div>
              <div v-else class="head_img">
                <img src="../../assets/default_head.png" alt="" width="40" height="40">
              </div>
            </template>
            <template slot="operation" slot-scope="text, record">
              <a href="javascript:;">
                <a-icon type="setting" :title="$t('button.userSetRole')" @click="handleSettingUser(record.userId, record.userName)" class="blue-green-text"/>
              </a>
              <a-divider type="vertical" />
              <a href="javascript:;">
                <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateUser(record.userId)" class="blue-green-text"/>
              </a>
              <a-divider type="vertical" />
              <a href="javascript:;">
                <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteUser(record.userId)" class="blue-green-text"/>
              </a>
            </template>
          </a-table>
        </div>
      </a-col>
    </a-row>
    <!-- 分页 -->
    <a-row>
      <a-col>
        <footer-pagination
          @onChangePage="onChangePage"
          @onShowSizeChange="onShowSizeChange"
          :pageConfig="sysUserList"
        >
          <a-button type="primary" @click="handleToCreateSysUser">
            <a-icon type="plus"/>
            {{ $t('button.add') }}
          </a-button>
        </footer-pagination>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import BreadCrumb from '@/components/tools/Breadcrumb'
  import SearchBar from '../components/top_search_bar/TopSearchBar'
  import FooterPagination from '../components/footer_pagination/FooterPagination'
  import { getSystemUserList, deleteSystemUser } from '../../api/sys/sys_user.js'

  export default {
    components: {
      BreadCrumb,
      SearchBar,
      FooterPagination
    },
    data () {
      return {
        loading: false,
        orgId: 0,
        orgType: undefined,
        leftWidth: 4,
        rightWidth: 20,
        sysOrganTree: [],
        defaultSelectedKeys: [],
        sysUserDTO: {
          userName: '',
          loginName: '',
          phone: '',
          userSex: undefined,
          orgName: ''
        },
        sysUserList: {
          total: 0,
          size: 10,
          current: 0,
          records: []
        },
        orderBy: {
          // 排序列
          sortField: '',
          // 排序规则
          sortOrder: ''
        }
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
          {
            title: this.$t('sysUser.login_name'),
            dataIndex: 'loginName',
            sorter: true,
            columnKey: 'login_name',
            align: 'left'
          },
          {
            title: this.$t('sysUser.head_img_url'),
            dataIndex: 'headImgUrl',
            sorter: true,
            columnKey: 'head_img_url',
            scopedSlots: { customRender: 'headImgUrl' }
          },
          {
            title: this.$t('sysUser.user_sex'),
            dataIndex: 'userSex',
            sorter: true,
            columnKey: 'user_sex'
          },
          {
            title: this.$t('sysUser.birth'),
            dataIndex: 'birth',
            sorter: true,
            columnKey: 'birth'
          },
          {
            title: this.$t('sysUser.phone'),
            dataIndex: 'phone',
            sorter: true,
            columnKey: 'phone'
          },
          {
            title: this.$t('sysUser.address'),
            dataIndex: 'address',
            sorter: true,
            columnKey: 'address'
          }
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
      this.sysUserDTO = this.getCondition(this.$route.path, this.sysUserDTO)
      this.sysUserList = this.getCondition(this.$route.path, this.sysUserList)
      this.loadingSysUserList(this.sysUserList.current)
    },
    methods: {
      loadingSysUserList (current) {
        this.loading = true
        this.saveCondition(this.$route.path, Object.assign({}, this.sysUserDTO, this.sysUserList))
        getSystemUserList({
          current: current || this.sysUserList.current,
          size: this.sysUserList.size,
          sortField: this.orderBy.sortField,
          sortOrder: this.orderBy.sortOrder,
          userName: this.sysUserDTO.userName,
          loginName: this.sysUserDTO.loginName,
          phone: this.sysUserDTO.phone,
          userSex: this.sysUserDTO.userSex,
          orgName: this.sysUserDTO.orgName,
          orgId: this.orgId
        }).then(res => {
          // console.log('sysUserList:', res.data)
          this.sysOrganTree = res.data.sysOrganTree
          this.sysUserList = res.data.sysUserList
          this.orgType = res.data.orgType
          if (this.orgType === 0) {
            this.leftWidth = 4
            this.rightWidth = 20
          } else {
            this.leftWidth = 0
            this.rightWidth = 24
          }
        }).finally(() => {
          this.loading = false
        })
      },
      /* 点击页码加载当前页的数据 */
      onChangePage (current) {
        this.sysUserList.current = current
        this.loadingSysUserList(current)
      },
      /* 切换每页显示记录数 */
      onShowSizeChange (current, size) {
        this.sysUserList.size = size
        this.loadingSysUserList(current)
      },
      /* 表格根据字段排序 */
      handleTableChange (pagination, filters, sorter) {
        if (sorter.column) {
          if (sorter.column.columnKey) {
            this.orderBy.sortField = sorter.column.columnKey
          } else {
            this.orderBy.sortField = sorter.columnKey
          }
          this.orderBy.sortOrder = sorter.order
        } else {
          this.orderBy = {
            sortField: '',
            sortOrder: ''
          }
        }
        this.loadingSysUserList(1)
      },
      /* 重置查询条件 */
      handleToRemoveSearch () {
        this.sysUserDTO.userName = ''
        this.sysUserDTO.loginName = ''
        this.sysUserDTO.phone = ''
        this.sysUserDTO.userSex = undefined
        this.sysUserDTO.orgName = ''
        this.removeCondition(this.$router.path)
        this.loadingSysUserList(1)
      },
      // 删除
      handleDeleteUser (userId) {
        const _this = this
        this.$confirm({
          okText: this.$t('button.confirm'),
          cancelText: this.$t('button.cancel'),
          title: this.$t('message.delete_confirm'),
          onOk () {
            _this.executeDeleteSysUser(userId)
          }
        })
      },
      executeDeleteSysUser (userId) {
        deleteSystemUser(userId).then(() => {
          this.$success({
            title: this.$t('message.delete_success')
          })
          this.loadingSysUserList(this.sysUserList.current)
        })
      },
      /* 创建角色 */
      handleToCreateSysUser () {
        this.$router.push({ path: '/system/user/detail/create' })
      },
      /* 修改角色 */
      handleUpdateUser (userId) {
        this.$router.push({ path: '/system/user/detail/edit/' + userId })
      },
      handleSettingUser (userId, userName) {
        this.$router.push({ path: '/system/user/detail/setting/' + userId + '/' + userName })
      },
      onOrganSelect (selectedKeys, info) {
        // console.log('selectedKeys', selectedKeys[0])
        this.orgId = selectedKeys[0]
        this.defaultSelectedKeys = selectedKeys
        this.loadingSysUserList(this.sysUserList.current)
      }
    }
  }
</script>
<style lang="less" scoped>
  @import url('~@/assets/less/qm-erp-list.less');
  .head_img{
    padding: 4px;
  }
</style>
