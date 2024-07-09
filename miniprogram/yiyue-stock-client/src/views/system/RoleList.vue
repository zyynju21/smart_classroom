<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingSysRoleList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="sysRoleDTO" :model="sysRoleDTO" :wrapper-col="{span:24}">
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="sysRoleDTO.roleName"
              :placeholder="$t('sysRole.role_name')"
              allow-clear
            />
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
            <a-input
              v-model="sysRoleDTO.orgName"
              :placeholder="$t('sysOrgan.org_name')"
              allow-clear
            />
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
            :data-source="sysRoleList.records"
            :columns="roleColumns"
            :rowKey="record => record.roleId"
            :pagination="false"
            @change="handleTableChange"
          >
            <!-- <span slot="roleState" slot-scope="roleState">
              <a-tag v-if="roleState === 0" color="green">{{ $t('sysRole.role_state_enable') }}</a-tag>
              <a-tag v-if="roleState === -1" color="gray">{{ $t('sysRole.role_state_disable') }}</a-tag>
            </span> -->
            <template slot="operation" slot-scope="text, record">
              <a href="javascript:;">
                <a-icon type="setting" :title="$t('button.roleSetMenu')" @click="handleSettingRole(record.roleId, record.roleName)" class="blue-green-text"/>
              </a>
              <a-divider type="vertical" />
              <a href="javascript:;">
                <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateRole(record.roleId)" class="blue-green-text"/>
              </a>
              <a-divider type="vertical" />
              <a href="javascript:;">
                <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteRole(record.roleId)" class="blue-green-text"/>
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
          :pageConfig="sysRoleList"
        >
          <a-button type="primary" @click="handleToCreateSysRole">
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
  import { getSystemRoleList, deleteSystemRole } from '../../api/sys/sys_role.js'

  export default {
    components: {
      BreadCrumb,
      SearchBar,
      FooterPagination
    },
    data () {
      return {
        loading: false,
        leftWidth: 4,
        rightWidth: 20,
        orgId: 0,
        orgType: undefined,
        sysRoleDTO: {
          roleName: '',
          orgName: ''
        },
        sysOrganTree: [],
        defaultSelectedKeys: [],
        sysRoleList: {
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
      roleColumns () {
        const roleColums = [
          {
            title: this.$t('sysRole.role_name'),
            dataIndex: 'roleName',
            sorter: true,
            columnKey: 'role_name',
            align: 'left'
          },
          {
            title: this.$t('sysRole.role_desc'),
            dataIndex: 'roleDesc',
            sorter: true,
            columnKey: 'role_desc',
            align: 'left'
          }
        ]
        if (this.orgType === 0) {
          roleColums.push({
            title: this.$t('sysOrgan.org_name'),
            dataIndex: 'orgName',
            sorter: true,
            columnKey: 'org_name',
            align: 'left'
          })
        }
        roleColums.push({
          title: this.$t('button.operation'),
          dataIndex: 'operation',
          scopedSlots: { customRender: 'operation' }
        })
        return roleColums
      }
    },
    mounted () {
      this.sysRoleDTO = this.getCondition(this.$route.path, this.sysRoleDTO)
      this.sysRoleList = this.getCondition(this.$route.path, this.sysRoleList)
      this.loadingSysRoleList(this.sysRoleList.current, this.orgId)
    },
    methods: {
      loadingSysRoleList (current, orgId) {
        this.loading = true
        this.saveCondition(this.$route.path, Object.assign({}, this.sysRoleDTO, this.sysRoleList))
        getSystemRoleList({
          current: current || this.sysRoleList.current,
          size: this.sysRoleList.size,
          sortField: this.orderBy.sortField,
          sortOrder: this.orderBy.sortOrder,
          roleName: this.sysRoleDTO.roleName,
          orgName: this.sysRoleDTO.orgName,
          orgId: this.orgId
        }).then(res => {
          // console.log('roleList:', res)
          this.sysRoleList = res.data.sysRoleList
          this.sysOrganTree = res.data.sysOrganTree
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
        this.sysRoleList.current = current
        this.loadingSysRoleList(current, this.orgId)
      },
      /* 切换每页显示记录数 */
      onShowSizeChange (current, size) {
        this.sysRoleList.size = size
        this.loadingSysRoleList(current, this.orgId)
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
        this.loadingSysRoleList(1, this.orgId)
      },
      /* 重置查询条件 */
      handleToRemoveSearch () {
        this.sysRoleDTO.roleName = ''
        this.sysRoleDTO.orgName = ''
        this.removeCondition(this.$router.path)
        this.loadingSysRoleList(1, this.orgId)
      },
      // 删除
      handleDeleteRole (roleId) {
        const _this = this
        this.$confirm({
          okText: this.$t('button.confirm'),
          cancelText: this.$t('button.cancel'),
          title: this.$t('message.delete_confirm'),
          onOk () {
            _this.executeDeleteSysRole(roleId)
          }
        })
      },
      executeDeleteSysRole (roleId) {
        deleteSystemRole(roleId).then(() => {
          this.$success({
            title: this.$t('message.delete_success')
          })
          this.loadingSysRoleList(this.sysRoleList.current, this.orgId)
        })
      },
      /* 创建角色 */
      handleToCreateSysRole () {
        this.$router.push({ path: '/system/role/detail/create' })
      },
      /* 修改角色 */
      handleUpdateRole (roleId) {
        this.$router.push({ path: '/system/role/detail/edit/' + roleId })
      },
      handleSettingRole (roleId, roleName) {
        this.$router.push({ path: '/system/role/detail/setting/' + roleId + '/' + roleName })
      },
      onOrganSelect (selectedKeys, info) {
        // console.log('selectedKeys', selectedKeys[0])
        this.orgId = selectedKeys[0]
        this.defaultSelectedKeys = selectedKeys
        this.loadingSysRoleList(this.sysRoleList.current, this.orgId)
      }
    }
  }
</script>

<style lang="less" scoped>
  @import url('~@/assets/less/qm-erp-list.less');
</style>
