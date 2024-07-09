<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 搜索栏 -->
    <!-- <search-bar @handleSearch="loadingSeatStatusList(1)" @handleReset="handleToRemoveSearch">
       <a-form-model ref="seatStatusDTO" :model="seatStatusDTO" :wrapper-col="{span:24}">
        &lt;!&ndash; <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
           <a-form-model-item>
             <a-input
               v-model="seatStatusDTO.id"
               :placeholder="$t('seatStatus.id')"
               allow-clear
             />
           </a-form-model-item>
         </a-col>&ndash;&gt;
        &lt;!&ndash; <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
           <a-form-model-item>
             <a-input
               v-model="seatStatusDTO.status"
               :placeholder="$t('seatStatus.status')"
               allow-clear
             />
           </a-form-model-item>
         </a-col>&ndash;&gt;
        &lt;!&ndash; <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
           <a-form-model-item>
             <a-input
               v-model="seatStatusDTO.seatName"
               :placeholder="$t('seatStatus.seat_name')"
               allow-clear
             />
           </a-form-model-item>
         </a-col>&ndash;&gt;
        &lt;!&ndash; <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
           <a-form-model-item>
             <a-input
               v-model="seatStatusDTO.libraryType"
               :placeholder="$t('seatStatus.library_type')"
               allow-clear
             />
           </a-form-model-item>
         </a-col>&ndash;&gt;
       </a-form-model>
     </search-bar>-->
    <!-- 表格 -->
    <a-row>
      <a-col>
        <a-table
          :loading="loading"
          :data-source="seatStatusList.records"
          :columns="seatStatusColumns"
          :rowKey="record => record.id"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="status" slot-scope="text, record">
            <a-tag>{{record.status===0?'空闲':'占用'}}</a-tag>
          </template>
          <template slot="operation" slot-scope="text, record">
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateSeatStatus(record.id)"
                      class="blue-green-text"/>
            </a>
            <a-divider type="vertical"/>
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteSeatStatus(record.id)"
                      class="blue-green-text"/>
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
          :pageConfig="seatStatusList"
        >
          <a-button type="primary" @click="handleToCreateSeatStatus">
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
  import { getSeatStatusList, deleteSeatStatus } from '../../api/wx/seat_status.js'

  export default {
    components: {
      BreadCrumb,
      SearchBar,
      FooterPagination
    },
    data () {
      return {
        loading: false,
        seatStatusDTO: {
          id: undefined,
          status: undefined,
          seatName: undefined,
          libraryType: undefined
        },
        seatStatusList: {
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
      seatStatusColumns () {
        return [
          /* {
             title: this.$t('seatStatus.id'),
             dataIndex: 'id',
             sorter: true,
             columnKey: 'id'
           },*/
          {
            title: this.$t('seatStatus.library_type'),
            dataIndex: 'libraryName',
            sorter: false,
            columnKey: 'libraryName'
          },
          {
            title: this.$t('seatStatus.seat_name'),
            dataIndex: 'seatName',
            sorter: true,
            columnKey: 'seat_name'
          },
          /*  {
              title: this.$t('seatStatus.status'),
              dataIndex: 'status',
              sorter: true,
              columnKey: 'status',
              scopedSlots: { customRender: 'status' }
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
      this.seatStatusDTO = this.getCondition(this.$route.path, this.seatStatusDTO)
      this.seatStatusList = this.getCondition(this.$route.path, this.seatStatusList)
      this.loadingSeatStatusList(this.seatStatusList.current)
    },
    methods: {
      loadingSeatStatusList (current) {
        this.loading = true
        this.saveCondition(this.$route.path, Object.assign({}, this.seatStatusDTO, this.seatStatusList))
        getSeatStatusList({
          current: current || this.seatStatusList.current,
          id: this.seatStatusDTO.id,
          status: this.seatStatusDTO.status,
          seatName: this.seatStatusDTO.seatName,
          libraryType: this.seatStatusDTO.libraryType,
          size: this.seatStatusList.size,
          sortField: this.orderBy.sortField,
          sortOrder: this.orderBy.sortOrder
        }).then(res => {
          // console.log('seatStatusList:', res)
          this.seatStatusList = res.data
        }).finally(() => {
          this.loading = false
        })
      },
      /* 点击页码加载当前页的数据 */
      onChangePage (current) {
        this.seatStatusList.current = current
        this.loadingSeatStatusList(current)
      },
      /* 切换每页显示记录数 */
      onShowSizeChange (current, size) {
        this.seatStatusList.size = size
        this.loadingSeatStatusList(current)
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
        this.loadingSeatStatusList(1)
      },
      /* 重置查询条件 */
      handleToRemoveSearch () {
        this.seatStatusDTO.id = ''
        this.seatStatusDTO.status = ''
        this.seatStatusDTO.seatName = ''
        this.seatStatusDTO.libraryType = ''
        this.removeCondition(this.$router.path)
        this.loadingSeatStatusList(1)
      },
      // 删除
      handleDeleteSeatStatus (id) {
        const _this = this
        this.$confirm({
          okText: this.$t('button.confirm'),
          cancelText: this.$t('button.cancel'),
          title: this.$t('message.delete_confirm'),
          onOk () {
            _this.executeDeleteSeatStatus(id)
          }
        })
      },
      executeDeleteSeatStatus (id) {
        deleteSeatStatus(id).then(() => {
          this.$success({
            title: this.$t('message.delete_success')
          })
          this.loadingSeatStatusList(this.seatStatusList.current)
        })
      },
      /* 创建对象 */
      handleToCreateSeatStatus () {
        this.$router.push({ path: '/wx/seat/status/detail/create' })
      },
      /* 修改对象 */
      handleUpdateSeatStatus (id) {
        this.$router.push({ path: '/wx/seat/status/detail/edit/' + id })
      }
    }
  }
</script>

<style lang="less" scoped>
  @import url('~@/assets/less/qm-erp-list.less');
</style>
