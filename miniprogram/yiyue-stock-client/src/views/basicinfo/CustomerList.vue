<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingCustomerList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="customerDTO" :model="customerDTO" :wrapper-col="{span:24}">
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="customerDTO.name"
              :placeholder="$t('customer.name')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="customerDTO.contact"
              :placeholder="$t('customer.contact')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="customerDTO.phone"
              :placeholder="$t('customer.phone')"
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
              :placeholder="$t('customer.form_city')"
              allow-clear/>
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="customerDTO.address"
              :placeholder="$t('customer.address')"
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
              v-model="customerDTO.orgName"
              :placeholder="$t('customer.org_name')"
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
          :data-source="customerList.records"
          :columns="customerColumns"
          :rowKey="record => record.id"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="operation" slot-scope="text, record">
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateCustomer(record.id)" class="blue-green-text"/>
            </a>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteCustomer(record.id)" class="blue-green-text"/>
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
          :pageConfig="customerList"
        >
          <a-button type="primary" @click="handleToCreateCustomer">
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
    import { getCustomerList, deleteCustomer } from '../../api/basicinfo/customer.js'
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
          customerDTO: {
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
          customerList: {
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
        customerColumns () {
          const customerColumn = [
            {
              title: this.$t('customer.name'),
              dataIndex: 'name',
              sorter: true,
              columnKey: 'name',
              align: 'left'
            },
            {
              title: this.$t('customer.contact'),
              dataIndex: 'contact',
              sorter: true,
              columnKey: 'contact',
              align: 'left'
            },
            {
              title: this.$t('customer.phone'),
              dataIndex: 'phone',
              sorter: true,
              columnKey: 'phone',
              align: 'left'
            },
            {
              title: this.$t('customer.address'),
              dataIndex: 'fullAddress',
              sorter: true,
              columnKey: 'full_address',
              align: 'left'
            },
            {
              title: this.$t('customer.remarks'),
              dataIndex: 'remarks',
              sorter: true,
              columnKey: 'remarks',
              align: 'left'
            }
          ]
          if (this.loginUserOrgan.orgType === 0) {
            customerColumn.push({
              title: this.$t('customer.org_name'),
              dataIndex: 'orgName',
              sorter: true,
              columnKey: 'org_name',
              align: 'left'
            })
          }
          customerColumn.push({
            title: this.$t('button.operation'),
            dataIndex: 'operation',
            scopedSlots: { customRender: 'operation' }
          })
          return customerColumn
        }
      },
      mounted () {
        this.loadingLoginUserOrganType()
        this.customerDTO = this.getCondition(this.$route.path, this.customerDTO)
        this.customerList = this.getCondition(this.$route.path, this.customerList)
        this.loadingCustomerList(this.customerList.current)
      },
      methods: {
        loadingLoginUserOrganType () {
          getLoginUserOrganType().then(res => {
            // console.log('organType:', res)
            this.loginUserOrgan = res.data
          })
        },
        loadingCustomerList (current) {
          this.loading = true
          this.saveCondition(this.$route.path, Object.assign({}, this.customerDTO, this.customerList))
          getCustomerList({
            current: current || this.customerList.current,
            id: this.customerDTO.id,
            province: this.customerDTO.province,
            city: this.customerDTO.city,
            county: this.customerDTO.county,
            address: this.customerDTO.address,
            contact: this.customerDTO.contact,
            name: this.customerDTO.name,
            phone: this.customerDTO.phone,
            remarks: this.customerDTO.remarks,
            isDel: this.customerDTO.isDel,
            orgId: this.customerDTO.orgId,
            orgName: this.customerDTO.orgName,
            size: this.customerList.size,
            sortField: this.orderBy.sortField,
            sortOrder: this.orderBy.sortOrder
          }).then(res => {
            // console.log('customerList:', res)
            this.customerList = res.data
          }).finally(() => {
            this.loading = false
          })
        },
        /* 点击页码加载当前页的数据 */
        onChangePage (current) {
          this.customerList.current = current
          this.loadingCustomerList(current)
        },
        /* 切换每页显示记录数 */
        onShowSizeChange (current, size) {
          this.customerList.size = size
          this.loadingCustomerList(current)
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
          this.loadingCustomerList(1)
        },
        /* 重置查询条件 */
        handleToRemoveSearch () {
          this.customerDTO.id = ''
          this.customerDTO.province = ''
          this.customerDTO.city = ''
          this.customerDTO.county = ''
          this.customerDTO.address = ''
          this.customerDTO.contact = ''
          this.customerDTO.name = ''
          this.customerDTO.phone = ''
          this.customerDTO.remarks = ''
          this.customerDTO.isDel = ''
          this.customerDTO.orgId = ''
          this.customerDTO.orgName = ''
          this.cascaderValue = []
          this.removeCondition(this.$router.path)
          this.loadingCustomerList(1)
        },
        // 删除
        handleDeleteCustomer (id) {
          const _this = this
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: this.$t('message.delete_confirm'),
            onOk () {
              _this.executeDeleteCustomer(id)
            }
          })
        },
        executeDeleteCustomer (id) {
          deleteCustomer(id).then(() => {
            this.$success({
              title: this.$t('message.delete_success')
            })
            this.loadingCustomerList(this.customerList.current)
          })
        },
        /* 创建对象 */
        handleToCreateCustomer () {
          this.$router.push({ path: '/basicinfo/customer/detail/create' })
        },
        /* 修改对象 */
        handleUpdateCustomer (id) {
          this.$router.push({ path: '/basicinfo/customer/detail/edit/' + id })
        },
        /* 选择城市处理 */
        handleCityChange (value) {
          this.customerDTO.province = ''
          this.customerDTO.city = ''
          this.customerDTO.county = ''
          this.cascaderValue = value
          if (value[0]) {
            this.customerDTO.province = value[0]
          }
          if (value[1]) {
            this.customerDTO.city = value[1]
          }
          if (value[2]) {
            this.customerDTO.county = value[2]
          }
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-list.less');
</style>
