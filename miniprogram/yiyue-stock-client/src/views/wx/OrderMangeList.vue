<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb/>

    <div>
      <a-modal
        width="190px"
        :mask="true"
        title="二维码确认签到"
        :visible="visible"
        @cancel="handleCancel"
        :footer="null"
        :confirm-loading="confirmLoading"
      >
        <div style="display: flex;justify-self: center;padding-left: 20px">
          <div id="qrcode" class="qrcode" ref="qrCodeUrl"></div>
        </div>
        <div style="margin-left: 20px;margin-top: 10px;">
          <span>姓名:{{userName}}</span>
          <br>
          <span>图书馆:{{userLibrary}}</span>
          <br>
          <span>座位：{{userSeat}}</span>

        </div>
      </a-modal>
    </div>
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingOrderMangeList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="orderMangeDTO" :model="orderMangeDTO" :wrapper-col="{span:24}">
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="orderMangeDTO.orderId"
              :placeholder="$t('orderMange.order_id')"
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
          :data-source="orderMangeList.records"
          :columns="orderMangeColumns"
          :rowKey="record => record.id"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="orderStatus" slot-scope="text, record">
            {{record.orderStatus==='0'?'预约':'已完成'}}
          </template>
          <template slot="operation" slot-scope="text, record">
            <a-icon type="scan" title="确认" @click="handleCode(record)" class="blue-green-text"/>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="edit" title="确认" @click="handleUpdateOrderMange(record)" class="blue-green-text"/>
            </a>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteOrderMange(record.id)" class="blue-green-text"/>
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
          :pageConfig="orderMangeList"
        >
          <a-button type="primary" @click="handleToCreateOrderMange">
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
  import { getOrderMangeList, updateOrderMange, deleteOrderMange } from '../../api/wx/order_mange.js'
  import QRCode from 'qrcodejs2'
  import { updateBookRead } from '@/api/wx/book_read'
  export default {
    components: {
      BreadCrumb,
      SearchBar,
      FooterPagination,
      QRCode
    },
    data () {
      return {
        userName:'',
        userSeat:'',
        userLibrary:'',
        visible: false,
        confirmLoading: false,
        orderMange: {},
        loading: false,
        orderMangeDTO: {
          id: undefined,
          orderId: undefined,
          userId: undefined,
          libraryId: undefined,
          libraryName: undefined,
          seatId: undefined,
          seatName: undefined,
          orderStatus: undefined,
          planTime: undefined,
          createDate: undefined
        },
        orderMangeList: {
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
      orderMangeColumns () {
        return [
          {
            title: '订单号',
            dataIndex: 'id',
            sorter: true,
            columnKey: 'id'
          },
          /* {
             title: this.$t('orderMange.order_id'),
             dataIndex: 'orderId',
             sorter: true,
             columnKey: 'order_id'
           },*/
          {
            title: this.$t('orderMange.user_id'),
            dataIndex: 'userName',
            sorter: true,
            columnKey: 'user_name'
          },
          /* {
             title: this.$t('orderMange.library_id'),
             dataIndex: 'libraryId',
             sorter: true,
             columnKey: 'library_id'
           },*/
          {
            title: this.$t('orderMange.library_name'),
            dataIndex: 'libraryName2',
            sorter: true,
            columnKey: 'library_name2'
          },
          /*  {
              title: this.$t('orderMange.seat_id'),
              dataIndex: 'seatId',
              sorter: true,
              columnKey: 'seat_id'
            },*/
          {
            title: this.$t('orderMange.seat_name'),
            dataIndex: 'seatName2',
            sorter: true,
            columnKey: 'seat_name2'
          },
          {
            title: this.$t('orderMange.order_status'),
            dataIndex: 'orderStatus',
            sorter: true,
            columnKey: 'order_status',
            scopedSlots: { customRender: 'orderStatus' }
          },
          {
            title: this.$t('orderMange.plan_time'),
            dataIndex: 'planTime',
            sorter: true,
            columnKey: 'plan_time'
          },
          {
            title: '确认时间',
            dataIndex: 'createDate',
            sorter: true,
            columnKey: 'create_date'
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

      this.orderMangeDTO = this.getCondition(this.$route.path, this.orderMangeDTO)
      this.orderMangeList = this.getCondition(this.$route.path, this.orderMangeList)
      this.loadingOrderMangeList(this.orderMangeList.current)
    },
    methods: {
      handleCancel(e) {
        this.visible = false;
        document.getElementById("qrcode").innerHTML = "";
      },
      //二维码
      handleCode(data){
        this.visible = true;
        this.userName=data.userName
          this.userSeat=data.seatName2
          this.userLibrary=data.libraryName2
        this.$nextTick(()=>{
          this.creatQrCode(data);
        })
      },
      creatQrCode(data) {
        console.log(JSON.stringify(data))
        var testObj=JSON.stringify(data)
        var qrcode = new QRCode(this.$refs.qrCodeUrl, {
          text: "id:"+data.id+",userId:"+data.userId+",libraryId:"+data.libraryId+",seatId:"+data.seatId, // 需要转换为二维码的内容
          width: 100,
          height: 100,
          colorDark: '#000000',
          colorLight: '#ffffff',
          correctLevel: QRCode.CorrectLevel.H
        })
      },
      loadingOrderMangeList (current) {
        this.loading = true
        this.saveCondition(this.$route.path, Object.assign({}, this.orderMangeDTO, this.orderMangeList))
        getOrderMangeList({
          current: current || this.orderMangeList.current,
          id: this.orderMangeDTO.id,
          orderId: this.orderMangeDTO.orderId,
          userId: this.orderMangeDTO.userId,
          libraryId: this.orderMangeDTO.libraryId,
          libraryName: this.orderMangeDTO.libraryName,
          seatId: this.orderMangeDTO.seatId,
          seatName: this.orderMangeDTO.seatName,
          orderStatus: this.orderMangeDTO.orderStatus,
          planTime: this.orderMangeDTO.planTime,
          createDate: this.orderMangeDTO.createDate,
          size: this.orderMangeList.size,
          sortField: this.orderBy.sortField,
          sortOrder: this.orderBy.sortOrder
        }).then(res => {
          console.log('orderMangeList:', res)
          this.orderMangeList = res.data

        }).finally(() => {
          this.loading = false
        })
      },
      /* 点击页码加载当前页的数据 */
      onChangePage (current) {
        this.orderMangeList.current = current
        this.loadingOrderMangeList(current)
      },
      /* 切换每页显示记录数 */
      onShowSizeChange (current, size) {
        this.orderMangeList.size = size
        this.loadingOrderMangeList(current)
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
        this.loadingOrderMangeList(1)
      },
      /* 重置查询条件 */
      handleToRemoveSearch () {
        this.orderMangeDTO.id = ''
        this.orderMangeDTO.orderId = ''
        this.orderMangeDTO.userId = ''
        this.orderMangeDTO.libraryId = ''
        this.orderMangeDTO.libraryName = ''
        this.orderMangeDTO.seatId = ''
        this.orderMangeDTO.seatName = ''
        this.orderMangeDTO.orderStatus = ''
        this.orderMangeDTO.planTime = ''
        this.orderMangeDTO.createDate = ''
        this.removeCondition(this.$router.path)
        this.loadingOrderMangeList(1)
      },
      // 删除
      handleDeleteOrderMange (id) {
        const _this = this
        this.$confirm({
          okText: this.$t('button.confirm'),
          cancelText: this.$t('button.cancel'),
          title: this.$t('message.delete_confirm'),
          onOk () {
            _this.executeDeleteOrderMange(id)
          }
        })
      },
      executeDeleteOrderMange (id) {
        deleteOrderMange(id).then(() => {
          this.$success({
            title: this.$t('message.delete_success')
          })
          this.loadingOrderMangeList(this.orderMangeList.current)
        })
      },
      /* 创建对象 */
      handleToCreateOrderMange () {
        this.$router.push({ path: '/wx/order/mange/detail/create' })
      },
      /* 修改对象 */
      handleUpdateOrderMange (data) {
        const _this = this
        _this.$confirm({
          okText: this.$t('button.confirm'),
          cancelText: this.$t('button.cancel'),
          title: "确认签到",
          onOk () {
            _this.orderMange.id = data.id
            _this.orderMange.libraryId = data.libraryId
            _this.orderMange.seatId = data.seatId
            _this.orderMange.orderStatus = 1
            updateOrderMange(_this.orderMange).then(res => {
              _this.$success({ title: _this.$t('message.success') })
              _this.loadingOrderMangeList(1)
            }).finally(() => {
              _this.loading = false
            })
            _this.loadingOrderMangeList(1)
            return
          }
        })


      }
    }
  }
</script>

<style lang="less" scoped>
  @import url('~@/assets/less/qm-erp-list.less');
</style>
