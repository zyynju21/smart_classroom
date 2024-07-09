<template>
  <div>
    <div v-if="chooseOrgan.orgType === 0">
      <a-input-search placeholder="请选择组织" @search="showModal" v-model="value" readOnly>
        <a-button slot="enterButton">
          选择组织
        </a-button>
      </a-input-search>
      <a-modal v-model="visible" title="组织选择对话框" :footer="null" width="60%">
        <search-bar @handleSearch="loadingSysOrganList(1)" @handleReset="handleToRemoveSearch">
          <a-form-model ref="sysOrganDTO" :model="sysOrganDTO" :wrapper-col="{span:24}">
            <a-col :xxl="6" :xl="6" :lg="6" :md="6" :sm="24">
              <a-form-model-item>
                <a-input v-model="sysOrganDTO.orgName" :placeholder="$t('sysOrgan.org_name')" allow-clear/>
              </a-form-model-item>
            </a-col>
            <a-col :xxl="6" :xl="6" :lg="6" :md="6" :sm="24">
              <a-form-model-item>
                <a-input v-model="sysOrganDTO.orgPerson" :placeholder="$t('sysOrgan.org_person')" allow-clear/>
              </a-form-model-item>
            </a-col>
            <a-col :xxl="6" :xl="6" :lg="6" :md="6" :sm="24">
              <a-form-model-item>
                <a-input v-model="sysOrganDTO.orgPhone" :placeholder="$t('sysOrgan.org_phone')" allow-clear/>
              </a-form-model-item>
            </a-col>
          </a-form-model>
        </search-bar>
        <a-row>
          <a-col>
            <a-table
              :loading="loading"
              :data-source="chooseOrgan.sysOrganList.records"
              :columns="organColumns"
              :rowKey="record => record.orgId"
              :pagination="false"
              @change="handleTableChange"
            >
              <template slot="operation" slot-scope="text, record">
                <a href="javascript:;">
                  <a-icon type="check" :title="$t('button.checked')" @click="handleOrganChoose(record.orgId,record.orgName)" class="blue-green-text"/>
                </a>
              </template>
            </a-table>
          </a-col>
        </a-row>
        <a-row>
          <a-col>
            <footer-pagination
              @onChangePage="onChangePage"
              @onShowSizeChange="onShowSizeChange"
              :pageConfig="chooseOrgan.sysOrganList"
            >
            </footer-pagination>
          </a-col>
        </a-row>
      </a-modal>
    </div>
    <div v-else>
      <a-input :placeholder="$t('sysOrgan.org_name')" v-model="value" readOnly/>
    </div>
  </div>
</template>

<script>
  import { getChooseOrganList } from '../../../api/sys/sys_organ.js'
  import SearchBar from '../top_search_bar/TopSearchBar'
  import FooterPagination from '../footer_pagination/FooterPagination.vue'
  export default {
    name: 'ChooseOrgan',
    components: {
      SearchBar,
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
        loading: false,
        sysOrganDTO: {
          orgName: '',
          orgPerson: '',
          orgPhone: ''
        },
        chooseOrgan: {
          orgType: undefined,
          orgId: undefined,
          orgName: undefined,
          sysOrganList: {
            total: 0,
            size: 10,
            current: 0,
            records: []
          }
        },
        orderBy: {
          // 排序列
          sortField: '',
          // 排序规则
          sortOrder: ''
        },
        select: {
          key: '',
          value: ''
        }
      }
    },
    computed: {
      organColumns () {
        return [
          {
            title: this.$t('sysOrgan.org_name'),
            dataIndex: 'orgName',
            sorter: true,
            columnKey: 'org_name',
            align: 'left'
          },
          {
            title: this.$t('sysOrgan.org_person'),
            dataIndex: 'orgPerson',
            sorter: true,
            columnKey: 'org_person',
            align: 'left'
          },
          {
            title: this.$t('sysOrgan.org_phone'),
            dataIndex: 'orgPhone',
            sorter: true,
            columnKey: 'org_phone',
            align: 'left'
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
      this.loadingSysOrganList()
    },
    methods: {
      loadingSysOrganList (current) {
        this.saveCondition(this.$route.path, Object.assign({}, this.sysOrganDTO, this.chooseOrgan.sysOrganList))
        getChooseOrganList({
          current: current || this.chooseOrgan.sysOrganList.current,
          size: this.chooseOrgan.sysOrganList.size,
          sortField: this.orderBy.sortField,
          sortOrder: this.orderBy.sortOrder,
          orgName: this.sysOrganDTO.orgName,
          orgPerson: this.sysOrganDTO.orgPerson,
          orgPhone: this.sysOrganDTO.orgPhone
        }).then(res => {
          // console.log(res)
          this.chooseOrgan = res.data
          if (this.chooseOrgan.orgType !== 0) {
            // console.log('chooseOrgan.orgType', this.chooseOrgan.orgType)
            this.select.key = this.chooseOrgan.orgId
            this.select.value = this.chooseOrgan.orgName
            this.$emit('input', this.chooseOrgan.orgName)
            this.$emit('selectOrgan', this.select)
          }
        })
      },
      showModal () {
        this.visible = true
      },
      /* 点击页码加载当前页的数据 */
      onChangePage (current) {
        this.chooseOrgan.sysOrganList.current = current
        this.loadingSysOrganList(current)
      },
      /* 切换每页显示记录数 */
      onShowSizeChange (current, size) {
        this.chooseOrgan.size = size
        this.loadingSysOrganList(current)
      },
      /* 表格根据字段排序 */
      handleTableChange (pagination, filters, sorter) {
        console.log(sorter)
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
        this.loadingSysOrganList(1)
      },
      /* 重置查询条件 */
      handleToRemoveSearch () {
        this.sysOrganDTO.orgName = ''
        this.sysOrganDTO.orgPerson = ''
        this.sysOrganDTO.orgPhone = ''
        this.removeCondition(this.$router.path)
        this.loadingSysOrganList(1)
      },
      handleOrganChoose (orgId, orgName) {
        this.select.key = orgId
        this.select.value = orgName
        this.$emit('input', orgName)
        this.$emit('selectOrgan', this.select)
        this.visible = false
      }
    }
  }
</script>

<style lang="less" scoped>
</style>
