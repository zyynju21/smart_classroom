<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingStoreInfoList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="storeInfoDTO" :model="storeInfoDTO" :wrapper-col="{span:24}">
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="storeInfoDTO.storeName"
              :placeholder="$t('storeInfo.store_name')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="storeInfoDTO.contact"
              :placeholder="$t('storeInfo.contact')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="storeInfoDTO.phone"
              :placeholder="$t('storeInfo.phone')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="storeInfoDTO.remarks"
              :placeholder="$t('storeInfo.remarks')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col
          v-show="loginUserOrgan.orgType === 0"
          :xxl="3"
          :xl="3"
          :lg="3"
          :md="3"
          :sm="24">
          <a-form-model-item>
            <a-input
              v-model="storeInfoDTO.orgName"
              :placeholder="$t('storeInfo.org_name')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
      </a-form-model>
    </search-bar>
    <!-- 表格 -->
    <a-row>
      <a-col>
        <a-table
          :loading="loading"
          :data-source="storeInfoList.records"
          :columns="storeInfoColumns"
          :rowKey="record => record.id"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="operation" slot-scope="text, record">
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateStoreInfo(record.id)" class="blue-green-text"/>
            </a>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteStoreInfo(record.id)" class="blue-green-text"/>
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
          :pageConfig="storeInfoList"
        >
          <a-button type="primary" @click="handleToCreateStoreInfo">
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
    import { getStoreInfoList, deleteStoreInfo } from '../../api/basicinfo/store_info.js'
    import { getLoginUserOrganType } from '../../api/sys/sys_organ.js'

    export default {
      components: {
        BreadCrumb,
        SearchBar,
        FooterPagination
      },
      data () {
        return {
          loading: false,
          loginUserOrgan: {
            orgType: undefined
          },
          storeInfoDTO: {
            id: undefined,
            storeName: undefined,
            contact: undefined,
            phone: undefined,
            remarks: undefined,
            isDel: undefined,
            orgId: undefined,
            orgName: undefined
          },
          storeInfoList: {
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
        storeInfoColumns () {
          const storeInfoColumn = [
            {
              title: this.$t('storeInfo.store_name'),
              dataIndex: 'storeName',
              sorter: true,
              columnKey: 'store_name',
              align: 'left'
            },
            {
              title: this.$t('storeInfo.contact'),
              dataIndex: 'contact',
              sorter: true,
              columnKey: 'contact',
              align: 'left'
            },
            {
              title: this.$t('storeInfo.phone'),
              dataIndex: 'phone',
              sorter: true,
              columnKey: 'phone',
              align: 'left'
            },
            {
              title: this.$t('storeInfo.remarks'),
              dataIndex: 'remarks',
              sorter: true,
              columnKey: 'remarks',
              align: 'left'
            }
          ]
          if (this.loginUserOrgan.orgType === 0) {
            storeInfoColumn.push({
              title: this.$t('storeInfo.org_name'),
              dataIndex: 'orgName',
              sorter: true,
              columnKey: 'org_name',
              align: 'left'
            })
          }
          storeInfoColumn.push({
            title: this.$t('button.operation'),
            dataIndex: 'operation',
            scopedSlots: { customRender: 'operation' }
          })
          return storeInfoColumn
        }
      },
      mounted () {
        this.loadingLoginUserOrganType()
        this.storeInfoDTO = this.getCondition(this.$route.path, this.storeInfoDTO)
        this.storeInfoList = this.getCondition(this.$route.path, this.storeInfoList)
        this.loadingStoreInfoList(this.storeInfoList.current)
      },
      methods: {
        loadingLoginUserOrganType () {
          getLoginUserOrganType().then(res => {
            // console.log('organType:', res)
            this.loginUserOrgan = res.data
          })
        },
        loadingStoreInfoList (current) {
          this.loading = true
          this.saveCondition(this.$route.path, Object.assign({}, this.storeInfoDTO, this.storeInfoList))
          getStoreInfoList({
            current: current || this.storeInfoList.current,
            id: this.storeInfoDTO.id,
            storeName: this.storeInfoDTO.storeName,
            contact: this.storeInfoDTO.contact,
            phone: this.storeInfoDTO.phone,
            remarks: this.storeInfoDTO.remarks,
            isDel: this.storeInfoDTO.isDel,
            orgId: this.storeInfoDTO.orgId,
            orgName: this.storeInfoDTO.orgName,
            size: this.storeInfoList.size,
            sortField: this.orderBy.sortField,
            sortOrder: this.orderBy.sortOrder
          }).then(res => {
            // console.log('storeInfoList:', res)
            this.storeInfoList = res.data
          }).finally(() => {
            this.loading = false
          })
        },
        /* 点击页码加载当前页的数据 */
        onChangePage (current) {
          this.storeInfoList.current = current
          this.loadingStoreInfoList(current)
        },
        /* 切换每页显示记录数 */
        onShowSizeChange (current, size) {
          this.storeInfoList.size = size
          this.loadingStoreInfoList(current)
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
          this.loadingStoreInfoList(1)
        },
        /* 重置查询条件 */
        handleToRemoveSearch () {
          this.storeInfoDTO.id = ''
          this.storeInfoDTO.storeName = ''
          this.storeInfoDTO.contact = ''
          this.storeInfoDTO.phone = ''
          this.storeInfoDTO.remarks = ''
          this.storeInfoDTO.isDel = ''
          this.storeInfoDTO.orgId = ''
          this.storeInfoDTO.orgName = ''
          this.removeCondition(this.$router.path)
          this.loadingStoreInfoList(1)
        },
        // 删除
        handleDeleteStoreInfo (id) {
          const _this = this
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: this.$t('message.delete_confirm'),
            onOk () {
              _this.executeDeleteStoreInfo(id)
            }
          })
        },
        executeDeleteStoreInfo (id) {
          deleteStoreInfo(id).then(() => {
            this.$success({
              title: this.$t('message.delete_success')
            })
            this.loadingStoreInfoList(this.storeInfoList.current)
          })
        },
        /* 创建对象 */
        handleToCreateStoreInfo () {
          this.$router.push({ path: '/basicinfo/store/info/detail/create' })
        },
        /* 修改对象 */
        handleUpdateStoreInfo (id) {
          this.$router.push({ path: '/basicinfo/store/info/detail/edit/' + id })
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-list.less');
</style>
