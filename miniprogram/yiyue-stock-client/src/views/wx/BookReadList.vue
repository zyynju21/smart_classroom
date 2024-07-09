<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingBookReadList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="bookReadDTO" :model="bookReadDTO" :wrapper-col="{span:24}">
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="bookReadDTO.userName"
              placeholder="请输入用户名"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="bookReadDTO.bookName"
              placeholder="请输入书名"
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
          :data-source="bookReadList.records"
          :columns="bookReadColumns"
          :rowKey="record => record.id"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="orderStatus" slot-scope="text, record">
            {{record.orderStatus==='0'?'已借':'已还'}}
          </template>

          <template slot="operation" slot-scope="text, record" v-if="record.orderStatus!=='1'">
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateBookRead(record)" class="blue-green-text"/>
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
          :pageConfig="bookReadList"
        >
          <a-button type="primary" @click="handleToCreateBookRead">
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
    import { getBookReadList, deleteBookRead } from '../../api/wx/book_read.js'
    import { updateBookRead } from '../../api/wx/book_read'

    export default {
      components: {
        BreadCrumb,
        SearchBar,
        FooterPagination
      },
      data () {
        return {
          loading: false,
          bookReadDTO2:{},
          bookReadDTO: {
            id: undefined,
            userId: undefined,
            userName: undefined,
            libraryId: undefined,
            libraryName: undefined,
            bookName: undefined,
            orderStatus: undefined,
            bookRemark: undefined,
            startDate: undefined,
            endDate: undefined,
            createDate: undefined
          },
          bookReadList: {
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
        bookReadColumns () {
          return [
            {
              title: this.$t('bookRead.id'),
              dataIndex: 'id',
              sorter: true,
              columnKey: 'id'
            },
            {
              title: '用户',
              dataIndex: 'userName',
              sorter: true,
              columnKey: 'user_name'
            },
            {
              title: '图书',
              dataIndex: 'bookName',
              sorter: true,
              columnKey: 'book_name'
            },
/*            {
              title: this.$t('bookRead.library_name'),
              dataIndex: 'libraryName',
              sorter: true,
              columnKey: 'library_name'
            },*/
            {
              title: this.$t('bookRead.order_status'),
              dataIndex: 'orderStatus',
              sorter: true,
              columnKey: 'order_status',
              scopedSlots: { customRender: 'orderStatus' }
            },
            {
              title: this.$t('bookRead.book_remark'),
              dataIndex: 'bookRemark',
              sorter: true,
              columnKey: 'book_remark'
            },
            {
              title: this.$t('bookRead.start_date'),
              dataIndex: 'startDate',
              sorter: true,
              columnKey: 'start_date'
            },
            {
              title: this.$t('bookRead.end_date'),
              dataIndex: 'endDate',
              sorter: true,
              columnKey: 'end_date'
            },
            {
              title: this.$t('bookRead.create_date'),
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

      /*  this.bookReadDTO = this.getCondition(this.$route.path, this.bookReadDTO)
        this.bookReadList = this.getCondition(this.$route.path, this.bookReadList)*/
        this.loadingBookReadList(this.bookReadList.current)

      },
      methods: {
        loadingBookReadList (current) {
          this.loading = true
          this.saveCondition(this.$route.path, Object.assign({}, this.bookReadDTO, this.bookReadList))
          getBookReadList({
            current: current || this.bookReadList.current,
            userName: this.bookReadDTO.userName,
            bookName: this.bookReadDTO.bookName,
            createDate: this.bookReadDTO.createDate,
            size: this.bookReadList.size,
            sortField: this.orderBy.sortField,
            sortOrder: this.orderBy.sortOrder
          }).then(res => {
            // console.log('bookReadList:', res)
            this.bookReadList = res.data
          }).finally(() => {
            this.loading = false
          })
        },
        /* 点击页码加载当前页的数据 */
        onChangePage (current) {
          this.bookReadList.current = current
          this.loadingBookReadList(current)
        },
        /* 切换每页显示记录数 */
        onShowSizeChange (current, size) {
          this.bookReadList.size = size
          this.loadingBookReadList(current)
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
          this.loadingBookReadList(1)
        },
        /* 重置查询条件 */
        handleToRemoveSearch () {
          this.bookReadDTO.userName = ''
          this.bookReadDTO.bookName = ''
          this.removeCondition(this.$router.path)
          this.loadingBookReadList(1)
        },
        // 删除
        handleDeleteBookRead (id) {
          const _this = this
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: this.$t('message.delete_confirm'),
            onOk () {
              _this.executeDeleteBookRead(id)
            }
          })
        },
        executeDeleteBookRead (id) {
          deleteBookRead(id).then(() => {
            this.$success({
              title: this.$t('message.delete_success')
            })
            this.loadingBookReadList(this.bookReadList.current)
          })
        },
        /* 创建对象 */
        handleToCreateBookRead () {
          this.$router.push({ path: '/wx/book/read/detail/create' })
        },
        /* 修改对象 */
        handleUpdateBookRead (record) {
          const _this = this
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: "确认归还",
            onOk () {
              _this.bookReadDTO.id=record.id
              _this.bookReadDTO.userId=record.userId
              _this.bookReadDTO.libraryId=record.libraryId
              _this.bookReadDTO.orderStatus = '1'

              updateBookRead(_this.bookReadDTO).then(res => {
                _this.$success({ title: _this.$t('message.success') })
                _this.$router.push({ path: '/wx/book/read/list' })
                _this.loadingBookReadList(1)

              }).finally(() => {
                _this.loading = false
              })
              return
            }
          })
          /*this.$router.push({ path: '/wx/book/read/detail/edit/' + id })*/
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-list.less');
</style>
