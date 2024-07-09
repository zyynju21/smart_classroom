<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingLibraryTypeList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="libraryTypeDTO" :model="libraryTypeDTO" :wrapper-col="{span:24}">
       <!-- <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="libraryTypeDTO.id"
              :placeholder="$t('libraryType.id')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>-->
        <!--<a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="libraryTypeDTO.libraryImg"
              :placeholder="$t('libraryType.library_img')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>-->
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="libraryTypeDTO.libraryName"
              :placeholder="$t('libraryType.library_name')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
       <!-- <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="libraryTypeDTO.addName"
              :placeholder="$t('libraryType.add_name')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>-->
      <!--  <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="libraryTypeDTO.businessDate"
              :placeholder="$t('libraryType.business_date')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>-->
      <!--  <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="libraryTypeDTO.libraryInfo"
              :placeholder="$t('libraryType.library_info')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>-->
       <!-- <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="libraryTypeDTO.seatId"
              :placeholder="$t('libraryType.seat_id')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>-->
      </a-form-model>
    </search-bar>
    <!-- 表格 -->
    <a-row>
      <a-col>
        <a-table
          :loading="loading"
          :data-source="libraryTypeList.records"
          :columns="libraryTypeColumns"
          :rowKey="record => record.id"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="libraryImg" slot-scope="text, record">
            <div v-if="record.libraryImg"><img loading="lazy" :src="axios.defaults.baseURL+'/'+record.libraryImg" width="120px" height="120px"></div>
            <div v-else><img src="" width="120px" height="120px"></div>
          </template>
          <template slot="operation" slot-scope="text, record">
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateLibraryType(record.id)" class="blue-green-text"/>
            </a>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteLibraryType(record.id)" class="blue-green-text"/>
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
          :pageConfig="libraryTypeList"
        >
          <a-button type="primary" @click="handleToCreateLibraryType">
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
    import {axios} from '@/utils/request'
    import SearchBar from '../components/top_search_bar/TopSearchBar'
    import FooterPagination from '../components/footer_pagination/FooterPagination'
    import { getLibraryTypeList, deleteLibraryType } from '../../api/wx/library_type.js'

    export default {
      components: {
        BreadCrumb,
        SearchBar,
        FooterPagination
      },
      data () {
        return {
          loading: false,
          libraryTypeDTO: {
            id: undefined,
            libraryImg: undefined,
            libraryName: undefined,
            addName: undefined,
            businessDate: undefined,
            libraryInfo: undefined,
            seatId: undefined
          },
          libraryTypeList: {
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
        libraryTypeColumns () {
          return [
            {
              title: this.$t('libraryType.id'),
              dataIndex: 'id',
              sorter: true,
              columnKey: 'id'
            },
            {
              title: this.$t('libraryType.library_img'),
              dataIndex: 'libraryImg',
              sorter: true,
              columnKey: 'library_img',
              scopedSlots: { customRender: 'libraryImg'}
            },
            {
              title: this.$t('libraryType.library_name'),
              dataIndex: 'libraryName',
              sorter: true,
              columnKey: 'library_name'
            },
            {
              title: this.$t('libraryType.add_name'),
              dataIndex: 'addName',
              sorter: true,
              columnKey: 'add_name'
            },
            {
              title: this.$t('libraryType.business_date'),
              dataIndex: 'businessDate',
              sorter: true,
              columnKey: 'business_date'
            },
          /*  {
              title: this.$t('libraryType.library_info'),
              dataIndex: 'libraryInfo',
              sorter: true,
              columnKey: 'library_info'
            },*/
/*            {
              title: this.$t('libraryType.seat_id'),
              dataIndex: 'seatId',
              sorter: true,
              columnKey: 'seat_id'
            },*/
            {
              title: this.$t('button.operation'),
              dataIndex: 'operation',
              scopedSlots: { customRender: 'operation' }
            }
          ]
        }
      },
      mounted () {
        this.libraryTypeDTO = this.getCondition(this.$route.path, this.libraryTypeDTO)
        this.libraryTypeList = this.getCondition(this.$route.path, this.libraryTypeList)
        this.loadingLibraryTypeList(this.libraryTypeList.current)
      },
      methods: {
        loadingLibraryTypeList (current) {
          this.loading = true
          this.saveCondition(this.$route.path, Object.assign({}, this.libraryTypeDTO, this.libraryTypeList))
          getLibraryTypeList({
            current: current || this.libraryTypeList.current,
            id: this.libraryTypeDTO.id,
            libraryImg: this.libraryTypeDTO.libraryImg,
            libraryName: this.libraryTypeDTO.libraryName,
            addName: this.libraryTypeDTO.addName,
            businessDate: this.libraryTypeDTO.businessDate,
            libraryInfo: this.libraryTypeDTO.libraryInfo,
            seatId: this.libraryTypeDTO.seatId,
            size: this.libraryTypeList.size,
            sortField: this.orderBy.sortField,
            sortOrder: this.orderBy.sortOrder
          }).then(res => {
            // console.log('libraryTypeList:', res)
            this.libraryTypeList = res.data
          }).finally(() => {
            this.loading = false
          })
        },
        /* 点击页码加载当前页的数据 */
        onChangePage (current) {
          this.libraryTypeList.current = current
          this.loadingLibraryTypeList(current)
        },
        /* 切换每页显示记录数 */
        onShowSizeChange (current, size) {
          this.libraryTypeList.size = size
          this.loadingLibraryTypeList(current)
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
          this.loadingLibraryTypeList(1)
        },
        /* 重置查询条件 */
        handleToRemoveSearch () {
          this.libraryTypeDTO.id = ''
          this.libraryTypeDTO.libraryImg = ''
          this.libraryTypeDTO.libraryName = ''
          this.libraryTypeDTO.addName = ''
          this.libraryTypeDTO.businessDate = ''
          this.libraryTypeDTO.libraryInfo = ''
          this.libraryTypeDTO.seatId = ''
          this.removeCondition(this.$router.path)
          this.loadingLibraryTypeList(1)
        },
        // 删除
        handleDeleteLibraryType (id) {
          const _this = this
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: this.$t('message.delete_confirm'),
            onOk () {
              _this.executeDeleteLibraryType(id)
            }
          })
        },
        executeDeleteLibraryType (id) {
          deleteLibraryType(id).then(() => {
            this.$success({
              title: this.$t('message.delete_success')
            })
            this.loadingLibraryTypeList(this.libraryTypeList.current)
          })
        },
        /* 创建对象 */
        handleToCreateLibraryType () {
          this.$router.push({ path: '/wx/library/type/detail/create' })
        },
        /* 修改对象 */
        handleUpdateLibraryType (id) {
          this.$router.push({ path: '/wx/library/type/detail/edit/' + id })
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-list.less');
</style>
