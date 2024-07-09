<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingSystemLogList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model :model="systemLogPageDTO" :wrapper-col="{span:24}">
        <a-col
          :xxl="3"
          :xl="3"
          :lg="3"
          :md="3"
          :sm="24">
          <a-form-model-item>
            <a-input
              v-model="systemLogPageDTO.operator"
              :placeholder="$t('systemLog.operator')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col
          :xxl="3"
          :xl="3"
          :lg="3"
          :md="3"
          :sm="24">
          <a-form-model-item>
            <a-date-picker v-model="systemLogPageDTO.startDate" :placeholder="$t('systemLog.startDate')"/>
          </a-form-model-item>
        </a-col>
        <a-col
          :xxl="3"
          :xl="3"
          :lg="3"
          :md="3"
          :sm="24">
          <a-form-model-item>
            <a-date-picker v-model="systemLogPageDTO.endDate" :placeholder="$t('systemLog.endDate')"/>
          </a-form-model-item>
        </a-col>
        <a-col
          :xxl="3"
          :xl="3"
          :lg="3"
          :md="3"
          :sm="24">
          <a-form-model-item>
            <a-select
              v-model="systemLogPageDTO.logType"
              allow-clear
              :placeholder="$t('systemLog.log_type')"
            >
              <a-select-option
                v-for="item in select.logTypeList"
                :value="item.logTypeId"
                :key="item.logTypeId"
              >
                {{ item.logTypeValue }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
        <a-col
          :xxl="3"
          :xl="3"
          :lg="3"
          :md="3"
          :sm="24">
          <a-form-model-item>
            <a-select
              v-model="systemLogPageDTO.status"
              allow-clear
              :placeholder="$t('systemLog.status')"
            >
              <a-select-option
                v-for="item in select.statusList"
                :value="item.statusId"
                :key="item.statusId"
              >
                {{ item.statusValue }}
              </a-select-option>
            </a-select>
          </a-form-model-item>
        </a-col>
      </a-form-model>
    </search-bar>
    <!-- 表格 -->
    <a-row>
      <a-col>
        <a-table
          :loading="loading"
          :data-source="systemLogList.records"
          :columns="coloaderColumns"
          :rowKey="record => record.systemLogId"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="logType" slot-scope="text">
            <span v-if="text === 1">
              <a-tag color="green">系统</a-tag>
            </span>
            <span v-else>
              <a-tag color="blue">微信小程序</a-tag>
            </span>
          </template>

          <template slot="status" slot-scope="text">
            <span v-if="text === 200">
              <a-tag color="green">成功</a-tag>
            </span>
            <span v-else>
              <a-tag color="red">失败</a-tag>
            </span>
          </template>

          <template slot="overflowHiding" slot-scope="text">
            <a-tooltip placement="top">
              <template slot="title">
                <span>{{ text }}</span>
              </template>
              {{ text === null ? '' : text.length > 20 ? text.slice(0, 20) + '...' : text }}
            </a-tooltip>
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
          :pageConfig="systemLogList"
        >
        </footer-pagination>
      </a-col>
    </a-row>
  </div>
</template>

<script>
  import BreadCrumb from '@/components/tools/Breadcrumb'
  import SearchBar from '../components/top_search_bar/TopSearchBar'
  import FooterPagination from '../components/footer_pagination/FooterPagination'
  import { getSystemLogList } from '../../api/sys/system_log'
  import { getNowDate, getStartDate, getEndDate } from '@/utils/formatDate'
  import moment from 'moment'

  export default {
    components: {
      BreadCrumb,
      SearchBar,
      FooterPagination
    },
    data () {
      return {
        // 查询条件
        systemLogPageDTO: {
          logType: undefined,
          status: undefined,
          operator: '',
          startDate: getNowDate(),
          endDate: getNowDate()
        },
        systemLogList: {
          // 分页条数据 total 总的记录数 size 一页多少条数据，current 当前页码 records 分页表格数据
          total: 0,
          size: 10,
          current: 0,
          records: []
        },
        orderBy: {
          // 排序列
          sortField: 'created_gmt',
          // 排序规则
          sortOrder: 'descend'
        },
        select: {
          logTypeList: [{ logTypeId: 1, logTypeValue: '系统' }, { logTypeId: 2, logTypeValue: '微信小程序' }],
          statusList: [{ statusId: 1, statusValue: '成功' }, { statusId: 0, statusValue: '失败' }]
        },
        loading: false
      }
    },
    mounted () {
      this.systemLogPageDTO = this.getCondition(this.$route.path, this.systemLogPageDTO)
      this.systemLogList = this.getCondition(this.$route.path, this.systemLogList)
      // 日期赋值需要特殊处理
      this.systemLogPageDTO = {
        startDate: this.systemLogPageDTO.startDate ? moment(this.systemLogPageDTO.startDate, 'YYYY-MM-DD HH:mm:ss') : undefined,
        endDate: this.systemLogPageDTO.endDate ? moment(this.systemLogPageDTO.endDate, 'YYYY-MM-DD HH:mm:ss') : undefined
      }
      this.loadingSystemLogList(this.systemLogList.current)
    },
    computed: {
      coloaderColumns () {
        const columns = [
          {
            title: this.$t('systemLog.controller_name'),
            dataIndex: 'controllerName',
            scopedSlots: { customRender: 'overflowHiding' }
          },
          {
            title: this.$t('systemLog.method_name'),
            dataIndex: 'methodName'
          },
          {
            title: this.$t('systemLog.paras'),
            dataIndex: 'paras',
            scopedSlots: { customRender: 'overflowHiding' }
          },
          {
            title: this.$t('systemLog.result'),
            dataIndex: 'result',
            scopedSlots: { customRender: 'overflowHiding' }
          },
          {
            title: this.$t('systemLog.log_type'),
            dataIndex: 'logType',
            scopedSlots: { customRender: 'logType' }
          },
          {
            title: this.$t('systemLog.status'),
            dataIndex: 'status',
            sorter: true,
            columnKey: 'status',
            scopedSlots: { customRender: 'status' }
          },
          {
            title: this.$t('systemLog.response_time'),
            dataIndex: 'responseTime',
            sorter: true,
            columnKey: 'response_time'
          },
          {
            title: this.$t('systemLog.client_ip'),
            dataIndex: 'clientIp'
          },
          {
            title: this.$t('systemLog.client_system'),
            dataIndex: 'clientSystem'
          },
          {
            title: this.$t('systemLog.client_browser'),
            dataIndex: 'clientBrowser'
          },
          {
            title: this.$t('systemLog.server_ip'),
            dataIndex: 'serverIp',
            scopedSlots: { customRender: 'overflowHiding' }
          },
          {
            title: this.$t('systemLog.created_gmt'),
            dataIndex: 'createdGmt',
            sorter: true,
            columnKey: 'created_gmt'
          },
          {
            title: this.$t('systemLog.operator'),
            dataIndex: 'operator',
            sorter: true,
            columnKey: 'login_name'
          }
        ]
        return columns
      }
    },
    methods: {
      /* 日志列表 */
      loadingSystemLogList (current) {
        this.loading = true
        this.saveCondition(this.$route.path, Object.assign({}, this.systemLogPageDTO, this.systemLogList))
        getSystemLogList({
          current: current || this.systemLogList.current,
          size: this.systemLogList.size,
          sortField: this.orderBy.sortField,
          sortOrder: this.orderBy.sortOrder,
          logType: this.systemLogPageDTO.logType,
          status: this.systemLogPageDTO.status,
          operator: this.systemLogPageDTO.operator,
          startDate: getStartDate(this.systemLogPageDTO.startDate),
          endDate: getEndDate(this.systemLogPageDTO.endDate)
        })
          .then((res) => {
            // console.log(res)
            this.systemLogList = res.data
          })
          .finally(() => {
            this.loading = false
          })
      },
      /* 点击页码加载当前页的数据 */
      onChangePage (current) {
        this.systemLogList.current = current
        this.loadingSystemLogList(current)
      },
      /* 切换每页显示记录数 */
      onShowSizeChange (current, size) {
        this.systemLogList.size = size
        this.loadingSystemLogList(current)
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
        this.loadingSystemLogList(1)
      },
      /* 重置 */
      handleToRemoveSearch () {
        this.systemLogPageDTO = {
          logType: undefined,
          status: undefined,
          operator: '',
          startDate: getNowDate(),
          endDate: getNowDate()
        }
        this.removeCondition(this.$router.path)
        this.loadingSystemLogList(1)
      }
    }
  }
</script>

<style>
  @import url('~@/assets/less/qm-erp-list.less');
</style>
