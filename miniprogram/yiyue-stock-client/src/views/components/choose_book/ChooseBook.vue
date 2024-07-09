<template>
  <div>
    <a-input-search placeholder="请输入图书" @search="onSearch" readOnly v-model="value">
      <a-button slot="enterButton">
        选择图书
      </a-button>
    </a-input-search>
    <a-modal
      width="60%"
      title="选择图书"
      :visible="visible"
      :confirm-loading="confirmLoading"
      :footer="null"
      @cancel="handleCancel"
    >
      <!-- 表格 -->
      <a-row>
        <a-col>
          <a-table
            :loading="loading"
            :data-source="booksMangerList.records"
            :columns="booksMangerColumns"
            :rowKey="record => record.id"
            :pagination="false"
            @change="handleTableChange"
          >
            <template slot="bookImg" slot-scope="text, record">
              <img :src="'http://127.0.0.1:8080/'+record.bookImg" width="90" height="90">
            </template>
            <template slot="useState" slot-scope="text, record">
              {{record.useState==='0'?'可借':'已借'}}
            </template>
            <template slot="operation" slot-scope="text, record">
              <a href="javascript:;">
                <a-icon type="check" title="选择" @click="handleUpdateBooksManger(record)" class="blue-green-text"/>
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
            :pageConfig="booksMangerList"
          >
          </footer-pagination>
        </a-col>
      </a-row>

    </a-modal>

  </div>
</template>

<script>
  import FooterPagination from '@/views/components/footer_pagination/FooterPagination'
  import { getBooksMangerList} from '@/api/wx/books_manger.js'
  import { deleteBooksManger } from '@/api/wx/books_manger'
  export default {
    components: {
      FooterPagination
    },
    props: {
      value: {
        type: String,
        default: ''
      }
    },
    data () {
      return {
        visible: false,
        loading:false,
        confirmLoading: false,
        booksMangerDTO: {
          id: undefined,
          bookName: undefined,
          bookImg: undefined,
          bookText: undefined,
          useState: undefined,
          bookMark: undefined,
          createDate: undefined
        },
        booksMangerList: {
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
      booksMangerColumns () {
        return [
          {
            title: this.$t('booksManger.id'),
            dataIndex: 'id',
            sorter: true,
            columnKey: 'id'
          },
          {
            title: this.$t('booksManger.book_img'),
            dataIndex: 'bookImg',
            sorter: true,
            columnKey: 'book_img',
            scopedSlots: { customRender: 'bookImg' }
          },
          {
            title: this.$t('booksManger.book_name'),
            dataIndex: 'bookName',
            sorter: true,
            columnKey: 'book_name'
          },
          /*   {
               title: this.$t('booksManger.book_text'),
               dataIndex: 'bookText',
               sorter: true,
               columnKey: 'book_text'
             },*/
          {
            title: this.$t('booksManger.use_state'),
            dataIndex: 'useState',
            sorter: true,
            columnKey: 'use_state',
            scopedSlots: { customRender: 'useState' }
          },
          {
            title: this.$t('booksManger.book_mark'),
            dataIndex: 'bookMark',
            sorter: true,
            columnKey: 'book_mark'
          },
          {
            title: this.$t('booksManger.create_date'),
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
      this.booksMangerDTO = this.getCondition(this.$route.path, this.booksMangerDTO)
      this.booksMangerList = this.getCondition(this.$route.path, this.booksMangerList)
      this.loadingBooksMangerList(this.booksMangerList.current)
    },
    methods: {
      onSearch () {
        this.visible = true
      },
      handleCancel (e) {
        this.visible = false
      },
      loadingBooksMangerList (current) {
        this.loading = true
        this.saveCondition(this.$route.path, Object.assign({}, this.booksMangerDTO, this.booksMangerList))
        getBooksMangerList({
          current: current || this.booksMangerList.current,
          id: this.booksMangerDTO.id,
          bookName: this.booksMangerDTO.bookName,
          bookImg: this.booksMangerDTO.bookImg,
          bookText: this.booksMangerDTO.bookText,
          useState: this.booksMangerDTO.useState,
          bookMark: this.booksMangerDTO.bookMark,
          createDate: this.booksMangerDTO.createDate,
          size: this.booksMangerList.size,
          sortField: this.orderBy.sortField,
          sortOrder: this.orderBy.sortOrder
        }).then(res => {
          // console.log('booksMangerList:', res)
          this.booksMangerList = res.data
        }).finally(() => {
          this.loading = false
        })
      },
      /* 点击页码加载当前页的数据 */
      onChangePage (current) {
        this.booksMangerList.current = current
        this.loadingBooksMangerList(current)
      },
      /* 切换每页显示记录数 */
      onShowSizeChange (current, size) {
        this.booksMangerList.size = size
        this.loadingBooksMangerList(current)
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
        this.loadingBooksMangerList(1)
      },
      /* 重置查询条件 */
      handleToRemoveSearch () {
        this.booksMangerDTO.id = ''
        this.booksMangerDTO.bookName = ''
        this.booksMangerDTO.bookImg = ''
        this.booksMangerDTO.bookText = ''
        this.booksMangerDTO.useState = ''
        this.booksMangerDTO.bookMark = ''
        this.booksMangerDTO.createDate = ''
        this.removeCondition(this.$router.path)
        this.loadingBooksMangerList(1)
      },
      // 删除
      handleDeleteBooksManger (id) {
        const _this = this
        this.$confirm({
          okText: this.$t('button.confirm'),
          cancelText: this.$t('button.cancel'),
          title: this.$t('message.delete_confirm'),
          onOk () {
            _this.executeDeleteBooksManger(id)
          }
        })
      },
      executeDeleteBooksManger (id) {
        deleteBooksManger(id).then(() => {
          this.$success({
            title: this.$t('message.delete_success')
          })
          this.loadingBooksMangerList(this.booksMangerList.current)
        })
      },
      /* 创建对象 */
      handleToCreateBooksManger () {
        this.$router.push({ path: '/wx/books/manger/detail/create' })
      },
      /* 选择对象 */
      handleUpdateBooksManger (e) {
        if(e.useState==="1"){
          this.$message.error("该图书已借出")
          return
        }
        this.$emit("selectBookName",e)
        this.visible=false
      }
    }
  }

</script>

<style lang="less" scoped>
  @import url('~@/assets/less/qm-erp-list.less');
</style>