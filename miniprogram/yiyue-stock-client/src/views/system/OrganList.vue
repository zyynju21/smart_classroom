<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingSysOrganList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="sysOrganDTO" :model="sysOrganDTO" :wrapper-col="{span:24}">
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input v-model="sysOrganDTO.orgName" :placeholder="$t('sysOrgan.org_name')" allow-clear/>
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input v-model="sysOrganDTO.orgPerson" :placeholder="$t('sysOrgan.org_person')" allow-clear/>
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input v-model="sysOrganDTO.orgPhone" :placeholder="$t('sysOrgan.org_phone')" allow-clear/>
          </a-form-model-item>
        </a-col>
      </a-form-model>
    </search-bar>
    <!-- 表格 -->
    <a-row>
      <a-col>
        <a-table
          :loading="loading"
          :data-source="sysOrganList.records"
          :columns="organColumns"
          :rowKey="record => record.orgId"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="operation" slot-scope="text, record">
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateOrgan(record.orgId)" class="blue-green-text"/>
            </a>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteOrgan(record.orgId)" class="blue-green-text"/>
            </a>
          </template>
        </a-table>
      </a-col>
    </a-row>
    <!-- 分页 -->
    <a-row>
      <a-col>
        <footer-pagination
          @onChangePage="onChangePage"
          @onShowSizeChange="onShowSizeChange"
          :pageConfig="sysOrganList"
        >
          <a-button type="primary" @click="handleToCreateSysOrgan">
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
  import { getSystemOrganList, deleteSystemOrgan } from '../../api/sys/sys_organ.js'

  export default {
    components: {
      BreadCrumb,
      SearchBar,
      FooterPagination
    },
    data () {
      return {
        loading: false,
        sysOrganDTO: {
          orgName: ''
        },
        sysOrganList: {
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
      organColumns () {
        return [
          {
            title: this.$t('sysOrgan.org_name'),
            dataIndex: 'orgName',
            sorter: true,
            columnKey: 'org_name',
            align: 'left'
          },
          {
            title: this.$t('sysOrgan.org_person'),
            dataIndex: 'orgPerson',
            sorter: true,
            columnKey: 'org_person',
            align: 'left'
          },
          {
            title: this.$t('sysOrgan.org_phone'),
            dataIndex: 'orgPhone',
            sorter: true,
            columnKey: 'org_phone',
            align: 'left'
          },
          {
            title: this.$t('button.operation'),
            dataIndex: 'operation',
            scopedSlots: { customRender: 'operation' }
          }
        ]
      }
    },
    mounted () {
      this.sysOrganDTO = this.getCondition(this.$route.path, this.sysOrganDTO)
      this.sysOrganList = this.getCondition(this.$route.path, this.sysOrganList)
      this.loadingSysOrganList(this.sysOrganList.current)
    },
    methods: {
      loadingSysOrganList (current) {
        this.loading = true
        this.saveCondition(this.$route.path, Object.assign({}, this.sysOrganDTO, this.sysOrganList))
        getSystemOrganList({
          current: current || this.sysOrganList.current,
          size: this.sysOrganList.size,
          sortField: this.orderBy.sortField,
          sortOrder: this.orderBy.sortOrder,
          orgName: this.sysOrganDTO.orgName,
          orgPerson: this.sysOrganDTO.orgPerson,
          orgPhone: this.sysOrganDTO.orgPhone
        }).then(res => {
          // console.log('sysOrganList:', res)
          this.sysOrganList = res.data
        }).finally(() => {
          this.loading = false
        })
      },
      /* 点击页码加载当前页的数据 */
      onChangePage (current) {
        this.sysOrganList.current = current
        this.loadingSysOrganList(current)
      },
      /* 切换每页显示记录数 */
      onShowSizeChange (current, size) {
        this.sysOrganList.size = size
        this.loadingSysOrganList(current)
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
        this.loadingSysOrganList(1)
      },
      /* 重置查询条件 */
      handleToRemoveSearch () {
        this.sysOrganDTO.orgName = ''
        this.sysOrganDTO.orgPerson = ''
        this.sysOrganDTO.orgPhone = ''
        this.removeCondition(this.$router.path)
        this.loadingSysOrganList(1)
      },
      // 删除
      handleDeleteOrgan (orgId) {
        const _this = this
        this.$confirm({
          okText: this.$t('button.confirm'),
          cancelText: this.$t('button.cancel'),
          title: this.$t('message.delete_confirm'),
          onOk () {
            _this.executeDeleteSysOrgan(orgId)
          }
        })
      },
      executeDeleteSysOrgan (orgId) {
        deleteSystemOrgan(orgId).then(() => {
          this.$success({
            title: this.$t('message.delete_success')
          })
          this.loadingSysOrganList(this.sysOrganList.current)
        })
      },
      /* 创建角色 */
      handleToCreateSysOrgan () {
        this.$router.push({ path: '/system/organ/detail/create' })
      },
      /* 修改角色 */
      handleUpdateOrgan (orgId) {
        this.$router.push({ path: '/system/organ/detail/edit/' + orgId })
      }
    }
  }
</script>

<style lang="less" scoped>
  @import url('~@/assets/less/qm-erp-list.less');
</style>
