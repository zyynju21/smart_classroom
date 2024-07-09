<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <!-- 搜索栏 -->
    <search-bar @handleSearch="loadingNoticeAdviseList(1)" @handleReset="handleToRemoveSearch">
      <a-form-model ref="noticeAdviseDTO" :model="noticeAdviseDTO" :wrapper-col="{span:24}">
       <!-- <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="noticeAdviseDTO.id"
              :placeholder="$t('noticeAdvise.id')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>-->
        <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="noticeAdviseDTO.title"
              :placeholder="$t('noticeAdvise.title')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>
    <!--    <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="noticeAdviseDTO.noticeContent"
              :placeholder="$t('noticeAdvise.notice_content')"
              allow-clear
            />
          </a-form-model-item>
        </a-col>-->
       <!-- <a-col :xxl="3" :xl="3" :lg="3" :md="3" :sm="24">
          <a-form-model-item>
            <a-input
              v-model="noticeAdviseDTO.createDate"
              :placeholder="$t('noticeAdvise.create_date')"
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
          :data-source="noticeAdviseList.records"
          :columns="noticeAdviseColumns"
          :rowKey="record => record.id"
          :pagination="false"
          @change="handleTableChange"
        >
          <template slot="operation" slot-scope="text, record">
            <a href="javascript:;">
              <a-icon type="edit" :title="$t('button.edit')" @click="handleUpdateNoticeAdvise(record.id)" class="blue-green-text"/>
            </a>
            <a-divider type="vertical" />
            <a href="javascript:;">
              <a-icon type="delete" :title="$t('button.delete')" @click="handleDeleteNoticeAdvise(record.id)" class="blue-green-text"/>
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
          :pageConfig="noticeAdviseList"
        >
          <a-button type="primary" @click="handleToCreateNoticeAdvise">
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
    import { getNoticeAdviseList, deleteNoticeAdvise } from '../../api/wx/notice_advise.js'

    export default {
      components: {
        BreadCrumb,
        SearchBar,
        FooterPagination
      },
      data () {
        return {
          loading: false,
          noticeAdviseDTO: {
            id: undefined,
            title: undefined,
            noticeContent: undefined,
            createDate: undefined
          },
          noticeAdviseList: {
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
        noticeAdviseColumns () {
          return [
           /* {
              title: this.$t('noticeAdvise.id'),
              dataIndex: 'id',
              sorter: true,
              columnKey: 'id'
            },*/
            {
              title: this.$t('noticeAdvise.title'),
              dataIndex: 'title',
              sorter: true,
              columnKey: 'title'
            },
            {
              title: this.$t('noticeAdvise.notice_content'),
              dataIndex: 'noticeContent',
              sorter: true,
              columnKey: 'notice_content'
            },
            {
              title: this.$t('noticeAdvise.create_date'),
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
        this.noticeAdviseDTO = this.getCondition(this.$route.path, this.noticeAdviseDTO)
        this.noticeAdviseList = this.getCondition(this.$route.path, this.noticeAdviseList)
        this.loadingNoticeAdviseList(this.noticeAdviseList.current)
      },
      methods: {
        loadingNoticeAdviseList (current) {
          this.loading = true
          this.saveCondition(this.$route.path, Object.assign({}, this.noticeAdviseDTO, this.noticeAdviseList))
          getNoticeAdviseList({
            current: current || this.noticeAdviseList.current,
            id: this.noticeAdviseDTO.id,
            title: this.noticeAdviseDTO.title,
            noticeContent: this.noticeAdviseDTO.noticeContent,
            createDate: this.noticeAdviseDTO.createDate,
            size: this.noticeAdviseList.size,
            sortField: this.orderBy.sortField,
            sortOrder: this.orderBy.sortOrder
          }).then(res => {
            // console.log('noticeAdviseList:', res)
            this.noticeAdviseList = res.data
          }).finally(() => {
            this.loading = false
          })
        },
        /* 点击页码加载当前页的数据 */
        onChangePage (current) {
          this.noticeAdviseList.current = current
          this.loadingNoticeAdviseList(current)
        },
        /* 切换每页显示记录数 */
        onShowSizeChange (current, size) {
          this.noticeAdviseList.size = size
          this.loadingNoticeAdviseList(current)
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
          this.loadingNoticeAdviseList(1)
        },
        /* 重置查询条件 */
        handleToRemoveSearch () {
          this.noticeAdviseDTO.id = ''
          this.noticeAdviseDTO.title = ''
          this.noticeAdviseDTO.noticeContent = ''
          this.noticeAdviseDTO.createDate = ''
          this.removeCondition(this.$router.path)
          this.loadingNoticeAdviseList(1)
        },
        // 删除
        handleDeleteNoticeAdvise (id) {
          const _this = this
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: this.$t('message.delete_confirm'),
            onOk () {
              _this.executeDeleteNoticeAdvise(id)
            }
          })
        },
        executeDeleteNoticeAdvise (id) {
          deleteNoticeAdvise(id).then(() => {
            this.$success({
              title: this.$t('message.delete_success')
            })
            this.loadingNoticeAdviseList(this.noticeAdviseList.current)
          })
        },
        /* 创建对象 */
        handleToCreateNoticeAdvise () {
          this.$router.push({ path: '/wx/notice/advise/detail/create' })
        },
        /* 修改对象 */
        handleUpdateNoticeAdvise (id) {
          this.$router.push({ path: '/wx/notice/advise/detail/edit/' + id })
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-list.less');
</style>
