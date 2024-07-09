<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingGoodsUnitList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="goodsUnitDTO" :model="goodsUnitDTO" :wrapper-col="{span:24}">
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="goodsUnitDTO.name"
              :placeholder="$t('goodsUnit.name')"
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
          :sm="24" >
          <a-form-model-item>
            <a-input
              v-model="goodsUnitDTO.orgName"
              :placeholder="$t('goodsUnit.org_name')"
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
          :data-source="goodsUnitList.records"
          :columns="goodsUnitColumns"
          :rowKey="record => record.id"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="operation" slot-scope="text, record">
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateGoodsUnit(record.id)" class="blue-green-text"/>
            </a>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteGoodsUnit(record.id)" class="blue-green-text"/>
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
          :pageConfig="goodsUnitList"
        >
          <a-button type="primary" @click="handleToCreateGoodsUnit">
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
    import { getGoodsUnitList, deleteGoodsUnit } from '../../api/basicinfo/goods_unit.js'
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
          goodsUnitDTO: {
            id: undefined,
            name: undefined,
            orgId: undefined
          },
          loginUserOrgan: {
            orgType: undefined
          },
          goodsUnitList: {
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
        goodsUnitColumns () {
          const unitColumns = [
            {
              title: this.$t('goodsUnit.name'),
              dataIndex: 'name',
              sorter: true,
              columnKey: 'name'
            }
          ]
          if (this.loginUserOrgan.orgType === 0) {
            unitColumns.push({
              title: this.$t('goodsUnit.org_name'),
              dataIndex: 'orgName',
              sorter: true,
              columnKey: 'org_name',
              align: 'left'
            })
          }
          unitColumns.push({
            title: this.$t('button.operation'),
            dataIndex: 'operation',
            scopedSlots: { customRender: 'operation' }
          })
          return unitColumns
        }
      },
      mounted () {
        this.loadingLoginUserOrganType()
        this.goodsUnitDTO = this.getCondition(this.$route.path, this.goodsUnitDTO)
        this.goodsUnitList = this.getCondition(this.$route.path, this.goodsUnitList)
        this.loadingGoodsUnitList(this.goodsUnitList.current)
      },
      methods: {
        loadingLoginUserOrganType () {
          getLoginUserOrganType().then(res => {
            // console.log('organType:', res)
            this.loginUserOrgan = res.data
          })
        },
        loadingGoodsUnitList (current) {
          this.loading = true
          this.saveCondition(this.$route.path, Object.assign({}, this.goodsUnitDTO, this.goodsUnitList))
          getGoodsUnitList({
            current: current || this.goodsUnitList.current,
            id: this.goodsUnitDTO.id,
            name: this.goodsUnitDTO.name,
            orgName: this.goodsUnitDTO.orgName,
            size: this.goodsUnitList.size,
            sortField: this.orderBy.sortField,
            sortOrder: this.orderBy.sortOrder
          }).then(res => {
            // console.log('goodsUnitList:', res)
            this.goodsUnitList = res.data
          }).finally(() => {
            this.loading = false
          })
        },
        /* 点击页码加载当前页的数据 */
        onChangePage (current) {
          this.goodsUnitList.current = current
          this.loadingGoodsUnitList(current)
        },
        /* 切换每页显示记录数 */
        onShowSizeChange (current, size) {
          this.goodsUnitList.size = size
          this.loadingGoodsUnitList(current)
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
          this.loadingGoodsUnitList(1)
        },
        /* 重置查询条件 */
        handleToRemoveSearch () {
          this.goodsUnitDTO.name = ''
          this.goodsUnitDTO.orgName = ''
          this.removeCondition(this.$router.path)
          this.loadingGoodsUnitList(1)
        },
        // 删除
        handleDeleteGoodsUnit (id) {
          const _this = this
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: this.$t('message.delete_confirm'),
            onOk () {
              _this.executeDeleteGoodsUnit(id)
            }
          })
        },
        executeDeleteGoodsUnit (id) {
          deleteGoodsUnit(id).then(() => {
            this.$success({
              title: this.$t('message.delete_success')
            })
            this.loadingGoodsUnitList(this.goodsUnitList.current)
          })
        },
        /* 创建对象 */
        handleToCreateGoodsUnit () {
          this.$router.push({ path: '/basicinfo/goods/unit/detail/create' })
        },
        /* 修改对象 */
        handleUpdateGoodsUnit (id) {
          this.$router.push({ path: '/basicinfo/goods/unit/detail/edit/' + id })
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-list.less');
</style>
