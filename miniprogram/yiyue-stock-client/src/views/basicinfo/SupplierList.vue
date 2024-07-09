<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingSupplierList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="supplierDTO" :model="supplierDTO" :wrapper-col="{span:24}">
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="supplierDTO.name"
              :placeholder="$t('supplier.name')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="supplierDTO.contact"
              :placeholder="$t('supplier.contact')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="supplierDTO.phone"
              :placeholder="$t('supplier.phone')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-cascader
              :options="cityOptions"
              @change="handleCityChange"
              :default-value="selectCity"
              :value="cascaderValue"
              :placeholder="$t('supplier.form_city')"/>
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="supplierDTO.address"
              :placeholder="$t('supplier.address')"
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
              v-model="supplierDTO.orgName"
              :placeholder="$t('supplier.org_name')"
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
          :data-source="supplierList.records"
          :columns="supplierColumns"
          :rowKey="record => record.id"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="operation" slot-scope="text, record">
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateSupplier(record.id)" class="blue-green-text"/>
            </a>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteSupplier(record.id)" class="blue-green-text"/>
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
          :pageConfig="supplierList"
        >
          <a-button type="primary" @click="handleToCreateSupplier">
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
    import { getSupplierList, deleteSupplier } from '../../api/basicinfo/supplier.js'
    import { getLoginUserOrganType } from '../../api/sys/sys_organ.js'
    import cityOptions from '@/utils/city_options.js'

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
          supplierDTO: {
            id: undefined,
            province: undefined,
            city: undefined,
            county: undefined,
            address: undefined,
            contact: undefined,
            name: undefined,
            phone: undefined,
            remarks: undefined,
            isDel: undefined,
            orgId: undefined,
            orgName: undefined
          },
          supplierList: {
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
          },
          cityOptions,
          selectCity: [],
          cascaderValue: []
        }
      },
      computed: {
        supplierColumns () {
          const supplierColumn = [
            {
              title: this.$t('supplier.name'),
              dataIndex: 'name',
              sorter: true,
              columnKey: 'name',
              align: 'left'
            },
            {
              title: this.$t('supplier.contact'),
              dataIndex: 'contact',
              sorter: true,
              columnKey: 'contact',
              align: 'left'
            },
            {
              title: this.$t('supplier.phone'),
              dataIndex: 'phone',
              sorter: true,
              columnKey: 'phone',
              align: 'left'
            },
            {
              title: this.$t('supplier.address'),
              dataIndex: 'fullAddress',
              sorter: true,
              columnKey: 'full_address',
              align: 'left'
            },
            {
              title: this.$t('supplier.remarks'),
              dataIndex: 'remarks',
              sorter: true,
              columnKey: 'remarks',
              align: 'left'
            }
          ]
          if (this.loginUserOrgan.orgType === 0) {
            supplierColumn.push({
              title: this.$t('supplier.org_name'),
              dataIndex: 'orgName',
              sorter: true,
              columnKey: 'org_name',
              align: 'left'
            })
          }
          supplierColumn.push({
            title: this.$t('button.operation'),
            dataIndex: 'operation',
            scopedSlots: { customRender: 'operation' }
          })
          return supplierColumn
        }
      },
      mounted () {
        this.loadingLoginUserOrganType()
        this.supplierDTO = this.getCondition(this.$route.path, this.supplierDTO)
        this.supplierList = this.getCondition(this.$route.path, this.supplierList)
        this.loadingSupplierList(this.supplierList.current)
      },
      methods: {
        loadingLoginUserOrganType () {
          getLoginUserOrganType().then(res => {
            // console.log('organType:', res)
            this.loginUserOrgan = res.data
          })
        },
        loadingSupplierList (current) {
          this.loading = true
          this.saveCondition(this.$route.path, Object.assign({}, this.supplierDTO, this.supplierList))
          getSupplierList({
            current: current || this.supplierList.current,
            id: this.supplierDTO.id,
            province: this.supplierDTO.province,
            city: this.supplierDTO.city,
            county: this.supplierDTO.county,
            address: this.supplierDTO.address,
            contact: this.supplierDTO.contact,
            name: this.supplierDTO.name,
            phone: this.supplierDTO.phone,
            remarks: this.supplierDTO.remarks,
            isDel: this.supplierDTO.isDel,
            orgId: this.supplierDTO.orgId,
            orgName: this.supplierDTO.orgName,
            size: this.supplierList.size,
            sortField: this.orderBy.sortField,
            sortOrder: this.orderBy.sortOrder
          }).then(res => {
            // console.log('supplierList:', res)
            this.supplierList = res.data
          }).finally(() => {
            this.loading = false
          })
        },
        /* 点击页码加载当前页的数据 */
        onChangePage (current) {
          this.supplierList.current = current
          this.loadingSupplierList(current)
        },
        /* 切换每页显示记录数 */
        onShowSizeChange (current, size) {
          this.supplierList.size = size
          this.loadingSupplierList(current)
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
          this.loadingSupplierList(1)
        },
        /* 重置查询条件 */
        handleToRemoveSearch () {
          this.supplierDTO.id = ''
          this.supplierDTO.province = ''
          this.supplierDTO.city = ''
          this.supplierDTO.county = ''
          this.supplierDTO.address = ''
          this.supplierDTO.contact = ''
          this.supplierDTO.name = ''
          this.supplierDTO.phone = ''
          this.supplierDTO.remarks = ''
          this.supplierDTO.isDel = ''
          this.supplierDTO.orgId = ''
          this.supplierDTO.orgName = ''
          this.cascaderValue = []
          this.removeCondition(this.$router.path)
          this.loadingSupplierList(1)
        },
        // 删除
        handleDeleteSupplier (id) {
          const _this = this
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: this.$t('message.delete_confirm'),
            onOk () {
              _this.executeDeleteSupplier(id)
            }
          })
        },
        executeDeleteSupplier (id) {
          deleteSupplier(id).then(() => {
            this.$success({
              title: this.$t('message.delete_success')
            })
            this.loadingSupplierList(this.supplierList.current)
          })
        },
        /* 创建对象 */
        handleToCreateSupplier () {
          this.$router.push({ path: '/basicinfo/supplier/detail/create' })
        },
        /* 修改对象 */
        handleUpdateSupplier (id) {
          this.$router.push({ path: '/basicinfo/supplier/detail/edit/' + id })
        },
        /* 选择城市处理 */
        handleCityChange (value) {
          this.supplierDTO.province = ''
          this.supplierDTO.city = ''
          this.supplierDTO.county = ''
          this.cascaderValue = value
          if (value[0]) {
            this.supplierDTO.province = value[0]
          }
          if (value[1]) {
            this.supplierDTO.city = value[1]
          }
          if (value[2]) {
            this.supplierDTO.county = value[2]
          }
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-list.less');
</style>
